#!/bin/bash
export FLINK_HOME=/opt/link/flink
export HADOOP_CONF_DIR=/opt/link/hadoop/etc/hadoop
export HADOOP_CLASSPATH=`hadoop classpath`
/opt/link/flink/bin/flink run \
-m yarn-cluster \
-ynm 2 \
-yjm 1024 \
-ytm 1024 \
-c itcast.batch.WordCountDemo \
/root/job/Flink-1.0-SNAPSHOT.jar