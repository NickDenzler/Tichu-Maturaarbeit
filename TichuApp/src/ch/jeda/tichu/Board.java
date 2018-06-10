package ch.jeda.tichu;


import ch.jeda.*;
import ch.jeda.ui.*;
import ch.jeda.event.*;
import static ch.jeda.ui.ViewFeature.*;


public class Board {
 
	private View view;
	 
	private Canvas background;
	 
	private ServerMain serverMain;
	 
	private ClientController controller;
	 
	Board(ClientController controller){
            this.controller = controller;
            view = new View(1600,900);
            background = view.getBackground();
            
        }
        
        public void draw() {
	 
	}
	 
}
 
