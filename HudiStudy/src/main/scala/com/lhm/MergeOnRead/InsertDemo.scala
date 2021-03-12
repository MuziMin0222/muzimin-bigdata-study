package com.lhm.MergeOnRead

import org.apache.hudi.DataSourceWriteOptions
import org.apache.hudi.config.{HoodieIndexConfig, HoodieWriteConfig}
import org.apache.hudi.index.HoodieIndex
import org.apache.spark.sql.{SaveMode, SparkSession}

/**
 * @author : 李煌民
 * @date : 2021-02-19 11:30
 *       ${description}
 **/
object InsertDemo {
  def main(args: Array[String]): Unit = {

  }

  def insert() = {
    val spark = SparkSession
      .builder
      .appName("hudi insert")
      .config("spark.serializer", "org.apache.spark.serializer.KryoSerializer")
      .master("local[3]")
      .getOrCreate()

    // 读取文本文件转换为df
    val insertData = spark
      .read
      .parquet("/tmp/1563959377699.parquet")

    insertData
      .write
      .format("org.apache.hudi")
      //这是merge on read的主要设置
      .option(DataSourceWriteOptions.TABLE_TYPE_OPT_KEY, DataSourceWriteOptions.MOR_TABLE_TYPE_OPT_VAL)
      // 设置主键列名
      .option(DataSourceWriteOptions.RECORDKEY_FIELD_OPT_KEY, "rowkey")
      // 设置数据更新时间的列名
      .option(DataSourceWriteOptions.PRECOMBINE_FIELD_OPT_KEY, "lastupdatedttm")
      // 设置分区列
      .option(DataSourceWriteOptions.PARTITIONPATH_FIELD_OPT_KEY, "dt")
      // 设置当分区变更时，当前数据的分区目录是否变更
      .option(HoodieIndexConfig.BLOOM_INDEX_UPDATE_PARTITION_PATH, "true")
      // 设置索引类型目前有HBASE,INMEMORY,BLOOM,GLOBAL_BLOOM 四种索引 为了保证分区变更后能找到必须设置全局GLOBAL_BLOOM
      .option(HoodieIndexConfig.INDEX_TYPE_PROP, HoodieIndex.IndexType.GLOBAL_BLOOM.name())
      // 并行度参数设置
      .option("hoodie.insert.shuffle.parallelism", "2")
      .option("hoodie.upsert.shuffle.parallelism", "2")
      .option(HoodieWriteConfig.TABLE_NAME, "test_partition_merge_on_read")
      .mode(SaveMode.Overwrite)
      .save("/tmp/hudi_merge_on_read")
  }
}
