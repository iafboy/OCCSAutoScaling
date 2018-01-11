# Oracle Container Cloud Service的一种自动扩展方案
## 原理
1.通过脚本定期扫描container状态，并上传至扩展控制服务器
2.扩展控制服务器根据扩展规则，确定是否将docker container数量增加或者减少
## REST命令
curl command
curl -k "https://140.86.1.119/api/auth" -d '{"username":"admin","password":"Welcome1"}'

{
  "id": 100,
  "first_name": "",
  "last_name": "",
  "username": "admin",
  "token": "e96e132dbdf2c5205124f726a080148c4e45feac47957033d29a3c34e92ed992",
  "type": 0,
  "role": 0,
  "group": "",
  "last_password_reset_date": "0001-01-01T00:00:00Z"
}


curl -k -H "Authorization: Session e96e132dbdf2c5205124f726a080148c4e45feac47957033d29a3c34e92ed992" "https://140.86.1.119/api/token"
{
  "token": "0829416b6aedfdd9aa50f5eff0e58e8faa4768c4a507bbc04a58b039fd61dc19"
}


curl -k \
 -X PUT -H "Authorization: Bearer 0829416b6aedfdd9aa50f5eff0e58e8faa4768c4a507bbc04a58b039fd61dc19" \
 "https://140.86.1.119/api/v2/deployments/Redis-20170923-175443" \
  -d @scale_deployment.json



get template file
http://localhost:8080/occsscalingctr-1.0/GetDeployFileTemplate/MongoDB-20171004-234712
update scaling
http://localhost:8080/occsscalingctr-1.0/UpdateDeployScaling/MongoDB-20171004-234712


JSON:

