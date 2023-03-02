import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedList;

public class ServerWorkers implements Runnable {

    //private static ArrayList<ServerWorkers> serverWorkers = new ArrayList<>();
    private String user;
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private boolean active;
    private ArrayList<ServerWorkers> serverWorkers;
    //Client client;



    public ServerWorkers(Socket socket, String user,ArrayList serverWorkers) throws IOException {
        this.user = user;
        this.socket = socket;
        this.serverWorkers=serverWorkers;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
        this.active = true;

        //serverWorkers.add();
    }

    @Override
    public void run() {

        String msg = "";

        while (true) {

            try {

                msg = reader.readLine();
                System.out.println(msg);

            } catch (IOException e) {

                e.printStackTrace();
            }

            for (ServerWorkers sw : serverWorkers ) {
                try {

                    if(sw.user.equals(msg) && sw.active){
                        sw.writer.write(user + ": " + msg);
                        break;
                    }

                    //writer.write(msg);
                    System.out.println(sw);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
                //socket.close();

               // reader.close();
                //writer.close();








            }
    }
}