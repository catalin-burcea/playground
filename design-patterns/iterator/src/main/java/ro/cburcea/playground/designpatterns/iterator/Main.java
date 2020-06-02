package ro.cburcea.playground.designpatterns.iterator;

class Main {

    public static void main(String[] args) {
        NotificationCollection nc = new NotificationCollection();
        nc.addItem("Notification 1");
        nc.addItem("Notification 2");
        nc.addItem("Notification 3");
        nc.addItem("Notification 4");

        NotificationBar nb = new NotificationBar(nc);
        nb.printNotifications();
    }
} 