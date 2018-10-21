package ch.jeda.tichu;

import ch.jeda.*;

public class ServerMain extends Program {

    public int playerNumber;

    private ServerController controller;

    @Override
    public void run() {
        controller = new ServerController(this);
    }
}
