package ro.cburcea.playground.designpatterns.strategy.filecompression;

import java.util.List;

public class ZipCompressionStrategy implements CompressionStrategy {

    public void compressFiles(List<String> files) {
        System.out.println("applying zip compression to: " + files);
    }
}