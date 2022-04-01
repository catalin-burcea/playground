#### How to apply schema migrations

* using hibernate.hbm2ddl.auto schema generation tool
* running the schema migration scripts manually
* flyway: automatically at startup
* flyway: running an integration test
* flyway: running a mvn command

#### Run Flyway Migrate with Maven
* go to playground\db\flyway directory
* run
    ```
    mvn clean flyway:migrate -Dflyway.configFiles=src\main\resources\flyway.conf
    ```
    or 
    ```
    mvn clean flyway:migrate -Dflyway.user=SA -Dflyway.password= -Dflyway.schemas=flyway-schema -Dflyway.url=jdbc:h2:mem:DATABASE -Dflyway.locations="filesystem:src/main/resources/db/migration, filesystem:src/main/resources/db/callback"
    ```
