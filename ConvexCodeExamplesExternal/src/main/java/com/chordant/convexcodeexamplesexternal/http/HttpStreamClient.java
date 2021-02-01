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
import com.chordant.convexcodeexamplesexternal.utils.DateUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;

import java.io.IOException;
import java.text.SimpleDateFormat;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPut;

import org.apache.log4j.Logger;

public class HttpStreamClient {

    // one instance, reuse
    private final CloseableHttpClient httpClient = HttpClients.createDefault();

    final static Logger logger = Logger.getLogger(HttpStreamClient.class);

    private static String MSG_TX = "None";
    private static String MSG_RX = "None";

    public HttpStreamClient(String orgaId, String orgaToken, String orgbId, String orgbToken) throws Exception {

        try {

            //Publish test message
            String baseUrl = "https://http.convexglobal.io";

            String message = DateUtil.getTimestamp();
            MSG_TX = message;

            System.out.println("Stage 1 - Publish a message");
            String response = publishMessage(baseUrl, message, orgaId, orgaToken);
            System.out.println("Response: " + response);
            System.out.println("------------------------");

            try {
                Thread.sleep(1000);
            } catch (Exception e) {
                e.printStackTrace();
            }

            System.out.println("Stage 2 - Consume a message");
            String response2 = consumeMessage(baseUrl, orgbId, orgbToken);
            MSG_RX = response2;

            System.out.println("Response: " + response2);
            System.out.println("------------------------");

        } finally {
            close();
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

    private static String consumeMessage(String baseUrl, String resourceId, String token) throws Exception {

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

    public String getMsgTx() {
        return MSG_TX;
    }

    public String getMsgRx() {
        return MSG_RX;
    }

}
