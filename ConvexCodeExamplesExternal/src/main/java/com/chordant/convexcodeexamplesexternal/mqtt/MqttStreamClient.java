/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chordant.convexcodeexamplesexternal.mqtt;

import com.chordant.convexcodeexamplesexternal.utils.DateUtil;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.net.URI;
import java.net.URISyntaxException;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

/**
 * A sample application that demonstrates how to use the Paho MQTT v3.1 Client
 * blocking API.
 */
public class MqttStreamClient implements MqttCallback {

    private final int qos = 1;
    private String topic = "P1";
    private MqttClient client;

    private static String MSG_TX = "None";
    private static String MSG_RX = "None";
    
    public MqttStreamClient() throws MqttException {

        MqttConnectOptions conOpt = new MqttConnectOptions();

        try {
            SSLContext sslContext = SSLContext.getDefault();
            SSLSocketFactory socketFactory = sslContext.getSocketFactory();
            conOpt.setSocketFactory(socketFactory);
        } catch (Exception e) {
            e.printStackTrace();
        }
        conOpt.setCleanSession(true);
        conOpt.setUserName("01OZLElajKpQJonw");

        String password = "";
        conOpt.setPassword(password.toCharArray());

        String host = "ssl://mqtt.convexglobal.io:8883";

        
        String clientId = "P1";
        
        this.topic = "stream/3ffaa3";
        
        this.client = new MqttClient(host, clientId, new MemoryPersistence());
        this.client.setCallback(this);
        
        this.client.connect(conOpt);
     
        this.client.subscribe(this.topic, qos);
    }

    private String[] getAuth(URI uri) {
        String a = uri.getAuthority();
        String[] first = a.split("@");
        return first[0].split(":");
    }

    public void sendMessage(String payload) throws MqttException {
        MqttMessage message = new MqttMessage(payload.getBytes());
        message.setQos(qos);
        this.client.publish(this.topic, message); // Blocking publish
    }

    /**
     * @see MqttCallback#connectionLost(Throwable)
     */
    public void connectionLost(Throwable cause) {
        System.out.println("Connection lost because: " + cause);
        System.exit(1);
    }

    /**
     * @see MqttCallback#deliveryComplete(IMqttDeliveryToken)
     */
    public void deliveryComplete(IMqttDeliveryToken token) {
        System.out.println("Message Delivery Complete: "+token);
    }

    /**
     * @see MqttCallback#messageArrived(String, MqttMessage)
     */
    public void messageArrived(String topic, MqttMessage message) throws MqttException {
        String messageReceived = new String(message.getPayload());
        MSG_RX = messageReceived;
        
        System.out.println(String.format("Message Received [%s] %s", topic, new String(message.getPayload())));
        //this.client.close();
    }

    public static void main(String[] args) throws MqttException, URISyntaxException {

        //mqtt publish --username '01OZLElajKpQJonw' 
        MqttStreamClient sc = new MqttStreamClient();
        
        String message = DateUtil.getTimestamp();
        
        MSG_TX = message;
        
        sc.sendMessage(message);
       
    }
    
    public String getMsgTx() {
        return MSG_TX;
    }

    public String getMsgRx() {
        return MSG_RX;
    }
    
}

   