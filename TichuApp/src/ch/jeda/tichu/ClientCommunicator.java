package ch.jeda.tichu;

import ch.jeda.*;

import ch.jeda.event.*;

import java.util.Collections;

public class ClientCommunicator implements MessageReceivedListener {

    ClientController controller;

    TcpConnection connection;
    String servername = "Localhost";
    int port = 1616;

    ClientCommunicator(ClientController controller) {
        this.controller = controller;
        connection = new TcpConnection();
        connection.open(servername, port);
        Jeda.addEventListener(this);
    }

    //sendet eineNachricht an den Server
    public void send(String text) {
        connection.sendLine(controller.playerNumber + ":" + text);
    }

    @Override
    public void onMessageReceived(MessageEvent event) {

        String[] parts = event.getLine().split(":");
        String message = parts[1];
        String mType = parts[0];
        String player = "";
        if (parts.length > 2) {
            player = parts[1];
            message = parts[2];
        }

        if (mType.equals("Cards")) {
            String[] ids = message.split(",");
            for (String s : ids) {
                int x = Integer.parseInt(s);
                controller.myCards.add(controller.cards[x]);
            }
            Collections.sort(controller.myCards);

            for (Card c : controller.myCards) {
                System.out.println(c.getString());
            }
            controller.board.draw();
        } else if (mType.equals("YourTurn")) {
            if (message.equals("true")) {
                System.out.println("playing");
                controller.isPlaying = true;
                String title = controller.board.view.getTitle();
                controller.board.view.setTitle(title + " playing");
                if (controller.board.pass[controller.board.opp1] || controller.board.finnished[controller.board.opp1]) {
                    if (controller.board.pass[controller.board.opp2] || controller.board.finnished[controller.board.opp2]) {
                        if (controller.board.pass[controller.board.part] || controller.board.finnished[controller.board.part]) {
                            System.out.println("won");
                            send("Won:Round");
                            controller.board.draw();
                        }
                    }
                }

            } else if (message.equals("false")) {
                System.out.println("not playing");
                controller.isPlaying = false;
                String title = controller.board.view.getTitle();
                controller.board.view.setTitle(title.replaceAll(" playing", ""));
            }

        } else if (mType.equals("Finnished")) {
            int p = Integer.parseInt(message);
            controller.board.finnished[p - 1] = true;
        } else if (mType.equals("Won")) {
            controller.board.pass[controller.board.opp1] = false;
            controller.board.pass[controller.board.opp2] = false;
            controller.board.pass[controller.board.part] = false;
            controller.board.pass[controller.board.me] = false;
            for (int i = 0; i < 4; i++) {
                controller.playedCards[i].clear();
                controller.playedCards[i].trimToSize();
            }
            controller.board.draw();
        } else if (mType.equals("SchupfedCards")) {
            String[] ids = message.split(",");
            for (String s : ids) {
                int x = Integer.parseInt(s);
                controller.myCards.add(controller.cards[x]);
            }
            Collections.sort(controller.myCards);
            controller.board.draw();
        } else if (mType.equals("Error")) {
            controller.board.message(message);
        } else if (mType.equals("Passed")) {
            int p = Integer.parseInt(message);
            controller.playedCards[p - 1].clear();
            controller.board.pass[p - 1] = true;
            controller.board.draw();
        } else if (mType.equals("Played")) {
            String[] ids = message.split(",");
            System.out.println(player);
            int p = Integer.parseInt(player);
            if (p == controller.playerNumber) {
                if (ids.length == 1) {
                    if (ids[0].equals("53")) {
                        controller.board.MahJong();
                    }
                }
                for (String s : ids) {
                    int x = Integer.parseInt(s);
                    boolean remove = controller.myCards.remove(controller.cards[x]);
                    if (remove == true) {
                        System.out.println(controller.cards[x].getString() + " enternt");
                    } else {
                        System.out.println("fehler beim Entfernen von ");
                        System.out.println(controller.cards[x].getString());
                    }

                }

                controller.myCards.trimToSize();
                Collections.sort(controller.myCards);
                if (controller.myCards.isEmpty()) {
                    controller.board.finnished[controller.board.me] = true;
                    send("Finnished:now");
                }
                controller.board.draw();
            }
            controller.playedCards[p - 1].clear();
            for (String s : ids) {
                int x = Integer.parseInt(s);
                controller.playedCards[p - 1].add(controller.cards[x]);
                controller.board.cardsLeft[p - 1]--;
            }
            controller.playedCards1.trimToSize();
            Collections.sort(controller.playedCards1);
            for (int i = 0; i < 4; i++) {
                controller.board.pass[i] = false;
            }

            controller.board.draw();
        } else if (mType.equals("RoundOver")) {
            String[] strings = message.split(",");
            controller.board.End(Integer.parseInt(strings[0]),
                    Integer.parseInt(strings[1]), Integer.parseInt(strings[2]),
                    Integer.parseInt(strings[3]));
        } else if (mType.equals("Message")) {
            System.out.println(message);
            if (message.startsWith("Wollkommen Spieler nr.1")) {
                int x = 1;
                controller.playerNumber = x;
                System.out.println("playerNumber = " + x);
            } else if (message.startsWith("Wollkommen Spieler nr.2")) {
                int x = 2;
                controller.playerNumber = x;
                System.out.println("playerNumber = " + x);
            } else if (message.startsWith("Wollkommen Spieler nr.3")) {
                int x = 3;
                controller.playerNumber = x;
                System.out.println("playerNumber = " + x);
            } else if (message.startsWith("Wollkommen Spieler nr.4")) {
                int x = 4;
                controller.playerNumber = x;
                System.out.println("playerNumber = " + x);
            }
            controller.board.number(controller.playerNumber);
        } else {
            System.out.println("Unerwartete Meldung:" + event.getLine());
        }
    }

}
