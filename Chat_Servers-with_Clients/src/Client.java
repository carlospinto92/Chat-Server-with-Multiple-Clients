import javax.crypto.Cipher;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Client {


    public static String host = "127.0.0.1";
    private String user;
    private Socket socketClient;
    PrintWriter out;


    public Client(String user) throws IOException {
        this.user = user;
        this.socketClient = new Socket(host, 10101);
        //listen();
        sendMessage();
    }


    public void listen() throws IOException {

        while (socketClient.isConnected()) {

            BufferedReader in = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
            System.out.println(in.readLine());


            //in.close();

        }

    }

    public void sendMessage() throws IOException {

        while (socketClient.isConnected()) {
            Scanner sc = new Scanner(System.in);
            System.out.println(" <<< Write your message >>> \r\n");
            String message = sc.nextLine();


            out = new PrintWriter(socketClient.getOutputStream(), true);
            out.println(message);


        }


        //out.close();


    }


    public static void main(String[] args) {

        Socket clientSocket;

        try {
            Scanner sc = new Scanner(System.in);
            String user = sc.nextLine();
            new Client(user);


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
