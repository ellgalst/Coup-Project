package src.am.aua.coup.core;

import src.am.aua.coup.exceptions.InvalidNumberOfPlayersException;
import src.am.aua.coup.perform.BasePerformer;
import src.am.aua.coup.perform.Bot;
import src.am.aua.coup.perform.Player;

import java.util.ArrayList;
import java.util.List;
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
    /**
     * Default names for the bots.
     */
    private final String[] defaultNamesForPlayers = {"Suzan", "Brad", "Pedro", "Maddy", "Dima", "Larisa"};

    // getter
    public String[] getDefaultNamesForPlayers(){
        return defaultNamesForPlayers;
    }
    /**
     * Initializes a new game with the specified number of players.
     *
     * @param numberOfPlayers The number of players in the game.
     */
    public Game(int numberOfPlayers) {
        super();
        this.numberOfPlayers = numberOfPlayers;
        this.players = new ArrayList<BasePerformer>(numberOfPlayers);
        for (int i = 0; i < numberOfPlayers; i++) {
            this.players.add(new Bot(defaultNamesForPlayers[i], 2));
        }
    }

    /**
     * Updates the list of players removing any players who have lost all their influences.
     *
     * @param currentPlayer The player who just took a turn.
     * @return The updated list of players.
     */
    public ArrayList<BasePerformer> updatePlayers(BasePerformer currentPlayer) {
        List<BasePerformer> playersToRemove = new ArrayList<>();
        for (BasePerformer player : players) {
            if (player.getInfluences().isEmpty()) {
                System.out.println("Player " + player + " left the game!");
                playersToRemove.add(player);
            }
        }
        players.removeAll(playersToRemove);
        for (BasePerformer basePerformer : playersToRemove) {
            System.out.println("Player " + basePerformer + " left the game!");
        }
        int index = players.indexOf(currentPlayer);
        ArrayList<BasePerformer> new_players = new ArrayList<>(players.subList(index + 1, players.size()));
        new_players.addAll(players.subList(0, index + 1));

        return new_players;
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
     * Allows the current player to choose a challenger for their action.
     *
     * @param players      The list of players.
     * @param currentPlayer The player who performed the action.
     * @return The chosen challenger.
     */
    public static BasePerformer chooseChallenger(ArrayList<BasePerformer> players, BasePerformer currentPlayer) {
        BasePerformer challenger = null;
        Scanner scanner = new Scanner(System.in);
        boolean correct = false;

        if (currentPlayer instanceof Player) {
            challenger = Bot.chooseBot(players);
        } else {
            ArrayList<Player> humanPlayers = new ArrayList<>();

            for (BasePerformer player : players) {
                if (player instanceof Player) {
                    humanPlayers.add((Player) player);
                }
            }

            while (!correct) {
                if (!humanPlayers.isEmpty()) {
                    System.out.println(humanPlayers.getFirst() + ", do you want to challenge " + currentPlayer + "? Answer yes or no!");
                    String answer = scanner.next();
                    if (answer.equalsIgnoreCase("yes")) {
                        challenger = humanPlayers.getFirst();
                        correct = true;
                    } else if (answer.equalsIgnoreCase("no") && players.size() > 2) {
                        challenger = Bot.chooseBot(players, (Bot) currentPlayer);
                        correct = true;
                    } else if (answer.equalsIgnoreCase("no") && players.size() <= 2) {
                        return null;
                    } else {
                        System.out.println("Choose yes or no, try again!");
                    }
                } else {
                    challenger = Bot.chooseBot(players, (Bot) currentPlayer);
                    correct = true;
                }
            }
        }
        return challenger;

    }

    /**
     * Prints data about the current state of the game.
     */
    public void printData() {
        System.out.println(players);
        for (BasePerformer current : players) {
            System.out.println(current + "'s influences: " + current.getInfluences());
            System.out.println("Wallet: " + current.getWallet());
        }
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
        String playerName = userInput.next();
        Player humanPlayer = new Player(playerName, 2);
        humanPlayer.setName(playerName);
        playerList.add(humanPlayer);

        for (int i = 0; i < numberOfPlayers - 1; i++) {
            playerList.add(new Bot(defaultNamesForPlayers[i], 2));
        }

        Deck myDeck = new Deck();
        players = myDeck.distributeCards(playerList);
        System.out.println(players);

        for (BasePerformer player : players) {
            System.out.println(player.getInfluences());
        }
    }
}