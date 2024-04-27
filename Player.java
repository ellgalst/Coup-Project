import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * A separate class to keep track of all players.
 */
public class Player {
    ArrayList<Character> influences = new ArrayList<Character>(2);
    /**
     * The initial number of coins in the player's wallet.
     */
    public int wallet;
    /**
     * The number of character influences (cards) the player possesses.
     */
    private int theNumberOfInfluences;
    private String playerName;
    public boolean isHuman;
    public String getName() {
        return playerName;
    }
    public void setName(String update) {
        playerName = update;


    }
    /**
     * Constructor method.
     * Initializes a player with the specified initial number of coins in their wallet. If the player is a bot
     * initializes its name.
     * @param initialCoins The initial number of coins in the player's wallet.
     */
    public Player(int initialCoins, boolean isHuman) {
        this.wallet = initialCoins;
        this.isHuman = isHuman;
        if(!isHuman){
            playerName = "BOT";
        }
    }

    /**
     * Changes the player's wallet balance by the specified amount.
     *
     * @param update The amount by which to update the player's wallet balance.
     */
    public void changeWallet(int update) {
        this.wallet += update;
    }
    /**
     * Retrieves the number of character influences (cards) the player possesses.
     *
     * @return The number of character influences (cards) the player possesses.
     */
    public int getTheNumberOfInfluences() {
        return influences.size();
    }

    /**
     * Gets the actions available to the player based on their wish to cheat or be honest.
     *
     * @param isCorrectAction True if the player wishes to be honest, false if they wish to cheat.
     * @return The list of available actions for the player.
     */
    public ArrayList<Action.Types> getAvailableActions(boolean isCorrectAction) {
        ArrayList<Action.Types> availableActions = new ArrayList<>(Arrays.asList(
                Action.Types.FOREIGNAID,
                Action.Types.INCOME
        ));
        ArrayList<Action.Types> correctActions = new ArrayList<>();
        System.out.println(influences);
        for (Character influence : influences) {
            System.out.println(influence);
            Action.Types actionType = influence.canAct();
            if (actionType == Action.Types.ASSASSINATE && wallet >= 3) {
                System.out.println(actionType);
                correctActions.add(actionType);
            }
            System.out.println(actionType);
            correctActions.add(actionType);
        }

        if (wallet >= 7) {
            return new ArrayList<Action.Types>(List.of(Action.Types.COUP));
        } else if (isCorrectAction) {
            availableActions.addAll(correctActions);
            System.out.println("else if (isCorrectAction)");
            System.out.println(availableActions);
        } else {
            for (Action.Types element : Action.Types.values()) {
                if (!(availableActions.contains(element) || correctActions.contains(element) || element == Action.Types.COUP)) {
                    availableActions.add(element);
                }
            }
            if (!correctActions.contains(Action.Types.ASSASSINATE) && wallet >= 3) {
                availableActions.add(Action.Types.ASSASSINATE);
            }
        }
        return availableActions;
    }

    /**
     * A method to return the action user wants to do from the given list of available actions.
     * @param available arrayList of available actions.
     * @return action the user wants to do.
     */

    //either create another method for handling the choice, if you can, make a generic method
/*    public Action.Types getUserAction(ArrayList<Action.Types> available) {
        Scanner userInput = new Scanner(System.in);
        int actionIndex;

        System.out.println("Available actions: ");
        for (int i = 0; i < available.size(); i++) {
            System.out.println("The action behind index " + i + " is: " + available.get(i));
        }

        while (true) {
            System.out.print("Enter an index for the preferred action: ");
            if (userInput.hasNextInt()) {
                actionIndex = userInput.nextInt();
                if (actionIndex >= 0 && actionIndex < available.size()) {
                    return available.get(actionIndex);
                }
                else {
                    System.out.println("Please, enter a number within the range of the provided actions.");
                }
            }
            else {
                System.out.println("Please enter a number!");
                userInput.next();
            }
        }

    }*/

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


    /**
     * Challenges another player's action.
     *
     * @param other The player whose action is being challenged.
     * @return True if the challenge is successful, false otherwise.
     */
    public boolean challenge(Player other) {
        return false;
    }


}
