package ch.jeda.tichu;

import ch.jeda.ui.*;
import java.util.*;

public class ServerController {

    Combination currentComb;

    Player currentPlayer;

    ArrayList<Combination> combinations;

    ArrayList<Player> finnished = new ArrayList<Player>();

    Evaluator evaluator;

    Counter counter;

    Canvas canvas;

    ServerMain main;

    Player player1;
    Player player2;
    Player player3;
    Player player4;

    Player[] players = new Player[4];

    boolean p1Schupfed;
    boolean p2Schupfed;
    boolean p3Schupfed;
    boolean p4Schupfed;

    Card[] cards;

    int MahJong;
    int Phoenix;
    int Dragon;

    boolean roundOver;
    boolean doubleWin;

    ServerCommunicator communicator;

    View view;

    protected ServerController(ServerMain main) {
        this.main = main;
        communicator = new ServerCommunicator(this);
        evaluator = new Evaluator(this);
        combinations = new ArrayList<Combination>();
        counter = new Counter(this);

        view = new View(10, 10);
    }

    public void mix() {
        player1 = new Player(this, 1);
        player2 = new Player(this, 2);
        player3 = new Player(this, 3);
        player4 = new Player(this, 4);

        players[0] = player1;
        players[1] = player2;
        players[2] = player3;
        players[3] = player4;
        //Karten werden erstellt
        cards = new Card[56];
        String[] col = {"blue", "black", "red", "green"};
        int id = 0;
        for (String c : col) {
            for (int i = 0; i < 13; i++) {
                cards[id] = new Card(id, i + 2, c);
                id++;
            }
        }
        //Sonderkarten werden erstellt
        cards[id] = new Card(id, 15, "Dragon");
        cards[id + 1] = new Card(id + 1, 1, "MahJong");
        cards[id + 2] = new Card(id + 2, 0, "Dog");
        cards[id + 3] = new Card(id + 3, -1, "Phoenix");

        //verteilt die Karten zufällig auf die Spieler
        ArrayList<Card> used = new ArrayList<Card>();
        for (int n = 0; n < 14; n++) {
            Card c = cards[(int) (Math.random() * 56)];
            while (used.contains(c)) {
                c = cards[(int) (Math.random() * 56)];
            }
            player1.cards.add(c);
            used.add(c);
        }
        for (int n = 0; n < 14; n++) {
            Card c = cards[(int) (Math.random() * 56)];
            while (used.contains(c)) {
                c = cards[(int) (Math.random() * 56)];
            }
            player2.cards.add(c);
            used.add(c);
        }
        for (int n = 0; n < 14; n++) {
            Card c = cards[(int) (Math.random() * 56)];
            while (used.contains(c)) {
                c = cards[(int) (Math.random() * 56)];
            }
            player3.cards.add(c);
            used.add(c);
        }
        for (Card c : cards) {
            if (used.contains(c)) {

            } else {
                player4.cards.add(c);
                used.add(c);
            }
        }
        //Karten werden den Clients zugesendet
        String p1 = "Cards:";
        String p2 = "Cards:";
        String p3 = "Cards:";
        String p4 = "Cards:";

        for (Card c : player1.cards) {
            p1 = p1 + c.id + ",";
        }
        for (Card c : player2.cards) {
            p2 = p2 + c.id + ",";
        }
        for (Card c : player3.cards) {
            p3 = p3 + c.id + ",";
        }
        for (Card c : player4.cards) {
            p4 = p4 + c.id + ",";
        }
        p1 = p1.substring(0, p1.length() - 1);
        p2 = p2.substring(0, p2.length() - 1);
        p3 = p3.substring(0, p3.length() - 1);
        p4 = p4.substring(0, p4.length() - 1);

        communicator.send(1, p1);
        communicator.send(2, p2);
        communicator.send(3, p3);
        communicator.send(4, p4);

        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);
        System.out.println(p4);

    }

    //findet Spieler, der den Mah Jong auf der Hand hat
    Player findStarter() {
        for (Card c : player1.cards) {
            if (c.id == 53) {
                return player1;
            }
        }
        for (Card c : player2.cards) {
            if (c.id == 53) {
                return player2;
            }
        }
        for (Card c : player3.cards) {
            if (c.id == 53) {
                return player3;
            }
        }
        for (Card c : player4.cards) {
            if (c.id == 53) {
                return player4;
            }
        }
        return null;
    }
}
