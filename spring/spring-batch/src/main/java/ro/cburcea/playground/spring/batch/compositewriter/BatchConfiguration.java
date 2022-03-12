package ro.cburcea.playground.spring.batch.compositewriter;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamWriter;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.batch.item.support.ClassifierCompositeItemWriter;
import org.springframework.batch.item.xml.StaxEventItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.oxm.Marshaller;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Value("file:customer-output.xml")
    private Resource outputXml;

    @Value("file:customer-output.json")
    private Resource outputJson;

    @Bean
    public ItemReader<Customer> itemReader(DataSource dataSource) {
        return new JdbcCursorItemReaderBuilder<Customer>()
                .dataSource(dataSource)
                .name("creditReader")
                .sql("select ID, NAME, CREDIT from CUSTOMER")
                .rowMapper(new CustomerCreditRowMapper())
                .build();

    }

    @Bean
    public ItemStreamWriter<Customer> xmlItemWriter(Marshaller marshaller) {
        StaxEventItemWriter<Customer> itemWriter = new StaxEventItemWriter<>();
        itemWriter.setMarshaller(marshaller);
        itemWriter.setRootTagName("customers");
        itemWriter.setResource(outputXml);
        return itemWriter;
    }

    @Bean
    public ItemStreamWriter<Customer> jsonItemWriter() {
        return new JsonFileItemWriterBuilder<Customer>()
                .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
                .resource(outputJson)
                .name("jsonFileItemWriter")
                .build();
    }

    @Bean
    public Marshaller marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(Customer.class);
        return marshaller;
    }


    @Bean
    public ClassifierCompositeItemWriter classifierEmployeeCompositeItemWriter(ItemWriter<Customer> xmlItemWriter,
                                                                               ItemWriter<Customer> jsonItemWriter) {
        ClassifierCompositeItemWriter compositeItemWriter = new ClassifierCompositeItemWriter();
        compositeItemWriter.setClassifier(new CustomerClassifier(xmlItemWriter, jsonItemWriter));
        return compositeItemWriter;
    }

    @Bean
    protected Step step1(ItemReader<Customer> reader,
                         ClassifierCompositeItemWriter<Customer> writer,
                         ItemStreamWriter<Customer> xmlItemWriter,
                         ItemStreamWriter<Customer> jsonItemWriter) {
        return stepBuilderFactory
                .get("step1").<Customer, Customer>chunk(5)
                .reader(reader)
                .writer(writer)
                .stream(xmlItemWriter)
                .stream(jsonItemWriter)
                .build();
    }

    @Bean(name = "compositeWriterJob")
    public Job job(@Qualifier("step1") Step step1) {
        return jobBuilderFactory
                .get("compositeWriterJob")
                .start(step1)
                .build();
    }

}