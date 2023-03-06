

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server {
    static int port = 10101;
    private static ArrayList<ServerWorkers> arrayList = new ArrayList<>();

    // private static int clientsCounter=0;

    private static boolean active = true;


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

                // Block waiting for client connections
                clientSocket = serverSocket.accept();
                ++clientsCounter;
                System.out.println(clientsCounter + " : ==> Client accepted !! ");

                // Create a new Server Worker
                ServerWorkers serverWorkers = new ServerWorkers(clientSocket, "USER : " + clientsCounter, arrayList);
                arrayList.add(serverWorkers);

                // Serve the client connection with a new Thread
                Thread thread = new Thread(serverWorkers);
                thread.setName(serverWorkers.getUser());
                System.out.println(thread.getName() + " entrou no chat");
                thread.start();


            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

/*
    public static void main(String[] args) {
       // String user;
        ServerSocket serverSocket = null;

        try {

            serverSocket = new ServerSocket(port);



        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        Socket clientSocket;


        while(true){

            try {

                clientSocket = serverSocket.accept();

                ++clientsCounter;
                System.out.println(clientsCounter + " : ==> Client accepted !! " );


                //BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                //BufferedWriter output = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                ServerWorkers serverWorkers = new ServerWorkers(clientSocket,"USER : " + clientsCounter,arrayList);

                Thread thread = new Thread(serverWorkers);
                thread.setName(serverWorkers.getUser());

                System.out.println(thread.getName()+ " entrou no chat");


                arrayList.add(serverWorkers);
                thread.start();




            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

    */

    public static void main(String[] args) {

        Server server = new Server();
        server.startServer(port);
    }

}
