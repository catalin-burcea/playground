package ro.cburcea.playground.springsecurity.formlogin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import ro.cburcea.playground.springsecurity.formlogin.model.Student;

import javax.validation.Valid;
import java.util.List;

@Controller
public class StudentController {

    @PostMapping("/saveStudent")
    public String saveStudent(@Valid @ModelAttribute Student student, BindingResult errors, Model model) {
        if (!errors.hasErrors()) {
            List<Student> students = StudentUtils.buildStudents();
            students.add(student);
            model.addAttribute("students", students);
        }
        return errors.hasErrors() ? "addStudent.html" : "listStudents.html";
    }

    @GetMapping("/addStudent")
    public String addStudent(Model model) {
        model.addAttribute("student", new Student());

        return "addStudent.html";
    }

    @GetMapping("/listStudents")
    public String listStudent(Model model) {
        model.addAttribute("students", StudentUtils.buildStudents());

        return "listStudents.html";
    }

}
