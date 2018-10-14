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
            if(text.equals("YourTurn:true")){
                if(controller.players[n-1].finished){
                    if(n == 4){
                        n = 1;
                    }
                    else{
                        n++;
                    }
                    switch (n){
                        case 1:
                            controller.currentPlayer = controller.player1;
                            break;
                        case 2:
                            controller.currentPlayer = controller.player2;
                            break;
                        case 3:
                            controller.currentPlayer = controller.player3;
                            break;
                        case 4:
                            controller.currentPlayer = controller.player4;
                            break;
                        
                    }
                            
                                
//                    controller.currentPlayer = controller.players[n-1];
                    send(n,text);
                    return;
                }
//                System.out.println("Spieler "+controller.currentPlayer.playerNumber+" ist an der Reihe");
            }
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
                        controller.player1.cards.remove(controller.cards[iIds[0]]);
                        controller.player1.cards.remove(controller.cards[iIds[1]]);
                        controller.player1.cards.remove(controller.cards[iIds[2]]);
                        
//                            controller.p1Schupfed=true;
                        break;
                    case 2:
                        controller.player1.cards.add(controller.cards[iIds[0]]);
                        controller.player3.cards.add(controller.cards[iIds[1]]);
                        controller.player4.cards.add(controller.cards[iIds[2]]);
                        p1=p1.replaceFirst("k2", ids[0]);
                        p3=p3.replaceFirst("k1", ids[1]);
                        p4=p4.replaceFirst("k3", ids[2]);
                        controller.player2.cards.remove(controller.cards[iIds[0]]);
                        controller.player2.cards.remove(controller.cards[iIds[1]]);
                        controller.player2.cards.remove(controller.cards[iIds[2]]);
//                            controller.p2Schupfed=true;
                        break;
                    case 3:
                        controller.player2.cards.add(controller.cards[iIds[0]]);
                        controller.player4.cards.add(controller.cards[iIds[1]]);
                        controller.player1.cards.add(controller.cards[iIds[2]]);
                        p2=p2.replaceFirst("k2", ids[0]);
                        p4=p4.replaceFirst("k1", ids[1]);
                        p1=p1.replaceFirst("k3", ids[2]);
                        controller.player3.cards.remove(controller.cards[iIds[0]]);
                        controller.player3.cards.remove(controller.cards[iIds[1]]);
                        controller.player3.cards.remove(controller.cards[iIds[2]]);
