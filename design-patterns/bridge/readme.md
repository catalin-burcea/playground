## Bridge -  A Structural Design Pattern

This pattern is used to decouple an abstraction from its implementation so that the two can vary independently.

This bridge uses **encapsulation, aggregation, and inheritance** to separate responsibilities into various classes.

The bridge pattern is useful when both the class and what it does vary. We can think of the class itself as an **abstraction**, what the class can do as the **implementation**, and the bridge pattern, itself, as **two layers of abstraction**.

The bridge pattern **allows us to avoid compile-time binding between an abstraction and its implementation**. This is so that an implementation can be selected at run-time.

Since we can change the reference to the implementor in the abstraction, we are able to change the abstraction’s implementor at run-time.

**Participants**
* Abstraction - contains a reference to the implementer 
* RefinedAbstraction
* Implementer - It defines the interface for implementation classes. This interface does not need to correspond directly to the abstraction interface and can be very different. 
* ConcreteImplementor

## References
* https://dzone.com/articles/bridge-design-pattern-in-java
* https://www.baeldung.com/java-bridge-pattern
* https://www.geeksforgeeks.org/bridge-design-pattern/
* https://www.tutorialspoint.com/design_pattern/bridge_pattern.htm
