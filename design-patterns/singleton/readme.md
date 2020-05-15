## Singletons

#### Existential issues
Conceptually, a singleton is a kind of global variable. In general, we know that global variables should be avoided — especially if their states are mutable.

We're not saying that we should never use singletons. However, we are saying that there might be more efficient ways to organize our code.

#### Implementational issues
There are several other issues with the singletons related to JVM itself that could cause us to end up with multiple instances of a singleton. These issues are quite subtle, and we'll give a brief description for each of them:

* In a **multithreaded** environment, we should use the synchronization technique to guarantee the atomicity of the operation
* Every **class loader** might load its own version of the singleton.
* **Reflection** can be caused to destroy singleton property of a singleton class.
* **Serialization** can also cause breakage of singleton property of singleton classes. Serialization is used to convert an object of byte stream and save in a file or send over a network. Suppose you serialize an object of a singleton class. Then if you de-serialize that object it will create a new instance and hence break the singleton pattern.
* **Cloning** is a concept to create duplicate objects. Using clone we can create copy of object. Suppose
* A singleton might be garbage-collected once no one holds a reference to it. This issue does not lead to the presence of multiple singleton instances at a time, but when recreated, the instance might differ from its previous version.
* A singleton is supposed to be unique per JVM. This might be a problem for distributed systems or systems whose internals are based on distributed technologies.

**Enums and reflection**
As enums don’t have any constructor so it is not possible for Reflection to utilize it.
Enums have their by-default constructor, we can’t invoke them by ourselves. **JVM handles the creation and invocation of enum constructors internally.**
As enums don’t give their constructor definition to the program, it is not possible for us to access them by Reflection also. Hence, reflection can’t break singleton property in case of enums.

## Resources

* https://www.callicoder.com/java-singleton-design-pattern-example/
* https://www.baeldung.com/java-singleton
* https://www.geeksforgeeks.org/prevent-singleton-pattern-reflection-serialization-cloning/