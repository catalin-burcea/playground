package ro.cburcea.playground.designpatterns.command.remotecontrol;

//Receiver
public class Light {
    private boolean on;

    public void switchOn() {
        System.out.println("switch on...");
        on = true;
    }

    public void switchOff() {
        System.out.println("switch off...");
        on = false;
    }
}