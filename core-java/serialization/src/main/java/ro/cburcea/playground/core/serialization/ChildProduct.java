package ro.cburcea.playground.core.serialization;

import java.io.Serializable;

public class ChildProduct extends ParentProduct implements Serializable {

    private Integer price;

    public ChildProduct(String name, String description, Integer price) {
        super(name, description);
        this.price = price;
    }


    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}