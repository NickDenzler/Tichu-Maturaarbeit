package ch.jeda.tichu;


import ch.jeda.*;
import ch.jeda.ui.*;
import ch.jeda.event.*;
import static ch.jeda.ui.ViewFeature.*;
import java.util.*;


public class Player {
 
	private int playerNumber;
	 
	ArrayList<Card> cards;
	 
	ArrayList<Card> wonCards;
	 
	boolean finished;
	 
	boolean announcedTichu;
	 
	boolean announcedBigTichu;
	 
	Combination lastPlayed;
	 
	Combination combination;
	 
	Card card;
	 
	ServerController controller;
	 
	Player(ServerController controller)
        {
            cards = new ArrayList<Card>();
        }
        
        public void play() {
	 
	}
	 
	public void pass() {
	 
	}
	 
}
 
