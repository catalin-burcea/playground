package ro.cburcea.playground.spring.batch.dbtoconsole;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Bean
    public ItemReader<CustomerCredit> itemReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<CustomerCredit>()
                .dataSource(dataSource)
                .name("creditReader")
                .sql("select ID, NAME, CREDIT from CUSTOMER")
                .rowMapper(new CustomerCreditRowMapper())
                .build();

    }

    @Bean
    public ItemWriter<CustomerCredit> itemWriter() {
        return emps -> {
            System.out.println("\nWriting chunk to console");
            for (Object emp : emps) {
                System.out.println(emp);
            }
        };
    }

    @Bean
    protected Step step1(ItemReader<CustomerCredit> reader,
                         ItemWriter<CustomerCredit> writer) {
        return stepBuilderFactory
                .get("step1").<CustomerCredit, CustomerCredit>chunk(5)
                .reader(reader)
                .writer(writer)
                .build();
    }

    @Bean(name = "firstBatchJob")
    public Job job(@Qualifier("step1") Step step1) {
        return jobBuilderFactory
                .get("firstBatchJob")
                .start(step1)
                .build();
    }

}