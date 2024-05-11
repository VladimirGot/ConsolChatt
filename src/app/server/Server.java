package app.server;

import app.IO;
import app.message.Message;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Server {
    private final IO io = new IO();
    private static final long WATCHING_INTERVAL = 10000L;
    private static final int ACTIVITY_CHECK_INTERVAL = 60000;
    private static final int PING_TIMEOUT = 10000;
    public static List<Handler> clients = new ArrayList();

    public Server() {
    }

    public static void main(String[] args) throws IOException {
        Server server = new Server();
        server.run();
    }

    public void run() {
        try {
            ServerSocket serverSocket = new ServerSocket(12345);

            try {
                this.io.println("" + "Сервер запущен на порту: 12345");

                while(true) {
                    Socket socket = serverSocket.accept();
                    Handler client = new Handler(socket, this);
                    clients.add(client);
                    (new Thread(client)).start();
                }
            } catch (Throwable var5) {
                try {
                    serverSocket.close();
                } catch (Throwable var4) {
                    var5.addSuppressed(var4);
                }

                throw var5;
            }
        } catch (IOException var6) {
            this.io.println("Ошибка при запуске сервера: " + var6.getMessage());
        }
    }

    public static List<Handler> getClient() {
        return clients;
    }

    public synchronized void removeClient(Handler client) {
        clients.remove(client);
    }

    public synchronized void broadcast(Message message) {
        Iterator var2 = clients.iterator();

        while(var2.hasNext()) {
            Handler client = (Handler)var2.next();
            if (!client.equals(message.getHandler())) {
                client.sendMessage(message.toString());
            }
        }

    }

    public static int getPING_TIMEOUT() {
        return 10000;
    }
}
