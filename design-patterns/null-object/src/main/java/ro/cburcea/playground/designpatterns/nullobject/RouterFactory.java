package ro.cburcea.playground.designpatterns.nullobject;

public class RouterFactory {

    public static Router getRouterForMessage(Message msg) {

        if (msg.getPriority() == null) {
            return new NullRouter();
        }

        switch (msg.getPriority()) {
            case "high":
                return new SmsRouter();

            case "medium":
                return new JmsRouter();

            default:
                return new NullRouter();
        }

    }
}