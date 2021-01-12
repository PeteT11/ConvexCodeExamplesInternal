/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chordant.convexcodeexamplesexternal.mqtt;

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

    /*public MqttStreamClient(String uri) throws MqttException, URISyntaxException {
        this(new URI(uri));
    }*/
    public MqttStreamClient() throws MqttException {

        /*String host = String.format("tcp://%s:%d", uri.getHost(), uri.getPort());
        String[] auth = this.getAuth(uri);
        String username = auth[0];
        String password = auth[1];
        String clientId = "MQTT-Java-Example";
        if (!uri.getPath().isEmpty()) {
            this.topic = uri.getPath().substring(1);
        }*/
        MqttConnectOptions conOpt = new MqttConnectOptions();

        try {
            SSLContext sslContext = SSLContext.getDefault();
            //sslContext.init(null, null, new java.security.SecureRandom());
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
        
        this.topic = "stream/2cc2aa";
        
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
        System.out.println(String.format("[%s] %s", topic, new String(message.getPayload())));
    }

    public static void main(String[] args) throws MqttException, URISyntaxException {

        //mqtt publish --username '01OZLElajKpQJonw' 
        MqttStreamClient sc = new MqttStreamClient();
        sc.sendMessage("Hello everyone");
        //sc.sendMessage("Hello 2");
    }
}
