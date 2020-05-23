package ro.cburcea.playground.designpatterns.decorator;

public interface Shape {
    void draw();

    void resize();

    String description();

    boolean isHide();
}