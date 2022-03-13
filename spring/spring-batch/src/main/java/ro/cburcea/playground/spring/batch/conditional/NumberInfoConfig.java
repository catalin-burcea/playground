package ro.cburcea.playground.spring.batch.conditional;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import static ro.cburcea.playground.spring.batch.conditional.NumberInfoDecider.NOTIFY;

@Configuration
@EnableBatchProcessing
public class NumberInfoConfig {

    @Bean
    @Qualifier("notificationStep")
    public Step notificationStep(StepBuilderFactory sbf) {
        return sbf.get("Notify step")
                .tasklet(new NotifierTasklet())
                .build();
    }

    public Step numberGeneratorStep(StepBuilderFactory sbf, int[] values, String prepend) {
        return sbf.get("Number generator")
                .<NumberInfo, Integer>chunk(1)
                .reader(new NumberInfoGenerator(values))
                .processor(new NumberInfoClassifier())
                .writer(new PrependingStdoutWriter<>(prepend))
                .build();
    }

    public Step numberGeneratorStepDecider(StepBuilderFactory sbf, int[] values, String prepend) {
        return sbf.get("Number generator decider")
                .<NumberInfo, Integer>chunk(1)
                .reader(new NumberInfoGenerator(values))
                .processor(new NumberInfoClassifierWithDecider())
                .writer(new PrependingStdoutWriter<>(prepend))
                .build();
    }

    @Bean
    @Qualifier("first_job")
    public Job numberGeneratorNonNotifierJob(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                                             @Qualifier("notificationStep") Step notificationStep) {
        int[] inputData = {-1, -2, -3};
        Step step = numberGeneratorStep(stepBuilderFactory, inputData, "First Dataset Processor");
        return jobBuilderFactory.get("Number generator - first dataset")
                .start(step)
                .on(NOTIFY)
                .to(notificationStep)
                .from(step)
                .on("*")
                .stop()
                .end()
                .build();
    }

    @Bean
    @Qualifier("second_job")
    public Job numberGeneratorNotifierJob(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                                          @Qualifier("notificationStep") Step notificationStep) {
        int[] inputData = {11, -2, -3};
        Step dataProviderStep = numberGeneratorStep(stepBuilderFactory, inputData, "Second Dataset Processor");
        return jobBuilderFactory.get("Number generator - second dataset")
                .start(dataProviderStep)
                .on(NOTIFY)
                .to(notificationStep)
                .end()
                .build();
    }

    @Bean
    @Qualifier("third_job")
    @Primary
    public Job numberGeneratorNotifierJobWithDecider(JobBuilderFactory jobBuilderFactory, StepBuilderFactory stepBuilderFactory,
                                                     @Qualifier("notificationStep") Step notificationStep) {
        int[] inputData = {11, -2, -3};
        Step dataProviderStep = numberGeneratorStepDecider(stepBuilderFactory, inputData, "Third Dataset Processor");
        return jobBuilderFactory.get("Number generator - third dataset")
                .start(dataProviderStep)
                .next(new NumberInfoDecider())
                .on(NOTIFY)
                .to(notificationStep)
                .end()
                .build();
    }
}