package ro.cburcea.playground.spring.batch.conditional;

public class NumberInfo {
    private int number;

    public NumberInfo(int number) {
        this.number = number;
    }

    public boolean isPositive() {
        return number > 0;
    }

    public int getNumber() {
        return number;
    }

    @Override
    public String toString() {
        return "NumberInfo{" + "number=" + number + '}';
    }
}