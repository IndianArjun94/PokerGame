package Game;

import java.util.LinkedList;
import java.util.Random;

public class Game {
    public Card[] playerCards = new Card[2];
    public Card[] computerCards = new Card[2];

    public LinkedList<Integer> usedCards = new LinkedList<>();

    public Card newRandomCard() {
        int rank = new Random().nextInt(1, 52);
        while (usedCards.contains(rank)) {
            rank = new Random().nextInt(1, 52);
        }
        usedCards.add(rank);

        int suit;
        Card returnValue = new Card();

        if (rank > 39) {
            rank = rank - 39;
            suit = 4;
        } else if (rank > 26) {
            rank = rank - 26;
            suit = 3;
        } else if (rank > 13) {
            rank = rank - 13;
            suit = 2;
        } else {
            suit = 1;
        }

        returnValue.suit(suit);
        returnValue.rank(rank);
        return returnValue;
    }
}
