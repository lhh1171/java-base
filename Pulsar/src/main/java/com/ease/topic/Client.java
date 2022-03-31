package com.ease.topic;

import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;

public class Client {
    private PulsarClient client;

    public Client() throws PulsarClientException {
         client = PulsarClient.builder()
                .serviceUrl("pulsar://slave2:6650")
                .build();
    }


    public void close() throws PulsarClientException {
        client.close();
    }

    public PulsarClient getPulsarClient(){
        return client;
    }
}
