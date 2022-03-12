package ro.cburcea.playground.spring.batch.compositewriter;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerCreditRowMapper implements RowMapper<Customer> {

    public static final String ID_COLUMN = "id";
    public static final String NAME_COLUMN = "name";
    public static final String CREDIT_COLUMN = "credit";

    public Customer mapRow(ResultSet rs, int rowNum) throws SQLException {
        Customer customer = new Customer();

        customer.setId(rs.getInt(ID_COLUMN));
        customer.setName(rs.getString(NAME_COLUMN));
        customer.setCredit(rs.getInt(CREDIT_COLUMN));

        return customer;
    }
}