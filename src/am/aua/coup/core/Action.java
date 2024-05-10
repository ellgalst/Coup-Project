package src.am.aua.coup.core;

import src.am.aua.coup.influences.Character;
import src.am.aua.coup.perform.BasePerformer;
import src.am.aua.coup.perform.Player;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents the various actions that can be taken in the coup game
 */
public class Action {

    /**
     * Enum for all the actions.
     */
    public enum Types {
        STEAL,
        EXCHANGE,
        ASSASSINATE,
        TAX,
        COUP,
        INCOME,
        FOREIGN_AID
    }

    /**
     * Performs the specified action.
     *
     * @param player The player performing the action.
     * @param action The type of action to perform.
     * @param target The target of the action if applicable.
     * @return true if the action is performed successfully, false otherwise.
     */
    public static boolean performAction(BasePerformer player, Action.Types action, BasePerformer target) {
        boolean works = false;
        switch (action) {
            case STEAL:
                performSteal(player, target);
                works = true;
                break;
            case EXCHANGE:
                performExchange(player);
                works = true;
                break;
            case ASSASSINATE:
                performAssassinate(player, target);
                works = true;
                break;
            case TAX:
                performTax(player);
                works = true;
                break;
            case COUP:
                performCoup(player, target);
                works = true;
                break;
            case INCOME:
                performIncome(player);
                works = true;
                break;
            case FOREIGN_AID:
                performForeignAid(player);
                works = true;
                break;
        }
        return works;
    }

    /**
     * Performs the "Steal" action.
     *
     * @param player The player performing the action.
     * @param target The target player from whom the coins are stolen.
     */
    public static void performSteal(BasePerformer player, BasePerformer target) {
        if (target.getWallet() < 2) {
            player.setWallet(player.getWallet() + target.getWallet());
            target.setWallet(0);
        } else {
            target.setWallet(target.getWallet() - 2);
            player.setWallet(player.getWallet() + 2);
        }
    }

    /**
     * Performs the "Exchange" action, allowing the player to exchange influence cards.
     *
     * @param player The player performing the action.
     */
    public static void performExchange(BasePerformer player) {

        player.getInfluences().addAll(Deck.randomizer(Deck.deck, 2));
        if (player instanceof Player) {
            System.out.println("What 2 Influences would you like to keep from these: ");
            Character choice = Player.getUserChoice(player.getInfluences());
            player.getInfluences().remove(choice);

            Deck.deck.add(choice);
            choice = Player.getUserChoice(player.getInfluences());
            player.getInfluences().remove(choice);
            Deck.deck.add(choice);
        } else {
            ArrayList<Character> removes = Deck.randomizer(player.getInfluences(), 2);
            player.getInfluences().removeAll(removes);
            Deck.addToDeck(removes);
        }
        System.out.println(player.getName() + "'s influences now are: " + player.getInfluences());
    }

    /**
     * Performs the "Assassinate" action, removing an influence from the target player.
     *
     * @param player1 The player performing the action.
     * @param player2 The target player.
     */
    public static void performAssassinate(BasePerformer player1, BasePerformer player2) {
        performElimination(player1, player2, 3);
    }

    /**
     * Performs the "Tax" action, adding coins to the player's wallet.
     *
     * @param player The player performing the action.
     */
    public static void performTax(BasePerformer player) {
        player.setWallet(player.getWallet() + 3);
    }

    /**
     * Performs the "Coup" action, removing an influence from the target player.
     *
     * @param player1 The player performing the action.
     * @param player2 The target player.
     */
    public static void performCoup(BasePerformer player1, BasePerformer player2) {
        performElimination(player1, player2, 7);
    }

    /**
     * Performs the "Income" action, adding coins to the player's wallet.
     *
     * @param player The player performing the action.
     */
    public static void performIncome(BasePerformer player) {
        player.setWallet(player.getWallet() + 1);
    }

    /**
     * Performs the "Foreign Aid" action, adding coins to the player's wallet.
     *
     * @param player The player performing the action.
     */
    public static void performForeignAid(BasePerformer player) {
        player.setWallet(player.getWallet() + 2);
    }

    /**
     * Performs elimination actions like Assassinate or Coup.
     *
     * @param player        The player performing the action.
     * @param target        The target player.
     * @param coinsToRemove The number of coins to remove from the player performing the action.
     */
    public static void performElimination(BasePerformer player, BasePerformer target, int coinsToRemove) {
        ArrayList<Character> charactersToRemove = Deck.randomizer(target.getInfluences(), 1);
        target.getInfluences().removeAll(charactersToRemove);
        Deck.addToDeck(charactersToRemove);
        player.setWallet(player.getWallet() - coinsToRemove);
    }

    /**
     * Gets the available actions for the player based on their current state.
     *
     * @param player The player for whom to determine available actions.
     * @return A list of available actions.
     */
    public static ArrayList<Types> getAvailableActions(BasePerformer player) {
        if (player.getWallet() >= 7) {
            player.setCheat(false);
            return new ArrayList<Action.Types>(List.of(Action.Types.COUP));
        }
        ArrayList<Action.Types> availableActions = new ArrayList<Action.Types>();

        ArrayList<Action.Types> correctActions = new ArrayList<Action.Types>(Arrays.asList(
                Action.Types.FOREIGN_AID,
                Action.Types.INCOME
        ));
        for (Character influence : player.getInfluences()) {
            Action.Types actionType = influence.canAct();
            if (actionType == Action.Types.ASSASSINATE) {
                if (player.getWallet() >= 3 && !correctActions.contains(actionType)) {
                    correctActions.add(actionType);
                }
            } else if (actionType != Action.Types.COUP && !correctActions.contains(actionType) && actionType != null) {
                correctActions.add(actionType);
            }
        }
        if (!player.getCheat()) {
            availableActions.addAll(correctActions);
        } else {
            for (Action.Types action : Action.Types.values()) {
                if (action == Action.Types.ASSASSINATE) {
                    if (player.getWallet() >= 3 && !correctActions.contains(Action.Types.ASSASSINATE) && !availableActions.contains(action)) {
                        availableActions.add(Action.Types.ASSASSINATE);
                    }
                } else if (!correctActions.contains(action) && action != Action.Types.COUP && !availableActions.contains(action) && action != null) {
                    availableActions.add(action);
                }
            }
        }

        return availableActions;
    }
}
