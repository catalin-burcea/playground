package ro.cburcea.playground.spring.data;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.InvalidDataAccessResourceUsageException;
import ro.cburcea.playground.spring.data.dao.CustomerDao;

@SpringBootTest
public class CustomerDaoSpringBootTest {

    @Autowired
    private CustomerDao customerDao;

    @Test
    public void testCustomerDaoWithRepositoryAnnotation() {
        Assertions.assertThrows(InvalidDataAccessResourceUsageException.class, () -> customerDao.findCustomer());
    }
}
