package ch.jeda.tichu;


import ch.jeda.*;
import ch.jeda.ui.*;
import ch.jeda.event.*;
import static ch.jeda.ui.ViewFeature.*;
import java.util.ArrayList;
import java.util.Collections;


public class ServerCommunicator implements MessageReceivedListener, 
                                               ConnectionAcceptedListener {
 
	ServerController controller;
        
        TcpServer server;
        ArrayList<Connection> connections = new ArrayList<Connection>();
        
        
        String p1 = "SchupfedCards:k1,k2,k3";
        String p2 = "SchupfedCards:k1,k2,k3";
        String p3 = "SchupfedCards:k1,k2,k3";
        String p4 = "SchupfedCards:k1,k2,k3";
        
        int schupfed = 0;

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
                
                
                String[] ids = message.split(",");
                int[] iIds = new int[3];
                for(int i=0;i<3;i++){
                    iIds[i]=Integer.parseInt(ids[i]);
                }
                switch(Integer.parseInt(sender)){
                    case 1:
                        controller.player4.cards.add(controller.cards[iIds[0]]);
                        controller.player2.cards.add(controller.cards[iIds[1]]);
                        controller.player3.cards.add(controller.cards[iIds[2]]);
                        p4=p4.replaceFirst("k2", ids[0]);
                        p2=p2.replaceFirst("k1", ids[1]);
                        p3=p3.replaceFirst("k3", ids[2]);
//                            controller.p1Schupfed=true;
                        break;
                    case 2:
                        controller.player1.cards.add(controller.cards[iIds[0]]);
                        controller.player3.cards.add(controller.cards[iIds[1]]);
                        controller.player4.cards.add(controller.cards[iIds[2]]);
                        p1=p1.replaceFirst("k2", ids[0]);
                        p3=p3.replaceFirst("k1", ids[1]);
                        p4=p4.replaceFirst("k3", ids[2]);
//                            controller.p2Schupfed=true;
                        break;
                    case 3:
                        controller.player2.cards.add(controller.cards[iIds[0]]);
                        controller.player4.cards.add(controller.cards[iIds[1]]);
                        controller.player1.cards.add(controller.cards[iIds[2]]);
                        p2=p2.replaceFirst("k2", ids[0]);
                        p4=p4.replaceFirst("k1", ids[1]);
                        p1=p1.replaceFirst("k3", ids[2]);
//                            controller.p3Schupfed=true;
                        break;
                    case 4:
                        controller.player3.cards.add(controller.cards[iIds[0]]);
                        controller.player1.cards.add(controller.cards[iIds[1]]);
                        controller.player2.cards.add(controller.cards[iIds[2]]);
                        p3=p3.replaceFirst("k2", ids[0]);
                        p1=p1.replaceFirst("k1", ids[1]);
                        p2=p2.replaceFirst("k3", ids[2]);
//                            controller.p4Schupfed=true;
                        break;
                }
                schupfed++;
                if(schupfed==4){
                    send(1,p1);
                    send(2,p2);
                    send(3,p3);
                    send(4,p4);
                    controller.currentPlayer = controller.findStarter();
                }
            }
            else if(mType.equals("Play")){
                int n = Integer.parseInt(sender);
                if(n == controller.currentPlayer.playerNumber){
                    ArrayList<Card> played = new ArrayList<Card>();
                    String[] ids = message.split(",");
                    for(String s : ids){
                        int x = Integer.parseInt(s);
                        played.add(controller.cards[x]);
                    }
                    Collections.sort(played);
                    Combination comb = controller.evaluator.evaluate(played);
                    if(comb == null){
                        send(n, "Error:Falsche oder zu tiefe Kombination");
                    }
                    else {
                        controller.currentComb = comb;
                        controller.combinations.add(comb);
                        
                        String cards = "Played:"   +n+":";
                        for(Card c: played){
                            cards=cards+c.id+",";
                        }
                        cards = cards.substring(0, cards.length()-1);
                        for(int i = 1;i<5;i++){
                            send(i,cards);
                        }
                    }
                }
                else{
                    send(n, "Error:Du bist nicht an der Reihe");
                }
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
 
