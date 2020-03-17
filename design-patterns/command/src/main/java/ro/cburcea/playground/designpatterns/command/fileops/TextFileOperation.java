package ro.cburcea.playground.designpatterns.command.fileops;

@FunctionalInterface
public interface TextFileOperation {

    String execute();

}