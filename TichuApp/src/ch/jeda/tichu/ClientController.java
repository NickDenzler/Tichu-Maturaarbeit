package ch.jeda.tichu;


import ch.jeda.*;
import ch.jeda.ui.*;
import ch.jeda.event.*;
import static ch.jeda.ui.ViewFeature.*;
import java.util.*;


public class ClientController extends Program{
 
	boolean isPlaying;
        
        int playerNumber;
	 
	Card[] cards = new Card[56];
	 
	ArrayList<Card> myCards = new ArrayList<Card>();
	 
	Board board;
	 
	ClientCommunicator communicator;
	 
	CardSelector cardSelector;
	 
	private Card card;
	 
	PlayButton playButton;
	 
	PassButton passButton;
	 
        @Override
	public void run() {
            
            board = new Board(this);
            cards = new Card[56];
            String[] col = {"blue","black","red","green"};
            int id = 0;
            for (String c : col){
                for(int i=0; i<13; i++){
                    cards[id] = new Card(id,i+2,c);
                    id++;
                }
            }
            cards[id]=new Card(id,15,"Dragon");
            cards[id+1]=new Card(id+1,1,"MahJong");
            cards[id+2]=new Card(id+2,0,"Dog");
            cards[id+3]=new Card(id+3,-1,"Phoenix");
            communicator = new ClientCommunicator(this);
            board.draw();
	}
	 
}
 
