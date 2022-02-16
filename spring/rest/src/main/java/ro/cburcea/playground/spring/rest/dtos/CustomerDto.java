package ro.cburcea.playground.spring.rest.dtos;

import java.util.List;


public class CustomerDto {

    private int id;
    private String firstName;
    private String lastName;
    private int age;
    private List<OrderDto> orders;

    public CustomerDto() {
    }

    public CustomerDto(int id, String firstName, String lastName, int age, List<OrderDto> orders) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
        this.orders = orders;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<OrderDto> getOrders() {
        return orders;
    }

    public void setOrders(List<OrderDto> orders) {
        this.orders = orders;
    }
}
