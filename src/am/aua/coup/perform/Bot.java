package src.am.aua.coup.perform;

import src.am.aua.coup.core.Action;
import src.am.aua.coup.core.Game;
import src.am.aua.coup.influences.Character;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * A class representing a bot player in the coup game.
 */
public class Bot extends BasePerformer {
    // possibilities
    private final static double CHALLENGE_PROBABILITY = 0.5;
    private final static double BLOCK_POSSIBILITY = 0.5;
    private final static double CHEAT_PROBABILITY = 0.5;

    /**
     * Initializes a bot player with default name "Miguel" and initial coins of 2.
     */
    public Bot() {
        super.setName("Miguel");
        this.setWallet(2);
    }

    /**
     * Initializes a bot player with the given name and initial coins.
     *
     * @param playerName    The name of the bot player.
     * @param initialCoins  The initial number of coins for the bot player.
     */
    public Bot(String playerName, int initialCoins) {
        super.setName(playerName);
        this.setWallet(initialCoins);
    }

    /**
     * Chooses a bot player randomly from the list of players.
     *
     * @param players The list of players.
     * @return A randomly chosen bot player.
     */
    public static Bot chooseBot(ArrayList<BasePerformer> players) {
        Random random = new Random();
        ArrayList<BasePerformer> possibleChallengers = new ArrayList<BasePerformer>();

        for (BasePerformer player : players) {
            if (player instanceof Bot) {
                possibleChallengers.add(player);
            }
        }
        return (Bot) possibleChallengers.get(random.nextInt(possibleChallengers.size()));
    }

    /**
     * Chooses a bot player randomly from the list of players except the current bot.
     *
     * @param players     The list of players.
     * @param currentBot  The current bot player.
     * @return A randomly chosen bot player excluding the current bot.
     */
    public static Bot chooseBot(ArrayList<BasePerformer> players, Bot currentBot) {
        ArrayList<BasePerformer> possibleChallengers = new ArrayList<BasePerformer>();
        for (BasePerformer player : players) {
            if ((player instanceof Bot) && !player.equals(currentBot)) {
                possibleChallengers.add(player);
            }
        }
        Random random = new Random();
        if (possibleChallengers.size() == 1) {
            return (Bot) possibleChallengers.getFirst();
        }
        return (Bot) possibleChallengers.get(random.nextInt(possibleChallengers.size()));
    }

    /**
     * Performs an action for the bot player's turn.
     *
     * @param myGame The current game state.
     * @return The type of action chosen by the bot.
     */
    public Action.Types act(Game myGame) {
        Random number = new Random();
        this.setCheat(number.nextDouble() >= CHEAT_PROBABILITY);
        ArrayList<Action.Types> available = Action.getAvailableActions(this);
        int randomIndex;
        randomIndex = (int) (Math.random() * (available.size()));
        Action.Types chosenAction = available.get(randomIndex);
        System.out.println(this + " decided to perform " + chosenAction + "!");
        return chosenAction;
    }

    /**
     * Handles challenges initiated by the bot player.
     *
     * @param playerToChallenge  The player being challenged.
     * @param myDeck             The deck.
     * @param action             The action being challenged.
     * @param isActionChallenge True if challenging an action, false if challenging a block.
     * @return True if the challenge is successful, false otherwise.
     */
    public boolean challenges(BasePerformer playerToChallenge, ArrayList<Character> myDeck, Action.Types action, boolean isActionChallenge) {
        Random challenge = new Random();

        double nextDouble = challenge.nextDouble();
        if (nextDouble >= CHALLENGE_PROBABILITY) {
            if (isActionChallenge) {
                System.out.println("Player " + this + " decided to challenge the player " + playerToChallenge + "'s action!");
                return this.challenge(playerToChallenge, myDeck, action, true);
            } else {
                System.out.println("Player " + this + " decided to challenge the player " + playerToChallenge + "'s block!");
                return this.challenge(playerToChallenge, myDeck, action, false);
            }
        }
        return true;
    }

    /**
     * Handles blocking actions initiated by the bot player.
     *
     * @param blocked The player attempting to block.
     * @param myDeck  The deck.
     * @param action  The action being blocked.
     * @return True if the block is successful, false otherwise.
     */
    public boolean block(BasePerformer blocked, ArrayList<Character> myDeck, Action.Types action) {
        Random number = new Random();
        if (number.nextDouble() >= BLOCK_POSSIBILITY) {
            System.out.println("Player " + this + " decided to block " + blocked + "'s action!");
            boolean answer;
            if (blocked instanceof Player) {
                Scanner userInput = new Scanner(System.in);
                System.out.println("Do you want to challenge them? Answer yes or no!");
                answer = userInput.next().equalsIgnoreCase("yes");
            } else {
                answer = number.nextDouble() >= CHALLENGE_PROBABILITY;
            }
            if (answer) {
                System.out.println(blocked + " decided to challenge player " + this + "'s block!");
                return blocked.challenge(this, myDeck, action, false);
            } else {
                return true;
            }
        }
        return false;
    }

}