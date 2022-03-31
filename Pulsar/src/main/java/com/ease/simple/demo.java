package com.ease.simple;

import org.apache.pulsar.client.api.Producer;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;

public class demo {
    public static void main(String[] args) throws PulsarClientException {
        // 构建Client
        PulsarClient client = PulsarClient.builder()
                .serviceUrl("pulsar://localhost:6650")
                .build();

// 创建生产者
        Producer<byte[]> producer = client.newProducer()
                .topic("my-topic")
                .create();

// You can then send messages to the broker and topic you specified:
        producer.send("My message".getBytes());
    }
}
