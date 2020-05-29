## Chain of Responsibility - A Behavioral Design Pattern

Chain of responsibility pattern is used to achieve loose coupling in software design where a request from the client is passed to a chain of objects to process them.
Later, the object in the chain will decide themselves who will be processing the request and whether the request is required to be sent to the next object in the chain or not.

The COR pattern can be implemented in two ways:

* One Request Handler/Processor will serve the input request
* Multiple Request Handlers/Processors will serve the input request

**Examples**

* In the Java world, we benefit from Chain of Responsibility every day. One such classic example is Servlet Filters in Java that allow multiple filters to process an HTTP request. 

**Benefits**

* Decouples the sender and receivers of the request.
* Simplifies our object because it doesn't have to know the chain’s structure and keep direct references to its members.
* Allows us to **add or remove responsibilities dynamically** by changing the members or order of the chain.

**Drawbacks**

* Execution of the request isn’t guaranteed; it may fall off the end of the chain if no object handles it.
* It can be hard to observe and debug the runtime characteristics.
* if a processor calls the wrong processor, it can lead to a cycle
* if a processor fails to call the next processor, the command gets dropped
* It can create deep stack traces, which can affect performance
* It can lead to duplicate code across processors, increasing maintenance

## References

* https://www.baeldung.com/chain-of-responsibility-pattern
* https://www.tutorialspoint.com/design_pattern/chain_of_responsibility_pattern.htm
* https://dzone.com/articles/chain-of-responsibility-design-pattern-in-java-2-i
* https://www.geeksforgeeks.org/chain-responsibility-design-pattern/
