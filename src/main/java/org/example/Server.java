package org.example;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
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
    }

    public void runServer() throws IOException{
        while(this.running.get()){
            try {
                Socket socket = this.server.accept();
                System.out.println("New client on port: " + socket.getPort());

                NumberGenerator numberGenerator = new NumberGenerator();

                this.threadPool.execute(numberGenerator);
            } catch (SocketException e){
                System.out.println("SocketException, still running? " + this.running);
            }
        }
    }

    public void stopServer() throws IOException {
        this.running.set(false);
        this.threadPool.shutdown();
        this.server.close();
    }

}
