## Proxy - A Structural Design Pattern

The Proxy pattern allows us to create an intermediary that acts as an interface to another resource, while also hiding the underlying complexity of the component.
It Controls and manage access to the object they are protecting.

Proxies are also called surrogates, handles, and wrappers.

**Types of proxies**

* **Virtual proxy** - These proxies will provide some default and instant results if the real object is supposed to take some time to produce results. These proxies initiate the operation on real objects and provide a default result to the application. Once the real object is done, these proxies push the actual data to the client where it has provided dummy data earlier.
* **Remote proxy** - They are responsible for representing the object located remotely. Talking to the real object might involve marshalling and unmarshalling of data and talking to the remote object. All that logic is encapsulated in these proxies and the client application need not worry about them.
* **Protection proxy** - When we want to add a layer of security to the original underlying object to provide controlled access based on access rights of the client. 

**Examples**

* A real world example can be a cheque or credit card is a proxy for what is in our bank account. It can be used in place of cash, and provides a means of accessing that cash when required. 
* A very simple real life scenario is the college internet, which restricts few site access. The proxy first checks the host you are connecting to, if it is not part of restricted site list, then it connects to the real internet. 
* Remote Proxy used in Java API: java.rmi.*;

## References

* https://www.geeksforgeeks.org/proxy-design-pattern/
* https://www.baeldung.com/java-proxy-pattern
* https://www.tutorialspoint.com/design_pattern/proxy_pattern.htm
