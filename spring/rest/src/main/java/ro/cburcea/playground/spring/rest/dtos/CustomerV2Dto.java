package ro.cburcea.playground.spring.rest.dtos;

import java.util.List;


public class CustomerV2Dto {

    private Long id;
    private String name;
    private int age;
    private List<OrderDto> orders;

    public CustomerV2Dto() {
    }

    public CustomerV2Dto(Long id, String name, int age, List<OrderDto> orders) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.orders = orders;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
