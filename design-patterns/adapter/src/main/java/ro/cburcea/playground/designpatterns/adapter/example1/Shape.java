package ro.cburcea.playground.designpatterns.adapter.example1;

public interface Shape {
    void draw();

    void resize();

    String description();

    boolean isHide();
}