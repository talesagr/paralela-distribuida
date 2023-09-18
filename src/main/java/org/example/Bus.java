package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Random;

import static org.example.Server.numberGenerator;

public class Bus implements DataSendingStrategy{
    private static ObjectMapper objectMapper = new ObjectMapper();
    private String busID;

    public Bus(String busID) {
        this.busID = busID;
    }

    @Override
    public void sendData(Socket socket) throws IOException {
        numberGenerator(socket, busID, objectMapper);
    }

}
