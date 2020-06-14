package ro.cburcea.playground.designpatterns.visitor.document;

public interface Visitor {

    void visit(XmlElement xe);

    void visit(JsonElement je);
}