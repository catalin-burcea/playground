package ro.cburcea.playground.designpatterns.singleton;

/**
 * Simple singleton implementation.
 * The disadvantage of this approach is that the instance is created irrespective of whether it is accessed or not.
 * This is fine if the object is simple and does not hold any system resources.
 * But can have performance implications if it allocates a large amount of system resources and remains unused.
 */
public class EagerSingleton {

    private static final EagerSingleton INSTANCE = new EagerSingleton();

    private EagerSingleton() {
    }

    public static EagerSingleton getInstance() {
        return INSTANCE;
    }
}
