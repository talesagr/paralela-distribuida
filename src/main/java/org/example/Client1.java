package org.example;

public class Client1 {
    public static void main(String[] args) throws Exception {
        Socket socket = new Socket("localhost", 1234);
        System.out.println("Client1 -> Connection succeed");

        Thread threadSend = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    System.out.println("Available to send");

                    PrintStream output = new PrintStream(socket.getOutputStream());
                    Random random = new Random();

                    int randomNumber = random.nextInt(101);
                    output.println(randomNumber);

                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }
}
