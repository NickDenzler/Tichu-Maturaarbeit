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
//            System.out.println(event.getLine());
            
            
            String message = event.getLine().split(":")[1];
            String mType = event.getLine().split(":")[0];
            
            if(mType.equals("Cards")){
                String[] ids = message.split(",");
                
            }
            else if(mType.equals("Error")){
                
            }
            else if(mType.equals("Played")){
                String[] ids = message.split(",");
                
            }
            
            else{
                System.out.println("Unerwartete Meldung:" + event.getLine());
            }
	}
	 
}
 
