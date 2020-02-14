package ro.cburcea.playground.spring.profiles;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("dev")
@SpringBootTest
public class SpringDevProfileIntegrationTest {

    @Value("${props.user.name}")
    private String username;

    @Autowired
    ProfileBean profileBean;

    @Test
    public void test() {
        System.out.println("running test from DEV profile" + profileBean.getName());
        Assertions.assertEquals("dev_bean", profileBean.getName());
        Assertions.assertEquals("dev-user", username);

    }
}
