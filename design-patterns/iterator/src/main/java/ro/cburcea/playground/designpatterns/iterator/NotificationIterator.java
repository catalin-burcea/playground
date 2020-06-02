package ro.cburcea.playground.designpatterns.iterator;

class NotificationIterator implements Iterator {

    private Notification[] notificationList;
    private int pos = 0;

    public NotificationIterator(Notification[] notificationList) {
        this.notificationList = notificationList;
    }

    public Object next() {
        return notificationList[pos++];
    }

    public boolean hasNext() {
        return pos < notificationList.length && notificationList[pos] != null;
    }
} 