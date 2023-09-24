package org.example.server;

import org.example.clients.Client;
import org.example.strategy.DataSendingStrategy;
import org.example.clients.Bus;
import org.example.util.IPGenerator;

import javax.xml.crypto.Data;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ServerManager {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 1234);
        DataSendingStrategy busStrategy = new Bus("bus");
        List<DataSendingStrategy> clientStrategies = new ArrayList<>();

        int numClients = 50;

        for (int i = 1; i < numClients; i++) {
            String clientID = IPGenerator.generateIP();
            DataSendingStrategy strategy = new Client(clientID);
            clientStrategies.add(strategy);
        }

        busStrategy.sendData(socket);
        for (DataSendingStrategy strategy: clientStrategies) {
            strategy.sendData(socket);
        }

        System.out.println("ServerManager Socket closing!");

        socket.close();
    }
}