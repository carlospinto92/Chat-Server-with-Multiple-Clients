import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ServerWorkers implements Runnable {

    private String user;
    private Socket clientSocket;
    private BufferedReader reader;
    private BufferedWriter writer;
    private boolean active;
    private ArrayList<ServerWorkers> serverWorkers;

    public ServerWorkers(Socket clientSocket, ArrayList serverWorkers) throws IOException {
        this.clientSocket = clientSocket;
        this.serverWorkers = serverWorkers;
        reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        writer = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
        this.user = reader.readLine();
        active = true;
    }

    public String getUser() {
        return user;
    }

    @Override
    public void run() {
        while (!clientSocket.isClosed()) {
            String msg = "";
            try {
                msg = reader.readLine();
                if (msg.equals("/quit")) {
                    System.out.println(user + " out");
                    clientSocket.close();
                    active = false;
                    serverWorkers.remove(this);
                    reader.close();
                    writer.close();
                    break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            for (ServerWorkers sw : serverWorkers) {
                try {
                    if (!sw.user.equals(user)) {
                        sw.writer.write(user + ": " + msg);
                        sw.writer.newLine();
                        sw.writer.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
    }
}