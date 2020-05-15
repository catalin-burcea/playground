package ro.cburcea.playground.designpatterns.singleton;

/**
 * Lazy initialization means delaying the initialization of something until the first time it is needed.
 */
public class LazySingleton {

    private static LazySingleton instance;

    private LazySingleton() {}

    /**
     * Notice the use of synchronized keyword in the getInstance() method.
     * This is needed to prevent race conditions in multi-threaded environments.
     */
    public static synchronized LazySingleton getInstance() {
        if(instance == null) {
            instance = new LazySingleton();
        }
        return instance;
    }
}