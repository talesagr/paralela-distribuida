package org.example.clients;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.strategy.DataSendingStrategy;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


import static org.example.server.Server.numberGenerator;

public class Bus implements DataSendingStrategy {
    private static ObjectMapper objectMapper = new ObjectMapper();
    private String busID;

    private List<Integer> receivedNumbers = new ArrayList<>();


    public Bus(String busID) {
        this.busID = busID;
    }

    @Override
    public void sendData(Socket socket) throws IOException {
        numberGenerator(socket, busID, objectMapper, receivedNumbers);
    }

}
