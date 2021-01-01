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

            //Publish test message
            String baseUrl = "https://http.convexglobal.io";
            String content = "Hello Everyone";
            String resourceId = "2cc2aa";
            String token = "01OZLElajKpQJonw";

            System.out.println("Stage 1 - Publish a message");
            String response = publishMessage(baseUrl, content, resourceId, token);
            System.out.println("Response: " + response);
            System.out.println("------------------------");
            
            System.out.println("Stage 2 - Consume a message");
            String response2 = consumeMessage(baseUrl, content, resourceId, token);
            System.out.println("Response: " + response2);
            System.out.println("------------------------");

        } finally {
            sc.close();
        }
    }

    private void close() throws IOException {
        httpClient.close();
    }

   
   

    private static String publishMessage(String baseUrl, String content, String resourceId, String token) throws Exception {

        String responseString = null;

        String url = baseUrl + "/stream/" + resourceId;

        HttpPut put = new HttpPut(url);

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
    }
    
    private static String consumeMessage(String baseUrl, String content, String resourceId, String token) throws Exception {

        String responseString = null;

        String url = baseUrl + "/stream/" + resourceId;

        HttpGet get = new HttpGet(url);

        if (token != null) {
            get.addHeader("Authorization", "Bearer " + token);
        }

        String json = null;

        try (CloseableHttpClient httpClient = HttpClients.createDefault();
                CloseableHttpResponse response = httpClient.execute(get)) {

            responseString = EntityUtils.toString(response.getEntity());

            System.out.println("Response String: " + responseString);

            //JSONArray ja = new JSONArray(responseString);
            //String messageId = ja.getJSONObject(0).getString("id");
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            return responseString;

        }
    }

}
