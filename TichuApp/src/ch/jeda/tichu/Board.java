package ch.jeda.tichu;


import ch.jeda.*;
import ch.jeda.ui.*;
import ch.jeda.event.*;
import static ch.jeda.ui.ViewFeature.*;
import java.awt.TextField;
import javax.swing.JTextField;


public class Board {
 
	int f = 1;
        
        View view;
        View wish;
        View end;
        View text;
        
        WishButton[] all;
        WishButton[] all2;
        WishButton[] all3;
        String s;
	 
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
        
        boolean mePass;
        boolean partPass;
        boolean opp1Pass;
	boolean opp2Pass;
        
        boolean meFinnished;
        boolean partFinnished;
        boolean opp1Finnished;
	boolean opp2Finnished;
        
        int myCardsLeft = 14;
        int partCardsLeft = 14;
        int opp1CardsLeft = 14;
	int opp2CardsLeft = 14;
	
        boolean[] pass = new boolean[4];
        
        boolean[] finnished = new boolean[4];
        
        int[] cardsLeft = new int[4];
        
	Board(ClientController controller){
            this.controller = controller;
            view = new View(1600/f,900/f);
            view.setTitle("Spieler " + Integer.toString(controller.playerNumber));
            background = view.getBackground();
            width = background.getWidth();
            height = background.getHeight();
            cardW = width/16;
            cardH = height/6;
            
            
        }
        
        public void draw() {
            background.setColor(Color.WHITE);
            background.fillRectangle(0, 0, width, height);
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
            background.setTextSize(24);
            background.setColor(Color.BLACK);
            background.drawText(width/2, height/9*8, ""+cardsLeft[part]);
            background.drawText(cardW*15, cardH * 3.74, ""+cardsLeft[opp1]);
            background.drawText(cardW/2, cardH * 3.74, ""+cardsLeft[opp2]);
            if(pass[me]){
                background.drawText(width/2, height/9*2, "Pass");
            }
            else{
                int meSize = controller.playedCards[me].size();
                for(int i = 0; i < meSize; i++){
                    Card c =controller.playedCards[me].get(i);
                    Image img = new Image(c.image);
                    background.drawImage(width/2-(meSize/2*(cardW/2))+ i*(cardW/2), height/9*2, cardW/2, cardH/2, img);
                }
            }
            if(pass[part]){
                background.drawText(width/2 -2, height/9*7, "Pass");
            }
            else{
                int partSize = controller.playedCards[part].size();
                for(int i = 0; i < partSize; i++){
                    Card c =controller.playedCards[part].get(i);
                    Image img = new Image(c.image);
                    background.drawImage(width/2-(partSize/2*(cardW/2))+ i*(cardW/2), height/9*7, cardW/2, cardH/2, img);
                }
            }
            if(pass[opp2]){
                background.drawText(cardW, cardH*3.74, "Pass");
            }
            else{
                int opp2Size = controller.playedCards[opp2].size();
                for(int i = 0; i < opp2Size; i++){
                    Card c = controller.playedCards[opp2].get(i);
                    Image img = new Image(c.image);
//                    img = img.rotateDeg(270);
//                    background.drawImage(cardW, cardH*3.5-opp2Size/2 + i*(cardW/2), cardH/2, cardW/2, img);
                    background.drawImage(cardW + i *(cardW/2), cardH* 3.5, cardW/2, cardH/2, img);
                }
            }
            if(pass[opp1]){
                background.drawText(cardW*14, cardH*3.74, "Pass");
            }
            else{
                int opp1Size = controller.playedCards[opp1].size();
                for(int i = 0; i < opp1Size; i++){
                    Card c = controller.playedCards[opp1].get(i);
                    Image img = new Image(c.image);
//                    img = img.rotateDeg(90);
//                    background.drawImage(cardW*15, cardH*3.5+opp1Size/2 + i*(cardW/2), cardH/2, cardW/2, img);
                    background.drawImage(cardW*14 -(opp1Size*(cardW/2)) + i *(cardW/2), cardH* 3.5, cardW/2, cardH/2, img);
                }
            }
//            if(controller.isPlaying){
//                background.setColor(Color.GREEN);
//                background.fillRectangle(cardW*14,cardH/3, cardW*2,cardH/3);
//            }
            if(schupfed){
                
            }
	 
	}
        
