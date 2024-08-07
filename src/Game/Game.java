package Game;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Random;
import java.util.concurrent.ExecutionException;

public class Game implements Runnable {
    public Card[] playerCards = new Card[2];
    public Card[] computerCards = new Card[2];
    public Card[] communityCards = new Card[5];

    public boolean isPlayerBigBlind = true;

    public LinkedList<Integer> usedCards = new LinkedList<>();

    public int playerBetAmount = 5;
    public int computerBetAmount = 5;
    public int pot = 0;
    public int bettingRound = 0;

    public Thread bettingThread = new Thread(this);

    public Game() {
//        Deal cards for player & computer
        playerCards[0] = newRandomCard();
        playerCards[1] = newRandomCard();
        computerCards[0] = newRandomCard();
        computerCards[1] = newRandomCard();

//        Set Community Cards
        communityCards[0] = newRandomCard();
        communityCards[1] = newRandomCard();
        communityCards[2] = newRandomCard();
        communityCards[3] = newRandomCard();
        communityCards[4] = newRandomCard();

//        TODO: Randomize isPlayerBigBlind either true or false

        if (isPlayerBigBlind) {
            playerBetAmount = playerBetAmount + 5;
        } else {
            computerBetAmount = computerBetAmount + 5;
        }

        bettingRound++;

        bettingThread.start();

        bettingRound++;

        bettingThread.start();

        bettingRound++;

        bettingThread.start();

        bettingRound++;

        bettingThread.start();

        gameOver();
    }

    private int checkPairs(Card[] list, boolean threeOfAKind) {
        int number;
        int length = list.length;
        int pairs = 0;
        int streak = 0;

        for (int i = 0; i < length; i++) {
            number = list[i].rank();
            list[i].rank(100);

            for (Card currentCard : list) {
                if (number == currentCard.rank()) {
                    if (threeOfAKind) {
                        if (streak == 1) {
                            pairs++;
                            streak = 0;
                            break;
                        } else {
                            streak++;
                        }
                    } else {
                        pairs++;
                        break;
                    }
                }
            }
        }

        return pairs;
    }

    private int getComputerCardsValue() {
        int value = 0;

        if (computerCards[0].value() > 7 || computerCards[1].value() > 7) { // High Card
            value = 1;
        } if (computerCards[0].rank() == computerCards[1].rank()) { // One Pair
            value = 2;
        } if (bettingRound >= 2) {
            Card[] listOfCards = new Card[0];

            if (bettingRound == 2) {
                listOfCards = new Card[]{computerCards[0], computerCards[1], communityCards[0], communityCards[1], communityCards[2]};
            } else if (bettingRound == 3) {
                listOfCards = new Card[]{computerCards[0], computerCards[1], communityCards[0], communityCards[1], communityCards[2], communityCards[3]};
            } else if (bettingRound == 4) {
                listOfCards = new Card[]{computerCards[0], computerCards[1], communityCards[0], communityCards[1], communityCards[2], communityCards[3], communityCards[4]};
            }

            if (checkPairs(listOfCards, false) == 1) { // One Pair
                value = 2;
            } if (checkPairs(listOfCards, false) == 2) { // Two Pair
                value = 3;
            } if (checkPairs(listOfCards, true) >= 1) { // Three Pair
                value = 4;
            } if (checkPairs(listOfCards, false) >= 1 && checkPairs(listOfCards, true) >= 1) {
                value = 7;
            }

            if (bettingRound >= 3) {
                int streak = 0;
                int prev = listOfCards[0].suit();
                int[] list = new int[listOfCards.length];
                int i = 0;
                for (Card card : listOfCards) {
                    if (card.suit() == prev) {
                        streak++;
                    }
                    prev = card.suit();
                    list[i] = card.rank();
                    i++;
                }

                if (streak >= 5) { // Flush
                    value = 6;
                }

                Arrays.sort(list);
                int prevElement = 0;
                int straight = 2;
                for (int element : list) {
                    if (prevElement == element && straight != 0) {
                        straight = 1;
                    } else {
                        straight = 0;
                    }
                }

                if (straight == 1) { // Straight
                    value = 5;
                }
            }
        }

        return value;
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

    public void gameOver() {
//        TODO Game Over
    }
}
