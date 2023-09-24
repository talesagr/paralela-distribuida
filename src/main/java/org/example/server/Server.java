package org.example.server;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.example.handlers.ClientHandler;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicBoolean;

public class Server {

    private ServerSocket server;
    private AtomicBoolean running;
    private ExecutorService threadPool;
    private final int serverPort = 1234;
    private List<Integer> receivedNumbers;

    public Server() throws IOException {
        System.out.println("Starting server");
        this.server = new ServerSocket(serverPort);
        this.threadPool = Executors.newCachedThreadPool();
        this.running = new AtomicBoolean(true);
        this.receivedNumbers = new ArrayList<>();
    }

    public void runServer() {
        while (this.running.get()) {
            try {
                Socket socket = this.server.accept();
                System.out.println("New client on port: " + socket.getPort());

                ClientHandler clientHandler = new ClientHandler(socket, receivedNumbers,this);
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

    public static void numberGenerator(Socket socket, String ID, ObjectMapper objectMapper, List<Integer> receivedNumbers) throws IOException {
        System.out.println("Client " + ID + "-> Connection succeed");

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        Random random = new Random();
        HashMap<String, Integer> dataToSend = new HashMap<>();
        int randomNumber = random.nextInt(101);

        dataToSend.put(ID, randomNumber);
        String json = objectMapper.writeValueAsString(dataToSend);

        out.println(json);

        System.out.println("Data sent to server from Client " + ID + ": " + json);

        synchronized (receivedNumbers) {
            receivedNumbers.add(randomNumber);
        }
    }


}
