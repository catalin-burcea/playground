package ro.cburcea.playground.spring.rest.domain;


public class Order {

    private int orderId;
    private int price;
    private int quantity;

    public Order() {
    }

    public Order(final int orderId, final int price, final int quantity) {
        this.orderId = orderId;
        this.price = price;
        this.quantity = quantity;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(final int orderId) {
        this.orderId = orderId;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(final int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(final int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Order [orderId=" + orderId + ", price=" + price + ", quantity=" + quantity + "]";
    }
}
