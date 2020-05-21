package ro.cburcea.playground.designpatterns.proxy.protectionproxy;

public class RealInternet implements Internet {

    @Override
    public void connectTo(String serverhost) {
        System.out.println("Connecting to " + serverhost);
    }
} 