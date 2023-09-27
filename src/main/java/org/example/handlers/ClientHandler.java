package org.example.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.server.Server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public class ClientHandler implements Runnable {

    private Socket clientSocket;
    private CopyOnWriteArrayList<Integer> receivedNumbers;
    private Server server;

    private int connectedClientsCount;
    private int busNumber;

    private int maxClients;
    private Set<String> connectedClients;

    public ClientHandler(Socket clientSocket, CopyOnWriteArrayList<Integer> receivedNumbers, Server server, int connectedClientsCount, int busNumber, int maxClients, Set<String> connectedClients){
        this.clientSocket = clientSocket;
        this.receivedNumbers = receivedNumbers;
        this.server = server;
        this.connectedClientsCount = connectedClientsCount;
        this.busNumber = busNumber;
        this.maxClients = maxClients;
        this.connectedClients = connectedClients;
    }

    @Override
    public synchronized void run() {
        try {
            System.out.println("Handling clients!");
            clientSocket.setSoTimeout(10000);

            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        //    PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            Random random = new Random();
        //    HashMap<String, Integer> dataToSend = new HashMap<>();


            HashMap<String, Integer> data;

            while ((data = readDataFromClient(reader)) != null && connectedClientsCount < maxClients) {
                processReceivedData(data);
//alterar para connectedClientsCount
                if(receivedNumbers.size() >= maxClients){
                    busNumber=random.nextInt(101);

                    receivedNumbers.add(0,busNumber);

                    sortNumbersByProximity(receivedNumbers);
                    System.out.println("Ordered Numbers - " + receivedNumbers);

                    // Montar o caminho do ônibus
                    simulateBusPath();

                    clientSocket.close();
                    server.stopServer();
                }
            }

        } catch (IOException e) {
            System.out.println("IOException :" + e.getMessage());
        }
    }

    private void sortNumbersByProximity(List<Integer> receivedNumbers) {
        int busNumber = receivedNumbers.get(0);

        Comparator<Integer> proximityComparator = (a, b) -> {
            int d1 = Math.abs(a - busNumber);
            int d2 = Math.abs(b - busNumber);
            return Integer.compare(d1,d2);
        };

        Collections.sort(receivedNumbers,proximityComparator);
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
        String clientIP = clientSocket.getInetAddress().getHostAddress();
//REMOVIDO PARA TESTES COM UM SÓ COMPUTADOR
// if(!connectedClients.contains(clientIP)){
            connectedClients.add(clientIP);
            connectedClientsCount++;
//        }
        for (Map.Entry<String, Integer> entry : data.entrySet()) {
            String key = entry.getKey();
            int value = entry.getValue();

            System.out.println("Received data from Client " + key + " (IP: " + clientIP + "): " + value);
            receivedNumbers.add(value);
        }
    }

}