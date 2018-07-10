package ch.jeda.tichu;


import ch.jeda.*;
import ch.jeda.ui.*;
import ch.jeda.event.*;
import static ch.jeda.ui.ViewFeature.*;
import java.util.*;


public class Evaluator {
 
	ServerController controller;

    Evaluator(ServerController controller) {
        this.controller = controller;
        
    }
	 
    public Combination evaluate(ArrayList<Card> cards) {
        int n = cards.size();
        switch(n){
            case 1:
                return test("SingleCard",cards.get(0).value,cards,0);
            case 2:
                if(cards.get(0).value == cards.get(1).value){
                    return test("Pair",cards.get(0).value,cards,0);
                }
            case 3:
                if(cards.get(0).value == cards.get(1).value && cards.get(1).value == cards.get(2).value){
                    return test("Triplet",cards.get(0).value,cards,0);
                }
            case 4:
                if(cards.get(0).value == cards.get(1).value && cards.get(1).value == cards.get(2).value && cards.get(2).value == cards.get(3).value){
                    return test("Bomb",cards.get(0).value,cards,0);
                }
                else if(cards.get(0).value == cards.get(1).value && cards.get(2).value == cards.get(3).value){
                    return test("stair",cards.get(0).value,cards,2);
                }
        }
        return null;
    }
    
    boolean ok(String type,int value){
        if(controller.currentComb == null){
            return true;
        }
        else if("Bomb".equals(type)){
            return !(controller.currentComb.type.equals(type) && controller.currentComb.value >= value);
        }
        
        else return controller.currentComb.type.equals(type) && controller.currentComb.value < value;
    }
    
    Combination test(String type, int value, ArrayList<Card> cards, int length){
        if(ok(type,value)){
            Combination comb = new Combination(type,value,cards,controller.currentPlayer, length);
            return comb;
        }
        else return null;
    }
    
	 
}
 
