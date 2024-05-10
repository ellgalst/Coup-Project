package src.am.aua.coup.perform;

import src.am.aua.coup.core.Action;
import src.am.aua.coup.core.Game;
import src.am.aua.coup.influences.Character;

import java.util.*;

/**
 * A class representing a human player in the coup game.
 */
public class Player extends BasePerformer {
    /**
     * Initializes a player with default name "Pedro" and initial coins of 2.
     */
    public Player() {
        super.setName("Pedro");
        super.setWallet(2);
    }

    /**
     * Initializes a player with the specified name and initial coins in their wallet.
     *
     * @param playerName   The name of the player.
     * @param initialCoins The initial number of coins in the player's wallet.
     */
    public Player(String playerName, int initialCoins) {
        super.setName(playerName);
        this.setWallet(initialCoins);
    }

    /**
     * Prompts the user to choose an action from the available options.
     *
     * @param available An ArrayList of available actions to choose from.
     * @return The action chosen by the user.
     */
    public static <T> T getUserChoice(ArrayList<T> available) {
        Scanner userInput = new Scanner(System.in);
        int index;

        System.out.println("Available: ");
        for (int i = 0; i < available.size(); i++) {
            System.out.println("The item behind index " + i + " is: " + available.get(i));
        }

        while (true) {
            System.out.print("Enter an index for the preferred item: ");
            if (userInput.hasNextInt()) {
                index = userInput.nextInt();
                if (index >= available.size()) {
                    System.out.println("Now you can't perform this action, please try again later");
                } else if (index >= 0) {
                    return available.get(index);
                } else {
                    System.out.println("Please, enter a number within the range of the provided items.");
                }
            } else {
                System.out.println("Please enter a NUMBER!");
                userInput.next();
            }
        }
    }

    /**
     * Gets the target bot for an action chosen by the player.
     *
     * @param players The list of players including bots.
     * @return The chosen target bot.
     */
    public static Bot getTarget(ArrayList<BasePerformer> players) {
        ArrayList<Bot> available = new ArrayList<Bot>();
        for (BasePerformer player : players) {
            if (player instanceof Bot) {
                available.add((Bot) player);
            }
        }
        return getUserChoice(available);

    }

    /**
     * Handles the action chosen by the player during their turn.
     *
     * @param myGame The current game state.
     * @return The type of action chosen by the player.
     */
    public Action.Types act(Game myGame) {
        Scanner scanner = new Scanner(System.in);
        boolean validAnswer = false;
        while (!validAnswer) {
            System.out.println(this + ", do you want to cheat in this turn? Answer yes or no!");
            String answer = scanner.next();
            if (answer.equalsIgnoreCase("no")) {
                this.setCheat(false);
                validAnswer = true;
            } else if (answer.equalsIgnoreCase("yes")) {
                this.setCheat(true);
                validAnswer = true;
            } else {
                System.out.println("Not sure what you typed. Try again.");
            }
        }
        return getUserChoice(Action.getAvailableActions(this));
    }

    /**
     * Handles challenges initiated by the player.
     *
     * @param botToChallenge    The bot being challenged.
     * @param myDeck            The deck.
     * @param action            The action being challenged.
     * @param isActionChallenge True if challenging an action, false if challenging a block.
     * @return True if the challenge is successful, false otherwise.
     */
    public boolean challenges(BasePerformer botToChallenge, ArrayList<Character> myDeck, Action.Types action, boolean isActionChallenge) {
        if (isActionChallenge) {
            return this.challenge(botToChallenge, myDeck, action, true);
        } else {
            return this.challenge(botToChallenge, myDeck, action, false);
        }
    }


    /**
     * Handles blocking actions initiated by the player.
     *
     * @param blocked The bot player attempting to block.
     * @param myDeck  The deck.
     * @param action  The action being blocked.
     * @return True if the block is successful, false otherwise.
     */
    public boolean block(BasePerformer blocked, ArrayList<Character> myDeck, Action.Types action) {
        Scanner userInput = new Scanner(System.in);
        System.out.println(this + ", do you want to block player " + blocked + "'s action? Answer yes or no!");
        if (userInput.next().equalsIgnoreCase("yes")) {
            System.out.println(this + " decided to block player " + blocked + "'s action!");
            Bot target = (Bot) blocked;
            return target.challenges(this, myDeck, action, false);
        }
        return false;
    }

}