        public void End(int a,int b,int A,int B){
            end = new View(700,700);
            end.setTitle("Rundenende");
            Canvas canvas = end.getBackground();
            canvas.drawText(300, 600, "Spieler 1 & 3");
            canvas.drawText(500, 600, "Spieler 2 & 4");
            canvas.drawText(100, 400, "Diese Runde:");
            canvas.drawText(300, 400, a+" Punkte");
            canvas.drawText(500, 400, b+" Punkte");
            canvas.drawText(100,200, "Insgesamt:");
            canvas.drawText(300,200, A+" Punkte");
            canvas.drawText(500, 200, B+" Punkte");
            
        }
        public void Dragon(){
            wish = new View(400,100);
            wish.setTitle("Wähle eine Gegener");
            Canvas canvas = wish.getBackground();
            WishButton eins =  new WishButton(5,40,195,20,"Links",controller,"Dragon");
            WishButton zwei =  new WishButton(200,40,195,20,"Rechts",controller,"Dragon");
            wish.add(eins);
            wish.add(zwei);
            all3 = new WishButton[2];
            all3[0] = eins;
            all3[1] = zwei;
        }
        public void MahJong(){
            wish = new View(400,100);
            wish.setTitle("Wähle eine Kartenhöhe");
            Canvas canvas = wish.getBackground();
            WishButton zwei = new WishButton(5,40,30,20,"2",controller,"MahJong");
            WishButton drei = new WishButton(35,40,30,20,"3",controller,"MahJong");
            WishButton vier = new WishButton(65,40,30,20,"4",controller,"MahJong");
            WishButton fünf = new WishButton(95,40,30,20,"5",controller,"MahJong");
            WishButton sechs = new WishButton(125,40,30,20,"6",controller,"MahJong");
            WishButton sieben = new WishButton(155,40,30,20,"7",controller,"MahJong");
            WishButton acht = new WishButton(185,40,30,20,"8",controller,"MahJong");
            WishButton neun = new WishButton(215,40,30,20,"9",controller,"MahJong");
            WishButton zehn = new WishButton(245,40,30,20,"10",controller,"MahJong");
            WishButton j = new WishButton(275,40,30,20,"J",controller,"MahJong");
            WishButton q = new WishButton(305,40,30,20,"Q",controller,"MahJong");
            WishButton k = new WishButton(335,40,30,20,"K",controller,"MahJong");
            WishButton a = new WishButton(365,40,30,20,"A",controller,"MahJong");
            wish.add(zwei);
            wish.add(drei);
            wish.add(vier);
            wish.add(fünf);
            wish.add(sechs);
            wish.add(sieben);
            wish.add(acht);
            wish.add(neun);
            wish.add(zehn);
            wish.add(j);
            wish.add(q);
            wish.add(k);
            wish.add(a);
            
            all = new WishButton[13];
            all[0] = zwei;
            all[1] = drei;
            all[2] = vier;
            all[3] = fünf;
            all[4] = sechs;
            all[5] = sieben;
            all[6] = acht;
            all[7] = neun;
            all[8] = zehn;
            all[9] = j;
            all[10] = q;
            all[11] = k;
            all[12] = a;
            
            
        }
        public void Phoenix(String cards){
            s = cards;
            wish = new View(400,100);
            wish.setTitle("Wähle eine Kartenhöhe für den Phönix");
            Canvas canvas = wish.getBackground();
            WishButton zwei = new WishButton(5,40,30,20,"2",controller,"Phoenix");
            WishButton drei = new WishButton(35,40,30,20,"3",controller,"Phoenix");
            WishButton vier = new WishButton(65,40,30,20,"4",controller,"Phoenix");
            WishButton fünf = new WishButton(95,40,30,20,"5",controller,"Phoenix");
            WishButton sechs = new WishButton(125,40,30,20,"6",controller,"Phoenix");
            WishButton sieben = new WishButton(155,40,30,20,"7",controller,"Phoenix");
            WishButton acht = new WishButton(185,40,30,20,"8",controller,"Phoenix");
            WishButton neun = new WishButton(215,40,30,20,"9",controller,"Phoenix");
            WishButton zehn = new WishButton(245,40,30,20,"10",controller,"Phoenix");
            WishButton j = new WishButton(275,40,30,20,"J",controller,"Phoenix");
            WishButton q = new WishButton(305,40,30,20,"Q",controller,"Phoenix");
            WishButton k = new WishButton(335,40,30,20,"K",controller,"Phoenix");
            WishButton a = new WishButton(365,40,30,20,"A",controller,"Phoenix");
            wish.add(zwei);
            wish.add(drei);
            wish.add(vier);
            wish.add(fünf);
            wish.add(sechs);
            wish.add(sieben);
            wish.add(acht);
            wish.add(neun);
            wish.add(zehn);
            wish.add(j);
            wish.add(q);
            wish.add(k);
            wish.add(a);
            
            all2 = new WishButton[13];
            all2[0] = zwei;
            all2[1] = drei;
            all2[2] = vier;
            all2[3] = fünf;
            all2[4] = sechs;
            all2[5] = sieben;
            all2[6] = acht;
            all2[7] = neun;
            all2[8] = zehn;
            all2[9] = j;
            all2[10] = q;
            all2[11] = k;
            all2[12] = a;
            
            
        }
        public String servername(){
            text = new View();
            
            
            return "Localhost";
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
            
            pass[me] = mePass;
            pass[part] = partPass;
            pass[opp1] = opp1Pass;
            pass[opp2] = opp2Pass;
            
            finnished[me] = meFinnished;
            finnished[part] = partFinnished;
            finnished[opp1] = opp1Finnished;
            finnished[opp2] = opp2Finnished;
            
            cardsLeft[me] = myCardsLeft;
            cardsLeft[part] = partCardsLeft;
            cardsLeft[opp1] = opp1CardsLeft;
            cardsLeft[opp2] = opp2CardsLeft;
            
        }
}
 
