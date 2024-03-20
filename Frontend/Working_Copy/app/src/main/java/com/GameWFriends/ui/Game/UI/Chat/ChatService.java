package com.GameWFriends.ui.Game.UI.Chat;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

public class ChatService {
    private WebSocket webSocket;

    public void start() {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("ws://yourserver.com/chat").build(); // Replace with your server URL
        WebSocketListener webSocketListener = new WebSocketListener() {
            @Override
            public void onOpen(WebSocket webSocket, okhttp3.Response response) {
                // Connection opened
            }

            @Override
            public void onMessage(WebSocket webSocket, String text) {
                // Handle incoming messages
            }

            @Override
            public void onClosing(WebSocket webSocket, int code, String reason) {
                // Handle closing
            }

            @Override
            public void onFailure(WebSocket webSocket, Throwable t, okhttp3.Response response) {
                // Handle failure
            }
        };

        webSocket = client.newWebSocket(request, webSocketListener);

        // Don't forget to close the client somewhere in your code
        // client.dispatcher().executorService().shutdown();
    }

    public void sendMessage(String message) {
        webSocket.send(message);
    }

    public void close() {
        webSocket.close(1000, "Goodbye !");
    }
}
