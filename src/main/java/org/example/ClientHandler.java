package org.example;

import lombok.AllArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@AllArgsConstructor
public class ClientHandler implements Runnable {

    private Socket clientSocket;

    @Override
    public void run() {
        try {
            System.out.println("Handling clients!");
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            Random random = new Random();

            String clientInput;
            while ((clientInput = reader.readLine()) != null) {
                // Split the input to get the identifier and data
                String parts[] = clientInput.split(",");
                System.out.println(clientInput);


//                    if ("BUS".equals(clientInput.get)) {
//                        // Data from Bus class
//                        // Process Bus data here
//                    } else if ("CLIENT".equals(identifier)) {
//                        // Data from Client class
//                        // Process Client data here
//                    } else {
//                        System.out.println("Unknown identifier: " + identifier);
//                    }
            }

            clientSocket.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
