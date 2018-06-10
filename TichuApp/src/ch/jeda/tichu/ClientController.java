package ch.jeda.tichu;


import ch.jeda.*;
import ch.jeda.ui.*;
import ch.jeda.event.*;
import static ch.jeda.ui.ViewFeature.*;
import java.util.*;


public class ClientController extends Program{
 
	private boolean isPlaying;
	 
	private ArrayList<Card> cards;
	 
	private ArrayList<Card> myCards;
	 
	private Board board;
	 
	private ClientCommunicator communicator;
	 
	private CardSelector cardSelector;
	 
	private Card card;
	 
	private PlayButton playButton;
	 
	private PassButton passButton;
	 
        @Override
	public void run() {
            communicator = new ClientCommunicator(this);
            board = new Board(this);
            
	}
	 
}
 
