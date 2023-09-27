package org.example.server;

import org.example.handlers.ClientHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;
public class Server {

    private ServerSocket server;
    private AtomicBoolean running;
    private ExecutorService threadPool;
    private final int serverPort = 1234;
    private CopyOnWriteArrayList<Integer> receivedNumbers;
    private int connectedClientsCount;
    private int busNumber;
    private final int maxClients = 5;
    Set<String> connectedClients = new HashSet<>();

    public Server() throws IOException {
        System.out.println("Starting server");
        this.server = new ServerSocket(serverPort);
        this.threadPool = Executors.newCachedThreadPool();
        this.running = new AtomicBoolean(true);
        this.receivedNumbers = new CopyOnWriteArrayList<>();
        this.connectedClientsCount=0;
        this.busNumber=0;
        this.connectedClients = new HashSet<>();
    }

    public void runServer() {
        while (this.running.get()) {
            try {
                Socket socket = this.server.accept();
                System.out.println("New client connected: " + socket.getInetAddress());

                ClientHandler clientHandler = new ClientHandler(socket, receivedNumbers,this,busNumber,connectedClientsCount,maxClients,connectedClients);
                threadPool.execute(clientHandler);
            } catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
            }
        }
    }

    public synchronized void stopServer() throws IOException {
        this.running.set(false);
        this.threadPool.shutdown();
        this.server.close();
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.runServer();
    }



}
