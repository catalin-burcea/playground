package ro.cburcea.playground.spring.rest.domain;


public class Movie {

   private int id;
   private String title;
   private int year;
   private int rating;

   public Movie() {
   }

   public Movie(int id, String title, int year, int rating) {
      this.id = id;
      this.title = title;
      this.year = year;
      this.rating = rating;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getTitle() {
      return title;
   }

   public void setTitle(String title) {
      this.title = title;
   }

   public int getYear() {
      return year;
   }

   public void setYear(int year) {
      this.year = year;
   }

   public int getRating() {
      return rating;
   }

   public void setRating(int rating) {
      this.rating = rating;
   }
}