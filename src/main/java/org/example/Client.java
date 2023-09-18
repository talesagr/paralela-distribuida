package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Random;

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
