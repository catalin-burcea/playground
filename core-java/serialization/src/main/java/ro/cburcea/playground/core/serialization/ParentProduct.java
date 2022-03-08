package ro.cburcea.playground.core.serialization;

public class ParentProduct {

    protected String name;
    protected String description;

    public ParentProduct() {
    }

    public ParentProduct(String name, String description) {
        this.name = name;
        this.description = description;
    }

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

}