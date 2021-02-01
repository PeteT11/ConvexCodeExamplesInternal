package com.chordant.convexcodeexamplesexternal.websockets;

import com.chordant.convexcodeexamplesexternal.utils.DateUtil;
import java.io.*;
import com.neovisionaries.ws.client.*;

public class WebsocketStreamClient {

    /**
     * The echo server on websocket.org.
     */
    
    private static final String BASE_URL = "wss://ws.convexglobal.io/stream/";
    /**
     * The timeout value in milliseconds for socket connection.
     */
    private static final int TIMEOUT = 5000;

    private static String MSG_TX = "None";
    private static String MSG_RX = "None";

    private final static String DATA_RESOURCE_ID_ORGA = "3ffaa3";
    private final static String ACCESS_TOKEN_ORGA = "01OZLElajKpQJonw";

    private final static String DATA_RESOURCE_ID_ORGB = "d85e79";
    private final static String ACCESS_TOKEN_ORGB = "01JSrW7sY5W4J7rD";

    public WebsocketStreamClient(String orgaId, String orgaToken, String orgbId, String orgbToken) throws Exception {

        try {
            WebSocket ws = connect(orgaId, orgaToken, orgbId, orgbToken);

            String message = DateUtil.getTimestamp();
            MSG_TX = message;

            ws.sendText(message);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Connect to the server.
     */
    private static WebSocket connect(String orgaId, String orgaToken, String orgbId, String orgbToken) throws IOException, WebSocketException {
        return new WebSocketFactory()
                .setConnectionTimeout(TIMEOUT)
                .createSocket(BASE_URL +orgaId)
                .addListener(new WebSocketAdapter() {
                    // A text message arrived from the server.
                    public void onTextMessage(WebSocket websocket, String message) {
                        System.out.println("Text Message: " + message);
                    }

                    public void onBinaryMessage(WebSocket websocket, String message) {
                        System.out.println("Binary Message: " + message);
                    }
                })
                .setUserInfo(orgaToken)
                .connect();
    }

    /**
     * Wrap the standard input with BufferedReader.
     */
    private static BufferedReader getInput() throws IOException {
        return new BufferedReader(new InputStreamReader(System.in));
    }

    public String getMsgTx() {
        return MSG_TX;
    }

    public String getMsgRx() {
        return MSG_RX;
    }
}
