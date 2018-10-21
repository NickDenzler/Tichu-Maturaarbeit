package ch.jeda.tichu;

import ch.jeda.ui.*;

public class SchupfButton extends TextButton {

    ClientController controller;

    SchupfButton(double x, double y, double width, double height, String text, ClientController controller) {
        super(x, y, text, 0);
        this.controller = controller;
        this.setWidth(width);
        this.setHeight(height);

    }

    @Override
    public void clicked() {
        if (controller.cardSelector.selected.size() == 3) {
            String s = "SchupfCards:";
            for (Card c : controller.cardSelector.selected) {
                s = s + c.id + ",";
                controller.myCards.remove(c);
            }
            controller.cardSelector.selected.clear();
            s = s.substring(0, s.length() - 1);
            controller.communicator.send(s);
            controller.board.draw();
            System.out.println(s);
            controller.board.view.remove(this);
            controller.board.view.add(controller.play);
            controller.board.view.add(controller.pass);
        } else if (controller.cardSelector.selected.size() < 3) {
            controller.board.message("Du musst 3 karten Schupfen");
        } else if (controller.cardSelector.selected.size() > 3) {
            controller.board.message("Du kannst nur 3 karten Schupfen");
        }

    }

}
