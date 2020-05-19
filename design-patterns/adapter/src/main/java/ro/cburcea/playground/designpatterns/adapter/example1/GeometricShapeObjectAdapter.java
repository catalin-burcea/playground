package ro.cburcea.playground.designpatterns.adapter.example1;

/**
 * An Adapter pattern acts as a connector between two incompatible interfaces that otherwise cannot be connected directly.
 * An Adapter wraps an existing class with a new interface so that it becomes compatible with the client’s interface.
 *
 * The main motive behind using this pattern is to convert an existing interface into another interface that the client expects. It's usually implemented once the application is designed.
 */
public class GeometricShapeObjectAdapter implements Shape {
    private GeometricShape adaptee;

    public GeometricShapeObjectAdapter(GeometricShape adaptee) {
        super();
        this.adaptee = adaptee;
    }

    @Override
    public void draw() {
        adaptee.drawShape();
    }

    @Override
    public void resize() {
        System.out.println(description() + " can't be resized. Please create new one with required values.");
    }

    @Override
    public String description() {
        if (adaptee instanceof Triangle) {
            return "Triangle object";
        } else if (adaptee instanceof Rhombus) {
            return "Rhombus object";
        } else {
            return "Unknown object";
        }
    }

    @Override
    public boolean isHide() {
        return false;
    }
}