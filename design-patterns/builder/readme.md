## Builder Pattern

Builder pattern aims to "Separate the construction of a complex object from its representation so that the same construction process can create different representations." 
It is used to construct a complex object step by step and the final step will return the object. The process of constructing an object should be generic so that it can be used to create different representations of the same object.

#### Advantages

* Builder design pattern helps in **minimizing the number of parameters** in constructor and thus there is no need to pass in null for optional parameters to the constructor.
* Eliminates the need of a **complex or telescoping constructor**
* Object is always instantiated in a **complete state**
* **Object Creation code is less error-prone** as user will know what they are passing because of explicit method call.
* Immutable objects can be build without much complex logic in object building process.

#### Disadvantages

* The number of lines of code increase at least to double in builder pattern, but the effort pays off in terms of design flexibility and much more readable code.
* Requires creating a separate builder for each different class.

## References
* https://medium.com/@ajinkyabadve/builder-design-patterns-in-java-1ffb12648850
* https://dzone.com/articles/design-patterns-the-builder-pattern
* https://www.geeksforgeeks.org/builder-design-pattern
