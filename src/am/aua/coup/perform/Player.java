package src.am.aua.coup.perform;

import src.am.aua.coup.core.Action;
import src.am.aua.coup.core.Deck;
import src.am.aua.coup.core.Game;
import src.am.aua.coup.influences.Character;

import java.util.*;

/**
 * A separate class to keep track of all players.
 */

// MODIFY
public class Player extends BasePerformer {
    ArrayList<Character> influences = new ArrayList<Character>(2);
    public boolean cheat;
    public String getName() {
        return super.getName();
    }
    public void setName(String update) {
        super.setName(update);
    }

    public Player () {
        super.setName("Pedro");
        super.changeWallet(2);
        this.isHuman = false;
    }
    /**
     * Constructor method.
     * Initializes a player with the specified initial number of coins in their wallet. If the player is a bot
     * initializes its name.
     * @param initialCoins The initial number of coins in the player's wallet.
     */
    // isHuman remove
    public Player(String playerName, int initialCoins, boolean isHuman) {
        super.setName(playerName);
        super.changeWallet(initialCoins);
        this.isHuman = isHuman;
    }

    // override equals method. checks the equality based on the name of the player.
    public boolean equals(Player other) {
        if (other == null || !other.getClass().getSimpleName().equals("src.am.aua.coup.perform.Player")) {
            return false;
        }
        return this.getName().equals(other.getName());
    }

    /**
     * Gets the actions available to the player based on their wish to cheat or be honest.
     *
     * @return The list of available actions for the player.
     */

    // assassinate
    public ArrayList<Action.Types> getAvailableActions() {
        ArrayList<Action.Types> availableActions = new ArrayList<>(Arrays.asList(
                Action.Types.FOREIGNAID,
                Action.Types.INCOME
        ));
        ArrayList<Action.Types> correctActions = new ArrayList<>();
        for (Character influence : influences) {
            Action.Types actionType = influence.canAct();
            if (actionType == Action.Types.ASSASSINATE && super.getWallet() >= 3) {
                correctActions.add(actionType);
            }
            correctActions.add(actionType);
        }

        if (super.getWallet() >= 7) {
            return new ArrayList<Action.Types>(List.of(Action.Types.COUP));
        } else if (!cheat) {
            availableActions.addAll(correctActions);
        } else {
            for (Action.Types element : Action.Types.values()) {
                if (!(availableActions.contains(element) || correctActions.contains(element) || element == Action.Types.COUP)) {
                    availableActions.add(element);
                }
            }
            if (!correctActions.contains(Action.Types.ASSASSINATE) && super.getWallet() >= 3) {
                availableActions.add(Action.Types.ASSASSINATE);
            }
        }
        System.out.println(availableActions);
        return availableActions;
    }

    /**
     * A method to return the action user wants to do from the given list of available actions.
     * @param available arrayList of available actions.
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
                }
                else {
                    System.out.println("Please, enter a number within the range of the provided items.");
                }
            }
            else {
                System.out.println("Please enter a NUMBER!");
                userInput.next();
            }
        }
    }

    public String toString() {
        return this.getName();
    }


    public boolean challenge(BasePerformer playerToChallenge, ArrayList<Character> myDeck, Action.Types action, boolean isActionChallenge) {
        if (playerToChallenge.cheat) {
            playerToChallenge.influences.remove(Deck.randomizer(playerToChallenge.influences, 1).getFirst());
            System.out.println("Congratulations, " + super.getName() + "! You won the challenge!");
            return false;
        }
        else {
            this.influences.remove(Objects.requireNonNull(Deck.randomizer(this.influences, 1)).getFirst());
            if (isActionChallenge) {
                this.influences.remove(Objects.requireNonNull(Deck.randomizer(this.influences, 1)).getFirst());
                if (playerToChallenge.influences.getFirst().canAct() == action) {
                    playerToChallenge.influences.removeFirst();
                } else {
                    playerToChallenge.influences.removeLast();
                }
                playerToChallenge.influences.add(Deck.randomizer(myDeck, 1).getFirst());
            }
            System.out.println("Congratulations, " + playerToChallenge.getName() + "! You won the challenge!");
            return true;
        }

    }



}