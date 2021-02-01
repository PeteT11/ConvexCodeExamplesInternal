/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chordant.convexcodeexamplesexternal.test;

import com.chordant.convexcodeexamplesexternal.http.HttpStreamClient;
import com.chordant.convexcodeexamplesexternal.websockets.WebsocketStreamClient;

/**
 *
 * @author groov
 */
public class StreamTests {

    /**
     * @param args the command line arguments
     */
    
    private static int totalTests = 2;
    private static int testsPassed = 0;    
     
    private final static String DATA_RESOURCE_ID_ORGA = "3ffaa3";
    private final static String ACCESS_TOKEN_ORGA = "01OZLElajKpQJonw";
    
    private final static String DATA_RESOURCE_ID_ORGB = "d85e79";
    private final static String ACCESS_TOKEN_ORGB = "01JSrW7sY5W4J7rD";
    
    public static void main(String[] args) {
        
        System.out.println("[1] Publish using WebSocket - ORG A");
        try {
            WebsocketStreamClient wsc = new WebsocketStreamClient(DATA_RESOURCE_ID_ORGA, ACCESS_TOKEN_ORGA, DATA_RESOURCE_ID_ORGB, ACCESS_TOKEN_ORGB);        
            System.out.println("[1] Message Sent: "+wsc.getMsgTx());
            Thread.sleep(1000);
            System.out.println("[1] Message Received: "+wsc.getMsgRx());
            System.out.println("[1] Test Status: "+getTestState(wsc.getMsgTx(), wsc.getMsgRx()));            
            
        } catch (Exception e) {
           e.printStackTrace();
        }
        
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
              
        /*System.out.println("[1] Publish and Consume using HTTP - ORG A >  ORG B");
        try {
            HttpStreamClient hsc = new HttpStreamClient(DATA_RESOURCE_ID_ORGA, ACCESS_TOKEN_ORGA, DATA_RESOURCE_ID_ORGB, ACCESS_TOKEN_ORGB);        
            System.out.println("[1] Message Sent: "+hsc.getMsgTx());
            Thread.sleep(1000);
            System.out.println("[1] Message Received: "+hsc.getMsgRx());
            System.out.println("[1] Test Status: "+getTestState(hsc.getMsgTx(), hsc.getMsgRx()));            
            
        } catch (Exception e) {
           e.printStackTrace();
        }
        
        try {
            Thread.sleep(3000);
        } catch (Exception e) {
            e.printStackTrace();
        }*/
       
        
        /*System.out.println("[2] Publish and Consume using AMQP - ORG A");
        try {
            AmqpStreamClient client = new AmqpStreamClient();        
            System.out.println("[2] Message Sent: "+client.getMsgTx());
            Thread.sleep(1000);
            System.out.println("[2] Message Received: "+client.getMsgRx());
            System.out.println("[2] Test Status: "+getTestState(client.getMsgTx(), client.getMsgRx()));
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }*/
        
        
        /*System.out.println("[2] Publish and Consume using AMQP - ORG A > ORG B");
        try {
            AmqpStreamClient client2 = new AmqpStreamClient(DATA_RESOURCE_ID_ORGA, ACCESS_TOKEN_ORGA, DATA_RESOURCE_ID_ORGB, ACCESS_TOKEN_ORGB);        
            System.out.println("[2] Message Sent: "+client2.getMsgTx());
            Thread.sleep(1000);
            System.out.println("[2] Message Received: "+client2.getMsgRx());
            System.out.println("[2] Test Status: "+getTestState(client2.getMsgTx(), client2.getMsgRx()));
            
            
        } catch (Exception e) {
            e.printStackTrace();
        }      */  
        
        System.out.println("Tests Passed: " +testsPassed + " out of "+totalTests);
           
    }
    
    private static String getTestState(String msg1, String msg2) {
        if (msg1.equals(msg2)) {
            testsPassed++;
            return "PASS";
        }
        else return "FAIL";
    }
    
}
