package warsim;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;

public class WarSim {

    private static ArrayList<Integer> deck = new ArrayList<>();
    
    private static void InitializeDeck()
    {
        for(int i = 1; i < 14; i++)
        {
            deck.add(i);
            deck.add(i);
            deck.add(i);
            deck.add(i);
        }
        Collections.shuffle(deck);
        Collections.shuffle(deck);
        Collections.shuffle(deck);
        Collections.shuffle(deck);
    }
    
    private static void TransferCards(int times,ArrayList<Integer> wDeck,ArrayList<Integer> lDeck)
    {
        for (int i = 0; i < times; i++)
        {
            wDeck.add(lDeck.get(0));
            lDeck.remove(0);
        }   
    }
    
    public static void main(String[] args) {
        ArrayList<Integer> p1Deck = new ArrayList<>();
        ArrayList<Integer> p2Deck = new ArrayList<>();
        int winner=-1;
        int choice=-1;
        int tempChoice=-1;
        int gameChoice = -1;
        int turnCount = 0;
        String line=" ";
        String[] tempLine;
        InitializeDeck();

        Scanner scanner = new Scanner(System.in);
        System.out.print("Do you want to enter your cards, or run it with a random order of cards?\n1)My Own Cards\n2)Random Order\nInput: ");
        gameChoice=scanner.nextInt();
        scanner.nextLine();
        if(gameChoice==1)
        {
            System.out.print("Enter the first player's cards, with a space separating each. (Jack=11,Queen=12,King=13,Ace=1): ");
            line=scanner.nextLine();
            tempLine=line.split(" ");
            for(int i=0;i<tempLine.length;i++)
                p1Deck.add(Integer.parseInt(tempLine[i]));
            System.out.print("Enter the second player's cards, with a space separating each. (Jack=11,Queen=12,King=13,Ace=1): ");
            line=scanner.nextLine();
            tempLine=line.split(" ");
            for(int i=0;i<tempLine.length;i++)
                p2Deck.add(Integer.parseInt(tempLine[i]));
        }
        else if (gameChoice==2)
        {
            for (int i = 0; i < deck.size(); i++) {
                if (i % 2 == 0) {
                    p1Deck.add(deck.get(i));
                } else {
                    p2Deck.add(deck.get(i));
                }   
            }
        }
        else
        {
            System.out.println("Invalid Input. Try again");
        }
        System.out.print("Do you want to see the cards after each bout?\n1)Yes\n2)No\n3)Surprise Me\nInput: ");
        choice = scanner.nextInt();
        if (choice == 3) {
            tempChoice = (int) Math.ceil(Math.random() * 10);
            if (tempChoice % 2 == 0) {
                choice = 1;
            } else {
                choice = 2;
            }
        }
        while (!p1Deck.isEmpty() && !p2Deck.isEmpty() && winner == -1) 
        {
            if (choice == 1) 
            {
                System.out.print("\nP1: ");
                for (int i = 0; i < p1Deck.size(); i++) {
                    if (i != p1Deck.size() - 1) {
                        System.out.print(p1Deck.get(i) + ",");
                    } else {
                        System.out.print(p1Deck.get(i));
                    }
                }
                System.out.print("\nP2: ");
                for (int i = 0; i < p2Deck.size(); i++) {
                    if (i != p2Deck.size() - 1) {
                        System.out.print(p2Deck.get(i)+ ",");
                    } else {
                        System.out.print(p2Deck.get(i));
                    }
                }
                System.out.println();
            }
            turnCount++;
            if (p1Deck.get(0) < p2Deck.get(0)) {    //p1Deck lost
                TransferCards(1,p2Deck,p1Deck);
                TransferCards(1,p2Deck,p2Deck);
            } else if (p1Deck.get(0) > p2Deck.get(0)) {     //p2Deck lost
                TransferCards(1,p1Deck,p2Deck);
                TransferCards(1,p1Deck,p1Deck);
            } else if (p1Deck.get(0) == p2Deck.get(0)) {       //they equal once
                if (p1Deck.size() < 5) {
                    winner = 2;
                    p1Deck.clear();
                    break;
                } else if (p2Deck.size() < 5) {
                    winner = 1;
                    p2Deck.clear();
                    break;
                }
                if (p1Deck.get(4) < p2Deck.get(4)) {     //equal once, p1Deck loses
                    TransferCards(5,p2Deck,p1Deck);
                    TransferCards(5,p2Deck,p2Deck);
                } else if (p1Deck.get(4) > p2Deck.get(4)) {  //equal once, p1Deck wins
                    TransferCards(5,p1Deck,p2Deck);
                    TransferCards(5,p1Deck,p1Deck);
                } else if (p1Deck.get(4) == p2Deck.get(4)) {   //equals twice
                    if (p1Deck.size() < 9) {
                        winner = 2;
                        p1Deck.clear();
                        break;
                    } else if (p2Deck.size() < 9) {
                        winner = 1;
                        p2Deck.clear();
                        break;
                    }
                    if (p1Deck.get(8) < p2Deck.get(8)) {  //equals twice, p1Deck loses
                        TransferCards(9,p2Deck,p1Deck);
                        TransferCards(9,p2Deck,p2Deck);
                    } else if (p1Deck.get(8) > p2Deck.get(8)) {   //equals twice, p1Deck wins
                        TransferCards(9,p1Deck,p2Deck);
                        TransferCards(9,p1Deck,p1Deck);
                    } else if (p1Deck.get(8) == p2Deck.get(8)) {  //equal three times
                        if (p1Deck.size() < 13) {
                            p1Deck.clear();
                            winner = 2;
                            break;
                        } else if (p2Deck.size() < 13) {
                            winner = 1;
                            p2Deck.clear();
                            break;
                        }
                        if (p1Deck.get(12) < p2Deck.get(12)) {   //equals three times, p1Deck loses
                            TransferCards(13,p2Deck,p1Deck);
                            TransferCards(13,p2Deck,p2Deck);
                        } else if (p1Deck.get(12) > p2Deck.get(12)) {   //equals three times, p1Deck wins
                            TransferCards(13,p1Deck,p2Deck);
                            TransferCards(13,p1Deck,p1Deck);
                        }
                    }
                }
            }
        }
        if (winner == 1) {
            System.out.println("P1 WON");
        } else if (winner == 2) {
            System.out.println("P2 WON");
        } else if (p1Deck.size() != 0) {
            System.out.println("P1 WON");
        } else {
            System.out.println("P2 WON");
        }

        System.out.print("Do you want to see how many turns it took?\n1)Yes\n2)No\n3)Surprise Me\nInput: ");
        choice = scanner.nextInt();
        if (choice == 3) {
            tempChoice = (int) Math.ceil(Math.random() * 10);
            if (tempChoice % 2 == 0) {
                choice = 1;
            } else {
                choice = 0;
            }
        }
        if (choice == 1) {
            System.out.println("It took " + turnCount + " turns");
        }
    }
}