{
  "deployment": {
    "deployment_id": "cncustompoc-20171011-151456",
    "placement": {
      "pool_id": "default"
    },
    "created_by": "admin",
    "created_on": "2017-10-11T07:15:00Z",
    "stack": {
      "service_id": "cncustompoc",
      "service_name": "cncustompoc",
      "subtype": "stack",
      "content": "version: 2\nservices:\n  cncustom-rabbitmq:\n    image: 'rabbitmq:3-management'\n    environment:\n      - CONFIG_SERVICE_PASSWORD=oracle\n      - NOTIFICATIO
N_SERVICE_PASSWORD=oracle\n      - STATISTICS_SERVICE_PASSWORD=oracle\n      - ACCOUNT_SERVICE_PASSWORD=oracle\n      - MONGODB_PASSWORD=oracle\n      - 'occs:availability=per-pool'\n      - 'occs:s
cheduler=random'\n    ports:\n      - '15672:15672/tcp'\n    container_name: rabbitmq\n    hostname: rabbitmq\n  cncustom-config:\n    image: 'cncustompoc-config:latest'\n    environment:\n      - C
ONFIG_SERVICE_PASSWORD=oracle\n      - NOTIFICATION_SERVICE_PASSWORD=oracle\n      - STATISTICS_SERVICE_PASSWORD=oracle\n      - ACCOUNT_SERVICE_PASSWORD=oracle\n      - MONGODB_PASSWORD=oracle\n
   - 'occs:availability=per-pool'\n      - 'occs:scheduler=random'\n    ports:\n      - '8888:8888/tcp'\n    container_name: config\n    hostname: config\n  zipkin-server:\n    image: 'cncustompoc-z
ipkin-server:latest'\n    environment:\n      - CONFIG_SERVICE_PASSWORD=oracle\n      - NOTIFICATION_SERVICE_PASSWORD=oracle\n      - STATISTICS_SERVICE_PASSWORD=oracle\n      - ACCOUNT_SERVICE_PASS
WORD=oracle\n      - MONGODB_PASSWORD=oracle\n      - 'occs:availability=per-pool'\n      - 'occs:scheduler=random'\n    ports:\n      - '9411:9411/tcp'\n    links:\n      - 'cncustom-config:cncusto
m-config'\n    container_name: zipkin-server\n    hostname: zipkin-server\n  cncustom-registry:\n    image: 'cncustompoc-registry:latest'\n    environment:\n      - CONFIG_SERVICE_PASSWORD=oracle\n
     - NOTIFICATION_SERVICE_PASSWORD=oracle\n      - STATISTICS_SERVICE_PASSWORD=oracle\n      - ACCOUNT_SERVICE_PASSWORD=oracle\n      - MONGODB_PASSWORD=oracle\n      - 'occs:availability=per-pool
'\n      - 'occs:scheduler=random'\n    container_name: registry\n    hostname: registry\n    ports:\n      - '8761:8761/tcp'\n    links:\n      - 'cncustom-config:cncustom-config'\n      - 'cncusto
m-rabbitmq:cncustom-rabbitmq'\n  auth-service:\n    image: 'cncustompoc-auth-service:latest'\n    environment:\n      - CONFIG_SERVICE_PASSWORD=oracle\n      - NOTIFICATION_SERVICE_PASSWORD=oracle\n
      - STATISTICS_SERVICE_PASSWORD=oracle\n      - ACCOUNT_SERVICE_PASSWORD=oracle\n      - MONGODB_PASSWORD=oracle\n      - 'occs:availability=per-pool'\n      - 'occs:scheduler=random'\n    ports
:\n      - '5000:5000/tcp'\n    container_name: auth-service\n    hostname: auth-service\n    links:\n      - 'cncustom-registry:cncustom-registry'\n      - 'auth-mongodb:auth-mongodb'\n      - 'cnc
ustom-config:cncustom-config'\n      - 'cncustom-rabbitmq:cncustom-rabbitmq'\n  auth-mongodb:\n    image: 'cncustompoc_auth-mongodb:latest'\n    environment:\n      - CONFIG_SERVICE_PASSWORD=oracle\
n      - NOTIFICATION_SERVICE_PASSWORD=oracle\n      - STATISTICS_SERVICE_PASSWORD=oracle\n      - ACCOUNT_SERVICE_PASSWORD=oracle\n      - MONGODB_PASSWORD=oracle\n      - 'occs:availability=per-po
ol'\n      - 'occs:scheduler=random'\n    ports:\n      - '25000:27017/tcp'\n    container_name: auth-mongodb\n    hostname: auth-mongodb\n    command: /init.sh\n  gateway:\n    image: 'cncustompoc-
gateway:latest'\n    environment:\n      - CONFIG_SERVICE_PASSWORD=oracle\n      - NOTIFICATION_SERVICE_PASSWORD=oracle\n      - STATISTICS_SERVICE_PASSWORD=oracle\n      - ACCOUNT_SERVICE_PASSWORD=
oracle\n      - MONGODB_PASSWORD=oracle\n      - 'occs:availability=per-pool'\n      - 'occs:scheduler=random'\n    ports:\n      - '80:4000/tcp'\n    container_name: gateway\n    hostname: gateway\
n    links:\n      - 'cncustom-config:cncustom-config'\n      - 'cncustom-registry:cncustom-registry'\n      - 'cncustom-rabbitmq:cncustom-rabbitmq'\n      - 'zipkin-server:zipkin-server'\n      - '
auth-service:auth-service'\n  account-service:\n    image: 'cncustompoc-account-service:latest'\n    environment:\n      - CONFIG_SERVICE_PASSWORD=oracle\n      - NOTIFICATION_SERVICE_PASSWORD=oracl
e\n      - STATISTICS_SERVICE_PASSWORD=oracle\n      - ACCOUNT_SERVICE_PASSWORD=oracle\n      - MONGODB_PASSWORD=oracle\n      - 'occs:availability=per-pool'\n      - 'occs:scheduler=random'\n    po
rts:\n      - '6000:6000/tcp'\n    container_name: account-service\n    hostname: account-service\n    links:\n      - 'auth-service:auth-service'\n      - 'cncustom-registry:cncustom-registry'\n
   - 'cncustom-config:cncustom-config'\n      - 'gateway:gateway'\n      - 'account-mongodb:account-mongodb'\n      - 'cncustom-rabbitmq:cncustom-rabbitmq'\n  account-mongodb:\n    image: 'cncustomp
oc_account-mongodb:latest'\n    environment:\n      - CONFIG_SERVICE_PASSWORD=oracle\n      - NOTIFICATION_SERVICE_PASSWORD=oracle\n      - STATISTICS_SERVICE_PASSWORD=oracle\n      - ACCOUNT_SERVIC
E_PASSWORD=oracle\n      - MONGODB_PASSWORD=oracle\n      - 'occs:availability=per-pool'\n      - 'occs:scheduler=random'\n    ports:\n      - '26000:27017/tcp'\n    container_name: account-mongodb\
n    hostname: account-mongodb\n    command: /init.sh\n  statistics-service:\n    image: 'cncustompoc-statistics-service:latest'\n    environment:\n      - CONFIG_SERVICE_PASSWORD=oracle\n      - NO
TIFICATION_SERVICE_PASSWORD=oracle\n      - STATISTICS_SERVICE_PASSWORD=oracle\n      - ACCOUNT_SERVICE_PASSWORD=oracle\n      - MONGODB_PASSWORD=oracle\n      - 'occs:availability=per-pool'\n
- 'occs:scheduler=random'\n    ports:\n      - '7000:7000/tcp'\n    container_name: statistics-service\n    hostname: statistics-service\n    links:\n      - 'auth-service:auth-service'\n      - 'cn
custom-registry:cncustom-registry'\n      - 'cncustom-config:cncustom-config'\n      - 'gateway:gateway'\n      - 'statistics-mongodb:statistics-mongodb'\n      - 'cncustom-rabbitmq:cncustom-rabbitm
q'\n  statistics-mongodb:\n    image: 'cncustompoc_statistics-mongodb:latest'\n    environment:\n      - CONFIG_SERVICE_PASSWORD=oracle\n      - NOTIFICATION_SERVICE_PASSWORD=oracle\n      - STATIST
ICS_SERVICE_PASSWORD=oracle\n      - ACCOUNT_SERVICE_PASSWORD=oracle\n      - MONGODB_PASSWORD=oracle\n      - 'occs:availability=per-pool'\n      - 'occs:scheduler=random'\n    ports:\n      - '270
00:27017/tcp'\n    container_name: statistics-mongodb\n    hostname: statistics-mongodb\n    command: /init.sh\n  notification-service:\n    image: 'cncustompoc-notification-service:latest'\n    env
ironment:\n      - CONFIG_SERVICE_PASSWORD=oracle\n      - NOTIFICATION_SERVICE_PASSWORD=oracle\n      - STATISTICS_SERVICE_PASSWORD=oracle\n      - ACCOUNT_SERVICE_PASSWORD=oracle\n      - MONGODB_
PASSWORD=oracle\n      - 'occs:availability=per-pool'\n      - 'occs:scheduler=random'\n    ports:\n      - '8000:8000/tcp'\n    container_name: notification-service\n    hostname: notification-serv
ice\n    links:\n      - 'auth-service:auth-service'\n      - 'cncustom-registry:cncustom-registry'\n      - 'cncustom-config:cncustom-config'\n      - 'gateway:gateway'\n      - 'cncustom-rabbitmq:
cncustom-rabbitmq'\n  notification-mongodb:\n    image: 'cncustompoc_notification-mongodb:latest'\n    environment:\n      - CONFIG_SERVICE_PASSWORD=oracle\n      - NOTIFICATION_SERVICE_PASSWORD=ora
cle\n      - STATISTICS_SERVICE_PASSWORD=oracle\n      - ACCOUNT_SERVICE_PASSWORD=oracle\n      - MONGODB_PASSWORD=oracle\n      - 'occs:availability=per-pool'\n      - 'occs:scheduler=random'\n
ports:\n      - '28000:27017/tcp'\n    container_name: notification-mongodb\n    hostname: notification-mongodb\n    command: /init.sh\n  monitoring:\n    image: 'cncustompoc-monitoring:latest'\n
 environment:\n      - CONFIG_SERVICE_PASSWORD=oracle\n      - NOTIFICATION_SERVICE_PASSWORD=oracle\n      - STATISTICS_SERVICE_PASSWORD=oracle\n      - ACCOUNT_SERVICE_PASSWORD=oracle\n      - MONG
ODB_PASSWORD=oracle\n      - 'occs:availability=per-pool'\n      - 'occs:scheduler=random'\n    ports:\n      - '9000:8080/tcp'\n      - '8989:8989/tcp'\n    container_name: monitoring\n    hostname
: monitoring\n    links:\n      - 'auth-service:auth-service'\n      - 'cncustom-registry:cncustom-registry'\n      - 'cncustom-config:cncustom-config'\n      - 'gateway:gateway'\n      - 'cncustom-
rabbitmq:cncustom-rabbitmq'\n  declaration-service:\n    image: 'cncustompoc-declaration-service:latest'\n    environment:\n      - CONFIG_SERVICE_PASSWORD=oracle\n      - NOTIFICATION_SERVICE_PASSW
ORD=oracle\n      - STATISTICS_SERVICE_PASSWORD=oracle\n      - ACCOUNT_SERVICE_PASSWORD=oracle\n      - MONGODB_PASSWORD=oracle\n      - 'occs:availability=per-pool'\n      - 'occs:scheduler=random
'\n    ports:\n      - '6001:6001/tcp'\n    container_name: declaration-service\n    hostname: declaration-service\n    links:\n      - 'auth-service:auth-service'\n      - 'cncustom-registry:cncust
om-registry'\n      - 'cncustom-config:cncustom-config'\n      - 'gateway:gateway'\n      - 'zipkin-server:zipkin-server'\n      - 'cncustom-rabbitmq:cncustom-rabbitmq'\n      - 'monitoring:monitori
ng'\n  goodsinfo-service:\n    image: 'cncustompoc-goodsinfo-service:latest'\n    environment:\n      - CONFIG_SERVICE_PASSWORD=oracle\n      - NOTIFICATION_SERVICE_PASSWORD=oracle\n      - STATISTI
CS_SERVICE_PASSWORD=oracle\n      - ACCOUNT_SERVICE_PASSWORD=oracle\n      - MONGODB_PASSWORD=oracle\n      - 'occs:availability=per-pool'\n      - 'occs:scheduler=random'\n    ports:\n      - '6002
:6002/tcp'\n    container_name: goodsinfo-service\n    hostname: goodsinfo-service\n    links:\n      - 'auth-service:auth-service'\n      - 'cncustom-registry:cncustom-registry'\n      - 'cncustom-
config:cncustom-config'\n      - 'gateway:gateway'\n      - 'zipkin-server:zipkin-server'\n      - 'cncustom-rabbitmq:cncustom-rabbitmq'\n      - 'monitoring:monitoring'\n  license-service:\n    ima
ge: 'cncustompoc-license-service:latest'\n    environment:\n      - CONFIG_SERVICE_PASSWORD=oracle\n      - NOTIFICATION_SERVICE_PASSWORD=oracle\n      - STATISTICS_SERVICE_PASSWORD=oracle\n      -
ACCOUNT_SERVICE_PASSWORD=oracle\n      - MONGODB_PASSWORD=oracle\n      - 'occs:availability=per-pool'\n      - 'occs:scheduler=random'\n    ports:\n      - '6003:6003/tcp'\n    container_name: lice
nse-service\n    hostname: license-service\n    links:\n      - 'auth-service:auth-service'\n      - 'cncustom-registry:cncustom-registry'\n      - 'cncustom-config:cncustom-config'\n      - 'gatewa
y:gateway'\n      - 'zipkin-server:zipkin-server'\n      - 'cncustom-rabbitmq:cncustom-rabbitmq'\n      - 'monitoring:monitoring'\n  importgoodsfreetax-service:\n    image: 'cncustompoc-importgoodsf
reetax-service:latest'\n    environment:\n      - CONFIG_SERVICE_PASSWORD=oracle\n      - NOTIFICATION_SERVICE_PASSWORD=oracle\n      - STATISTICS_SERVICE_PASSWORD=oracle\n      - ACCOUNT_SERVICE_PA
SSWORD=oracle\n      - MONGODB_PASSWORD=oracle\n      - 'occs:availability=per-pool'\n      - 'occs:scheduler=random'\n    ports:\n      - '6004:6004/tcp'\n    container_name: importgoodsfreetax-ser
vice\n    hostname: importgoodsfreetax-service\n    links:\n      - 'auth-service:auth-service'\n      - 'cncustom-registry:cncustom-registry'\n      - 'cncustom-config:cncustom-config'\n      - 'ga
teway:gateway'\n      - 'zipkin-server:zipkin-server'\n      - 'cncustom-rabbitmq:cncustom-rabbitmq'\n      - 'monitoring:monitoring'\n  manifestinfo-service:\n    image: 'cncustompoc-manifestinfo-s
ervice:latest'\n    environment:\n      - CONFIG_SERVICE_PASSWORD=oracle\n      - NOTIFICATION_SERVICE_PASSWORD=oracle\n      - STATISTICS_SERVICE_PASSWORD=oracle\n      - ACCOUNT_SERVICE_PASSWORD=o
racle\n      - MONGODB_PASSWORD=oracle\n      - 'occs:availability=per-pool'\n      - 'occs:scheduler=random'\n    ports:\n      - '6005:6005/tcp'\n    container_name: manifestinfo-service\n    host
name: manifestinfo-service\n    links:\n      - 'auth-service:auth-service'\n      - 'cncustom-registry:cncustom-registry'\n      - 'cncustom-config:cncustom-config'\n      - 'gateway:gateway'\n
  - 'zipkin-server:zipkin-server'\n      - 'cncustom-rabbitmq:cncustom-rabbitmq'\n      - 'monitoring:monitoring'\n  cottonquotas-service:\n    image: 'cncustompoc-cottonquotas-service:latest'\n
environment:\n      - CONFIG_SERVICE_PASSWORD=oracle\n      - NOTIFICATION_SERVICE_PASSWORD=oracle\n      - STATISTICS_SERVICE_PASSWORD=oracle\n      - ACCOUNT_SERVICE_PASSWORD=oracle\n      - MONGO
DB_PASSWORD=oracle\n      - 'occs:availability=per-pool'\n      - 'occs:scheduler=random'\n    ports:\n      - '6006:6006/tcp'\n    container_name: cottonquotas-service\n    hostname: cottonquotas-s
ervice\n    links:\n      - 'auth-service:auth-service'\n      - 'cncustom-registry:cncustom-registry'\n      - 'cncustom-config:cncustom-config'\n      - 'gateway:gateway'\n      - 'zipkin-server:z
ipkin-server'\n      - 'cncustom-rabbitmq:cncustom-rabbitmq'\n      - 'monitoring:monitoring'\n"
    },
    "creation_info_generation": 14,
    "deployment_name": "cncustompoc",
    "quantities": {
      "account-mongodb": 1,
      "account-service": 1,
      "auth-mongodb": 1,
      "auth-service": 1,
      "cncustom-config": 1,
      "cncustom-rabbitmq": 1,
      "cncustom-registry": 1,
      "cottonquotas-service": 1,
      "declaration-service": 1,
      "gateway": 1,
      "goodsinfo-service": 1,
      "importgoodsfreetax-service": 1,
      "license-service": 1,
      "manifestinfo-service": 1,
      "monitoring": 1,
      "notification-mongodb": 1,
      "notification-service": 1,
      "statistics-mongodb": 1,
      "statistics-service": 1,
      "zipkin-server": 1
    },
    "desired_state": 0,
    "desired_state_changed_at": "2017-10-12T16:27:20.212571617Z",
    "generation": 41,
    "current_state": 0,
    "pending_state": 0
  }
}
