#!/bin/bash
seatunnel_home=/Users/muzimin/opt/apache-seatunnel-2.3.4
work_home=$(cd $(dirname $0);cd ../;pwd)

sh ${seatunnel_home}/bin/start-seatunnel-flink-13-connector-v2.sh --config ${work_home}/config/$1