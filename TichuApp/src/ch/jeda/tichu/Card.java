package ch.jeda.tichu;

public class Card implements Comparable<Card> {

    String deck = " - Kopie";       //Auswahl der Karten Bilder: 
                                    //" - Kopie" für Originalkarten,
                                    //"" für selbst gemalte
    int value;

    String color;

    String image;
    String imageS;
    boolean isSelected;

    private boolean isPlayed;

    private Player player;

    private ClientController clientController;

    int id;

    Card(int id, int val, String col) {
        this.id = id;
        color = col;
        value = val;
        image = "res:cards/" + color + val + deck + ".png";      //Pfade für nicht gewählte
        imageS = "res:cardsSel/" + color + val + deck + ".png";  //und gewählte Karten
    }

    public String getString() {
        String s = color + value;
        return s;
    }

    @Override
    public int compareTo(Card t) {
        if (value < t.value) {
            return -1;
        } else if (value > t.value) {
            return 1;

        } else {
            return 0;
        }
    }

}
