## Memento - A Behavioral Design Patter

Memento pattern is used to restore state of an object to a previous state, all without violating encapsulation.
The Memento pattern is useful when you need to provide an undo mechanism in your applications, when the internal state of an object may need to be restored at a later stage. Using serialization along with this pattern, it's easy to preserve the object state and bring it back later on.

**Participants**
* Originator - is the object that knows how to save itself
* Memento - holds the information about the Originator's state, and cannot be modified by the Caretaker.
* Caretaker - is that object that deals with when, and why, the Originator needs to save or restore itself.

**Downsides**
* Used incorrectly, it can expose the internal structure of your object, thus allowing any other object to change the state of your object.
* The saving and restoring of state can be time consuming. 
* If Originator object is very huge then Memento object size will also be huge and use a lot of memory.

## References
* https://www.baeldung.com/java-memento-design-pattern
* https://dzone.com/articles/design-patterns-memento
