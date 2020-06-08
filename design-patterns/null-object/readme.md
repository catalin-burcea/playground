## Null Object - A Behavioral Design Pattern

In most object-oriented programming languages, we're not allowed to use a null reference. That's why we're often forced to write null checks.
Sometimes, if the number of such if statements get high, the code may become ugly, hard to read and error-prone.

**The intent of the Null Object Pattern is to minimize that kind of null check.**
Instead, **we can identify the null behavior and encapsulate it in the type expected by the client code.** More often than not, **such neutral logic is very simple – do nothing.**
These null objects can also be used to provide default behavior in case data is unavailable.
This way we no longer need to deal with special handling of null references.

As null objects should not have any state, there's no need to create identical instances multiple times. Thus, we'll often implement null objects as singletons.

## References

* https://www.baeldung.com/java-null-object-pattern
* https://www.geeksforgeeks.org/null-object-design-pattern/
