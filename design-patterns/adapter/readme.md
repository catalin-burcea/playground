## Adapter Pattern

An Adapter pattern acts as a connector between two incompatible interfaces that otherwise cannot be connected directly. An Adapter wraps an existing class with a new interface so that it becomes compatible with the client’s interface.
The main motive behind using this pattern is to convert an existing interface into another interface that the client expects. It's usually implemented once the application is designed.

A real life example could be a case of card reader which acts as an adapter between memory card and a laptop.

One of the great real life example of Adapter design pattern is mobile charger. Mobile battery needs 3 volts to charge but the normal socket produces either 120V (US) or 240V (India). So the mobile charger works as an adapter between mobile charging socket and the wall socket.

Adapter is about creating an intermediary abstraction that translates, or maps, the old component to the new system. Clients call methods on the Adapter object which redirects them into calls to the legacy component. This strategy can be implemented either with inheritance or with aggregation.

**Use case**

Wrappers used to adopt 3rd parties libraries and frameworks - most of the applications using third party libraries use adapters as a middle layer between the application and the 3rd party library to decouple the application from the library.
If another library has to be used only an adapter for the new library is required without having to change the application code.


**Advantages**

* Helps achieve reusability and flexibility.
* Client class is not complicated by having to use a different interface **and can use polymorphism to swap between different implementations of adapters.**

**Disadvantages**

* All requests are forwarded, so there is a slight increase in the overhead.
* Sometimes many adaptations are required along an adapter chain to reach the type which is required.

## References

* https://dzone.com/articles/adapter-design-pattern-in-java
* https://www.tutorialspoint.com/design_pattern/adapter_pattern.htm
* https://www.geeksforgeeks.org/adapter-pattern/
* https://www.baeldung.com/java-adapter-pattern
