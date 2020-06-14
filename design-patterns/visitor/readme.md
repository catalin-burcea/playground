## Visitor - A Behavioral Design Pattern

This pattern allows for one or more operation to be applied to a set of objects at runtime, decoupling the operations from the object structure.
It also makes good use of the Open/Closed principle.

The core of this pattern is the **Visitor** interface. This interface defines a visit operation for each type of **ConcreteElement** in the object structure.
Meanwhile, the **ConcreteVisitor** implements the operations defined in the Visitor interface.
The concrete visitor will store local state, typically as it traverses the set of elements.
The element interface simply defines an **accept** method to allow the visitor to run some action over that element - the ConcreteElement will implement this accept method. 

The pattern should be used when you have distinct and unrelated operations to perform across a structure of objects.
**This avoids adding in code throughout your object structure that is better kept separate, so it encourages cleaner code.**
 
**Examples**
* A taxi example, where the customer calls orders a taxi, which arrives at his door. Once the person sits in, the visiting taxi is in control of the transport for that person. 
* Shopping in the supermarket, where the shopping cart is your set of elements. When you get to the checkout, the cashier acts as a visitor, taking the disparate set of elements (your shopping), some with prices and others that need to be weighed, in order to provide you with a total. 

**Disadvantages**

* It makes it more difficult to maintain the code if we need to add new elements to the object's structure.

## References

* https://dzone.com/articles/design-patterns-visitor
* https://www.baeldung.com/java-visitor-pattern
* https://www.geeksforgeeks.org/visitor-design-pattern/
* https://www.tutorialspoint.com/design_pattern/visitor_pattern.htm