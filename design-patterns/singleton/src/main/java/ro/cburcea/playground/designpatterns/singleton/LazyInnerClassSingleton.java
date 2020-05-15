package ro.cburcea.playground.designpatterns.singleton;

/**
 * A Bill Pugh Singleton is based on the "initialization on demand holder" idiom.
 * This solution is thread-safe and doesn't require any synchronization.
 * It is the most efficient approach among all the singleton design pattern implementations.
 */
public class LazyInnerClassSingleton {

    private LazyInnerClassSingleton() {
    }

    /**
     * This inner class is loaded only after getInstance() is called for the first time.
     */
    private static class SingletonHelper {
        private static final LazyInnerClassSingleton INSTANCE = new LazyInnerClassSingleton();
    }

    public static LazyInnerClassSingleton getInstance() {
        return SingletonHelper.INSTANCE;
    }
}