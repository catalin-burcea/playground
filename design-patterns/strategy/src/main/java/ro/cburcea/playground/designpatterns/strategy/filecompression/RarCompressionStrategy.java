
package ro.cburcea.playground.designpatterns.strategy.filecompression;

import java.util.List;

public class RarCompressionStrategy implements CompressionStrategy {

    public void compressFiles(List<String> files) {
        System.out.println("applying rar compression to: " + files);
    }
}