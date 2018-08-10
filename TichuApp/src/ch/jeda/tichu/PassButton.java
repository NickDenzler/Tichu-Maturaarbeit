package ch.jeda.tichu;


import ch.jeda.*;
import ch.jeda.ui.*;
import ch.jeda.event.*;
import static ch.jeda.ui.ViewFeature.*;


public class PassButton extends TextButton{
 
	ClientController controller;
	
        PassButton(double x, double y, double width, double height, String text, ClientController controller){
            super(x, y, text, 0);
            this.controller = controller;
            this.setWidth(width);
            this.setHeight(height);
            
        }
        
        
        @Override
	public void clicked() {
            if(controller.isPlaying){
                controller.communicator.send("Pass: ");
            }
	}
	 
}
 
