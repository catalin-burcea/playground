package ro.cburcea.playground.spring.batch.xmltojson;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.batch.item.xml.builder.StaxEventItemReaderBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Value("classpath:trades-input.xml")
    private Resource inputXml;

    @Value("file:trades-output.json")
    private Resource outputJson;

    @Bean
    public ItemReader itemReader() {
        return new StaxEventItemReaderBuilder<Trade>()
                .name("itemReader")
                .resource(inputXml)
                .addFragmentRootElements("trade")
                .unmarshaller(getJaxb2Marshaller())
                .build();

    }

    @Bean
    public ItemWriter<Trade> jsonFileItemWriter() {
        return new JsonFileItemWriterBuilder<Trade>()
                .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
                .resource(outputJson)
                .name("tradeJsonFileItemWriter")
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

    private Jaxb2Marshaller getJaxb2Marshaller() {
        Jaxb2Marshaller marshaller = new Jaxb2Marshaller();
        marshaller.setClassesToBeBound(Trade.class);
        return marshaller;
    }

}