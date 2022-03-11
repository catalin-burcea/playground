package ro.cburcea.playground.spring.batch.jsontocsv;


import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="trade")
public class Trade {

    private String isin;
    private Integer quantity;
    private Double price;
    private String customer;

    public Trade() {
    }

    public Trade(String isin, Integer quantity, Double price, String customer) {
        this.isin = isin;
        this.quantity = quantity;
        this.price = price;
        this.customer = customer;
    }

    public String getIsin() {
        return isin;
    }

    public void setIsin(String isin) {
        this.isin = isin;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }
}
