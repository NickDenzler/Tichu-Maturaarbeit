package ch.jeda.tichu;

import ch.jeda.*;
import java.util.*;

public class ClientController extends Program {

    boolean isPlaying;

    boolean finnished;
    boolean partFinnished;
    boolean opp1Finnished;
    boolean opp2Finnished;

    int playerNumber;

    PlayButton play;
    PassButton pass;
    SchupfButton schupf;

    Card[] cards = new Card[56];

    ArrayList<Card> myCards = new ArrayList<Card>();

    ArrayList<Card> playedCards1 = new ArrayList<Card>();
    ArrayList<Card> playedCards2 = new ArrayList<Card>();
    ArrayList<Card> playedCards3 = new ArrayList<Card>();
    ArrayList<Card> playedCards4 = new ArrayList<Card>();

    ArrayList<Card>[] playedCards = (ArrayList<Card>[]) new ArrayList[4];

    Board board;

    ClientCommunicator communicator;

    CardSelector cardSelector;

    private Card card;

    PlayButton playButton;

    PassButton passButton;

    int wishedHeight;
    int wishedHeight2;

    @Override
    public void run() {

        board = new Board(this);
        cardSelector = new CardSelector(this);
        //Erstellen der Karten
        cards = new Card[56];
        String[] col = {"blue", "black", "red", "green"};
        int id = 0;
        for (String c : col) {
            for (int i = 0; i < 13; i++) {
                cards[id] = new Card(id, i + 2, c);
                id++;
            }
        }
        //Erstellen der Sonderkarten
        cards[id] = new Card(id, 15, "Dragon");
        cards[id + 1] = new Card(id + 1, 1, "MahJong");
        cards[id + 2] = new Card(id + 2, 0, "Dog");
        cards[id + 3] = new Card(id + 3, -1, "Phoenix");

        communicator = new ClientCommunicator(this);
        play = new PlayButton(board.cardW * 14, board.cardH * 2 / 3, board.cardW * 2, board.cardH / 3, "Spielen", this);
        pass = new PassButton(board.cardW * 14, 0, board.cardW * 2, board.cardH / 3, "Passen", this);
        schupf = new SchupfButton(board.cardW * 14, board.cardH / 3, board.cardW * 2, board.cardH / 3, "Schupfen", this);
        board.view.add(schupf);

        playedCards[0] = playedCards1;
        playedCards[1] = playedCards2;
        playedCards[2] = playedCards3;
        playedCards[3] = playedCards4;

    }

}
