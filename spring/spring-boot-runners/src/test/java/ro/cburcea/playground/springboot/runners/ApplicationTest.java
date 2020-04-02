package ro.cburcea.playground.springboot.runners;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;

import static org.junit.Assert.assertEquals;

//@SpringBootTest(args = "--custom.property=off")
//public class ApplicationTest {
//
//    @Autowired
//    private Environment env;
//
//    @Test
//    public void whenUsingSpringBootTestArgs_thenCommandLineArgSet() {
//        assertEquals(env.getProperty("custom.property"), "off");
//    }
//}