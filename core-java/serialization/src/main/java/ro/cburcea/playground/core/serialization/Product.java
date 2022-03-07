package ro.cburcea.playground.core.serialization;

import java.io.Serializable;

public class Product implements Serializable {

    private static final long serialVersionUID = 1234567L;

    private String name;
    private String description;
    private Integer price;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }
}