## Mediator - A Behavioral Design Pattern

The intent of the Mediator Pattern is to reduce the complexity and dependencies between tightly coupled objects communicating directly with one another.
This is achieved by creating a mediator object that takes care of the interaction between dependent objects. Consequently, all the communication goes through the mediator.

**Examples**

* An airport control tower is an excellent example of the mediator pattern. The tower looks after who can take off and land - all communications are done from the airplane to control tower, rather than having plane-to-plane communication. 
* A chat room acts as a mediator between multiple users.

**Disadvantage**

* It centralizes control. The mediator pattern trades complexity of interaction for complexity in the mediator.
Because a mediator encapsulates protocols, it can become more complex than any individual colleague. This can make the mediator itself a monolith that’s hard to maintain

## References

* https://www.baeldung.com/java-mediator-pattern
* https://dzone.com/articles/design-patterns-mediator
* https://www.geeksforgeeks.org/mediator-design-pattern/
