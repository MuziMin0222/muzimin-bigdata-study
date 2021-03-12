package com.lhm.MergeOnRead

import org.apache.hudi.DataSourceWriteOptions
import org.apache.hudi.config.{HoodieIndexConfig, HoodieWriteConfig}
import org.apache.hudi.index.HoodieIndex
import org.apache.spark.sql.{SaveMode, SparkSession}

/**
 * @author : 李煌民
 * @date : 2021-02-19 11:34
 *       ${description}
 **/
object SyncHiveDemo {
  def main(args: Array[String]): Unit = {

  }

  def hiveSyncMergeOnRead() = {
    val spark = SparkSession
      .builder
      .appName("delta hiveSync")
      .config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .master("local[3]")
      .getOrCreate()

    val upsertData = spark
      .read
      .parquet("/tmp/1563959377699.parquet")

    upsertData.write.format("org.apache.hudi")
      // 配置读时合并
      .option(DataSourceWriteOptions.TABLE_TYPE_OPT_KEY, DataSourceWriteOptions.MOR_TABLE_TYPE_OPT_VAL)
      // 设置主键列名
      .option(DataSourceWriteOptions.RECORDKEY_FIELD_OPT_KEY, "rowkey")
      // 设置数据更新时间的列名
      .option(DataSourceWriteOptions.PRECOMBINE_FIELD_OPT_KEY, "lastupdatedttm")
      // 分区列设置
      .option(DataSourceWriteOptions.PARTITIONPATH_FIELD_OPT_KEY, "dt")
      // 设置要同步的hive库名
      .option(DataSourceWriteOptions.HIVE_DATABASE_OPT_KEY, "hj_repl")
      // 设置要同步的hive表名
      .option(DataSourceWriteOptions.HIVE_TABLE_OPT_KEY, "test_partition_merge_on_read")
      // 设置数据集注册并同步到hive
      .option(DataSourceWriteOptions.HIVE_SYNC_ENABLED_OPT_KEY, "true")
      // 设置当分区变更时，当前数据的分区目录是否变更
      .option(HoodieIndexConfig.BLOOM_INDEX_UPDATE_PARTITION_PATH, "true")
      // 设置要同步的分区列名
      .option(DataSourceWriteOptions.HIVE_PARTITION_FIELDS_OPT_KEY, "dt")
      // 设置jdbc 连接同步
      .option(DataSourceWriteOptions.HIVE_URL_OPT_KEY, "jdbc:hive2://localhost:10000")
      // hudi表名称设置
      .option(HoodieWriteConfig.TABLE_NAME, "test_partition_merge_on_read")
      // 用于将分区字段值提取到Hive分区列中的类,这里我选择使用当前分区的值同步
      .option(DataSourceWriteOptions.HIVE_PARTITION_EXTRACTOR_CLASS_OPT_KEY, "org.apache.hudi.hive.MultiPartKeysValueExtractor")
      // 设置索引类型目前有HBASE,INMEMORY,BLOOM,GLOBAL_BLOOM 四种索引 为了保证分区变更后能找到必须设置全局GLOBAL_BLOOM
      .option(HoodieIndexConfig.INDEX_TYPE_PROP, HoodieIndex.IndexType.GLOBAL_BLOOM.name())
      // 并行度参数设置
      .option("hoodie.insert.shuffle.parallelism", "2")
      .option("hoodie.upsert.shuffle.parallelism", "2")
      .mode(SaveMode.Append)
      .save("/tmp/hudi_merge_on_read");
  }
}
