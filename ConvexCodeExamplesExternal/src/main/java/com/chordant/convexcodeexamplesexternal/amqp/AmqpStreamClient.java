/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
        
        //channel.queueBind(QUEUE_NAME, QUEUE_NAME, "");
        
        //channel.queueDeclare(QUEUE_NAME, false, false, false, null);
        
        
        channel.basicPublish(QUEUE_NAME, QUEUE_NAME, null, message.getBytes());
        System.out.println(" [x] Sent '" + message + "'");

        channel.close();
        connection.close();
    }
}