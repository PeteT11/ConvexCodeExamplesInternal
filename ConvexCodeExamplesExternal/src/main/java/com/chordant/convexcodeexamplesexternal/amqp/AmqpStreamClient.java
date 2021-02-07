package com.chordant.convexcodeexamplesexternal.amqp;

import com.chordant.convexcodeexamplesexternal.utils.DateUtil;
import com.chordant.convexcodeexamplesexternal.utils.FileUtil;
import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class AmqpStreamClient {

    private final static String DATA_RESOURCE_ID = "3ffaa3";
    private final static String ACCESS_TOKEN = "01OZLElajKpQJonw";
   
    
    private static String MSG_TX = "None";
    private static String MSG_RX = "None";
    

    public AmqpStreamClient() throws Exception {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setHost("amqp.convexglobal.io");
        factory.setPort(5671);
        factory.setVirtualHost("stream");
        factory.setUsername(ACCESS_TOKEN);
        factory.setPassword("");

        try {
            factory.useSslProtocol();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        
        //String message = DateUtil.getTimestamp();
        
        String message = FileUtil.readMessageFromFile("c:/Temp2/test.txt");
       
        //channel.queueBind(DATA_RESOURCE_ID, DATA_RESOURCE_ID, "");
        //channel.queueDeclare(QUEUE_NAME, false, false, false, null);        
        channel.basicPublish(DATA_RESOURCE_ID, DATA_RESOURCE_ID, null, message.getBytes());
        //System.out.println(" [x] Sent '" + message + "'");
        MSG_TX = message;

        DefaultConsumer consumer = new DefaultConsumer(channel) {
            @Override
            public void handleDelivery(String consumerTag,
                    Envelope envelope, AMQP.BasicProperties properties,
                    byte[] body) throws IOException {
                String message = new String(body, "UTF-8");
                //System.out.println(" [x] Received '" + message + "'");
                MSG_RX = message;
                try {
                    channel.close();
                    connection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        channel.basicConsume(DATA_RESOURCE_ID, true, consumer);

        //channel.close();
        //connection.close();
    }
    
    public AmqpStreamClient(String orgaId, String orgaToken, String orgbId, String orgbToken) throws Exception {
        
        
        System.out.println("orgaId: "+orgaId);
        System.out.println("orgaToken: "+orgaToken);
        System.out.println("orgbId: "+orgbId);
        System.out.println("orgbToken: "+orgbToken);
        
        ConnectionFactory txFactory = new ConnectionFactory();

        txFactory.setHost("amqp.convexglobal.io");
        txFactory.setPort(5671);
        txFactory.setVirtualHost("stream");
        txFactory.setUsername(orgaToken);
        txFactory.setPassword("");

        try {
            txFactory.useSslProtocol();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Connection txConnection = txFactory.newConnection();
        Channel txChannel = txConnection.createChannel();
        
        ConnectionFactory rxFactory = new ConnectionFactory();

        rxFactory.setHost("amqp.convexglobal.io");
        rxFactory.setPort(5671);
        rxFactory.setVirtualHost("stream");
        rxFactory.setUsername(orgbToken);
        rxFactory.setPassword("");

        try {
            rxFactory.useSslProtocol();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Connection rxConnection = rxFactory.newConnection();
        Channel rxChannel = rxConnection.createChannel();

        String message = DateUtil.getTimestamp();

        //channel.queueBind(DATA_RESOURCE_ID, DATA_RESOURCE_ID, "");
        //channel.queueDeclare(QUEUE_NAME, false, false, false, null);        
        txChannel.basicPublish(orgaId, orgaId, null, message.getBytes());
        //System.out.println(" [x] Sent '" + message + "'");
        MSG_TX = message;

        DefaultConsumer consumer = new DefaultConsumer(rxChannel) {
            @Override
            public void handleDelivery(String consumerTag,
                    Envelope envelope, AMQP.BasicProperties properties,
                    byte[] body) throws IOException {
                String rxMessage = new String(body, "UTF-8");
                //System.out.println(" [x] Received '" + message + "'");
                MSG_RX = rxMessage;
                try {
                    rxChannel.close();
                    rxConnection.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };
        rxChannel.basicConsume(orgbId, true, consumer);

        //channel.close();
        //connection.close();
    }
    
    public String getMsgTx() {
        return MSG_TX;
    }
    
    public String getMsgRx() {
        return MSG_RX;
    }
}
