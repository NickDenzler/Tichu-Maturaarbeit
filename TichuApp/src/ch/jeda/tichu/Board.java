package ch.jeda.tichu;


import ch.jeda.*;
import ch.jeda.ui.*;
import ch.jeda.event.*;
import static ch.jeda.ui.ViewFeature.*;


public class Board {
 
	View view;
	 
	Canvas background;
        boolean schupfed;
        
	private ServerMain serverMain;
	 
	private ClientController controller;
        
        double width;
        double height;
        double cardW;
        double cardH;
	 
	Board(ClientController controller){
            this.controller = controller;
            view = new View(1600/2,900/2);
            background = view.getBackground();
            width = background.getWidth();
            height = background.getHeight();
            cardW = width/16;
            cardH = height/6;
            
        }
        
        public void draw() {
//            background.drawImage(0, 0, width/15, height/6, Image.JEDA_LOGO_16x16);
            background.setColor(Color.WHITE);
            background.fillRectangle(0, 0, cardW*14, cardH);
            for(int i=0;i<controller.myCards.size();i++){
                Card c = controller.myCards.get(i);
                if(c.isSelected){
                    Image img = new Image(c.imageS);
                    background.drawImage(i*cardW, 0, cardW, cardH, img);
                }
                else{
                    Image img = new Image(c.image);
                    background.drawImage(i*cardW, 0, cardW, cardH, img);
                }
                
            }
            if(schupfed){
                
            }
	 
	}
        
        public void message(String text){
            background.setTextSize(32);
            background.fillRectangle(0, cardH, width, height-cardH);
            background.setColor(Color.RED);
            background.drawText(width/2, height/2, text);
        }
	 
}
 
