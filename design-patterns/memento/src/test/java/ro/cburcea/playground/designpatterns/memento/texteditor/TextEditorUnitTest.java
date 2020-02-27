package ro.cburcea.playground.designpatterns.memento.texteditor;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TextEditorUnitTest {

    @Test
    void givenTextEditor_whenAddTextSaveAddMoreAndUndo_thenSavecStateRestored() {
        TextEditor textEditor = new TextEditor(new TextWindow());
        textEditor.write("The Memento Design Pattern\n");
        textEditor.write("How to implement it in Java?\n");
        textEditor.hitSave();
        textEditor.write("Buy milk and eggs before coming home\n");
        textEditor.hitUndo();

        assertEquals("The Memento Design Pattern\nHow to implement it in Java?\n", textEditor.print());
    }
}