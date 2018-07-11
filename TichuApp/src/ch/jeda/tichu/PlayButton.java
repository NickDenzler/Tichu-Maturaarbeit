package ch.jeda.tichu;


import ch.jeda.*;
import ch.jeda.ui.*;
import ch.jeda.event.*;
import static ch.jeda.ui.ViewFeature.*;
import java.util.*;

public class PlayButton extends TextButton{
 
	ClientController controller;
	 
        PlayButton(double x, double y, double width, double height, String text, ClientController controller){
            super(x, y, text, 0);
            this.controller = controller;
            this.setWidth(width);
            this.setHeight(height);
            
        }
        
        @Override
	public void clicked() {
            String s = "Play:";
            for(Card c: controller.cardSelector.selected){
                s = s + c.id + ",";
            }
            s = s.substring(0, s.length()-1);
            controller.communicator.send(s);
            System.out.println(s);
	}
	 
}
 
