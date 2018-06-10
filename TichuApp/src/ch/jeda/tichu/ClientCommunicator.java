package ch.jeda.tichu;


import ch.jeda.*;
import ch.jeda.ui.*;
import ch.jeda.event.*;
import static ch.jeda.ui.ViewFeature.*;


public class ClientCommunicator implements MessageReceivedListener{
	 
	ClientController controller;
        
        TcpConnection connection;
        String servername ="Legolas";
        int port=1616;
        
        
        
        ClientCommunicator(ClientController controller){
            this.controller = controller;
            connection = new TcpConnection();
            connection.open(servername, port);
            Jeda.addEventListener(this);
        }
	 
	public void send() {
	 
	}
	 
        @Override
	public void onMessageReceived(MessageEvent event) {
            System.out.println(event.getLine());
            
	}
	 
}
 
