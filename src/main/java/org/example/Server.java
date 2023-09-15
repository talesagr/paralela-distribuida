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

                ClientHandler clientHandler = new ClientHandler(socket);
                threadPool.execute(clientHandler);
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
