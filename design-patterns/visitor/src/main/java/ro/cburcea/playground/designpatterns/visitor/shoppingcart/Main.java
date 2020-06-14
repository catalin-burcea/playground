package ro.cburcea.playground.designpatterns.visitor.shoppingcart;

public class Main {

    public static void main(String[] args) {
        ShoppingCart shoppingCart = new ShoppingCart();

        System.out.println(shoppingCart.calculatePostage());
    }
}
