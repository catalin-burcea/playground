package com.cburcea.playground.jooq;

import org.jooq.DSLContext;
import org.jooq.Record;
import org.jooq.Result;
import org.jooq.SQLDialect;
import org.jooq.impl.DSL;

import java.sql.Connection;
import java.sql.DriverManager;

import static com.cburcea.playground.jooq.db.public_.tables.Book.BOOK;

public class JooqExample {

    public static String user = "sa";
    public static String pass = "";
    public static String url = "jdbc:h2:~/jooq";


    public static void main(String[] args) {

        // Create a JDBC Connection
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            // Create a context for your database
            DSLContext ctx = DSL.using(conn, SQLDialect.H2);

            Result<Record> result = ctx.select().from(BOOK).fetch();
            for (Record r : result) {
                Integer id = r.get(BOOK.ID);
                String title = r.get(BOOK.TITLE);
                System.out.println("Book: id=" + id + " title=" + title);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
