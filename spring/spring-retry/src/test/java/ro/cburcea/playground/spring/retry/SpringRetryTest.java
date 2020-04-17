package ro.cburcea.playground.spring.retry;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;

import java.sql.SQLException;

import static ro.cburcea.playground.spring.retry.MyService.FALLBACK_RESULT;
import static ro.cburcea.playground.spring.retry.MyService.SUCCESS_RESULT;

@RunWith(SpringRunner.class)
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
        Assert.assertEquals(SUCCESS_RESULT, result);
    }

    @Test
    public void givenDeclarativeRetry_whenRetryLimitExceeded_thenRetryWithFallback() throws SQLException {
        String result = myService.getUsers("");
        Assert.assertEquals(FALLBACK_RESULT, result);
    }

    @Test
    public void givenImperativeRetry_whenRetryLimitNotExceeded_thenRetryWithSuccess() throws SQLException {
        String result = retryTemplate.execute(retryContext -> myService.getUsers(SELECT_FROM_USERS), retryContext -> FALLBACK_RESULT);
        Assert.assertEquals(SUCCESS_RESULT, result);
    }

    @Test
    public void givenImperativeRetry_whenRetryLimitExceeded_thenRetryWithFallback() throws SQLException {
        String result = retryTemplate.execute(retryContext -> myService.getUsers(""), retryContext -> FALLBACK_RESULT);
        Assert.assertEquals(FALLBACK_RESULT, result);
    }

}