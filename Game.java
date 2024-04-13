import java.util.ArrayList;

/**
 * A class to manage the game itself.
 */
public class Game {
    public ArrayList<Player> players;
    public int numberOfPlayers;
    Deck myDeck = new Deck();

    Game(int numberOfPlayers) {
        super();
        this.numberOfPlayers = numberOfPlayers;
        this.players = new ArrayList<Player>(numberOfPlayers);
        for (int i = 0; i < numberOfPlayers; i++) {
            this.players.add(new Player(2));
        }
    }

    // checks if the game is still going on
    public boolean isNotOver() {
        return players.size() != 1;
    }

    // a method that starts the game (the process)
    public void start() throws InvalidNumberOfPlayers {
        if (!(this.numberOfPlayers > 1 && this.numberOfPlayers <= 7)) {
            throw new InvalidNumberOfPlayers();
        }

        ArrayList<Player> playerList = new ArrayList<Player>(numberOfPlayers);
        for (int i = 0; i < numberOfPlayers; i++) {
            playerList.add(new Player(2));
        }

        Deck myDeck = new Deck();
        ArrayList<Character> gameCards = myDeck.initializeDeck();
        players = myDeck.distributeCards(playerList);

        for (Player player : players) {
            System.out.println(player.influences);
        }
    }


}