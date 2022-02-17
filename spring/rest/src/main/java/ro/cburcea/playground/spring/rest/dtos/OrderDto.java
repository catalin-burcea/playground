package ro.cburcea.playground.spring.rest.dtos;


public class OrderDto {

    private Long id;
    private int price;
    private int quantity;

    public OrderDto() {
    }

    public OrderDto(final Long id, final int price, final int quantity) {
        this.id = id;
        this.price = price;
        this.quantity = quantity;
    }

    public Long getId() {
        return id;
    }

    public void setId(final Long id) {
        this.id = id;
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
        return "Order [id=" + id + ", price=" + price + ", quantity=" + quantity + "]";
    }
}
