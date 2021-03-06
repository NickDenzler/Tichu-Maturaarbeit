package ch.jeda.tichu;

import java.util.*;

public class Evaluator {

    ServerController controller;

    Evaluator(ServerController controller) {
        this.controller = controller;

    }

    public Combination evaluate(ArrayList<Card> cards) {
        int n = cards.size();
        switch (n) {
            case 1:
                if (controller.currentComb != null) {
                    if (controller.currentComb.cards.size() == 1
                            && controller.currentComb.cards.get(0).color.equals("MahJong")) {
                        if (cards.get(0).value == controller.MahJong) {
                            return test("SingleCard", cards.get(0).value, cards, 0);
                        } else {
                            controller.communicator.send(controller.currentPlayer.playerNumber,
                                    "Error:Du musst die gewünschte Karte spielen");
                            return null;
                        }
                    }
                    if(controller.currentComb.cards.size() == 1
                            && cards.get(0).color.equals("Phoenix")){
                        Combination comb = test("SingleCard", controller.currentComb.value+1,cards,0);
                        if(comb != null){
                            return new Combination("SingleCard", controller.currentComb.value,
                                    cards, controller.currentPlayer, 0);
                        }
                    }
                } else if (cards.get(0).color.equals("Phoenix")) {
                    if (controller.currentComb == null) {
                        return test("SingleCard", 0, cards, 0);
                    }
                    if (test("SingleCard", controller.currentComb.value + 1, cards, 0) != null) {
                        Combination comb = new Combination("SingleCard", controller.currentComb.value,
                                cards, controller.currentPlayer, 0);
                        return comb;
                    }
                }
                return test("SingleCard", cards.get(0).value, cards, 0);
            case 2:
                if (cards.get(0).value == cards.get(1).value) {
                    return test("Pair", cards.get(0).value, cards, 0);
                }
            case 3:
                if (cards.get(0).value == cards.get(1).value
                        && cards.get(1).value == cards.get(2).value) {
                    return test("Triplet", cards.get(0).value, cards, 0);
                }
            case 4:
                if (cards.get(0).value == cards.get(1).value
                        && cards.get(1).value == cards.get(2).value
                        && cards.get(2).value == cards.get(3).value) {
                    return test("Bomb", cards.get(0).value, cards, 0);
                } else if (cards.get(0).value == cards.get(1).value
                        && cards.get(2).value == cards.get(3).value) {
                    return test("Stair", cards.get(0).value, cards, 2);
                }
            case 5:
                if (cards.get(0).value == cards.get(1).value
                        && cards.get(1).value == cards.get(2).value
                        && cards.get(3).value == cards.get(4).value) {
                    return test("FullHouse", cards.get(0).value, cards, 0);
                } else if (cards.get(0).value == cards.get(1).value
                        && cards.get(2).value == cards.get(3).value
                        && cards.get(3).value == cards.get(4).value) {
                    return test("FullHouse", cards.get(2).value, cards, 0);
                }
            default:
                if (n > 4) {
                    if ((n % 2) == 0) {
                        if (stair(cards)) {
                            return test("Stair", cards.get(0).value, cards, n);
                        }
                    }
                    if (street(cards)) {
                        if (streetbomb(cards)) {
                            return test("Streetbomb", cards.get(0).value, cards, n);
                        }
                        return test("Street", cards.get(0).value, cards, n);
                    }
                }
        }
        return null;
    }

    boolean ok(String type, int value, int length) {
        if (controller.currentComb == null) {
            return true;
        } else if (controller.currentComb.type.equals("SingleCard")
                && controller.currentComb.cards.get(0).color.equals("Dog")) {
            return true;
        } else if ("Bomb".equals(type)) {
            if (!controller.currentComb.type.equals("Streetbomb")) {
                return !(controller.currentComb.type.equals(type)
                        && controller.currentComb.value >= value);
            } else {
                return false;
            }
        } else if ("Sreetbomb".equals(type)) {
            if (controller.currentComb.type.equals(type)) {
                if (controller.currentComb.length >= length) {
                    return !(controller.currentComb.value >= value);
                }
            }
            return true;
        } else {
            return controller.currentComb.type.equals(type) && controller.currentComb.value < value;
        }
    }

    Combination test(String type, int value, ArrayList<Card> cards, int length) {
        if (ok(type, value, length)) {
            Combination comb = new Combination(type, value, cards, controller.currentPlayer, length);
            return comb;
        } else {
            return null;
        }
    }

    boolean stair(ArrayList<Card> cards) {
        for (int n = 0; n < cards.size(); n = n + 2) {
            if (cards.get(n).value != cards.get(n + 1).value) {
                return false;
            }
            if (n > 0) {
                if (cards.get(n).value == cards.get(n - 1).value) {
                    return false;
                }
            }
        }
        return true;
    }

    boolean street(ArrayList<Card> cards) {
        for (int n = 0; n < cards.size() - 1; n++) {
            if (cards.get(n).value != cards.get(n + 1).value - 1) {
                return false;
            }
        }
        return true;
    }

    boolean streetbomb(ArrayList<Card> cards) {
        for (int n = 0; n < cards.size(); n++) {
            if (cards.get(n).value == cards.get(n + 1).value
                    && cards.get(n).color.equals(cards.get(n + 1).color)) {
            } else {
                return false;
            }
        }
        return true;
    }

}
