package org.example;

import lombok.Getter;

import java.io.*;
import java.net.*;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

@Getter
public class Bus {

    private static HashMap<String,Integer> map = new HashMap<>();

    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 1234);
        System.out.println("Bus -> Connection succeed");

        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        Thread threadSend = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Available to send");

                    PrintStream output = new PrintStream(socket.getOutputStream());
                    Random random = new Random();

                    int randomNumber = random.nextInt(101);
                    map.put("BUS",randomNumber);
                    out.println(map);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

//        Thread threadReceiveAnswer = new Thread(new Runnable() {
//            @Override
//            public void run() {
//                try {
//                    System.out.println("Receiving data from server");
//
//                    Scanner answer = new Scanner(socket.getInputStream());
//
//                    while (answer.hasNextLine()) {
//                        String line = answer.nextLine();
//                        System.out.println(line);
//                    }
//
//                    answer.close();
//
//                } catch (IOException e) {
//                    throw new RuntimeException(e);
//                }
//            }
//        });

        threadSend.start();
        threadSend.join();

        System.out.println("Bus socket closing!");

        socket.close();
    }
}
