package ch.jeda.tichu;

import ch.jeda.ui.*;

public class WishButton extends TextButton {

    ClientController controller;
    String value;
    String card;

    WishButton(double x, double y, double width, double height, String text, ClientController controller, String card) {
        super(x, y, text, 0);
        this.controller = controller;
        this.setWidth(width);
        this.setHeight(height);
        value = text;
        this.card = card;

    }

    @Override
    public void clicked() {
        //Falls für Mah Jong Wunsch verwendet wird
        if (card.equals("MahJong")) {
            if (value.equals("J")) {
                controller.wishedHeight = 11;
            } else if (value.equals("Q")) {
                controller.wishedHeight = 12;
            } else if (value.equals("K")) {
                controller.wishedHeight = 13;
            } else if (value.equals("A")) {
                controller.wishedHeight = 14;
            } else if (Integer.parseInt(value) < 11) {
                controller.wishedHeight = Integer.parseInt(value);
            }
            controller.board.wish.remove(controller.board.all);
            controller.communicator.send("MahJong:" + controller.wishedHeight);
        } //Falls für Phönix höhe verwendet wird
        else if (card.equals("Phoenix")) {
            if (value.equals("J")) {
                controller.wishedHeight2 = 11;
            } else if (value.equals("Q")) {
                controller.wishedHeight2 = 12;
            } else if (value.equals("K")) {
                controller.wishedHeight2 = 13;
            } else if (value.equals("A")) {
                controller.wishedHeight2 = 14;
            } else if (Integer.parseInt(value) < 11) {
                controller.wishedHeight2 = Integer.parseInt(value);
            }
            controller.board.wish.remove(controller.board.all2);
            controller.communicator.send("Phoenix:" + controller.wishedHeight2);
            controller.communicator.send(controller.board.s);
        } //Falls für Drachen weggeben vrwendet wird
        else if (card.equals("Dragon")) {
            int x = 0;
            if (value.equals("Rechts")) {
                x = controller.board.opp1 + 1;
            }
            if (value.equals("Links")) {
                x = controller.board.opp2 + 1;
            }
            controller.communicator.send("Dragon:" + x);
            controller.board.wish.remove(controller.board.all3);
            System.out.println("x = " + x);
        }
        controller.board.wish.setTitle(value + " gewählt");
    }
}
