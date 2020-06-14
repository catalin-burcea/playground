package ro.cburcea.playground.designpatterns.visitor.shoppingcart;

public class PostageVisitor implements Visitor {

    private double totalPostageForCart;

    public void visit(Book book) {
        //assume we have a calculation here related to weight and price
        //free postage for a book over 10
        if (book.getPrice() < 10.0) {
            totalPostageForCart += book.getWeight() * 2;
        }
    }

    public void visit(CD cd) {
        if (cd.getPrice() < 20.0) {
            totalPostageForCart += 2;
        }
    }

    public double getTotalPostage() {
        return totalPostageForCart;
    }
}