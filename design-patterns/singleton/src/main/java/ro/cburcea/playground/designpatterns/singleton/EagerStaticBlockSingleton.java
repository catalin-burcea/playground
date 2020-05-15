package ro.cburcea.playground.designpatterns.singleton;

/**
 * The advantage with static block initialization is that you can write your initialization logic or handle exceptions in the static block.
 * Just like the EagerSingleton solution, the INSTANCE is created whether or not it is needed by the application.
 */
public class EagerStaticBlockSingleton {

    private static final EagerStaticBlockSingleton INSTANCE;

    private EagerStaticBlockSingleton() {
    }

    static {
        try {
            INSTANCE = new EagerStaticBlockSingleton();
        } catch (Exception ex) {
            throw ex;
        }
    }

    public static EagerStaticBlockSingleton getInstance() {
        return INSTANCE;
    }
}