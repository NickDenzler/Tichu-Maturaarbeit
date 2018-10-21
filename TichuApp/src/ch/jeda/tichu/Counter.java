package ch.jeda.tichu;

import java.util.*;

public class Counter {

    int pointsTeamA;

    int pointsTeamB;

    private ServerController controller;

    Counter(ServerController controller) {
        this.controller = controller;
    }

    public int count(ArrayList<Card> cards) {
        int i = 0;
        if (cards.isEmpty()) {
            return i;
        }
        for (Card c : cards) {
            switch (c.value) {
                case -1:
                    i = i - 25;
                    break;
                case 5:
                    i += 5;
                    break;
                case 10:
                    i += 10;
                    break;
                case 13:
                    i += 10;
                    break;
                case 15:
                    i += 25;
                    break;
                default:
                    break;
            }
        }
        return i;
    }

    public int calculate(int points) {
        int i = 100 - points;
        return i;
    }
}
