### The database connection life-cycle

Every database read or write operation requires a connection.
The flow goes like:

* The application data layer ask the DataSource for a database connection
* The DataSource will use the database Driver to open a database connection
* A database connection is created and a TCP socket is opened
* The application reads/writes from/to the database over the socket
* The connection is no longer required so it is closed
* The socket is closed

You can easily deduce that **opening/closing connections are quite an expensive operation**.

### Database Connection Pools
By just simply implementing a database connection container, which allows us to **reuse a number of existing connections**, we can effectively save the cost of performing a huge number of expensive database trips, hence boosting the overall performance of our database-driven applications.

**Advantages of using db connection pools**:
* reducing the application and database management system OS I/O overhead for creating/destroying a TCP connection
* reducing JVM object garbage

The most relevant point to stress here is that once the pool is created, connections are fetched from the pool, so there's no need to create new ones.
Furthermore, when a connection is released, it's actually returned back to the pool, so other clients can reuse it.

In production-ready implementations, a connection pool should provide a bunch of extra features, such as the ability for tracking the connections that are currently in use, support for **prepared statement pooling**, and so forth.

#### Faster and safer
The waiting step and the timeout mechanism are safety hooks, preventing excessive database server load. If one application gets way too much database traffic, the connection pool is going to mitigate it, therefore, preventing it from taking down the database server (hence affecting the whole enterprise system).

All these benefits come at a price, materialized in the extra complexity of the pool configuration (especially in large enterprise systems). So this is no silver-bullet and you need to pay attention to many pool settings such as:

* minimum size
* maximum size
* max idle time
* acquire timeout
* timeout retry attempts

### HikariCP

This is a very lightweight (at roughly 130Kb) and lightning fast JDBC connection pooling framework.

The framework is so fast because the following techniques have been applied:

* **Bytecode-level engineering** – some extreme bytecode level engineering (including assembly level native coding) has been done
* **Micro-optimizations** – although barely measurable, these optimizations combined boost the overall performance
* **Intelligent use of the Collections framework** – the ArrayList<Statement> was replaced with a custom class FastList that eliminates range checking and performs removal scans from tail to head

HikariCP comes with *sane defaults that perform well in most deployments** without additional tweaking. Every property is optional, except for the "essentials" marked below:
* dataSourceClassName or jdbcUrl
* username
* password

Other properties(check official documentation for details and proper configuration):
* **autoCommit**
* **connectionTimeout** - This property controls the maximum number of milliseconds that a client (that's you) will wait for a connection from the pool. If this time is exceeded without a connection becoming available, a SQLException will be thrown. 
* **idleTimeout** - controls the maximum amount of time that a connection is allowed to sit idle in the pool.
* **maxLifetime** - controls the maximum lifetime of a connection in the pool. 
* **minimumIdle** - controls the minimum number of idle connections that HikariCP tries to maintain in the pool. 
* **maximumPoolSize** - controls the maximum size that the pool is allowed to reach, including both idle and in-use connections.
* **metricRegistry** - This property allows you to specify an instance of a Codahale/Dropwizard MetricRegistry to be used by the pool to record various metrics. 
* **healthCheckRegistry** - This property allows you to specify an instance of a Codahale/Dropwizard HealthCheckRegistry to be used by the pool to report current health information.
* **poolName** - a user-defined name for the connection pool and appears mainly in logging and JMX management consoles to identify pools and pool configurations.

Infrequently used: 
* **initializationFailTimeout**
* **readOnly**
* **connectionInitSql**
* **transactionIsolation**
* **threadFactory**
* ...

### Statement caching / Statement pooling

Statement pooling improves performance by caching SQL statements that are used repeatedly. The benefit of this cache is to prepare frequently used statements only once, but execute them multiple times, thus reducing the overall number of parse calls that must be issued against the database.
Preparing an SQL statement, however, for the database means to parse the SQL statement and choose the optimal execution plan, which is a rather expensive operation on most database platforms, and hence may lead to a considerable performance overhead in the long run.
Statement Pooling enables the applications to reuse a PreparedStatement object in a similar way as they can reuse a database connection when connection pooling is enabled.

A statement pool instance is associated with a physical database connection and it caches PreparedStatement and CallableStatement objects that are created using this connection. Plain Statement objects are not cached.

JDBC drivers are in a unique position to exploit database specific features, and nearly all of the caching implementations are capable of sharing execution plans across connections. 
Using a **statement cache at the pooling layer is an anti-pattern**, and will negatively impact your application performance compared to **driver-provided caches**.

### Statement vs PreparedStatement vs CallableStatement

The **Statement** is used for executing a **static SQL statement**. The Statement interface cannot accept parameters.

The **PreparedStatement** is used for executing **parametrized(dynamic), SQL statements. They are precompiled.** They help us in **preventing SQL injection attacks** because they automatically escapes the special characters. 

The **CallableStatement** is an interface which is used to execute **SQL stored procedures, cursors, and Functions**. The CallableStatement interface can also accept runtime input parameters.

### Log Statement Text / Slow Query Logging

Like Statement caching, most major database vendors support statement logging through properties of their own driver. This includes Oracle, MySQL, Derby, MSSQL, and others. Some even support slow query logging. 

## References
https://www.baeldung.com/java-connection-pooling
https://vladmihalcea.com/the-anatomy-of-connection-pooling/
https://www.baeldung.com/hikaricpf
https://github.com/brettwooldridge/HikariCP#configuration-knobs-baby
https://github.com/brettwooldridge/HikariCP#statement-cache
https://help.sap.com/doc/saphelp_srm702/7.0.2/en-US/26/34884778b7ef468ea6b3995e6784e3/content.htm?no_cache=true
https://github.com/brettwooldridge/HikariCP#log-statement-text--slow-query-logging
https://www.linkedin.com/pulse/jdbc-java-statementpreparedstatement-youssef-najeh/
https://www.tutorialspoint.com/jdbc/jdbc-statements.htm

