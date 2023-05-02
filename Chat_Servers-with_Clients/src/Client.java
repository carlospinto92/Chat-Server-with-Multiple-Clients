import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    public static String host = "127.0.0.1";
    public static String user;
    private Socket socketClient;
    private PrintWriter out;
    private Scanner sc;

    public String getUser() {
        return user;
    }

    public Client() throws IOException {
        this.socketClient = new Socket(host, 10101);
    }

    private String requestName() throws IOException {

        Scanner sc = new Scanner(System.in);
        System.out.println("=============> | N A M E | <============= ");
        user = sc.nextLine();
        out = new PrintWriter(socketClient.getOutputStream(), true);
        out.println(user);
        return user;
    }
    Thread sendMessage = new Thread(new Runnable() {

        @Override
        public void run() {
            try {
                requestName();

            } catch (IOException e) {
               e.printStackTrace();
            }
            while (!socketClient.isClosed()) {

                String message = "";
                sc = new Scanner(System.in);
                System.out.println(" <<< Waiting your message >>>");
                message = sc.nextLine();

                if (message.equals("/quit")) {
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
                BufferedReader in = null;
                try {
                    in = new BufferedReader(new InputStreamReader(socketClient.getInputStream()));
                    System.out.println(in.readLine());

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            readMessage.interrupt();
        }
    });

    public static void main(String[] args) {

        try {
            Client client = new Client();
            client.readMessage.start();
            client.sendMessage.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
