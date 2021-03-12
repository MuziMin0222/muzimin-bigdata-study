package itcast.Stream.CheckPoint

import java.sql.{Connection, DriverManager}
import java.util.Properties

import org.apache.flink.api.common.ExecutionConfig
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.api.common.typeutils.base.VoidSerializer
import org.apache.flink.api.java.typeutils.runtime.kryo.KryoSerializer
import org.apache.flink.runtime.state.filesystem.FsStateBackend
import org.apache.flink.streaming.api.CheckpointingMode
import org.apache.flink.streaming.api.environment.CheckpointConfig
import org.apache.flink.streaming.api.functions.sink.{SinkFunction, TwoPhaseCommitSinkFunction}
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.flink.api.scala._

/**
 * @author : 李煌民
 * @date : 2020-11-20 10:43
 *       实现kafka到mysql实现端到端一致性语义,两阶段要结合CK才可以实现
 *       从kafka中消费数据然后写入MySQL，但是要求使用两阶段提交协议保证一致性语义，自定义mysqlsink并且实现两阶段提交的相关方法
 **/
object KafkaToMysqlTwoPhaseDemp {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment

    //ck设置
    env.setStateBackend(new FsStateBackend("file:///D://ckMysql"))
    env.enableCheckpointing(2000)
    env.getCheckpointConfig.setCheckpointingMode(CheckpointingMode.EXACTLY_ONCE)
    env.getCheckpointConfig.setMinPauseBetweenCheckpoints(500)
    env.getCheckpointConfig.setCheckpointTimeout(60000)
    env.getCheckpointConfig.setFailOnCheckpointingErrors(false)
    env.getCheckpointConfig.enableExternalizedCheckpoints(CheckpointConfig.ExternalizedCheckpointCleanup.RETAIN_ON_CANCELLATION)
    env.getCheckpointConfig.setMaxConcurrentCheckpoints(1)

    val KafkaTopic = "ckdemo"
    val prop = new Properties()
    prop.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "hadoop01:9092,hadoop02:9092,hadoop03:9092")
    //设置消费者的隔离级别
    prop.setProperty(ConsumerConfig.ISOLATION_LEVEL_CONFIG, "read_committed")
    //设置程序消费kafka的偏移量提交策略
    prop.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false")

    val kafkaConsumer = new FlinkKafkaConsumer011[String](KafkaTopic, new SimpleStringSchema(), prop)
    //设置kafka消费偏移量是基于ck成功时提交
    kafkaConsumer.setCommitOffsetsOnCheckpoints(true)

    val sourceDS = env.addSource(kafkaConsumer)
    val wcDS = sourceDS.flatMap(_.split(" ")).map(_ -> 1).keyBy(0).sum(1)

    sourceDS.print()

    wcDS.addSink(new MysqlTwoPhaseCommit)

    env.execute()
  }
}

//自定义sinkFunction 实现TwoPhaseCommitSinkFunction  in:输入的数据情况，txn：transaction对象(自定义),context:void
class MysqlTwoPhaseCommit extends TwoPhaseCommitSinkFunction[(String, Int), MysqlConnectionSate, Void](
  //自定义对象的序列化类型
  new KryoSerializer[MysqlConnectionSate](classOf[MysqlConnectionSate], new ExecutionConfig),
  //上下文对象序列化类型
  VoidSerializer.INSTANCE
) {
  //开启事务，pre-commit开始阶段
  override def beginTransaction(): MysqlConnectionSate = {
    val conn: Connection = DriverManager.getConnection("jdbc:mysql://hadoop01:3306/flink", "root", "Lz19970222!")
    new MysqlConnectionSate(conn)
  }

  //执行Mysql插入数据的操作
  override def invoke(transaction: MysqlConnectionSate, value: (String, Int), context: SinkFunction.Context[_]): Unit = {
    //读取数据并写入到Mysql中
    val conn = transaction.conn
    //sql语句    word是主键，可以根据主键更新，duplicate key update：主键存在则更新字段的值，否则插入新的数据
    val sql = "insert into wordcount(name,count) value(?,?) on duplicate key update count = ?"
    val ps = conn.prepareStatement(sql)
    //赋值
    ps.setString(1, value._1)
    ps.setInt(2, value._2)
    ps.setInt(3, value._2)
    //关闭默认自动提交,在mysql中  是默认自动提交事务的，改为手动提交，ck成功时flink自动提交
    conn.setAutoCommit(false)
    //执行
    ps.executeUpdate()
    ps.close()
  }

  //预提交
  override def preCommit(transaction: MysqlConnectionSate): Unit = {
    //在invoke中的关闭自动提交就已经完成了该方法
  }

  //真正提交
  override def commit(transaction: MysqlConnectionSate): Unit = {
    val conn = transaction.conn
    conn.commit()
    conn.close()
  }

  //回滚数据
  override def abort(transaction: MysqlConnectionSate): Unit = {
    val conn = transaction.conn
    conn.rollback()
    conn.close()
  }
}

//要求：该connect无需被序列化   加上transient关键字可以保证该属性不会随着对象序列化
class MysqlConnectionSate(@transient val conn: Connection) {}
