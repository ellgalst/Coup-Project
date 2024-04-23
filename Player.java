import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    /**
     * Initializes a player with the specified initial number of coins in their wallet.
     * @param initialCoins The initial number of coins in the player's wallet.
     */
    public Player(int initialCoins) {
        this.wallet = initialCoins;
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
            System.out.println(actionType);
            correctActions.add(actionType);
        }

        if (wallet >= 7) {
            return new ArrayList<>(List.of(Action.Types.COUP));
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
        }
        return availableActions;
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
