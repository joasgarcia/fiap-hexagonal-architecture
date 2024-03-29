package com.fiap.restaurant.external.queue;

import io.awspring.cloud.sqs.operations.SqsTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsAsyncClient;
import software.amazon.awssdk.services.sqs.SqsAsyncClientBuilder;

import java.net.URI;

@Configuration
public class AwsSqsConfig {

    @Bean
    SqsAsyncClient sqsAsyncClient() {
        SqsAsyncClientBuilder builder = SqsAsyncClient.builder();
        builder.credentialsProvider(DefaultCredentialsProvider.create()).region(Region.US_EAST_1);

        String sqsEndpoint = System.getenv().getOrDefault("SQS_ENDPOINT", null);
        if (sqsEndpoint != null) builder.endpointOverride(URI.create(sqsEndpoint));

        return builder.build();
    }

    @Bean
    public SqsTemplate sqsTemplate(SqsAsyncClient sqsAsyncClient) {
        return SqsTemplate.builder().sqsAsyncClient(sqsAsyncClient).build();
    }
}
