package ro.cburcea.playground.spring.batch.jsontocsv;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    public JobBuilderFactory jobBuilderFactory;
    public StepBuilderFactory stepBuilderFactory;

    public BatchConfiguration(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory) {
        this.jobBuilderFactory = jobBuilderFactory;
        this.stepBuilderFactory = stepBuilderFactory;
    }

    @Value("classpath:trades-input.json")
    private Resource input;

    @Value("file:trades-output.csv")
    private Resource output;

    @Bean
    public ItemReader<Trade> itemReader() {
        return new JsonItemReaderBuilder<Trade>()
                .jsonObjectReader(new JacksonJsonObjectReader<>(Trade.class))
                .resource(input)
                .name("tradeJsonItemReader")
                .build();
    }

    @Bean
    public ItemWriter<Trade> itemWriter() throws Exception {
        return new FlatFileItemWriterBuilder<Trade>()
                .name("customerCreditWriter")
                .resource(output)
                .delimited()
                .delimiter(",")
                .names(new String[]{"isin", "quantity", "price", "customer"})
                .build();
    }

    @Bean
    protected Step step1(ItemReader<Trade> reader, ItemWriter<Trade> writer) {
        return stepBuilderFactory
                .get("step1").<Trade, Trade>chunk(5)
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