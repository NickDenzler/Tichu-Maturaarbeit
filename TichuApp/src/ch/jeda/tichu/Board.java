package ch.jeda.tichu;


import ch.jeda.*;
import ch.jeda.ui.*;
import ch.jeda.event.*;
import static ch.jeda.ui.ViewFeature.*;


public class Board {
 
	private View view;
	 
	Canvas background;
	 
	private ServerMain serverMain;
	 
	private ClientController controller;
        
        double width;
        double height;
	 
	Board(ClientController controller){
            this.controller = controller;
            view = new View(1600,900);
            background = view.getBackground();
            width = background.getWidth();
            height = background.getHeight();
            
        }
        
        public void draw() {
//            background.drawImage(0, 0, width/15, height/6, Image.JEDA_LOGO_16x16);
            for(int i=0;i<controller.myCards.size();i++){
                Card c = controller.myCards.get(i);
                background.drawImage(i*width/16, 0, width/16, height/6, "");
            }
	 
	}
	 
}
 
