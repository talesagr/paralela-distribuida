package org.example;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Random;

public class Client1 {

    private static HashMap<String,Integer> map = new HashMap<>();
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 1234);
        System.out.println("Client1 -> Connection succeed");

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        Thread threadSend = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Available to send");

                    PrintStream output = new PrintStream(socket.getOutputStream());
                    Random random = new Random();

                    int randomNumber = random.nextInt(101);
                    output.println(randomNumber);
                    map.put("CLIENT",randomNumber);
                    out.println(map);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
        threadSend.start();
        threadSend.join();

        System.out.println("Client1 socket closing!");

        socket.close();
    }
}
