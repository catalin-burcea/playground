package ro.cburcea.playground.designpatterns.command.fileops;

public class TextFileApplication {

    public static void main(String[] args) {

        TextFileOperation openTextFileOperation = new OpenTextFileOperation(new TextFile("file1.txt"));
        TextFileOperation saveTextFileOperation = new SaveTextFileOperation(new TextFile("file2.txt"));

        TextFileOperationExecutor textFileOperationExecutor = new TextFileOperationExecutor();

        System.out.println(textFileOperationExecutor.executeOperation(openTextFileOperation));
        System.out.println(textFileOperationExecutor.executeOperation(saveTextFileOperation));

        textFileOperationExecutor.executeOperation(() -> "Opening file file3.txt");
        textFileOperationExecutor.executeOperation(() -> "Saving file file3.txt");
    }
}