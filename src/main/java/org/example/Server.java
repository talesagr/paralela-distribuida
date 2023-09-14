package org.example;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server {

    private ServerSocket server;
    private AtomicBoolean running;
    private ExecutorService threadPool;
    private final int serverPort = 1234;
    public Server() throws IOException {
        System.out.println("Starting server");
        this.server = new ServerSocket(serverPort);
        this.threadPool = Executors.newCachedThreadPool();
        this.running = new AtomicBoolean(true);
    }

    public void runServer() throws IOException {
        while (this.running.get()) {
            try {
                Socket socket = this.server.accept();
                System.out.println("New client on port: " + socket.getPort());

                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String clientInput;

                while ((clientInput = reader.readLine()) != null) {
                    try {
                        int busNumber = Integer.parseInt(clientInput);

                        // Send the random number to the client
                        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                        out.println("Bus Number: " + busNumber);

                        System.out.println("Received: " + busNumber);

                        // Example to send the manipulated number back to the client if needed
                        // PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
                        // out.println("Manipulated Number: " + manipulatedNumber);
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid input from client: " + clientInput);
                    }
                }

                socket.close();
            } catch (IOException e) {
                System.out.println("IOException: " + e.getMessage());
            }
        }
    }

    public void stopServer() throws IOException {
        this.running.set(false);
        this.threadPool.shutdown();
        this.server.close();
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.runServer();
    }

}
