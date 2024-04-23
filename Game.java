import java.util.ArrayList;
import java.util.Scanner;

/**
 * A class to manage the game itself.
 */
public class Game {
    /**
     * The list of players participating in the game.
     */
    public ArrayList<Player> players;
    /**
     * The total number of players in the game.
     */
    public int numberOfPlayers;
    /**
     * The deck of cards used in the game.
     */

    /**
     * Initializes a new game with the specified number of players.
     *
     * @param numberOfPlayers The number of players in the game.
     */
    Game(int numberOfPlayers) {
        super();
        this.numberOfPlayers = numberOfPlayers;
        this.players = new ArrayList<Player>(numberOfPlayers);
        for (int i = 0; i < numberOfPlayers; i++) {
            this.players.add(new Player(2, false));
        }
    }

    /**
     * Checks if the game is still ongoing.
     *
     * @return True if the game is still ongoing, otherwise false.
     */
    public boolean isNotOver() {
        return players.size() != 1;
    }

    /**
     * Starts the game.
     *
     * @throws InvalidNumberOfPlayers If the number of players is invalid.
     */
     public void start() throws InvalidNumberOfPlayers {
        if (!(this.numberOfPlayers > 1 && this.numberOfPlayers <= 7)) {
            throw new InvalidNumberOfPlayers();
        }

         Scanner userInput = new Scanner(System.in);

         ArrayList<Player> playerList = new ArrayList<Player>(numberOfPlayers);

         System.out.println("Enter your name: ");
         String playerName = userInput.nextLine();
         Player humanPlayer = new Player(2, true);
         humanPlayer.setName(playerName);
         playerList.add(humanPlayer);

        for (int i = 0; i < numberOfPlayers - 1; i++) {
            playerList.add(new Player(2, false));
        }
        Deck myDeck = new Deck();
        players = myDeck.distributeCards(playerList);

        for (Player player : players) {
            System.out.println(player.influences);
        }
    }


}