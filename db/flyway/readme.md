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
