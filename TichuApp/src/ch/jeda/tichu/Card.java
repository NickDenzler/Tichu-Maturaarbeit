package ch.jeda.tichu;


import ch.jeda.*;
import ch.jeda.ui.*;
import ch.jeda.event.*;
import static ch.jeda.ui.ViewFeature.*;


public class Card implements Comparable<Card>{
 
	int value;
	 
	String color;
	 
	String image;
        String imageS;
	boolean isSelected;
        
	private boolean isPlayed;
	 
	private Player player;
	 
	private ClientController clientController;
	
        int id;
        
        String deck = " - Kopie";
//        String deck = "";
        
        Card(int id, int val, String col){
            this.id = id;
            color = col;
            value = val;
            image ="res:cards/"+color+val+deck+".png";
            imageS ="res:cardsSel/"+color+val+deck+".png";
        }
        
        public String getString(){
            String s = color+value;
            return s;
        }
        
        

    @Override
    public int compareTo(Card t) {
        if (value<t.value){
            return -1;
        }
        else if (value>t.value){
            return 1;
            
        }
        else{
            return 0;
        }
    }
        
}
 
