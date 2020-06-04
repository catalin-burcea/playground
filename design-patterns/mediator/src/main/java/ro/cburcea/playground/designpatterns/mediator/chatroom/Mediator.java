package ro.cburcea.playground.designpatterns.mediator.chatroom;

public interface Mediator {
    public void send(String message, Colleague colleague);
}