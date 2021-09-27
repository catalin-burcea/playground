package ro.cburcea.playground.java11;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FilesFeaturesTest {

    @Test
    void testWriteStringAndReadStringStaticMethods(@TempDir Path tempDir) throws IOException {
        Path filePath = Files.writeString(Files.createTempFile(tempDir, "demo", ".txt"), "Sample text");
        String fileContent = Files.readString(filePath);

        assertEquals(fileContent, "Sample text");
    }
}
