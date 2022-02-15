package ro.cburcea.playground.spring.rest.service;


import org.springframework.stereotype.Service;
import ro.cburcea.playground.spring.rest.domain.User;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private List<User> users = new ArrayList<>();

    @PostConstruct
    public void init() {

        for (int i = 1; i < 50_000; i++) {
            users.add(new User(i, "firstName" + i, "lastName" + i, i, Arrays.asList("Football", "Tennis")));
        }

    }

    public List<User> findAllUsers() {
        return users;
    }

    public Optional<User> findUserById(int id) {
        return users
                .stream()
                .filter(u -> id == u.getId())
                .findFirst();
    }

}
