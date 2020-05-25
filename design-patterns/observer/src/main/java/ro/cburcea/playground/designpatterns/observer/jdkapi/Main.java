package ro.cburcea.playground.designpatterns.observer.jdkapi;

public class Main {

    public static void main(String[] args) {
        PCLNewsAgency observable = new PCLNewsAgency();
        PCLNewsChannel observer1 = new PCLNewsChannel();
        PCLNewsChannel observer2 = new PCLNewsChannel();

        observable.addPropertyChangeListener(observer1);
        observable.addPropertyChangeListener(observer2);
        observable.setNews("news1");
        observable.setNews("news2");
    }
}
