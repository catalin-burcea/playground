## Strategy - A Behavioral Design Pattern

The strategy pattern allows us to change the behavior of an algorithm at runtime.

**Advantages**

* The application can switch strategies at run-time.
* By encapsulating the algorithm separately, new algorithms complying with the same interface can be easily introduced.
* Strategy enables the clients to choose the required algorithm, without using a “switch” statement or a series of “if-else” statements.
* Data structures used for implementing the algorithm are completely encapsulated in Strategy classes. Therefore, the implementation of an algorithm can be changed without affecting the Context class.

## References
* https://www.baeldung.com/java-strategy-pattern
* https://www.tutorialspoint.com/design_pattern/strategy_pattern.htm
* https://dzone.com/articles/design-patterns-strategy
* https://www.geeksforgeeks.org/strategy-pattern-set-1/
