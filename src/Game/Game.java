package Game;

import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class Game implements Runnable {
    public Card[] playerCards = new Card[2];
    public Card[] computerCards = new Card[2];

    public boolean isPlayerBigBlind = true;

    public LinkedList<Integer> usedCards = new LinkedList<>();

    public int playerBetAmount = 0;
    public int computerBetAmount = 0;
    public int pot = 0;

    public Game() {
//        Deal cards for player & computer
        playerCards[0] = newRandomCard();
        playerCards[1] = newRandomCard();
        computerCards[0] = newRandomCard();
        computerCards[1] = newRandomCard();

//        TODO: Randomize isPlayerBigBlind either true or false


    }

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

    public synchronized void playerBet(int value) {
        playerBetAmount = playerBetAmount + value;
        pot = playerBetAmount + computerBetAmount;
        notify();
    }

    @Override
    public void run()  {
        boolean bettingNotOver = false;
        if (isPlayerBigBlind) {
            while (!bettingNotOver) {
//                Waiting for player to bet
                try {
                    wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }

//                TODO Computer Betting code goes here
            }
        } else {
            while (!bettingNotOver) {
//                TODO Computer Betting code goes here

//                Waiting for player to bet
                try {
                    wait();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
