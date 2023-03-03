import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ClientWorkers implements Runnable {

    private ArrayList<ServerWorkers> serverWorkers;
    private String user;
    private Socket socket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private boolean active;

    public ClientWorkers(Socket socket, String user, ArrayList serverWorkers ) throws IOException {
        this.user = user;
        this.socket = socket;
        this.serverWorkers=serverWorkers;
        reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer= new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

    }



    @Override
    public void run() {


        String msg = "";
/*
        while (true) {

            try {

                msg = reader.readLine();
                System.out.println(msg);

            } catch (IOException e) {

                e.printStackTrace();
            }

            for (ServerWorkers cW : serverWorkers ) {

                try {

                    if(cW.getUser().equals(msg)&& cW.active){
                        cW.writer.write(user + " said: " + msg);
                        break;
                    }

                    //writer.write(msg);
                    System.out.println(cW);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            //socket.close();

            // reader.close();
            //writer.close();


        }
*/
    }
}
