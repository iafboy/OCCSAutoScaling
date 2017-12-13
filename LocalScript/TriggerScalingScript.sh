#! /bin/sh
basepath=$(cd `dirname $0`; pwd)
source ${basepath}/DeploymentCfg.etc
HOSTNAME=`hostname`
echo >${basepath}/${HOSTNAME}.json
returnMsg=`docker stats --no-stream|awk '{if (NR>2){print $1"-", substr($2,0,4)"-" , substr($8,0,4)";" }}'|tr "\n" " " ;echo|tr "\t" " " ;echo`
echo "{ \"host_name\":\"${HOSTNAME}\",\"statusMsg\":\"${returnMsg}\"}" > ${WORKSPACE}/${HOSTNAME}.json
curl -d "@${basepath}/${HOSTNAME}.json" -H "Content-Type: application/json;charset=UTF-8" -X POST http://${SERVERADDRESS}:${SERVERPORT}/InstanceStatus/