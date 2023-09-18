package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
public class ClientHandler implements Runnable {

    private Socket clientSocket;

    @Override
    public void run() {
        try {
            System.out.println("Handling clients!");
            BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            String clientInput;
            while ((clientInput = reader.readLine()) != null) {
                // Parse a JSON string into a HashMap
                ObjectMapper objectMapper = new ObjectMapper();
                HashMap<String, Integer> data = objectMapper.readValue(clientInput, HashMap.class);

                for (Map.Entry<String, Integer> entry : data.entrySet()) {
                    String clientId = entry.getKey();
                    int value = entry.getValue();

                    System.out.println("Received data from Client " + clientId + ": " + value);

                    // Process the data as needed
                    // ...

                    // Send a response if necessary
                    // ...
                }
            }

            clientSocket.close();
        } catch (IOException e) {
            System.out.println("IOException: " + e.getMessage());
        }
    }
}
