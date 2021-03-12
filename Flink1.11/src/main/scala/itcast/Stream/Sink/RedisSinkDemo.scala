package itcast.Stream.Sink

import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.api.scala._
import org.apache.flink.streaming.connectors.redis.RedisSink
import org.apache.flink.streaming.connectors.redis.common.config.FlinkJedisPoolConfig
import org.apache.flink.streaming.connectors.redis.common.mapper.{RedisCommand, RedisCommandDescription, RedisMapper}

/**
 * @author : 李煌民
 * @date : 2020-11-06 15:05
 *       ${description}
 **/
object RedisSinkDemo {
  def main(args: Array[String]): Unit = {
    val env = StreamExecutionEnvironment.getExecutionEnvironment
    val sourceDS = env.fromCollection(List("aaa", "bbb", "ccc"))
    //redisSink的构造：1、需要redis配置文件  2、redismapper对象
    //配置
    val config = new FlinkJedisPoolConfig.Builder().setHost("hadoop01").setPort(6379).build()
    //Mapper对象
    val redisMapper = new RedisMapper[String] {
      //获取命令描述器，确定数据结构，目前我们使用hash结构
      override def getCommandDescription: RedisCommandDescription = {
        //指定使用hset命令，并提供hash结构的第一个key
        new RedisCommandDescription(RedisCommand.HSET, "REDISSINK")
      }

      //指定Key值
      override def getKeyFromData(data: String): String = {
        data + "key"
      }

      //指定Value值
      override def getValueFromData(data: String): String = {
        data + "value"
      }
    }
    sourceDS.addSink(new RedisSink[String](config, redisMapper))

    env.execute()
  }
}
