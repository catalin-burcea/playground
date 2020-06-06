## Composite - A Structural Design Pattern

Composite pattern is a partitioning design pattern and **describes a group of objects that is treated the same way as a single instance of the same type of object**.
The intent of a composite is to **compose objects into tree structures to represent part-whole hierarchies.**
It allows you to have a tree structure and ask each node in the tree structure to perform a task.

In oop, a composite is an object designed as a composition of one-or-more **similar objects, all exhibiting similar functionality**.
This is known as a “has-a” relationship between objects.

**The Composite Pattern has four participants:**

* **Component** – Component declares the interface for objects in the composition and for accessing and managing its child components. It also implements default behavior for the interface common to all classes as appropriate.
* **Leaf** – Leaf defines behavior for primitive objects in the composition. It represents leaf objects in the composition.
* **Composite** – Composite stores child components and implements child related operations in the component interface.
* **Client** – Client manipulates the objects in the composition through the component interface.

## References

* https://www.baeldung.com/java-composite-pattern
* https://www.geeksforgeeks.org/composite-design-pattern/
