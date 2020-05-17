## Prototype

Prototype allows us to hide the complexity of making new instances from the client.
The concept is to copy an existing object rather than creating a new instance from scratch, something that may include **costly operations**.
The existing object acts as a prototype and contains the state of the object. The newly copied object may change same properties only if required.
**This approach saves costly resources and time**, especially when the object creation is a heavy process.

Prototype pattern, just like every other design pattern, should be used only when it's appropriate.
Since we are cloning the objects, the process could get complex when there are many classes, thereby resulting in a mess. Additionally, it's difficult to clone classes that have circular references.

**When to use the Prototype Design Pattern**

* When a system should be independent of how its products are created, composed, and represented and when the classes to instantiate are specified at run-time.
* When instances of a class can have one of only a few different combinations of state. It may be more convenient to install a corresponding number of prototypes and clone them rather than instantiating the class manually, each time with the appropriate state.

## Resources

* https://www.geeksforgeeks.org/prototype-design-pattern/
* https://www.baeldung.com/java-pattern-prototype