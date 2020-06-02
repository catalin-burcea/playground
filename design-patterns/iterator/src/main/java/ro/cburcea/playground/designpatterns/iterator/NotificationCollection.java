package ro.cburcea.playground.designpatterns.iterator;

class NotificationCollection implements Collection {

    static final int MAX_ITEMS = 6;
    int numberOfItems = 0;
    Notification[] notificationList;

    public NotificationCollection() {
        notificationList = new Notification[MAX_ITEMS];
    }

    public void addItem(String str) {
        Notification notification = new Notification(str);
        if (numberOfItems >= MAX_ITEMS)
            System.err.println("Full");
        else {
            notificationList[numberOfItems] = notification;
            numberOfItems++;
        }
    }

    public Iterator createIterator() {
        return new NotificationIterator(notificationList);
    }
} 