package ro.cburcea.playground.spring.rest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserV2Dto {

    private int id;
    private String name;
    private int age;
    private List<String> sports;
}
