/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.chordant.convexcodeexamplesexternal.http;

/**
 *
 * @author groov
 */
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.json.JSONArray;

import java.util.Date;
import org.apache.log4j.Logger;
import org.json.JSONObject;

public class StreamClient {

    // one instance, reuse
    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    private final String BEARER_TOKEN = "01UuYaJdPMZ2FsoE";

    private final String BASE_URL = "http://localhost:3000";
    
    final static Logger logger = Logger.getLogger(StreamClient.class);

    public static void main(String[] args) throws Exception {

        StreamClient sc = new StreamClient();

        try {
            logger.info("Running Java Stream Client...");
            logger.info("------------------------");

            //System.out.println("Stage 1 - Get Active Messages to send");
            String messagesResponse = sc.getActiveMessages();
            System.out.println("Response: " + messagesResponse);
            System.out.println("------------------------");

           
            System.out.println("Stage 2 - Iterate and Send Messages");
            JSONArray messageArray = new JSONArray(messagesResponse);
            System.out.println("Number of messages to process: " + messageArray.length());

            for (int i = 0; i < messageArray.length(); i++) {
                JSONObject jo = messageArray.getJSONObject(i);

                String url = jo.getString("url");
                String sentAt = jo.getString("sentat");

                long frequency = jo.getLong("frequency");

                System.out.println("url: " + url);
                System.out.println("SentAt: " + sentAt);
                System.out.println("Frequency: " + frequency);

                Timestamp ts1 = Timestamp.valueOf(sentAt.replace("T", " "));
                System.out.println("Timestamp in millis: " + ts1.getTime());                

            }

        } finally {
            sc.close();
        }
    }

    private void close() throws IOException {
        httpClient.close();
    }

    private String getActiveMessages() throws Exception {

        String responseString = null;

        HttpGet get = new HttpGet(BASE_URL + "/messages?state=eq.active");

        // add request headers
        //post.addHeader("Content-Type", "application/json");
        //post.addHeader("Accept", "application/json");
        //post.addHeader("Authorization", "Bearer "+BEARER_TOKEN);               
        //HttpEntity entity = new StringEntity(AUTH_JSON, ContentType.APPLICATION_JSON);
        //post.setEntity(entity);
        try (CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(get)) {

            responseString = EntityUtils.toString(response.getEntity());

            //JSONObject jo = new JSONObject(responseJson);            
            //token = jo.getJSONObject("token").getString("value");      
            //System.out.println("token: "+token);
        }

        return responseString;

    }

    private static String sendHttpsMessage(String messageId, String url, String content, String token) throws Exception {

        System.out.println("sendHttpsMessage - URL: "+url);
        
        String responseString = null;

        HttpPut put = new HttpPut(url);

        // add request headers
        //put.addHeader("Content-Type", "application/json");
        //put.addHeader("Accept", "application/json");
        //content = "test";
        token = "01OZLElajKpQJonw";

        if (token != null) {
            put.addHeader("Authorization", "Bearer " + token);
        }

        String json = null;

        HttpEntity entity = new StringEntity(content, ContentType.TEXT_PLAIN);
        put.setEntity(entity);

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(put)) {

            responseString = EntityUtils.toString(response.getEntity());

            System.out.println("Response String: " + responseString);

            //JSONArray ja = new JSONArray(responseString);
            //String messageId = ja.getJSONObject(0).getString("id");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");          
           
                      
            return responseString;

        }

        //return responseString;
    }
   
}
