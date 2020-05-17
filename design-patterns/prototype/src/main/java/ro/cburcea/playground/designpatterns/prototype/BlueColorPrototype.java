package ro.cburcea.playground.designpatterns.prototype;

public class BlueColorPrototype extends Color {

    public BlueColorPrototype() {
        this.colorName = "blue";
    }

    @Override
    void addColor() {
        System.out.println("Blue color added");
    }

} 