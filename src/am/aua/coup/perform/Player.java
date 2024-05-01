package src.am.aua.coup.perform;

import src.am.aua.coup.core.Action;
import src.am.aua.coup.core.Game;
import src.am.aua.coup.influences.Character;

import java.util.*;

/**
 * A separate class to keep track of all players.
 */

public class Player extends BasePerformer {
    public Player() {
        super.setName("Pedro");
        super.setWallet(2);
    }

    /**
     * Constructor method.
     * Initializes a player with the specified initial number of coins in their wallet. If the player is a bot
     * initializes its name.
     *
     * @param initialCoins The initial number of coins in the player's wallet.
     */
    // isHuman remove
    public Player(String playerName, int initialCoins) {
        super.setName(playerName);
        this.setWallet(initialCoins);
    }

    /**
     * A method to return the action user wants to do from the given list of available actions.
     *
     * @param available arrayList of available items to choose.
     * @return action the user wants to do.
     */
    public <T> T getUserChoice(ArrayList<T> available) {
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
                if (index >= 0 && index < available.size()) {
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

    // a method that that handles the action in the user's turn, returns whether the user cheated
    public boolean act(Game myGame) {
        Scanner user = new Scanner(System.in);

        boolean validAnswer = false;
        while (!validAnswer) {
            System.out.println(this.getName() + ", do you want to cheat in this turn? Answer yes or no!");
            if (user.next().equalsIgnoreCase("no")) {
                this.setCheat(false);
                validAnswer = true;
            } else if (user.next().equalsIgnoreCase("yes")) {
                this.setCheat(true);
                validAnswer = true;
            } else {
                System.out.println("Not sure what you typed. Try again.");
            }
        }
        Action.Types chosenAction = this.getUserChoice(this.getAvailableActions());
        Bot target = null;
        if (chosenAction == Action.Types.STEAL || chosenAction == Action.Types.ASSASSINATE || chosenAction == Action.Types.COUP) {
            System.out.println("Please, choose the player you want to perform the action on: ");
            ArrayList<BasePerformer> currentBots = new ArrayList<>(myGame.players);
            target = Bot.chooseBot(currentBots);
        }
        Action.performAction(this, chosenAction, target);
        return this.getCheat();
    }


    // a method for the user to challenge the bot's block or action. returns true if the challenge is successful, otherwise - false
    public boolean challenges(BasePerformer botToChallenge, ArrayList<Character> myDeck, Action.Types action, boolean isActionChallenge) {
        Scanner userInput = new Scanner(System.in);
        if (isActionChallenge) {
            System.out.println(this.getName() + ", do you want to challenge player " + botToChallenge.getName() + "'s action? Answer yes or no!");
            boolean wantsToChallenge = userInput.next().equalsIgnoreCase("yes");
            if (wantsToChallenge) {
                return this.challenge((Bot) botToChallenge, myDeck, action, true);
            }
        } else {
            System.out.println(this.getName() + ", do you want to challenge player " + botToChallenge.getName() + "'s block? Answer yes or no!");
            String answer = userInput.next();
            if (answer.equalsIgnoreCase("yes")) {
                return this.challenge((Bot) botToChallenge, myDeck, action, false);
            }
        }
        return false;
    }


    // block for user
    public boolean block(BasePerformer blocked, ArrayList<Character> myDeck, Action.Types action) {
        Scanner userInput = new Scanner(System.in);
        System.out.println(this.getName() + ", do you want to block player " + blocked.getName() + "'s action? Answer yes or no!");
        if (userInput.next().equalsIgnoreCase("yes")) {
            System.out.println(this.getName() + " decided to block player " + blocked.getName() + "'s action!");
            Bot target = (Bot) blocked;
            return !target.challenges(this, myDeck, action, false);
        }
        return false;
    }


}