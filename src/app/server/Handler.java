//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package app.server;

import app.IO;
import app.message.Message;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Handler extends Thread {
    private final IO io = new IO();
    private final Socket socket;
    private final Server server;
    private PrintWriter writer;
    private String clientName;
    public BufferedReader reader;
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
    private boolean active = true;
    private long lastPingTime;

    public Handler(Socket socket, Server server) throws IOException {
        this.socket = socket;
        this.server = server;
        this.reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        this.writer = new PrintWriter(socket.getOutputStream(), true);
    }

    public void run() {
        try {
            this.writer.println("Введите ваше имя: ");
            this.clientName = this.reader.readLine();
            this.io.println(this.clientName + " присоединился к чату");
            this.server.broadcast(new Message(this.clientName + " присоеденился к чату", (String)null, this));

            String message;
            while((message = this.reader.readLine()) != null) {
                String currentTime = LocalTime.now().format(this.formatter);
                this.io.println(currentTime + " " + this.clientName + ": " + message);
                this.server.broadcast(new Message(message, this.clientName, this));
            }
        } catch (IOException var11) {
            this.io.println("Ошибка при обработке клиента: " + var11.getMessage());
        } finally {
            if (this.clientName != null) {
                this.io.println(this.clientName + " покинул чат");
                this.server.removeClient(this);
                this.server.broadcast(new Message(this.clientName + "Покинул чат", (String)null, this));
            }

            try {
                this.socket.close();
            } catch (IOException var10) {
                this.io.println("Ошибка при закрытии соеденения с клиентами: ");
            }

        }

    }

    public void sendMessage(String message) {
        this.writer.println(message);
    }

    public void sendPing() {
        System.out.println("PING");
        this.lastPingTime = System.currentTimeMillis();
    }

    public boolean isActive() {
        return this.active && System.currentTimeMillis() - this.lastPingTime <= (long)Server.getPING_TIMEOUT();
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getClientName() {
        return this.clientName;
    }

    public Socket getSocket() {
        return this.socket;
    }

    public Server getServer() {
        return this.server;
    }
}
