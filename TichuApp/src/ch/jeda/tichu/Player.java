package ch.jeda.tichu;


import ch.jeda.*;
import ch.jeda.ui.*;
import ch.jeda.event.*;
import static ch.jeda.ui.ViewFeature.*;
import java.util.*;


public class Player {
 
	int playerNumber;
	 
	ArrayList<Card> cards;
	 
	ArrayList<Card> wonCards;
	 
	boolean finished;
	 
	boolean announcedTichu;
	 
	boolean announcedBigTichu;
	 
	Combination lastPlayed;
	 
	Combination combination;
	 
	Card card;
	 
	ServerController controller;
	 
	Player(ServerController controller, int number)
        {
            cards = new ArrayList<Card>();
            this.controller = controller;
            playerNumber = number;
        }
        
        public void play() {
	 
	}
	 
	public void pass() {
	 
	}
	 
}
 
