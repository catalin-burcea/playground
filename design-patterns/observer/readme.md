## Observer - A Behavioral Design Pattern

Observer is a behavioral design pattern. It specifies communication between objects: observable and observers. An observable is an object which notifies observers about the changes in its state.

GoF: "Defines a one-to-many dependency between objects so that when one object changes state, all its dependents are notified and updated automatically".

**Examples**

* A news agency can notify channels when it receives news. Receiving news is what changes the state of the news agency, and it causes the channels to be notified.
* Newspaper: Whenever a new edition is published by the publisher, it will be circulated among subscribers whom have subscribed to publisher.


## References

* https://www.baeldung.com/java-observer-pattern
* 