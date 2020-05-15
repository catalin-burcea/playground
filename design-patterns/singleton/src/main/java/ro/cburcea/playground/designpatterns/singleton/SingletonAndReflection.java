package ro.cburcea.playground.designpatterns.singleton;

import java.lang.reflect.Constructor;

public class SingletonAndReflection {

    public static void main(String[] args) {
        MySingleton singletonInstance = MySingleton.getInstance();
        MySingleton reflectionInstance = null;

        try {
            Constructor[] constructors = MySingleton.class.getDeclaredConstructors();
            for (Constructor constructor : constructors) {
                constructor.setAccessible(true);
                reflectionInstance = (MySingleton) constructor.newInstance();
            }
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        System.out.println("singletonInstance hashCode: " + singletonInstance.hashCode());
        System.out.println("reflectionInstance hashCode: " + reflectionInstance.hashCode());
    }
}

/**
 * To protect your singleton class against instantiation via reflection,
 * you can throw an exception from the private constructor if the instance is already created like this.
 *
 * You can also use an Enum singleton to guard against reflection. Enums can’t be initialized via reflection.
 * They are a sure shot way of having a single instance no matter what.
 */
class MySingleton {

    private static final MySingleton instance = new MySingleton();

    private MySingleton() {
        // protect against instantiation via reflection
        if (instance != null) {
            throw new IllegalStateException("Singleton already initialized");
        }
    }

    public static MySingleton getInstance() {
        return instance;
    }
}