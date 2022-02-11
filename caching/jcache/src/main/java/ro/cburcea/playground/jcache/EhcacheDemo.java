package ro.cburcea.playground.jcache;

public class EhcacheDemo {

    public static void main(String[] args) {
        SquaredCalculator squaredCalculator = new SquaredCalculator();
        System.out.println(squaredCalculator.getSquareValueOfNumber(4));
        System.out.println(squaredCalculator.getSquareValueOfNumber(4));
        System.out.println(squaredCalculator.getIncrementedSquareValueOfNumber(4));
        System.out.println(squaredCalculator.getSquareValueOfNumber(5));
        System.out.println(squaredCalculator.getSquareValueOfNumber(5));
        System.out.println(squaredCalculator.getIncrementedSquareValueOfNumber(5));
    }
}
