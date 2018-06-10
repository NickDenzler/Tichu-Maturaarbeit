package ch.jeda.tichu;


import ch.jeda.*;
import ch.jeda.ui.*;
import ch.jeda.event.*;
import static ch.jeda.ui.ViewFeature.*;


public class ServerMain extends Program{
 
	public int playerNumber;
	 
	private ServerController controller;
        
        
	 
        @Override
	public void run() {
            controller = new ServerController(this);
            
	}
	 
}
 
