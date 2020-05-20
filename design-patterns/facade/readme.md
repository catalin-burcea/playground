## Facade - A Structural Design Pattern

Simply put, a facade encapsulates a complex subsystem behind a simple interface.
It hides much of the complexity and makes the subsystem easy to use.

Also, if we need to use the complex subsystem directly, we still can do that; we aren't forced to use the facade all the time.
Besides a much simpler interface, there's one more benefit of using this design pattern. It decouples a client implementation from the complex subsystem. Thanks to this, we can make changes to the existing subsystem and don't affect a client.

Like the adapter pattern, the Facade can be used to hide the inner workings of a third party library, or some legacy code.  All that the client needs to do is interact with the Facade, and not the subsystem that it is encompassing.

**Examples**

Operating systems are one such example - you don't see all the inner workings of your computer, but the OS provides a simplified interface to use the machine. Buildings also have a facade - the exterior of the building.

In Java, the interface JDBC can be called a facade because, we as users or clients create connection using the “java.sql.Connection” interface, the implementation of which we are not concerned about. The implementation is left to the vendor of driver.

Another good example can be the startup of a computer. When a computer starts up, it involves the work of cpu, memory, hard drive, etc. To make it easy to use for users, we can add a facade which wrap the complexity of the task, and provide one simple interface instead.

## References

* https://www.baeldung.com/java-facade-pattern
* https://www.tutorialspoint.com/design_pattern/facade_pattern.htm
* https://www.geeksforgeeks.org/facade-design-pattern-introduction/
* https://dzone.com/articles/design-patterns-uncovered-1





