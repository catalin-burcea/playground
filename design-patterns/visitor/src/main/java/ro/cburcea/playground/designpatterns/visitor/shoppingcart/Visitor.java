package ro.cburcea.playground.designpatterns.visitor.shoppingcart;

public interface Visitor {
    public void visit(Book book);

    public void visit(CD cd);
}