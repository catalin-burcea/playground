package ro.cburcea.playground.jooq;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;
import ro.cburcea.playground.jooq.db.public_.tables.Book;

import java.sql.Connection;
import java.sql.DriverManager;

public class JooqExample {

    public static final String USER = "sa";
    public static final String PASSWORD = "";
    public static final String URL = "jdbc:h2:~/jooq";

    public static void main(String[] args) {

        // Create a JDBC Connection
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD)) {
            // Create a context for your database
            DSLContext ctx = DSL.using(conn, SQLDialect.H2);

            Result<Record> result = ctx.select().from(Book.BOOK).fetch();
            for (Record r : result) {
                Integer id = r.get(Book.BOOK.ID);
                String title = r.get(Book.BOOK.TITLE);
                System.out.println("Book: id=" + id + " title=" + title);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
