package ro.cburcea.playground.designpatterns.mediator.chatroom;

public class Client {

    public static void main(String[] args) {
        ChatRoomMediator mediator = new ChatRoomMediator();
        ConcreteColleague desktop = new ConcreteColleague(mediator);
        MobileColleague mobile = new MobileColleague(mediator);

        mediator.addColleague(desktop);
        mediator.addColleague(mobile);

        desktop.send("Hello World");
        mobile.send("Hello");
    }
}