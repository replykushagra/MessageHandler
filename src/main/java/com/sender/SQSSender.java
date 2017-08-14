package com.sender;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.model.SendMessageBatchRequest;
import com.amazonaws.services.sqs.model.SendMessageBatchRequestEntry;
import com.amazonaws.services.sqs.model.SendMessageRequest;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SQSSender {

    private final AmazonSQS amazonSqs;
    private final String queueName;

    public SQSSender(AmazonSQS amazonSqs,
                     String queueName) {
        this.amazonSqs = amazonSqs;
        this.queueName = queueName;
    }

    public void sendMessage(String message) {
        SendMessageRequest request = new SendMessageRequest()
            .withQueueUrl(amazonSqs.getQueueUrl(queueName).getQueueUrl())
            .withMessageBody(message);
        amazonSqs.sendMessage(request);
    }

    public void sendMessages(List<String> messages) {
        Collection<SendMessageBatchRequestEntry> messagePayload = new ArrayList<>();
        for (int i = 0; i< messages.size(); i++) {
            messagePayload.add(new SendMessageBatchRequestEntry(Integer.toString(i), messages.get(0)));
        }
        SendMessageBatchRequest request = new SendMessageBatchRequest()
            .withQueueUrl(amazonSqs.getQueueUrl(queueName).getQueueUrl())
            .withEntries(messagePayload);
        amazonSqs.sendMessageBatch(request);
    }
}
