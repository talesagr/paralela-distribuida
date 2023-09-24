package org.example.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.example.server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;
import java.util.stream.Collectors;

@AllArgsConstructor
public class ClientHandler implements Runnable {

    private Socket clientSocket;
    private List<Integer> receivedNumbers;
    private Server server;
    @Override
    public void run() {
        try {
            System.out.println("Handling clients!");
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            HashMap<String, Integer> data;

            while ((data = readDataFromClient(reader)) != null) {
                processReceivedData(data);
            }

            sortNumbersByProximity();
            System.out.println("Ordered Numbers - " + receivedNumbers);

            // Montar o caminho do ônibus
            simulateBusPath();
            
            clientSocket.close();
            server.stopServer();
        } catch (IOException e) {
            System.out.println("IOException :" + e.getMessage());
        }
    }

    private void simulateBusPath() {
        System.out.println("Onibus comecou a corrida ate a faculdade:");

        for (int i = 1; i < receivedNumbers.size(); i++) {
            int clientNumber = receivedNumbers.get(i);
            System.out.println("Onibus esta chegando no cliente: " + clientNumber);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        try {
            Thread.sleep(1500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Onibus chegou na Faculdade.");

    }

    private HashMap<String,Integer> readDataFromClient(BufferedReader reader) throws IOException{
            ObjectMapper objectMapper = new ObjectMapper();
            String clientInput = reader.readLine();

            if(clientInput == null) {
                return null;
            }

            return objectMapper.readValue(clientInput,HashMap.class);
        }

    private void processReceivedData(Map<String, Integer> data) {
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();

            System.out.println("Received data from Client " + key + ": " + value);
            receivedNumbers.add(value);
        }
    }

    private void sortNumbersByProximity(){
        int busNumber = receivedNumbers.get(0);

        Comparator<Integer> proximityComparator = (a,b) -> {
            int d1 = Math.abs(a - busNumber);
            int d2 = Math.abs(b - busNumber);
            return Integer.compare(d1,d2);
        };

        Collections.sort(receivedNumbers,proximityComparator);
    }

}