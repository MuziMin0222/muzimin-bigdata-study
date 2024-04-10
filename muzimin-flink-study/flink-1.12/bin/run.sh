#!/bin/bash
export FLINK_HOME=/opt/software/flink-1.12.1
export HADOOP_CONF_DIR=/opt/software/hadoop-2.7.2/etc/hadoop/
export HADOOP_CLASSPATH=`hadoop classpath`
className=$1

jobName=${className##*.}_`date +%Y%m%d_%H:%M:%S`
echo $jobName

$FLINK_HOME/bin/flink run \
  -m yarn-cluster\
  -yjm 1024m \
  -ytm 1024m \
  -p 4 \
  -ynm $jobName \
  -ys 8 \
  -c $1 \
  ./Flink1.12-1.0-SNAPSHOT.jar