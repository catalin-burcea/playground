## Factory and abstract factory design patterns

#### When to use Factory Design Pattern?

* Factory design pattern is used when we have a superclass with multiple sub-classes and based on the input, we need to return one of the sub-class.

#### Advantage

It's better to create a factory when you need to create complex products (i.e. "encapsulate" a complex product creation process),
have a single point of control for multiple products, or need to manage the lifetime and/or resources that these products consume from a single point of reference.

The advantage of using the factory method pattern is that you decouple the business logic of creation of a class from the actual logic of the class,
because if you don't have that factory method, every class that you add on your system needs to have a factory method inside,
and when you have to change something about the creation you may have to deal with all of that set of classes ( bad for the open-closed principle )

#### Disadvantage

While the pattern is great when creating predefined objects, adding the new ones might be challenging.

## Resources
* https://www.baeldung.com/java-abstract-factory-pattern
* https://www.tutorialspoint.com/design_pattern/factory_pattern.htm

