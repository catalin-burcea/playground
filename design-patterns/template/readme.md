## Template - A Behavioral Design Pattern

Template method design pattern is to define an algorithm as skeleton of operations and leave the details to be implemented by the child classes. The overall structure and sequence of the algorithm is preserved by the parent class.

**When Would I Use This Pattern?**

* When behaviour of an algorithm can vary, you let subclasses implement the behaviour through overriding
* You want to avoid code duplication, implementing variations of the algorithm in subclasses
* You want to control the point that subclassing is allowed.

**Examples**

* java.util.AbstractList, java.util.AbstractSet

## References

* https://www.baeldung.com/java-template-method-pattern
* https://dzone.com/articles/design-patterns-template-method
* https://www.tutorialspoint.com/design_pattern/template_pattern.htm
