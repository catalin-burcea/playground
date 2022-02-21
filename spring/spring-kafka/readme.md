## Kafka commands

#### Start Kafka
.\bin\windows\zookeeper-server-start.bat .\config\zookeeper.properties
.\bin\windows\kafka-server-start.bat .\config\server.properties

#### Topics
.\bin\windows\kafka-topics.bat --create --topic quickstart-events --bootstrap-server localhost:9092
.\bin\windows\kafka-topics.bat --create --topic quickstart-events --replication-factor 1 --partitions 2 --zookeeper localhost:2181
.\bin\windows\kafka-topics.bat --describe --topic quickstart-events --bootstrap-server localhost:9092
.\bin\windows\kafka-topics.bat --bootstrap-server=localhost:9092 --list

#### Producers
.\bin\windows\kafka-console-producer.bat --topic quickstart-events --bootstrap-server localhost:9092

#### Consumers
.\bin\windows\kafka-console-consumer.bat --topic quickstart-events --from-beginning --bootstrap-server localhost:9092

#### Consumer groups
.\bin\windows\kafka-consumer-groups.bat --list --bootstrap-server localhost:9092
.\bin\windows\kafka-consumer-groups.bat --describe --group foo --members --bootstrap-server localhost:9092
.\bin\windows\kafka-consumer-groups.bat --describe --group foo --bootstrap-server localhost:9092