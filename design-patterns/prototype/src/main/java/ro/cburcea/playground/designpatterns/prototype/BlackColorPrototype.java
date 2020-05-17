package ro.cburcea.playground.designpatterns.prototype;

public class BlackColorPrototype extends Color {

    public BlackColorPrototype() {
        this.colorName = "black";
    }

    @Override
    void addColor() {
        System.out.println("Black color added");
    }
} 