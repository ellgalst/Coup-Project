/**
 * A separate class to keep track of the deck cards.
 */
import java.util.ArrayList;
import java.util.Random;

public class Deck {
    public static final int cardCount = 15;
    // Array of the cards in the deck. dynamic.
    ArrayList<Character> currentCards = new ArrayList<Character>(cardCount);

    // add cards in the deck
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

    // randomly chooses any amount of cards from the current array (can be deck or player's cards)
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

    // returns any amount of arrays of 2 characters to distribute to the players
    public ArrayList<Player> distributeCards(ArrayList<Player> currentPlayers) {
        Character[][] pickedCards = new Character[currentPlayers.size()][2];
        for (Player player : currentPlayers){
            player.influences = randomizer(this.currentCards, 2);
        }
        return currentPlayers;
    }
}
