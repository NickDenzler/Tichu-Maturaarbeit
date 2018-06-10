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
        ArrayList connections = new ArrayList<Connection>();

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

        public void send() {
            
        }

        @Override
        public void onMessageReceived(MessageEvent event) {
            
        }
        
    
        @Override
        public void onConnectionAccepted(ConnectionEvent event) {
            if (4<=connections.size())
            {
                event.getConnection().sendLine("Es sind bereits vier Spieler in diesem Spiel");
                event.getConnection().close();
            }
            connections.add(event.getConnection());
            int x = connections.size();
            event.getConnection().sendLine("Wollkommen Spieler "+x);
            System.out.println("verbunden");
            
        }
	 
}
 
