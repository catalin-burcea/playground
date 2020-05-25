package ro.cburcea.playground.designpatterns.observer.jdkapi;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

public class PCLNewsChannel implements PropertyChangeListener {
 
    private String news;

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    public void propertyChange(PropertyChangeEvent evt) {
        System.out.println("receiving notification: " + evt.getNewValue());
        this.setNews((String) evt.getNewValue());
    }
}