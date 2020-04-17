package ro.cburcea.playground.spring.retry;

import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.sql.SQLException;

@Service
public class MyService {

    static final String SUCCESS_RESULT = "users: [user1, user2, user3, user4]";
    static final String FALLBACK_RESULT = "fallback users: [user5, user6]";

    @Retryable(
            value = {SQLException.class},
            maxAttempts = 3,
            backoff = @Backoff(delay = 2000),
            listeners = "defaultListenerSupport")
    public String getUsers(String sql) throws SQLException {
        if (StringUtils.isEmpty(sql)) {
            throw new SQLException("sql exception");
        }
        return SUCCESS_RESULT;
    }

    @Recover
    public String recover(SQLException e, String sql) {
        return FALLBACK_RESULT;
    }

}
