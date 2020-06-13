package ro.cburcea.playground.designpatterns.strategy.filecompression;

import java.util.List;

public class CompressionContext {

    private CompressionStrategy strategy;

    public void setCompressionStrategy(CompressionStrategy strategy) {
        this.strategy = strategy;
    }

    public void createArchive(List<String> files) {
        strategy.compressFiles(files);
    }
}