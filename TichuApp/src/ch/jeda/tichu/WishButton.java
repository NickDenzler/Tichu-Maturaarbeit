package ch.jeda.tichu;


import ch.jeda.*;
import ch.jeda.ui.*;
import ch.jeda.event.*;
import static ch.jeda.ui.ViewFeature.*;


public class WishButton extends TextButton{
 
	ClientController controller;
        String value;
	
        WishButton(double x, double y, double width, double height, String text, ClientController controller){
            super(x, y, text, 0);
            this.controller = controller;
            this.setWidth(width);
            this.setHeight(height);
            value = text;
        
        }
        @Override
	public void clicked() {
            if(Integer.parseInt(value) < 11){
            controller.wishedHeight = Integer.parseInt(value);
            }
            else if(value.equals("J")){
                controller.wishedHeight = 11;
            }
            else if(value.equals("Q")){
                controller.wishedHeight = 12;
            }
            else if(value.equals("K")){
                controller.wishedHeight = 13;
            }
            else if(value.equals("A")){
                controller.wishedHeight = 14;
            }
            controller.board.wish.setTitle(controller.wishedHeight+" gewählt");
            controller.board.wish.remove(controller.board.all);
	}
	 
}
 
