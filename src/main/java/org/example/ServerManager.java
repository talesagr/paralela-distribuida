package org.example;

import java.net.*;

public class ServerManager {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 1234);

        DataSendingStrategy busStrategy = new Bus("bus1");
        DataSendingStrategy strategy1 = new Client("client1");
        DataSendingStrategy strategy2 = new Client("client2");
        DataSendingStrategy strategy3 = new Client("client3");
        DataSendingStrategy strategy4 = new Client("client4");
        DataSendingStrategy strategy5 = new Client("client5");


        busStrategy.sendData(socket);
        strategy1.sendData(socket);
        strategy2.sendData(socket);
        strategy3.sendData(socket);
        strategy4.sendData(socket);
        strategy5.sendData(socket);

        System.out.println("Bus socket closing!");

        socket.close();
    }
}