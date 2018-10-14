package ch.jeda.tichu;


import ch.jeda.*;
import ch.jeda.ui.*;
import ch.jeda.event.*;
import static ch.jeda.ui.ViewFeature.*;
import java.util.*;

public class PlayButton extends TextButton{
 
	ClientController controller;
        boolean phoenix;
        boolean dragon;
	 
        PlayButton(double x, double y, double width, double height, String text, ClientController controller){
            super(x, y, text, 0);
            this.controller = controller;
            this.setWidth(width);
            this.setHeight(height);
            
        }
        
        @Override
	public void clicked() {
            String s = "Play:";
//            if(!controller.isPlaying){
//                controller.board.message("Du bist nicht an der Reihe");
//            }
            if(controller.cardSelector.selected.isEmpty()){
                controller.board.message("Du musst mind. 1 Karte wählen");
            }
            else{
                Collections.sort(controller.cardSelector.selected);
                for(Card c: controller.cardSelector.selected){
                    s = s + c.id + ",";
                    c.isSelected = false;
                    if(c.color.equals("Phoenix")){
                        phoenix = true;
                    }
                    else if(c.color.equals("Dragon")){
                        dragon = true;
                    }
                }
                s = s.substring(0, s.length()-1);
                if(phoenix){
                    if(controller.cardSelector.selected.size() != 1){
                        controller.board.Phoenix(s);
                    }
                    phoenix = false;
                }
                if(dragon){
                    if(controller.cardSelector.selected.size() == 1){
                        controller.board.Dragon();
                    }
                    dragon = false;
                    controller.communicator.send(s);
                }
                else{
                    controller.communicator.send(s);
                }
                controller.cardSelector.selected.clear();
                System.out.println(s);
                controller.board.draw();
            }
	}
	 
}
 
