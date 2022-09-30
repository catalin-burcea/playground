package ro.cburcea.playground.spring.batch.skip;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.batch.item.xml.StaxEventItemReader;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    public JobBuilderFactory jobBuilderFactory;
    public StepBuilderFactory stepBuilderFactory;

    public BatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Value("classpath:trades-input.xml")
    private Resource inputXml;

    @Value("file:trades-output.json")
    private Resource outputXml;

    @Bean
    public StaxEventItemReader<Trade> itemReader() {
        return new StaxEventItemReaderBuilder<Trade>()
                .name("itemReader")
                .resource(inputXml)
                .addFragmentRootElements("trade")
                .unmarshaller(getJaxb2Marshaller())
                .build();

    }

    @Bean
    public JsonFileItemWriter<Trade> jsonFileItemWriter() {
        return new JsonFileItemWriterBuilder<Trade>()
                .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
                .resource(outputXml)
                .name("tradeJsonFileItemWriter")
                .build();
    }

    // observation: if the first InvalidTradeException is thrown in batch N, all previous batches are successfully written
    // and the process is stopped. No item is written from batch N or any subsequent batch, if any.
    @Bean
    protected Step step1(ItemReader<Trade> reader, ItemWriter<Trade> writer) {
        return stepBuilderFactory
                .get("step1").<Trade, Trade>chunk(2)
                .reader(reader)
                .processor(new CustomItemProcessor())
                .writer(writer)
                .faultTolerant()
                .skipLimit(2)
                .skip(Exception.class)
                .noSkip(InvalidTradeException.class)
                .build();
    }

    @Bean(name = "firstBatchJob")
    public Job job(@Qualifier("step1") Step step1) {
        return jobBuilderFactory
                .get("firstBatchJob")
                .start(step1)
                .build();
    }

    private Jaxb2Marshaller getJaxb2Marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(Trade.class);
        return marshaller;
    }

}