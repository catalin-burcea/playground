package ro.cburcea.playground.designpatterns.observer;

public class NewsChannel implements Channel {

    private String news;

    public String getNews() {
        return news;
    }

    public void setNews(String news) {
        this.news = news;
    }

    @Override
    public void update(String news) {
        System.out.println("receiving notification: " + news);
        this.setNews(news);
    }
}