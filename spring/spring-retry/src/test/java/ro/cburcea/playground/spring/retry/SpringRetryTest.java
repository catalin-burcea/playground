package ro.cburcea.playground.spring.retry;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.sql.SQLException;

import static ro.cburcea.playground.spring.retry.MyService.FALLBACK_RESULT;
import static ro.cburcea.playground.spring.retry.MyService.SUCCESS_RESULT;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(
        classes = AppConfig.class,
        loader = AnnotationConfigContextLoader.class)
public class SpringRetryTest {

    private static final String SELECT_FROM_USERS = "select * from users";

    @Autowired
    private MyService myService;

    @Autowired
    private RetryTemplate retryTemplate;

    @Test
    public void givenDeclarativeRetry_whenRetryLimitNotExceeded_thenRetryWithSuccess() throws SQLException {
        String result = myService.getUsers(SELECT_FROM_USERS);
        Assertions.assertEquals(SUCCESS_RESULT, result);
    }

    @Test
    public void givenDeclarativeRetry_whenRetryLimitExceeded_thenRetryWithFallback() throws SQLException {
        String result = myService.getUsers("");
        Assertions.assertEquals(FALLBACK_RESULT, result);
    }

    @Test
    public void givenImperativeRetry_whenRetryLimitNotExceeded_thenRetryWithSuccess() throws SQLException {
        String result = retryTemplate.execute(retryContext -> myService.getUsers(SELECT_FROM_USERS), retryContext -> FALLBACK_RESULT);
        Assertions.assertEquals(SUCCESS_RESULT, result);
    }

    @Test
    public void givenImperativeRetry_whenRetryLimitExceeded_thenRetryWithFallback() throws SQLException {
        String result = retryTemplate.execute(retryContext -> myService.getUsers(""), retryContext -> FALLBACK_RESULT);
        Assertions.assertEquals(FALLBACK_RESULT, result);
    }

}