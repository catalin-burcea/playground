package ro.cburcea.playground.designpatterns.strategy.mathops;

public class OperationAdd implements Strategy {
    @Override
    public int doOperation(int num1, int num2) {
        return num1 + num2;
    }
}