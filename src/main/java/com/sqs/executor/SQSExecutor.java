package com.sqs.executor;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.internal.StaticCredentialsProvider;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.Message;
import com.consumer.SQSConsumer;
import com.creator.SQSCreator;
import com.sender.SQSSender;

import java.util.List;

@SuppressWarnings("deprecation")
public class SQSExecutor {

    private static final String QUEUE_NAME = "test_queue";
    private static final int DEFAULT_WAIT_TIME_IN_SECONDS = 4;

    public static void main(String[] args) {
        AWSCredentialsProvider credentialsProvidor = new StaticCredentialsProvider(
            new BasicAWSCredentials("", ""));
        AmazonSQS amazonSQS = AmazonSQSClientBuilder.standard()
            .withCredentials(credentialsProvidor)
            .withRegion(Regions.US_EAST_1)
            .build();

        new SQSCreator(amazonSQS, QUEUE_NAME).createQueue();
        new SQSSender(amazonSQS, QUEUE_NAME).sendMessage("ABBC");
        List<Message> messages = new SQSConsumer(amazonSQS, QUEUE_NAME, null).consume();
        messages.forEach(message -> System.out.println(message.getMessageId()));
    }
}
