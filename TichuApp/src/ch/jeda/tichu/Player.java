package ch.jeda.tichu;


import ch.jeda.*;
import ch.jeda.ui.*;
import ch.jeda.event.*;
import static ch.jeda.ui.ViewFeature.*;
import java.util.*;


public class Player {
 
	private int playerNumber;
	 
	private ArrayList<Card> cards;
	 
	private ArrayList<Card> wonCards;
	 
	private boolean finished;
	 
	private boolean announcedTichu;
	 
	private boolean announcedBigTichu;
	 
	private Combination lastPlayed;
	 
	private Combination combination;
	 
	private Card card;
	 
	private ServerController controller;
	 
	Player(ServerController controller)
        {
            
        }
        
        public void play() {
	 
	}
	 
	public void pass() {
	 
	}
	 
}
 
