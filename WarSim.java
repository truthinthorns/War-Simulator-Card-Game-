package warsim;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

record WarResult(int winner, int turnCount) {};

public class WarSim {

    private static final Random RANDOM = new Random();
    private static final int MAXTURNS = 1000;

    // this method adds cards with values 1-13 four times to the deck to simulate a real deck of cards.
    // After, it shuffles the deck.
    private static Deque<Integer> initializeDeck() {
        List<Integer> deck = new ArrayList<>();
        for (int i = 1; i < 14; i++) {
            for (int suit = 0; suit < 4; suit++) {
                deck.add(i);
            }
        }
        Collections.shuffle(deck);
        return new ArrayDeque<>(deck);
    }

    // this methods transfers the cards from the war pile to the winning deck
    private static void transferCards(Deque<Integer> winningDeck, Deque<Integer> warPile) {
        while (!warPile.isEmpty()) {
            winningDeck.addLast(warPile.removeFirst());
        }
    }

    // this method gets a random boolean
    private static int getSurpriseMeChoice() {
        return RANDOM.nextBoolean() ? 1 : 2;
    }

    private static WarResult gameLoop(int choice, Deque<Integer> p1Deck, Deque<Integer> p2Deck) {
        Deque<Integer> warPile = new ArrayDeque<>();
        int winner = -1;
        int turnCount = 0;
        // while both players have at least 1 card, and a winner hasn't been decided.
        while (!p1Deck.isEmpty() && !p2Deck.isEmpty() && winner == -1) {
            //if the user wants to see the cards after each bout/round. the if/else blocks adds a comma after each card if it's not the last one in the player's deck.
            if (choice == 1) {
                System.out.println(p1Deck);
                System.out.println(p2Deck);
            }
            turnCount++;
            if (turnCount == MAXTURNS) {
                winner = 0;
                break;
            }
            while (true) {
                if (p1Deck.isEmpty()) {
                    winner = 2;
                    break;
                }
                if (p2Deck.isEmpty()) {
                    winner = 1;
                    break;
                }
                int p1Card = p1Deck.removeFirst();
                int p2Card = p2Deck.removeFirst();
                warPile.addLast(p1Card);
                warPile.addLast(p2Card);
                if (p1Card < p2Card) {
                    transferCards(p2Deck, warPile);
                    break;
                } else if (p1Card > p2Card) {
                    transferCards(p1Deck, warPile);
                    break;
                }
                for (int i = 0; i < 3; i++) {
                    if (p1Deck.isEmpty()) {
                        winner = 2;
                        break;
                    }
                    if (p2Deck.isEmpty()) {
                        winner = 1;
                        break;
                    }
                    warPile.addLast(p1Deck.removeFirst());
                    warPile.addLast(p2Deck.removeFirst());
                }
                if (winner != -1) {
                    break;
                }
            }
        }
        return new WarResult(winner, turnCount);
    }

    public static void main(String[] args) {
        Deque<Integer> deck = initializeDeck();
        Deque<Integer> p1Deck = new ArrayDeque<>();
        Deque<Integer> p2Deck = new ArrayDeque<>();
        int choice;

        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Do you want to enter your cards, or run it with a random order of cards?\n1)My Own Cards\n2)Random Order\nInput: ");
            int gameChoice = scanner.nextInt();
            scanner.nextLine();

            //this code block prompts the user to enter each players cards, with each card value being separated by a space. it then adds the entered values/cards into the
            //corresponding player's deck
            switch (gameChoice) {
                case 1 -> {
                    System.out.print("Enter the first player's cards, with a space separating each. (Jack=11,Queen=12,King=13,Ace=1): ");
                    for (String line : scanner.nextLine().split(" ")) {
                        p1Deck.add(Integer.parseInt(line));
                    }
                    System.out.print("Enter the second player's cards, with a space separating each. (Jack=11,Queen=12,King=13,Ace=1): ");
                    for (String line : scanner.nextLine().split(" ")) {
                        p2Deck.add(Integer.parseInt(line));
                    }
                }
                case 2 -> {
                    // this code block simulates if the cards were dealt out one at a time to each player.
                    while (!deck.isEmpty()) {
                        p1Deck.addLast(deck.removeFirst());
                        p2Deck.addLast(deck.removeFirst());
                    }
                }
                default ->
                    System.out.println("Invalid Input. Try again");
            }
            System.out.print("Do you want to see the cards after each bout?\n1)Yes\n2)No\n3)Surprise Me\nInput: ");
            choice = scanner.nextInt();
            if (choice == 3) {
                choice = getSurpriseMeChoice();
            }

            WarResult result = gameLoop(choice, p1Deck, p2Deck);
            //output a message based on who won
            switch (result.winner()) {
                case 0 ->
                    System.out.println("Nobody WON... :(");
                case 1 ->
                    System.out.println("P1 WON");
                case 2 ->
                    System.out.println("P2 WON");
            }

            System.out.print("Do you want to see how many turns it took?\n1)Yes\n2)No\n3)Surprise Me\nInput: ");
            choice = scanner.nextInt();
            if (choice == 3) {
                choice = getSurpriseMeChoice();
            }
            if (choice == 1) {
                System.out.println("It took " + result.turnCount() + " turns");
            }
        }
    }
}
