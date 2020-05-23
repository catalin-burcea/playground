package ro.cburcea.playground.designpatterns.decorator;

public class Main {
    public static void main(String[] args) {
        System.out.println("Creating Simple Shape Objects...");
        Shape rectangle = new Rectangle();
        Shape circle = new Circle();
        System.out.println("Drawing Simple Shape Objects...");
        rectangle.draw();
        System.out.println();

        circle.draw();
        System.out.println();
        System.out.println("Creating Decorated Circle with Red Color, Blue Lines and thickness of 2 ...");

        Shape circle1 = new FillColorDecorator(
                new LineColorDecorator(
                new LineThicknessDecorator(new Circle(), 2.0d), Color.BLUE), Color.RED);
        circle1.draw();
        System.out.println();

        // order of decorator is also not much important here since all are unique functionalities.
        // we can also do this nesting of functionalities in separate statements.
        System.out.println();

        System.out.println("Creating Decorated Circle with Green Color, Black Lines ...");
        Shape circle2 = new FillColorDecorator(new LineColorDecorator(new Circle(), Color.BLACK), Color.GREEN);
        circle2.draw();
        System.out.println();

        System.out.println("Creating Decorated Rectangle with Yellow Color and Red Lines...");
        Shape rectangle1 = new FillColorDecorator(new LineColorDecorator(new Rectangle(), Color.RED), Color.YELLOW);
        rectangle1.draw();
        System.out.println();
    }
}