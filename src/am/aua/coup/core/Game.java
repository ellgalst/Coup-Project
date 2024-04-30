package src.am.aua.coup.core;

import src.am.aua.coup.exceptions.InvalidNumberOfPlayersException;
import src.am.aua.coup.perform.BasePerformer;
import src.am.aua.coup.perform.Bot;
import src.am.aua.coup.perform.Player;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * A class to manage the game itself.
 */
public class Game {
    /**
     * The list of players participating in the game.
     */
    public ArrayList<BasePerformer> players;
    /**
     * The total number of players in the game.
     */
    public int numberOfPlayers;
    public String[] defaultNamesForPlayers = {"Suzan", "Brad", "Tom", "Maddy", "Dima", "Larisa"};
    /**
     * Initializes a new game with the specified number of players.
     *
     * @param numberOfPlayers The number of players in the game.
     */
    public Game(int numberOfPlayers) {
        super();
        this.numberOfPlayers = numberOfPlayers;
        this.players = new ArrayList<Player>(numberOfPlayers);
        for (int i = 0; i < numberOfPlayers; i++) {
            this.players.add(new Player(defaultNamesForPlayers[i],2, false));
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

    public BasePerformer choosePlayerFromBots(ArrayList<Bot> players) {
        Random random = new Random();
        ArrayList<BasePerformer> possibleChallengers = new ArrayList<BasePerformer>();
        for (Bot player : players) {
            if (player.getNumberOfInfluences() == 2){
                possibleChallengers.add(player);
            }
        }
        return possibleChallengers.get(random.nextInt(possibleChallengers.size()));
    }

    /**
     * Starts the game.
     *
     * @throws InvalidNumberOfPlayersException If the number of players is invalid.
     */
     public void start() throws InvalidNumberOfPlayersException {
        if (!(this.numberOfPlayers > 1 && this.numberOfPlayers <= 7)) {
            throw new InvalidNumberOfPlayersException();
        }

         Scanner userInput = new Scanner(System.in);

         ArrayList<BasePerformer> playerList = new ArrayList<BasePerformer>(numberOfPlayers);

         System.out.println("Enter your name: ");
         String playerName = userInput.nextLine();
         Player humanPlayer = new Player(playerName,2, true);
         humanPlayer.setName(playerName);
         playerList.add(humanPlayer);

        for (int i = 0; i < numberOfPlayers - 1; i++) {
            playerList.add(new Player(defaultNamesForPlayers[i],2, false));
        }
        Deck myDeck = new Deck();
        players = myDeck.distributeCards(playerList);

        for (BasePerformer player : players) {
            System.out.println(player.getInfluences());
        }
    }


}