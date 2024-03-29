package com.fiap.restaurant.external.messagebroker;

import software.amazon.awssdk.auth.credentials.DefaultCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.SqsClientBuilder;

import java.net.URI;

public class SqsMessageBroker implements MessageBroker {

    private final String queueName;

    public SqsMessageBroker(String queueName) {
        this.queueName = queueName;
    }

    @Override
    public void send(String message) {
        SqsClient sqsClient = buildClient();

        try (sqsClient) {
            sqsClient.sendMessage(to -> to.queueUrl(this.queueName).messageBody(message));
        }
    }

    private SqsClient buildClient() {
        SqsClientBuilder builder = SqsClient.builder();

        builder.credentialsProvider(DefaultCredentialsProvider.create()).region(Region.US_EAST_1);

        String sqsEndpoint = System.getenv().getOrDefault("SQS_ENDPOINT", null);
        if (sqsEndpoint != null) builder.endpointOverride(URI.create(sqsEndpoint));

        return builder.build();
    }
}
