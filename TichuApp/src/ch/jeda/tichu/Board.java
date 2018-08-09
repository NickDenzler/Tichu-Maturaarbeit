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
        
        int me;
        int part;
        int opp1;
        int opp2;
        
	 
	Board(ClientController controller){
            this.controller = controller;
            view = new View(1600/2,900/2);
            view.setTitle("Spieler " + Integer.toString(controller.playerNumber));
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
            int meSize = controller.playedCards[me].size();
            for(int i = 0; i < meSize; i++){
                Card c =controller.playedCards[me].get(i);
                Image img = new Image(c.image);
                background.drawImage(width/2-(meSize/2*(cardW/2))+ i*(cardW/2), height/9*2, cardW/2, cardH/2, img);
            }
            int partSize = controller.playedCards[part].size();
            for(int i = 0; i < partSize; i++){
                Card c =controller.playedCards[part].get(i);
                Image img = new Image(c.image);
                background.drawImage(width/2-(partSize/2*(cardW/2))+ i*(cardW/2), height/9*8, cardW/2, cardH/2, img);
            }
            int opp2Size = controller.playedCards[opp2].size();
            for(int i = 0; i < opp2Size; i++){
                Card c = controller.playedCards[opp2].get(i);
                Image img = new Image(c.image);
                img.rotateDeg(270);
                background.drawImage(cardW, cardH*3.5-opp2Size/2 + i*(cardW/2), cardH/2, cardW/2, img);
            }
            int opp1Size = controller.playedCards[opp1].size();
            for(int i = 0; i < opp1Size; i++){
                Card c = controller.playedCards[opp1].get(i);
                Image img = new Image(c.image);
                img.rotateDeg(90);
                background.drawImage(cardW*15, cardH*3.5+opp1Size/2 + i*(cardW/2), cardH/2, cardW/2, img);
            }
            
//            if(controller.isPlaying){
//                background.setColor(Color.GREEN);
//                background.fillRectangle(cardW*14,cardH/3, cardW*2,cardH/3);
//            }
            if(schupfed){
                
            }
	 
	}
        
        public void message(String text){
//            background.setTextSize(32);
//            background.fillRectangle(0, cardH, width, height-cardH);
//            background.setColor(Color.RED);
//            background.drawText(width/2, height/2, text);
            
            View message = new View(400,100);
            message.setTitle("Error");
            Canvas canvas = message.getBackground();
            canvas.setTextSize(24);
            canvas.drawText(10, 50, text);
        }
	
        public void number(int playerNumber){
            view.setTitle("Spieler " + Integer.toString(playerNumber));
            switch(playerNumber){
                case 1:
                    me = 1;
                    part = 3;
                    opp1 = 2;
                    opp2 = 4;
                    break;
                case 2:
                    me = 2;
                    part = 4;
                    opp1 = 3;
                    opp2 = 1;
                    break;
                case 3:
                    me = 3;
                    part = 1;
                    opp1 = 4;
                    opp2 = 2;
                    break;
                case 4:
                    me = 4;
                    part = 2;
                    opp1 = 1;
                    opp2 = 3;
                    break;
                
            }
            me--;
            part--;
            opp1--;
            opp2--;
            
        }
}
 
