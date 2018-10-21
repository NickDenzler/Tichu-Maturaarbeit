package ch.jeda.tichu;

import ch.jeda.ui.*;

public class PassButton extends TextButton {

    ClientController controller;

    PassButton(double x, double y, double width, double height, String text, ClientController controller) {
        super(x, y, text, 0);
        this.controller = controller;
        this.setWidth(width);
        this.setHeight(height);

    }

    @Override
    public void clicked() {
        if (controller.isPlaying) {
            controller.communicator.send("Pass: ");
        }
    }

}
