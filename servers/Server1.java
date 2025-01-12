
import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class Server1 {

    private static final int SERVER_COMM_PORT = 12353;
    private static final int ADMIN_PORT = 1122;
    private static final int CLIENT_PORT = 1133;

    private final Map<String, String> clientData = new HashMap<>();

    public static void main(String[] args) {
        Server1 server = new Server1();

        new Thread(server::startServerCommunication).start();
        new Thread(server::startAdminListener).start();
        new Thread(server::startClientListener).start();

        server.connectToOtherServers();
    }

    public void startServerCommunication() {
        try (ServerSocket serverSocket = new ServerSocket(SERVER_COMM_PORT)) {
            System.out.println("Server communication port running on: " + SERVER_COMM_PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> handleServerRequest(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startAdminListener() {
        try (ServerSocket serverSocket = new ServerSocket(ADMIN_PORT)) {
            System.out.println("Admin port running on: " + ADMIN_PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> handleAdminRequest(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void startClientListener() {
        try (ServerSocket serverSocket = new ServerSocket(CLIENT_PORT)) {
            System.out.println("Client port running on: " + CLIENT_PORT);
            while (true) {
                Socket socket = serverSocket.accept();
                new Thread(() -> handleClientRequest(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void connectToOtherServers() {
        int[] otherServerPorts = {12351, 12356};
        for (int port : otherServerPorts) {
            try {
                Socket socket = new Socket("localhost", port);
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

                String messageToSend = "Hello from Server1!";
                out.writeObject(messageToSend);
                System.out.println("Sent to Server on port: " + port + " : " + messageToSend);

                String response = (String) in.readObject();
                System.out.println("Received from Server on port " + port + ": " + response);

                socket.close();
            } catch (IOException | ClassNotFoundException e) {
                System.out.println("Could not connect to server on port: " + port);
                e.printStackTrace();
            }
        }
    }

    private void handleServerRequest(Socket socket) {
        try (ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {
            String message = (String) in.readObject();
            System.out.println("Received from another server: " + message);

            synchronized (clientData) {
                String[] parts = message.split("->"); // Gelen veriyi "key -> value" formatında ayırıyoruz
                if (parts.length == 2) {
                    String clientAddress = parts[0].trim();
                    String clientMessage = parts[1].trim();
                    clientData.put(clientAddress, clientMessage);
                    System.out.println("Client data updated from server: " + clientAddress + " -> " + clientMessage);
                }
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void handleAdminRequest(Socket socket) {
        try (PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true)) {
            System.out.println("Admin client connected.");
            out.println("Server1 is ready for admin commands.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void handleClientRequest(Socket socket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream(), StandardCharsets.UTF_8)); PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true)) {
            String message = in.readLine();
            System.out.println("Received from client: " + message);
            sendDataToOtherServers(message);
            out.println("ACK: Message received by Server1.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void printClientData() {
        System.out.println("Current client data:");
        synchronized (clientData) {
            clientData.forEach((key, value) -> System.out.println(key + " -> " + value));
        }
    }

    private void sendDataToOtherServers(String data) {
        int[] otherServerPorts = {12351, 12353}; // Server2 ve Server3'ün portları
        for (int port : otherServerPorts) {
            try (Socket socket = new Socket("localhost", port); PrintWriter out = new PrintWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8), true)) {

                // Mesajı gönder
                out.println(data);
                System.out.println("Sent to Server on port: " + port + " : " + data);
            } catch (IOException e) {
                System.out.println("Could not send data to server on port: " + port);
                e.printStackTrace();
            }
        }
    }

}
