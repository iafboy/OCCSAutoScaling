#!/bin/bash
basepath=$(cd `dirname $0`; pwd)
while true;do
 sh /u01/data/workspace/triggerScaling/TriggerScalingScript.sh
 sleep 3;
done

exit 0