package ro.cburcea.playground.designpatterns.singleton;

class SuperClass implements Cloneable {
    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
}

class Singleton extends SuperClass {
    public static Singleton instance = new Singleton();

    private Singleton() {
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();

        // If you don't want to throw exception you can also return the same instance from clone method.
        // return instance;
    }
}

public class SingletonAndCloning {
    public static void main(String[] args) throws CloneNotSupportedException {
        Singleton instance1 = Singleton.instance;
        Singleton instance2 = (Singleton) instance1.clone();
        System.out.println("instance1 hashCode:- " + instance1.hashCode());
        System.out.println("instance2 hashCode:- " + instance2.hashCode());
    }
} 