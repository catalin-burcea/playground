## State - A Behavioral Design Pattern

In State pattern, a class behavior changes based on its internal state.

In State pattern, we create objects which represent various states and a context object whose behavior varies as its state object changes.
The client usually knows very little about the state objects.

It removes the dependency on the if/else or switch/case conditional logic and avoids having an ugly code base, unnecessarily complex and hard to maintain.

**Examples**

* Post office package: ordered, delivered, received.
* MP3 player: playing state, standby state
* Java thread states. A thread can be one of its five states during it’s life cycle. It’s next state can be determined only after getting it’s current state. e.g. we can not start a stopped thread or we cannot a make a thread wait, until it has started running.
* let’s visualize a TV box operated with remote controller. We can change the state of TV by pressing buttons on remote. But the state of TV will change or not, it depends on the current state of the TV. If TV is ON, we can switch it OFF, mute or change aspects and source. But if TV is OFF, nothing will happen.
* Vending Machines
* State patterns are used to implement state machine implementations in complex applications.


## References

* https://dzone.com/articles/design-patterns-state
* https://www.baeldung.com/java-state-design-pattern
* https://www.tutorialspoint.com/design_pattern/state_pattern.htm
