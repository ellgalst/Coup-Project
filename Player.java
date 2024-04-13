import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A separate class to keep track of all players.
 */
public class Player {
    public Player(int initialCoins) {
        this.wallet = initialCoins;
    }

    ArrayList<Character> influences = new ArrayList<Character>(2);
    public int wallet;
    private int theNumberOfInfluences;

    public void changeWallet(int update) {
        this.wallet += update;
    }
    public int getTheNumberOfInfluences() {
        return influences.size();
    }

    // gets the actions available to the player based on their wish to cheat (isCorrectAction = false) or be honest (isCorrectAction = true)
    public ArrayList<Action.Types> getAvailableActions(boolean isCorrectAction) {
        ArrayList<Action.Types> availableActions = new ArrayList<>(Arrays.asList(
                Action.Types.FOREIGNAID,
                Action.Types.INCOME
        ));
        ArrayList<Action.Types> correctActions = new ArrayList<>();
        System.out.println(influences);
        for (Character influence : influences) {
            System.out.println(influence.canAct);
            correctActions.add(influence.canAct);
        }
        if (wallet >= 7) {
            return new ArrayList<>(List.of(Action.Types.COUP));
        } else if (isCorrectAction) {
            availableActions.addAll(correctActions);
            System.out.println("else if (isCorrectAction)");
            System.out.println(availableActions);

        } else {
            for (Action.Types element : Action.Types.values()) {
                if (!(availableActions.contains(element) || correctActions.contains(element) || element != Action.Types.COUP)) {
                    availableActions.add(element);
                }
            }
        }

        return availableActions;
    }


    // challenges another player
    public boolean challenge(Player other) {
        return false;
    }


}
