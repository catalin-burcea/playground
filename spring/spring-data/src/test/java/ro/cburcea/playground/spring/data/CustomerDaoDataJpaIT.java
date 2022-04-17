package ro.cburcea.playground.spring.data;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Repository;
import ro.cburcea.playground.spring.data.dao.CustomerDao;

import javax.persistence.PersistenceException;

@DataJpaTest(includeFilters = @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = Repository.class))
public class CustomerDaoDataJpaIT {

    @Autowired
    private CustomerDao customerDao;

    @Test
    public void testCustomerDaoWithRepositoryAnnotation() {
        Assertions.assertThrows(PersistenceException.class, () -> customerDao.findCustomer());
    }
}
