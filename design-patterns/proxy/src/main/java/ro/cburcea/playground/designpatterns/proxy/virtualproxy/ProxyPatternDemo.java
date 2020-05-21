package ro.cburcea.playground.designpatterns.proxy.virtualproxy;

public class ProxyPatternDemo {

    public static void main(String[] args) {
        Image image = new VirtualProxyImage("test_10mb.jpg");

        //image will be loaded from disk
        image.display();

        //image will not be loaded from disk
        image.display();
    }
}