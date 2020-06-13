package ro.cburcea.playground.designpatterns.strategy.filecompression;

import java.util.Arrays;
import java.util.List;

public class Client {
    public static void main(String[] args) {
        CompressionContext ctx = new CompressionContext();
        final List<String> files = Arrays.asList("file1", "file2", "file3");

        ctx.setCompressionStrategy(new ZipCompressionStrategy());

        ctx.createArchive(files);

        ctx.setCompressionStrategy(new RarCompressionStrategy());

        ctx.createArchive(files);
    }
}