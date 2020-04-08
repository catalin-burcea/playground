package ro.cburcea.playground.springsecurity.oauth.resourceserver;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ro.cburcea.playground.springsecurity.oauth.resourceserver.model.Student;

import java.util.List;

@RestController
public class OAuth2Resource {

    @GetMapping("/")
    public String index(@AuthenticationPrincipal Jwt jwt) {
        return String.format("Hello, %s!", jwt.getSubject());
    }

    @GetMapping("/students")
    public List<Student> getStudents() {
        return StudentUtils.buildStudents();
    }

    @PostMapping("/students")
    public Student saveStudent(@RequestBody Student student) {
        StudentUtils.buildStudents().add(student);
        return student;
    }

}
