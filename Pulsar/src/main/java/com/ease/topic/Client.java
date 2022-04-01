package com.ease.topic;

import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.api.PulsarClientException;

public class Client {
    private PulsarClient client;

    public Client() throws PulsarClientException {
         client = PulsarClient.builder()
//                .serviceUrl("pulsar://slave2:6650")
                 .serviceUrl("pulsar://slave1:6650,slave2:6650,slave3:6650")
                .build();
    }


    public void close() throws PulsarClientException {
        client.close();
    }

    public PulsarClient getPulsarClient(){
        return client;
    }
}
