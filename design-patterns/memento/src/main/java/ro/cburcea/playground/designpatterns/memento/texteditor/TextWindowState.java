package ro.cburcea.playground.designpatterns.memento.texteditor;

//memento
public class TextWindowState {

    private String text;

    public TextWindowState(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }
}