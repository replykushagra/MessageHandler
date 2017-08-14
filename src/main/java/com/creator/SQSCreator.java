package com.creator;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.AmazonSQSException;

public class SQSCreator {

    private final String queueName;
    private final AmazonSQS amazonSqs;
    private static final String QUEUE_ALREADY_EXISTS = "QueueAlreadyExists";

    public SQSCreator(AmazonSQS amazonSqs, String queueName) {
        this.queueName = queueName;
        this.amazonSqs = amazonSqs;
    }

    public void createQueue() {
        try {
            amazonSqs.createQueue(queueName);
        } catch (AmazonSQSException e) {
            if (!e.getErrorCode().equals(QUEUE_ALREADY_EXISTS)) {
                throw e;
            }
        }

    }

}
