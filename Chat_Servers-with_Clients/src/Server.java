import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    static int port = 10101;
    private static ArrayList<ServerWorkers> arrayList = new ArrayList<>();

    public void startServer(int port) {

        int clientsCounter = 0;
        ServerSocket serverSocket = null;

        try {
            // Bind to local port
            System.out.println("Binding to port " + port + ", please wait  ...");
            serverSocket = new ServerSocket(port);
            System.out.println("Server started: " + serverSocket);

        } catch (IOException e) {
            e.printStackTrace();
        }
        Socket clientSocket;
        while (true) {
            try {
                String user = "";

                // Block waiting for client connections
                clientSocket = serverSocket.accept();
                ++clientsCounter;
                System.out.println(clientsCounter + " : ==> Client accepted !! ");

                // Create a new Server Worker
                ServerWorkers serverWorkers = new ServerWorkers(clientSocket, arrayList);
                arrayList.add(serverWorkers);

                // Serve the client connection with a new Thread
                Thread thread = new Thread(serverWorkers);
                thread.setName(serverWorkers.getUser());
                thread.start();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) throws IOException {

        Server server = new Server();
        server.startServer(port);
    }

}
