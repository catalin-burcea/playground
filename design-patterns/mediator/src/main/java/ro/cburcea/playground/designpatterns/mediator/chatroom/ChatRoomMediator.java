package ro.cburcea.playground.designpatterns.mediator.chatroom;

import java.util.ArrayList;

public class ChatRoomMediator implements Mediator {
    private ArrayList<Colleague> colleagues;

    public ChatRoomMediator() {
        colleagues = new ArrayList<>();
    }

    public void addColleague(Colleague colleague) {
        colleagues.add(colleague);
    }

    public void send(String message, Colleague originator) {
        for (Colleague colleague : colleagues) {
            if (colleague != originator) {
                colleague.receive(message);
            }
        }
    }
}