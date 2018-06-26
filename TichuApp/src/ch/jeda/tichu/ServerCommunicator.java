package ch.jeda.tichu;


import ch.jeda.*;
import ch.jeda.ui.*;
import ch.jeda.event.*;
import static ch.jeda.ui.ViewFeature.*;
import java.util.ArrayList;


public class ServerCommunicator implements MessageReceivedListener, 
                                               ConnectionAcceptedListener {
 
	ServerController controller;
        
        TcpServer server;
        ArrayList<Connection> connections = new ArrayList<Connection>();

        ServerCommunicator(ServerController controller) {
            this.controller = controller;
            startServer(1616);
            Jeda.addEventListener(this);
            
        }

        public void startServer(int port) {

            server = new TcpServer();
            if(server.start(port)){
                System.out.println("Server gestarted");
            }
            
            
        }

        public void send(int n, String text) {
            connections.get(n-1).sendLine(text);
        }

        @Override
        public void onMessageReceived(MessageEvent event) {
            String message = event.getLine().split(":")[2];
            String mType = event.getLine().split(":")[1];
            String sender = event.getLine().split(":")[0];
            
            if (mType.equals("SchupfCards")){
                
                
            }
            else if(mType.equals("Play")){
                
            }
            else if(mType.equals("Pass")){
                
            }
            else {
                System.out.println("Unerwartete Meldung:" + event.getLine());
            }
        }
        
    
        @Override
        public void onConnectionAccepted(ConnectionEvent event) {
            if (4<=connections.size())
            {
                event.getConnection().sendLine("Message:Es sind bereits vier Spieler in diesem Spiel");
                event.getConnection().close();
            }
            connections.add(event.getConnection());
            int x = connections.size();
            event.getConnection().sendLine("Message:Wollkommen Spieler nr."+x);
            System.out.println("verbunden");
            if(connections.size()==4){
                controller.mix();
            }
        }
	 
}
 
