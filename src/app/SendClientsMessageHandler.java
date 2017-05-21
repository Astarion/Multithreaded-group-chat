package app;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.util.Pair;
import netUtils.Host;
import netUtils.MessageHandler;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

/**
 * Created by Alex on 12.05.2017.
 */
public class SendClientsMessageHandler implements MessageHandler {

    private Host host;
//    @FXML
//    private TextArea area;

    SendClientsMessageHandler(Host host) {
        this.host = host;
    }

    @Override
    public String handle(String message, Socket socket) {
        try {
            ArrayList<Pair<Socket, String>> clients =Host.allClients; //host.getAllClients();
            DataOutputStream dataOutputStream;
            String clientName;

            int j = 0;
            while (socket != clients.get(j).getKey() && j < clients.size()) {
//                if(clients.get(j).getKey().isClosed()) {
//                    String leftClient = clients.get(j).getValue();
//                    clients.remove(j);
//                    for (int k = 0; k < clients.size(); k++) {
//                        Socket client = clients.get(k).getKey();
//                        dataOutputStream = new DataOutputStream(client.getOutputStream());
//                        dataOutputStream.writeUTF(leftClient + ' ' + "has left chat room");
//                    }
//                }
                j++;
            }
            //TODO Если j > clients.size()


//            if (j> clients.size())
//                clientName="default";
//            else
            clientName = clients.get(j).getValue();
           /* if (message.equals("-1")) {
                clients.remove(j);
                for (int i = 0; i < clients.size(); i++) {
//                    if (i == j)
//                        continue;
                    Socket clientSocket = clients.get(i).getKey();
                    dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                    dataOutputStream.writeUTF(clientName + ' ' + "has left chat room");
                }
            }
         else*/ if (message.equals("+1")) {
                for (int i = 0; i < clients.size()&& clients.get(i).getValue()!=clientName; i++) {
//                    if (i == j)
//                        continue;
                    Socket clientSocket = clients.get(i).getKey();
                    dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                    dataOutputStream.writeUTF(clientName + ' ' + "has joined to chat room");
                }
            }
            else {
                for (int i = 0; i < clients.size(); i++) {
                    Socket clientSocket = clients.get(i).getKey();
//                    if (clientSocket.isClosed()) {
//                        String leftClient = clients.get(i).getValue();
//                        clients.remove(i);
//                        for (int k = 0; k < clients.size(); k++) {
//                            Socket clientLeft = clients.get(k).getKey();
//                            dataOutputStream = new DataOutputStream(clientLeft.getOutputStream());
//                            dataOutputStream.writeUTF(leftClient + ' ' + "has left chat room");
//                        }
//                        continue;
//                    }
                    dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                    dataOutputStream.writeUTF(clientName + ":>" + message);
//                }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "OK";
    }

    @Override
    public String handle(String message) {  //users online
        ArrayList<Pair<Socket, String>> clients =Host.allClients; //host.getAllClients();
        try {
            int i=0;
            Socket clientSocket;
            DataOutputStream dataOutputStream = null;
        for (i = 0; i < clients.size()-1; i++) {
            clientSocket = clients.get(i).getKey();
            dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
            dataOutputStream.writeUTF(message);
        }
            clientSocket = clients.get(i).getKey();
            dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
            for (i = 0; i < clients.size(); i++) {
            String msg="&/?"+clients.get(i).getValue();
            dataOutputStream.writeUTF(msg);
            }
        } catch (IOException e) {
        e.printStackTrace();
    }
        return null;
    }

}
