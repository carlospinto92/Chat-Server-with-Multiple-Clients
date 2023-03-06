import java.io.*;
import java.net.Socket;
import java.sql.SQLOutput;
import java.util.ArrayList;

public class ServerWorkers implements Runnable {

    private String user;
    private Socket clientSocket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private boolean active;
    private ArrayList<ServerWorkers> serverWorkers;

    /*public String getUser() {
        return user;
    }*/


    public ServerWorkers(Socket clientSocket, String user, ArrayList serverWorkers) throws IOException {
        //this.user = user;
        this.clientSocket = clientSocket;
        this.serverWorkers = serverWorkers;
        reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        this.user= reader.readLine();
        this.active = true;
    }

    public String getUser() {
        return user;
    }

    @Override
    public void run() {

            System.out.println(user + " entrou crl");


        while (!clientSocket.isClosed()) {
            String msg = "";
            try {

                msg = reader.readLine();

                if(msg.equals("/quit")){
                    System.out.println(user + " out");
                    clientSocket.close();
                    active=false;
                    serverWorkers.remove(this);
                    reader.close();
                    writer.close();
                    break;
                }

                // System.out.println(msg);

            } catch (IOException e) {

                e.printStackTrace();
            }

            for (ServerWorkers sw : serverWorkers) {
                try {

                    if (!sw.user.equals(user)) {

                        sw.writer.write(user + ": " + msg);
                        sw.writer.newLine();
                        sw.writer.flush();
                        break;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }


            //writer.write(user + ": "+ msg);
            //writer.newLine();
            //writer.flush();


            //socket.close();

            // reader.close();
            //writer.close();


        }
    }
}