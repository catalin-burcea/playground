package ro.cburcea.playground.designpatterns.visitor.shoppingcart;

import java.util.ArrayList;
import java.util.List;

public class ShoppingCart {

    private List<Visitable> items = new ArrayList<>();

    ShoppingCart() {
        items.add(new Book(2, 3));
        items.add(new CD(20, 5));
        items.add(new Book(24, 10));
    }

    public double calculatePostage() {
        PostageVisitor visitor = new PostageVisitor();
        for (Visitable item : items) {
            item.accept(visitor);
        }

        return visitor.getTotalPostage();
    }
}