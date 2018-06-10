package ch.jeda.tichu;


import ch.jeda.*;
import ch.jeda.ui.*;
import ch.jeda.event.*;
import static ch.jeda.ui.ViewFeature.*;
import java.util.*;


public class ServerController {
 
	private Combination currentComb;
	 
	private Player currentPlayer;
	 
	private ArrayList<Combination> combinations;
	 
	private Evaluator evaluator;
	 
	private Counter counter;
	
        Canvas canvas;
        
	ServerMain main;
	 
	Player player1;
        Player player2;
        Player player3;
        Player player4;
	 
	private ServerCommunicator communicator;
        
        protected ServerController(ServerMain main)
        {
            this.main = main;
            communicator = new ServerCommunicator(this);
            
            
            
            player1 = new Player(this);
            player2 = new Player(this);
            player3 = new Player(this);
            player4 = new Player(this);
            
        }
	 
	public void mix() {
	 
	}
	 
}
 
