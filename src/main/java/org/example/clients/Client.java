package org.example.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.strategy.DataSendingStrategy;
import org.example.server.Server;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Client implements DataSendingStrategy {

    private static ObjectMapper objectMapper = new ObjectMapper();
    private String clientID;

    private List<Integer> receivedNumbers = new ArrayList<>();

    public Client(String clientID){
        this.clientID = clientID;
    }

    @Override
    public void sendData(Socket socket) throws IOException {
        Server.numberGenerator(socket, clientID, objectMapper, receivedNumbers);
    }
}
