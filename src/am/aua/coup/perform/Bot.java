package src.am.aua.coup.perform;

import src.am.aua.coup.core.Action;
import src.am.aua.coup.core.Game;
import src.am.aua.coup.influences.Character;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Bot extends BasePerformer {
    // possibilities
    private final static double CHALLENGE_PROBABILITY = 0.5;
    private final static double BLOCK_POSSIBILITY = 0.5;
    private final static double CHEAT_PROBABILITY = 0.5;

    public Bot() {
        super.setName("Miguel");
        this.setWallet(2);
    }

    public Bot(String playerName, int initialCoins) {
        super.setName(playerName);
        this.setWallet(initialCoins);
    }

    // a method to get choose one Bot from the list of bots
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

    // a method to get choose one Bot, except the current one, from the list of bots
    public static Bot chooseBot(ArrayList<BasePerformer> players, Bot currentBot) {
        ArrayList<BasePerformer> possibleChallengers = new ArrayList<BasePerformer>();
        for (BasePerformer player : players) {
            if (!(player instanceof Player) && !player.equals(currentBot)) {
                possibleChallengers.add(player);
            }
        }
        Random random = new Random();
        if (possibleChallengers.size() == 1) {
            return (Bot) possibleChallengers.getFirst();
        }
        return (Bot) possibleChallengers.get(random.nextInt(possibleChallengers.size()));
    }


    // a method that that handles the action in the bot's turn, returns whether the bot cheated
    public Action.Types act(Game myGame) {
        Random number = new Random();
        this.setCheat(number.nextDouble() >= CHEAT_PROBABILITY);
        ArrayList<Action.Types> available = this.getAvailableActions();
        int randomIndex;
        randomIndex = (int) (Math.random() * (available.size()));
        Action.Types chosenAction = available.get(randomIndex);
        System.out.println(this.getName() + " decided to perform " + chosenAction + "!");
        return chosenAction;
    }

    // challenge for the bot
    public boolean challenges(BasePerformer playerToChallenge, ArrayList<Character> myDeck, Action.Types action, boolean isActionChallenge) {
        Random challenge = new Random();
        if (isActionChallenge) {
            if (challenge.nextDouble() >= CHALLENGE_PROBABILITY) {
                System.out.println("Player " + this.getName() + " decided to challenge the player " + playerToChallenge.getName() + "'s action!");
                return this.challenge(playerToChallenge, myDeck, action, true);
            }
        } else {
            if (challenge.nextDouble() >= CHALLENGE_PROBABILITY) {
                System.out.println("Player " + this.getName() + " decided to challenge the player " + playerToChallenge.getName() + "'s block!");
                return this.challenge(playerToChallenge, myDeck, action, false);
            }
        }
        return true;
    }

    // block for the bot
    public boolean block(BasePerformer blocked, ArrayList<Character> myDeck, Action.Types action) {
        Random number = new Random();
        if (number.nextDouble() >= BLOCK_POSSIBILITY) {
            System.out.println("Player " + this.getName() + " decided to block " + blocked.getName() + "'s action!");

            boolean answer;
            if (blocked instanceof Player) {
                Scanner userInput = new Scanner(System.in);
                System.out.println("Do you want to challenge them? Answer yes or no!");
                answer = userInput.next().equalsIgnoreCase("yes");
            } else {
                answer = number.nextDouble() >= CHALLENGE_PROBABILITY;
            }
            if (answer) {
                System.out.println(blocked.getName() + " decided to challenge player " + this.getName() + "'s block!");
                return blocked.challenge(this, myDeck, action, false);
            } else {
                return true;
            }
        }
        return false;
    }

}