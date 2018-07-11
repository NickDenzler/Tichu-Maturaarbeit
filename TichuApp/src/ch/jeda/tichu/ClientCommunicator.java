package ch.jeda.tichu;


import ch.jeda.*;
import ch.jeda.ui.*;
import ch.jeda.event.*;
import static ch.jeda.ui.ViewFeature.*;
import java.util.Collections;


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
	 
	public void send(String text) {
            connection.sendLine(controller.playerNumber+":"+text);
	}
	 
        @Override
	public void onMessageReceived(MessageEvent event) {
//            System.out.println(event.getLine());
            
            String[] parts = event.getLine().split(":");
            String message = parts[1];
            String mType = parts[0];
            String player = "";
            if (parts.length > 2){
                player = parts[2];
            }
            
            if(mType.equals("Cards")){
                String[] ids = message.split(",");
                for(String s : ids){
                    int x = Integer.parseInt(s);
                    controller.myCards.add(controller.cards[x]);
                }
                Collections.sort(controller.myCards);
                
                for(Card c : controller.myCards){
                    System.out.println(c.getString());
                }
                controller.board.draw();
                
            }
            else if(mType.equals("SchupfedCards")){
                String[] ids = message.split(",");
                for(String s : ids){
                    int x = Integer.parseInt(s);
                    controller.myCards.add(controller.cards[x]);
                }
                Collections.sort(controller.myCards);
                controller.board.draw();
            }
            
            else if(mType.equals("Error")){
                controller.board.message(message);
            }
            else if(mType.equals("Played")){
                String[] ids = message.split(",");
                if(player.equals(controller.playerNumber)){
                    for(String s : ids){
                        int x = Integer.parseInt(s);
                        controller.myCards.remove(controller.cards[x]);
                    }
                }
            }
            else if(mType.equals("Message")){
                System.out.println(message);
                if(message.startsWith("Wollkommen Spieler nr.1")){
//                    int x = Integer.parseInt(message.split(".")[1]);
                    int x = 1;
                    controller.playerNumber = x;
                    System.out.println("playerNumber = "+x);
                }
                else if(message.startsWith("Wollkommen Spieler nr.2")){
//                    int x = Integer.parseInt(message.split(".")[1]);
                    int x = 2;
                    controller.playerNumber = x;
                    System.out.println("playerNumber = "+x);
                }
                else if(message.startsWith("Wollkommen Spieler nr.3")){
//                    int x = Integer.parseInt(message.split(".")[1]);
                    int x = 3;
                    controller.playerNumber = x;
                    System.out.println("playerNumber = "+x);
                }
                else if(message.startsWith("Wollkommen Spieler nr.4")){
//                    int x = Integer.parseInt(message.split(".")[1]);
                    int x = 4;
                    controller.playerNumber = x;
                    System.out.println("playerNumber = "+x);
                }
                
            }
            
            else{
                System.out.println("Unerwartete Meldung:" + event.getLine());
            }
	}
	 
}
 
