

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class Server {
    static int port = 10101;
    private static ArrayList<ServerWorkers> arrayList = new ArrayList<>();

    private static int clientsCounter=0;
    private static boolean active=true;

    public static boolean isActive() {
        return active;
    }


    ServerWorkers serverWorkers;




    public void startServer(){

    }


    public static void main(String[] args) {
       // String user;
        ServerSocket serverSocket = null;

        try {

            serverSocket = new ServerSocket(port);



        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Socket clientSocket;

        while(true){

            try {

                Socket clientSocket = serverSocket.accept();

               // Scanner sc = new Scanner(System.in);
               // user = sc.toString();

                ++clientsCounter;
                System.out.println(clientsCounter + " : ==> Client accepted !!\n" );


                //BufferedReader input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
                //BufferedWriter output = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));

                //DataInputStream input = new DataInputStream(clientSocket.getInputStream());
               // DataOutputStream output = new DataOutputStream(clientSocket.getOutputStream());

                ServerWorkers serverWorkers = new ServerWorkers(clientSocket, "==USER--( "+ clientsCounter,arrayList);
                Thread thread = new Thread(serverWorkers);
                thread.start();

                arrayList.add(serverWorkers);



            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    }

}
