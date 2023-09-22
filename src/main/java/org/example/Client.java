package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.Socket;

public class Client implements DataSendingStrategy {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private String clientID;

    public Client(String clientID) {
        this.clientID = clientID;
    }

    @Override
    public void sendData(Socket socket) throws IOException {
        Server.numberGenerator(socket, clientID, objectMapper);
    }
}
