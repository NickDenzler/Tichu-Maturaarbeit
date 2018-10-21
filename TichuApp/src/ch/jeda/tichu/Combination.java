package ch.jeda.tichu;

import java.util.*;

public class Combination {

    String type;

    int value;

    int length = 0;

    ArrayList<Card> cards;

    Player playedBy;

    Player player;

    Combination(String type, int value, ArrayList<Card> cards, Player playedBy, int length) {
        this.cards = cards;
        this.playedBy = playedBy;
        this.type = type;
        this.value = value;
        this.length = length;
    }

}
