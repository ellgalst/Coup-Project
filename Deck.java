/**
 * A separate class to keep track of the deck cards.
 */
import java.util.ArrayList;
public class Deck {
    public static final int cardCount = 15;
    ArrayList<Player> currentCards = new ArrayList<Player>(cardCount);

    public Deck() {
        for (int i = 0; i < 3; i++) {
            currentCards.add(new Player());
        }
    }
}
