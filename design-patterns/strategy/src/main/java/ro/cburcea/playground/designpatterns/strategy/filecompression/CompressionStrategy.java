package ro.cburcea.playground.designpatterns.strategy.filecompression;

import java.util.List;

public interface CompressionStrategy {
    public void compressFiles(List<String> files);
}