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
            if(!controller.isPlaying){
                controller.board.message("Du bist nicht an der Reihe");
            }
            else if(controller.cardSelector.selected.isEmpty()){
                controller.board.message("Du musst mind. 1 Karte wählen");
            }
            else{
                for(Card c: controller.cardSelector.selected){
                    s = s + c.id + ",";
                    c.isSelected = false;
                }
                s = s.substring(0, s.length()-1);
                controller.communicator.send(s);
                controller.cardSelector.selected.clear();
                System.out.println(s);
                controller.board.draw();
            }
	}
	 
}
 
