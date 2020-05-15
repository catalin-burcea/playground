package ro.cburcea.playground.designpatterns.singleton;

/**
 * This is an optimized version of the lazily initialized singleton.
 */
public class LazyDoubleCheckedLockingSingleton {

    private static volatile LazyDoubleCheckedLockingSingleton instance;

    private LazyDoubleCheckedLockingSingleton() {
    }

    public static LazyDoubleCheckedLockingSingleton getInstance() {
        if (instance == null) {
            synchronized (LazyDoubleCheckedLockingSingleton.class) {
                // double-check
                if (instance == null) {
                    instance = new LazyDoubleCheckedLockingSingleton();
                }
            }
        }
        return instance;
    }
}