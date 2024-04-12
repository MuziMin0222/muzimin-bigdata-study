#!/bin/bash
seatunnel_home=/Users/muzimin/opt/apache-seatunnel-2.3.4
work_home=$(cd $(dirname $0);cd ../;pwd)
dateTmp=$2
current_date=`date +%Y%m%d`
dt=${dateTmp:=$current_date}

sh ${seatunnel_home}/bin/start-seatunnel-spark-3-connector-v2.sh --config ${work_home}/config/$1 -m local -e client -i dt=${dt}
