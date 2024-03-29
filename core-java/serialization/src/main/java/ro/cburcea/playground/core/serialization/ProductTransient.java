package ro.cburcea.playground.core.serialization;

import java.io.Serializable;

public class ProductTransient implements Serializable {

    private static final long serialVersionUID = 1234567L;

    private String name;
    private transient String description;
    public static String commonDescription;

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