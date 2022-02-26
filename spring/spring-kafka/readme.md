## Kafka commands

#### Start Kafka
* .\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
* .\bin\windows\kafka-server-start.bat .\config\server.properties
* .\bin\windows\kafka-server-start.bat .\config\server.properties --override delete.topic.enable=true

#### Topics
* .\bin\windows\kafka-topics.bat --create --topic quickstart-events --bootstrap-server localhost:9092
* .\bin\windows\kafka-topics.bat --create --topic quickstart-events --replication-factor 1 --partitions 2 --zookeeper localhost:2181
* .\bin\windows\kafka-topics.bat --describe --topic quickstart-events --bootstrap-server localhost:9092
* .\bin\windows\kafka-topics.bat --list --bootstrap-server=localhost:9092 
* .\bin\windows\kafka-topics.sh --delete --topic quickstart-events --zookeeper localhost:2181

**Configs**
* .\bin\windows\kafka-topics.bat --bootstrap-server localhost:9092 --create --topic my-topic --partitions 1 \ 
--replication-factor 1 --config max.message.bytes=64000 --config flush.messages=1
* .\bin\windows\kafka-configs.bat --bootstrap-server localhost:9092 --entity-type topics --entity-name my-topic \
--alter --add-config max.message.bytes=128000
* .\bin\windows\kafka-configs.bat --bootstrap-server localhost:9092 --entity-type topics --entity-name my-topic --describe
* .\bin\windows\kafka-configs.bat --bootstrap-server localhost:9092  --entity-type topics --entity-name my-topic
    --alter --delete-config max.message.bytes
* .\bin\windows\kafka-delete-records.sh --bootstrap-server localhost:9092 --offset-json-file delete-config.json


#### Producers
.\bin\windows\kafka-console-producer.bat --topic quickstart-events --bootstrap-server localhost:9092

#### Consumers
.\bin\windows\kafka-console-consumer.bat --topic quickstart-events --from-beginning --bootstrap-server localhost:9092

#### Consumer groups
.\bin\windows\kafka-consumer-groups.bat --list --bootstrap-server localhost:9092
.\bin\windows\kafka-consumer-groups.bat --describe --group foo --members --bootstrap-server localhost:9092
.\bin\windows\kafka-consumer-groups.bat --describe --group foo --bootstrap-server localhost:9092