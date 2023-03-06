import javax.crypto.Cipher;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;

public class Client {


    public static String host = "127.0.0.1";
    public static String user;
    private Socket socketClient;
    PrintWriter out;


    public String getUser() {
        return user;
    }

    public Client(String user) throws IOException {
        this.user = user;
        this.socketClient = new Socket(host, 10101);
        socketClient.getOutputStream().write(user);
        //readMessage.start();
        //sendMessage.start();
    }


   /* public void listen() throws IOException {
        Thread listen = new Thread();
        listen.start();
        while (socketClient.isConnected()) {

            BufferedReader in = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
            System.out.println(in.readLine());

            //in.close();
        }

    } */

    Thread sendMessage = new Thread(new Runnable() {

        @Override
        public void run() {
            //out.println(user);

            while (!socketClient.isClosed()) {
            String message="";

                Scanner sc = new Scanner(System.in);
                System.out.println(" <<< Waiting your message >>>");
                message = sc.nextLine();

                if(message.equals("/quit")){
                    try {
                        socketClient.close();
                        sendMessage.interrupt();
                        readMessage.interrupt();
                        System.exit(1);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }

                try {

                    out = new PrintWriter(socketClient.getOutputStream(), true);
                    out.println(message);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            sendMessage.interrupted();
        }
    });

    Thread readMessage = new Thread(new Runnable() {
        @Override
        public void run() {
            while (!socketClient.isClosed()) {

                BufferedReader in=null;
                try {

                    in = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
                    System.out.println(in.readLine());


                } catch (IOException e) {
                    e.printStackTrace();
                }
                //in.close();
            }
            readMessage.interrupt();

        }
    });

   /* public void sendMessage() throws IOException {

        while (socketClient.isConnected()) {
            Scanner sc = new Scanner(System.in);
            System.out.println(" <<< Waiting your message >>>");
            String message = sc.nextLine();


            out = new PrintWriter(socketClient.getOutputStream(), true);
            out.println(message);


        }


        //out.close();


    }*/


    public static void main(String[] args) {

        //Socket clientSocket = null;

        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("=============> | N A M E | <============= ");
            String user = sc.nextLine();
            Client client = new Client(user);
            client.readMessage.start();
            client.sendMessage.start();




            //client.sendMessage();

            //PrintWriter out = new PrintWriter(clientSocket.getOutputStream(),true);
            //out.println(user);

            //ClientWorkers clientWorkers1 = new ClientWorkers(clientSocket,user, clientWorkers);


        } catch (IOException e) {
            e.printStackTrace();
        }


       /* PrintWriter out = null;

        try {


            out = new PrintWriter(clientSocket.getOutputStream(), true);
            out.println(message);

            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            System.out.println(in.readLine());


            in.close();
            out.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        try {

            clientSocket.close();


        } catch (IOException e) {
            e.printStackTrace();
        }*/


    }


}
