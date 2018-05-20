package ch.jeda.project;

import ch.jeda.*;
import ch.jeda.ui.*;
import ch.jeda.event.*;
import java.util.ArrayList;

public class MainClient extends Program {
 
	public int playerNumber;
	 
	private Board board;
	 
	private CardSelector cardSelector;
	 
	private ClientCommunicator communicator;
	 
	public void run() {
            board = new Board(this);
            board.draw();
	 
	}
	 
}
 