//                            controller.p3Schupfed=true;
                        break;
                    case 4:
                        controller.player3.cards.add(controller.cards[iIds[0]]);
                        controller.player1.cards.add(controller.cards[iIds[1]]);
                        controller.player2.cards.add(controller.cards[iIds[2]]);
                        p3=p3.replaceFirst("k2", ids[0]);
                        p1=p1.replaceFirst("k1", ids[1]);
                        p2=p2.replaceFirst("k3", ids[2]);
                        controller.player4.cards.remove(controller.cards[iIds[0]]);
                        controller.player4.cards.remove(controller.cards[iIds[1]]);
                        controller.player4.cards.remove(controller.cards[iIds[2]]);
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
                    send(controller.currentPlayer.playerNumber,"YourTurn:true");
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
                    if(played.get(0).color.equals("Phoenix")){
                        played.get(0).value = controller.Phoenix;
                    }
                    Combination comb = controller.evaluator.evaluate(played);
                    if(comb == null){
                        send(n, "Error:Falsche oder zu tiefe Kombination");
                    }
                    else {
                        comb.player = controller.players[n-1];
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
                        switch(n){
                            case 1:
                                for(Card c: played){
                                    controller.player1.cards.remove(c);
                                }
                                if(comb.cards.get(0).color.equals("Dog")){
                                    controller.currentPlayer = controller.player3;
                                    send(3,"YourTurn:true");
                                }
                                else{
                                    controller.currentPlayer = controller.player2;
                                    send(2,"YourTurn:true");
                                }
                                send(1,"YourTurn:false");
                                break;
                                
                            case 2:
                                for(Card c: played){
                                    controller.player2.cards.remove(c);
                                }
                                if(comb.cards.get(0).color.equals("Dog")){
                                    controller.currentPlayer = controller.player4;
                                    send(4,"YourTurn:true");
                                }
                                else{
                                    controller.currentPlayer = controller.player3;send(3,"YourTurn:true");
                                    send(3,"YourTurn:true");
                                }
                                send(2,"YourTurn:false");
                                break;
                            case 3:
                                for(Card c: played){
                                    controller.player3.cards.remove(c);
                                }
                                if(comb.cards.get(0).color.equals("Dog")){
                                    controller.currentPlayer = controller.player1;
                                    send(1,"YourTurn:true");
                                }
                                else{
                                    controller.currentPlayer = controller.player4;
                                    send(4,"YourTurn:true");
                                }
                                send(3,"YourTurn:false");
                                break;
                            case 4:
                                for(Card c: played){
                                    controller.player4.cards.remove(c);
                                }
                                if(comb.cards.get(0).color.equals("Dog")){
                                    controller.currentPlayer = controller.player2;
                                    send(2,"YourTurn:true");
                                }
                                else{
                                    controller.currentPlayer = controller.player1;
                                    send(1,"YourTurn:true");
                                }
                                send(4,"YourTurn:false");
                                break;
                            }
                            
                        
                    }
                }
                else{
                    send(n, "Error:Du bist nicht an der Reihe");
                }
            }
            else if(mType.equals("Pass")){
                int n = Integer.parseInt(sender);
                boolean pass = true;
                if(n == controller.currentPlayer.playerNumber){
                    
                    if(controller.currentComb.cards.size() == 1 &&
                    controller.currentComb.cards.get(0).color.equals("MahJong")){
                        for(Card c : controller.players[n-1].cards){
                            if(c.value == controller.MahJong){
                                pass = false;
                                send(n,"Error:Du musst die gewünschte Karte spielen");
                            }
                        }
                    }
                    if(pass){
                        for(int i = 1; i < 5; i++){
                        send(i,"Passed:"+n);
                        }
                        switch(n){
                            case 1:
                                controller.currentPlayer = controller.player2;
                                send(2,"YourTurn:true");
                                send(1,"YourTurn:false");
                                break;
                            case 2:
                                controller.currentPlayer = controller.player3;
                                send(3,"YourTurn:true");
                                send(2,"YourTurn:false");
                                break;
                            case 3:
                                controller.currentPlayer = controller.player4;
                                send(4,"YourTurn:true");
                                send(3,"YourTurn:false");
                                break;
                            case 4:
                                controller.currentPlayer = controller.player1;
                                send(1,"YourTurn:true");
                                send(4,"YourTurn:false");
                                break;
                        }
                    }
                    
                }
                else{
                    send(n,"Error:Du bist nicht an der Reihe");
                }
            }
            else if(mType.equals("Finnished")){
                    int n = Integer.parseInt(sender);
                    if(!controller.finnished.isEmpty()){
                        if(controller.finnished.size() == 1){
                            if(n<3){
                                if(controller.players[n+1].playerNumber == controller.finnished.get(0).playerNumber){
                                    controller.doubleWin = true;
                                }
                            }
                            else{
                                if(controller.players[n-3].playerNumber == controller.finnished.get(0).playerNumber){
                                    controller.doubleWin = true;
                                }
                            }
                        }
                        else if(controller.finnished.size()==2){
                            controller.roundOver = true;
                        }
                    }
                     switch (n){
                        case 1:
                            controller.player1.finished = true;
                            controller.finnished.add(controller.player1);
                            break;
                        case 2:
                           controller.player2.finished = true;
                            controller.finnished.add(controller.player2);
                            break;
                        case 3:
                            controller.player3.finished = true;
                            controller.finnished.add(controller.player3);
                            break;
                        case 4:
                            controller.player4.finished = true;
                            controller.finnished.add(controller.player4);
                            break;
                        
                    }
                    
//                    controller.finnished.add(controller.players[n-1]);
//                    controller.players[n-1].finished = true;
                    for(int i = 1; i<5; i++){
                        send(i,"Finnished:"+n);
                    }
                    if(controller.roundOver){
                        int x = 0;
                        for(Player p : controller.players){
                            if(!p.finished){
                                for(Card c : p.wonCards){
                                    controller.finnished.get(0).wonCards.add(c);
                                }
                                if(p.playerNumber > 2){
                                    for(Card c : p.cards){
                                        controller.players[p.playerNumber-3].wonCards.add(c);
                                    }
                                }
                                if(p.playerNumber < 3){
                                    for(Card c : p.cards){
                                        controller.players[p.playerNumber+1].wonCards.add(c);
                                    }
                                }
                            }
                            if(p.playerNumber == 1 || p.playerNumber == 3){
                                x = x + controller.counter.count(p.wonCards);
                            }
                        }
                        controller.counter.pointsTeamA += x;
                        int y = controller.counter.calculate(x);
                        controller.counter.pointsTeamB += y;
                        for(int i = 1; i < 5; i++){
                            send(i,"RoundOver:"+x+","+y+","+controller.counter.pointsTeamA+","+controller.counter.pointsTeamB);
                        }
//                        controller.mix();
                    }
                    else if(controller.doubleWin){
                        int x = 0;
                        int y = 0;
                        if(n == 1 || n == 3){
                            x = 200;
                        }
                        else if(n == 2 || n == 4){
                            y = 200;
                        }
                        controller.counter.pointsTeamB += y;
                        controller.counter.pointsTeamA += x;
                        for(int i = 1; i < 5; i++){
                            send(i,"RoundOver:"+x+","+y+","+controller.counter.pointsTeamA+","+controller.counter.pointsTeamB);
                        }
//                        controller.mix();
                    }
                }
            else if(mType.equals("Won")){
                int n = Integer.parseInt(sender);
                int x = 0;
                int p;
                if(n==4){
                    x = 1;
                }
                else{
                    x = n+1;
                }
                if(controller.currentComb.player.playerNumber == x || controller.players[x-1].finished) {
                    System.out.println("test");
                } 
                else {
                    if(controller.currentComb.type.equals("SingleCard") && controller.currentComb.cards.get(0).color.equals("Dragon")){
                        p = controller.Dragon;
                        System.out.println("Drache wurde Spieler "+p+" gegeben.");
                    }
                    else{
                        p = n;
                    }
                    for(Combination comb : controller.combinations){
                        for(Card c : comb.cards){
                            controller.players[p-1].wonCards.add(controller.cards[c.id]);
                        }
                    }
                    controller.combinations.clear();
                    controller.currentComb = null;
                    for(int i = 1; i<5; i++){
                        send(i,"Won:"+n);
                    }
                }
                
            }
            else if(mType.equals("Phoenix")){
                controller.Phoenix = Integer.parseInt(message);
            }
            else if(mType.equals("MahJong")){
                controller.MahJong = Integer.parseInt(message);
                for(int i = 1; i < 5;i ++){
                    send(i,"Error:"+controller.MahJong+" wurde gewünscht");
                }
            }
            else if(mType.equals("Dragon")){
                controller.Dragon = Integer.parseInt(message);
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
 
