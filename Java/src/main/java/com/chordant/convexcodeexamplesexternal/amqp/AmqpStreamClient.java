package com.chordant.convexcodeexamplesexternal.amqp;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class AmqpStreamClient {

    private final static String QUEUE_NAME = "2cc2aa";

    public static void main(String[]args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        
        factory.setHost("amqp.convexglobal.io");
        factory.setPort(5671);
        factory.setVirtualHost("stream");
        factory.setUsername("01OZLElajKpQJonw");
        factory.setPassword("");
        
        try {
            factory.useSslProtocol();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        String message = "product details";
        
        channel.queueBind(QUEUE_NAME, QUEUE_NAME, "");
        
        //channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        
        
        //channel.basicPublish(QUEUE_NAME, QUEUE_NAME, null, message.getBytes());
        //System.out.println(" [x] Sent '" + message + "'");
        
        
         DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag,
                                       Envelope envelope, AMQP.BasicProperties properties,
                                       byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                System.out.println(" [x] Received '" + message + "'");
            }
        };
        channel.basicConsume(QUEUE_NAME, true, consumer);
        

        //channel.close();
        //connection.close();
    }
}