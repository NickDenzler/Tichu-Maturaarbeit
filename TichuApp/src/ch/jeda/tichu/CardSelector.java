package ch.jeda.tichu;


import ch.jeda.*;
import ch.jeda.ui.*;
import ch.jeda.event.*;
import static ch.jeda.ui.ViewFeature.*;
import java.util.*;


public class CardSelector implements PointerDownListener{
 
        ArrayList<Card> selected = new ArrayList<Card>();
        ArrayList<Card> cards;
	double height;
        double width;
	
	 
	ClientController controller;
	 
	CardSelector(ClientController controller){
            this.controller = controller;
            height = controller.board.height;
            width = controller.board.width;
            cards = controller.myCards;
            controller.board.view.addEventListener(this);
        }
        
        
        @Override
        public void onPointerDown(PointerEvent pe) {
            double x = pe.getX();
            double y = pe.getY();
            //System.out.println("klick");
            if(y<height/6){
                int i = (int) (x/(width/16));
                if (i<14){
                    if(selected.contains(cards.get(i))){
                        selected.remove(cards.get(i));
                        cards.get(i).isSelected=false;
                        selected.trimToSize();
                }
                    else{
                        selected.add(cards.get(i));
                        cards.get(i).isSelected=true;
                        selected.trimToSize();
                    }
                }
                
                
//                switch(i){
//                    case 0:
//                        
//                    case 1:
                        
//                }
            }
            controller.board.draw();
            
	 
	}
	 
}
 
