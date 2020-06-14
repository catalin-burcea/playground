package ro.cburcea.playground.designpatterns.visitor.shoppingcart;

//Element interface
public interface Visitable {
    public void accept(Visitor visitor);
}