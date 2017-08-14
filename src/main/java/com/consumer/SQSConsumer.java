package com.consumer;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.Message;
import com.amazonaws.services.sqs.model.ReceiveMessageRequest;
import com.amazonaws.services.sqs.model.ReceiveMessageResult;

import java.util.List;

public class SQSConsumer {

    private final AmazonSQS amazonSqs;
    private final String queueName;
    private final Integer waitTimeInSeconds;

    public SQSConsumer(AmazonSQS amazonSqs, String queueName, Integer waitTimeInSeconds) {
        this.amazonSqs = amazonSqs;
        this.queueName = queueName;
        this.waitTimeInSeconds = waitTimeInSeconds;
    }

    public List<Message> consume() {
        ReceiveMessageRequest request = new ReceiveMessageRequest()
            .withQueueUrl(amazonSqs.getQueueUrl(queueName).getQueueUrl())
            .withWaitTimeSeconds(waitTimeInSeconds);
        ReceiveMessageResult result = amazonSqs.receiveMessage(request);
        return result.getMessages();
    }
}
