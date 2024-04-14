/**
 * A separate class to keep track of the deck cards.
 */
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
    ArrayList<Character> currentCards = new ArrayList<Character>(cardCount);

    /**
     * Initializes the deck with the default set of cards.
     *
     * @return The arraylist of initialized cards.
     */
    public ArrayList<Character> initializeDeck() {
        for (int i = 0; i < 3; i++) {
            currentCards.add(new Ambassador());
        }
        for (int i = 0; i < 3; i++) {
            currentCards.add(new Assassin());
        }
        for (int i = 0; i < 3; i++) {
            currentCards.add(new Captain());
        }
        for (int i = 0; i < 3; i++) {
            currentCards.add(new Contessa());
        }
        for (int i = 0; i < 3; i++) {
            currentCards.add(new Duke());
        }
        return currentCards;
    }

    /**
     * Randomly selects a specified number of cards from the given deck.
     *
     * @param deck  The deck from which cards are selected.
     * @param count The number of cards to select.
     * @return The arraylist of randomly selected cards.
     */
     public ArrayList<Character> randomizer(ArrayList<Character> deck, int count) {
        Random rn = new Random();
        int length = deck.size();

        if (count >= length) {
            System.out.println("Count cannot be greater than the size of the deck.");
            return null;
        }

        ArrayList<Character> randomCharacters = new ArrayList<Character>(count);

        for (int i = 0; i < count; i++) {
            int currentIndex = rn.nextInt(length);
            randomCharacters.add(deck.get(currentIndex));
            deck.remove(currentIndex);
            length--;
        }

        return randomCharacters;
    }

    /**
     * Distributes cards to the players from the deck.
     *
     * @param currentPlayers The arraylist of players to whom cards will be distributed.
     * @return The arraylist of players with their respective cards.
     */
     public ArrayList<Player> distributeCards(ArrayList<Player> currentPlayers) {
        Character[][] pickedCards = new Character[currentPlayers.size()][2];
        for (Player player : currentPlayers){
            player.influences = randomizer(this.currentCards, 2);
        }
        return currentPlayers;
    }
}
