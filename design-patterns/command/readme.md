## Command - A Behavioral Design Pattern

This pattern allows the requester of a particular action to be decoupled from the object that performs the action.

**Examples**

* The idea of a table order at a restaurant: the waiter takes the order, which is a command from the customer.This order is then queued for the kitchen staff.  The waiter tells the chef that the a new order has come in, and the chef has enough information to cook the meal.
* A remote control used to switch on or off.

**Components**
* Receiver
* Command and ConcreteCommands
* Invoker
* Client

**Disadvantages**

* Increase in the number of classes for each individual command

## References

* https://dzone.com/articles/design-patterns-command
* https://www.baeldung.com/java-command-pattern
* https://www.tutorialspoint.com/design_pattern/command_pattern.htm