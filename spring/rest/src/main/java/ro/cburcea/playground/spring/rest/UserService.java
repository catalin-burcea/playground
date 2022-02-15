package ro.cburcea.playground.spring.rest;


import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class UserService {

    private List<User> users = new ArrayList<>();

    @PostConstruct
    public void init() {
        users.add(new User(1, "firstName1", "lastName1", 22, Arrays.asList("Football", "Tennis")));
        users.add(new User(2, "firstName2", "lastName2", 43, Arrays.asList("Football", "Basketball")));
        users.add(new User(3, "firstName3", "lastName3", 35, Arrays.asList("Football", "Hiking")));
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
