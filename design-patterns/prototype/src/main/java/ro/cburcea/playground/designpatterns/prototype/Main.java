package ro.cburcea.playground.designpatterns.prototype;

// Driver class
class Main {
    public static void main(String[] args) {
        ColorCache.getColor("blue").addColor();
        ColorCache.getColor("black").addColor();
        ColorCache.getColor("black").addColor();
        ColorCache.getColor("blue").addColor();
    }
} 