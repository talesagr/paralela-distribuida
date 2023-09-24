package org.example.strategy;

import java.io.IOException;
import java.net.Socket;

public interface DataSendingStrategy {
    void sendData(Socket socket) throws IOException;
}
