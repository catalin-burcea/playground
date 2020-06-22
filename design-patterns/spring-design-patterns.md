## Design Patterns in Spring

#### DI/IOC

Dependency Injection/Inversion of Control design pattern allows us to remove the hard-coded dependencies and make our application loosely coupled, extendable and maintainable.
We can implement dependency injection in java to move the dependency resolution from compile-time to runtime.

#### Singleton

Beans defined by Spring are Singletons by default. However, **a singleton bean in Spring and the singleton pattern are quite different.**

Singleton pattern says that one and only one instance of a particular class will ever be created per classloader.
**Spring creates a single object instance per Spring IoC container.** 
Therefore, multiple objects of the same class can exist in a single application if we have multiple containers.

#### Prototype

Spring creates prototype objects based on a template of an existing object through cloning.

#### Factory

Spring uses this technique at the root of its Dependency Injection (DI) framework.
Fundamentally, Spring treats a bean container as a factory that produces beans.

Thus, Spring defines the **BeanFactory** interface as an abstraction of a bean container:
```java
public interface BeanFactory {
 
    getBean(Class<T> requiredType);
    getBean(Class<T> requiredType, Object... args);
    getBean(String name);
 
    // ...
}
```

Each of the getBean methods is considered a factory method.

Spring then extends BeanFactory with the **ApplicationContext** interface, which introduces additional application configuration. Spring uses this configuration to start-up a bean container based on some external configuration, such as an XML file or Java annotations.


#### MVC

The advantage with Spring MVC is that your controllers are POJOs as opposed to being servlets. This makes for easier testing of controllers.

#### Front Controller

Spring provides "DispatcherServlet" to ensure an incoming request gets dispatched to controllers.

#### Proxy

Generally, Spring uses two types of proxies:

* CGLib Proxies – Used when proxying classes
* JDK Dynamic Proxies – Used when proxying interfaces

**Examples**
* Spring AOP
* Transactions
* Spring Remoting

#### Template

**Examples**
* JdbcTemplate 
* JmsTemplate
* RestTemplate

#### State
**Spring Statemachine Project**

The idea is that your application may exist in a **finite number of states** and certain **predefined triggers** can take your application from one state to the next. Such triggers can be based on either **events or timers**.

#### Observer


## References

* https://www.javainuse.com/spring/spring-design-patterns
* https://www.quora.com/What-are-the-design-patterns-for-the-spring-framework
* https://www.baeldung.com/spring-framework-design-patterns
