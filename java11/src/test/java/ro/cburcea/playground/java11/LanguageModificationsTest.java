package ro.cburcea.playground.java11;

import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;


public class LanguageModificationsTest {

    @Test
    void testTryWithResources() throws FileNotFoundException {
        final Scanner scanner = new Scanner(new File("C:\\Worskspaces\\java\\intellij-ultimate\\playground\\java11\\src\\test\\resources\\file.txt"));
        PrintWriter writer = new PrintWriter(new File("C:\\Worskspaces\\java\\intellij-ultimate\\playground\\java11\\src\\test\\resources\\file.txt"));
        try (scanner; writer) {

        }
    }

}
