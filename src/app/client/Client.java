package app.client;

import app.IO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {
    private final IO io = new IO();
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 12345;
    private static BufferedReader reader;
    private static PrintWriter writer;
    public static BufferedReader consoleReader;
    private static Socket socket;

    public Client() {
    }

    public static void main(String[] args) throws IOException {
        socket = new Socket("localhost", 12345);
        consoleReader = new BufferedReader(new InputStreamReader(System.in));
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream(), true);
        (new Client()).run(12345, "localhost");
    }

    public void run(int serverPort, String ServerAddress) throws IOException {
        this.io.println("Соеденение установлено.");
        System.out.println(reader.readLine());
        String clientName = consoleReader.readLine();
        writer.println(clientName);
        this.io.println("Приятного общения!");
        (new Thread(() -> {
            while(true) {
                try {
                    String message;
                    if ((message = reader.readLine()) != null) {
                        System.out.println(message);
                        continue;
                    }
                } catch (IOException var2) {
                    this.io.println("Сервер отключился.");
                }

                return;
            }
        })).start();

        String userInput;
        while((userInput = consoleReader.readLine()) != null) {
            writer.println(userInput);
        }

    }

    private String getServerIp() {
        return "localhost";
    }

    private int getServerPort() {
        return 12345;
    }
}
