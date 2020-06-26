package ro.cburcea.playground.spring.data.redis;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.redis.DataRedisTest;
import org.springframework.test.context.ContextConfiguration;
import redis.embedded.RedisServer;
import redis.embedded.RedisServerBuilder;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

@ContextConfiguration(classes = RedisConfig.class)
@DataRedisTest
public class StudentRepositoryIntegrationTest {

    @Autowired
    private StudentRepository studentRepository;

    private static RedisServer redisServer;

    @BeforeAll
    public static void startRedisServer() {
        redisServer = new RedisServerBuilder()
                .port(6379)
                .setting("maxmemory 128M")
                .build();

        redisServer.start();
    }

    @AfterAll
    public static void stopRedisServer() {
        redisServer.stop();
    }

    @Test
    public void whenSavingStudent_thenAvailableOnRetrieval() {
        final Student student = new Student("Eng2015001", "John Doe", Student.Gender.MALE, 1);
        studentRepository.save(student);
        final Student retrievedStudent = studentRepository.findById(student.getId()).get();
        assertEquals(student.getId(), retrievedStudent.getId());
    }

    @Test
    public void whenUpdatingStudent_thenAvailableOnRetrieval() {
        final Student student = new Student("Eng2015001", "John Doe", Student.Gender.MALE, 1);
        studentRepository.save(student);
        student.setName("Richard Watson");
        studentRepository.save(student);
        final Student retrievedStudent = studentRepository.findById(student.getId()).get();
        assertEquals(student.getName(), retrievedStudent.getName());
    }

    @Test
    public void whenSavingStudents_thenAllShouldAvailableOnRetrieval() {
        final Student engStudent = new Student("Eng2015001", "John Doe", Student.Gender.MALE, 1);
        final Student medStudent = new Student("Med2015001", "Gareth Houston", Student.Gender.MALE, 2);
        studentRepository.save(engStudent);
        studentRepository.save(medStudent);
        List<Student> students = new ArrayList<>();
        studentRepository.findAll().forEach(students::add);
        assertEquals(students.size(), 2);
    }

    @Test
    public void whenDeletingStudent_thenNotAvailableOnRetrieval() {
        final Student student = new Student("Eng2015001", "John Doe", Student.Gender.MALE, 1);
        studentRepository.save(student);
        studentRepository.deleteById(student.getId());
        final Student retrievedStudent = studentRepository.findById(student.getId()).orElse(null);
        assertNull(retrievedStudent);
    }
}