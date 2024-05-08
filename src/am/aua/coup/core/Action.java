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
public class Action{

    /**
     * Enums for actions
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
     * Method that implements taxation action of card src.am.aua.coup.influences.Duke
     */
    public static void performTax(BasePerformer player) {
        player.setWallet(player.getWallet() + 3);
    }

    /**
     * Method that draws 2 influences and
     * puts 2 back of card src.am.aua.coup.influences.Ambassador
     */
    public static void performExchange(BasePerformer player) {

        player.getInfluences().addAll(Deck.randomizer(Deck.deck, 2));
        if(player instanceof Player){
            System.out.println("What 2 Influences would you like to keep from these: ");
            Character choice = Player.getUserChoice(player.getInfluences());
            player.getInfluences().remove(choice);

            Deck.deck.add(choice);
            choice = Player.getUserChoice(player.getInfluences());
            player.getInfluences().remove(choice);
            Deck.deck.add(choice);
        }
        else {
            Character characterToRemove = player.getInfluences().size()==2 ? Deck.randomizer(player.getInfluences(), 1).getFirst() : player.getInfluences().getFirst();
            player.getInfluences().remove(characterToRemove);
            Deck.addToDeck(characterToRemove);
        }
        System.out.println(player.getName() + "'s influences now are: " + player.getInfluences());
    }

    /**
     * Implements the action of influence src.am.aua.coup.influences.Captain
     * that can steal coins from other players
     */
    public static void performSteal(BasePerformer player1, BasePerformer player2){
        if(player2.getWallet()<2){
            player1.setWallet(player1.getWallet() + player2.getWallet());
            player2.setWallet(0);
        }
        else {
            player2.setWallet(player2.getWallet() - 2);
            player1.setWallet(player1.getWallet() + 2);
        }
    }

    /**
     * Method of card src.am.aua.coup.influences.Assassin that forcing
     * one player to give up influence
     */
    public static void performAssassinate(BasePerformer player1, BasePerformer player2){
        Character characterToRemove = Deck.randomizer(player2.getInfluences(), 1).getFirst();
        player2.getInfluences().remove(characterToRemove);
        Deck.addToDeck(characterToRemove);
        player1.setWallet(player1.getWallet() - 3);
    }

    /**
     * Method Income that collects one coin from the bank
     */
    public static void performIncome(BasePerformer player){
        player.setWallet(player.getWallet() + 1);
    }

    /**
     * Method ForeignAid that collects two coins from the bank
     */
    public static void performForeignAid(BasePerformer player){
        player.setWallet(player.getWallet() + 2);
    }

    /**
     * Method that cause a player to give up an influence
     */
    public static void performCoup(BasePerformer player1, BasePerformer player2){
        player1.setWallet(player1.getWallet() - 7);
        Character characterToRemove = Deck.randomizer(player2.getInfluences(), 1).getFirst();
        player2.getInfluences().remove(characterToRemove);
        Deck.addToDeck(characterToRemove);
    }


    /**
     * Gets the actions available to the player based on their wish to cheat or be honest.
     *
     * @return The list of available actions for the player.
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
        }
        else {
            for (Action.Types action : Action.Types.values()) {
                if (action == Action.Types.ASSASSINATE) {
                    if (player.getWallet() >= 3 && !correctActions.contains(Action.Types.ASSASSINATE) && !availableActions.contains(action)) {
                        availableActions.add(Action.Types.ASSASSINATE);
                    }
                }
                else if (!correctActions.contains(action) && action != Action.Types.COUP && !availableActions.contains(action) && action != null) {
                    availableActions.add(action);
                }
            }
        }


        return availableActions;
    }
}
