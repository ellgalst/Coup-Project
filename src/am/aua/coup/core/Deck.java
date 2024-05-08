package src.am.aua.coup.core;
/**
 * A separate class to keep track of the deck cards with the use of randomizer.
 */

import src.am.aua.coup.influences.*;
import src.am.aua.coup.influences.Character;
import src.am.aua.coup.perform.BasePerformer;

import java.util.ArrayList;
import java.util.Random;

public class Deck {
    /**
     * The total number of cards in the deck.
     */
    public static final int cardCount = 15;
    /**
     * Arraylist to store the current cards in the deck.
     */
    public static ArrayList<Character> deck = new ArrayList<Character>(cardCount);


    /**
     * No-arg constructor
     * Initializes the deck with the default set of cards.
     */
    public Deck() {
        for (int i = 0; i < 3; i++) {
            deck.add(new Ambassador());
        }
        for (int i = 0; i < 3; i++) {
            deck.add(new Assassin());
        }
        for (int i = 0; i < 3; i++) {
            deck.add(new Captain());
        }
        for (int i = 0; i < 3; i++) {
            deck.add(new Contessa());
        }
        for (int i = 0; i < 3; i++) {
            deck.add(new Duke());
        }
    }

    /**
     * Accessor method
     */
    public ArrayList<Character> getDeck() {
        return new ArrayList<>(deck);
    }

    public static void addToDeck(Character influence){
        deck.add(influence);
    }


    /**
     * Randomly selects a specified number of cards from the given deck.
     *
     * @param deck  The deck from which cards are selected.
     * @param count The number of cards to select.
     * @return The arraylist of randomly selected cards.
     */
    public static ArrayList<Character> randomizer(ArrayList<Character> deck, int count) {
        Random random = new Random();

        if (count > deck.size()) {
            System.out.println("Count cannot be greater than or equal to the size of the deck.");
            return new ArrayList<>();
        }

        ArrayList<Character> randomCharacters = new ArrayList<Character>(count);

        for (int i = 0; i < count; i++) {
            int randomIndex = random.nextInt(deck.size());
            randomCharacters.add(deck.get(randomIndex));
        }
        return randomCharacters;
    }


    /**
     * Distributes cards to the players from the deck.
     *
     * @param currentPlayers The arraylist of players to whom cards will be distributed.
     * @return The arraylist of players with their respective cards.
     */
    public ArrayList<BasePerformer> distributeCards(ArrayList<BasePerformer> currentPlayers) {
        for (BasePerformer player : currentPlayers) {
            player.setInfluences(randomizer(deck, 2));
        }
        return currentPlayers;
    }
}