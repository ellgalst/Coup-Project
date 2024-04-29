package src.am.aua.coup.core;

import src.am.aua.coup.influences.Character;
import src.am.aua.coup.perform.Player;

/**
 * Represents the various actions that can be taken in the coup game
 */
public class Action {

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
        FOREIGNAID
    }

    public static void performAction(Player player, Action.Types action, Player target) {
        switch (action) {
            case STEAL:
                performSteal(player, target);
                break;
            case EXCHANGE:
                performExchange(player);
                break;
            case ASSASSINATE:
                performAssassinate(player, target);
                break;
            case TAX:
                performTax(player);
                break;
            case COUP:
                performCoup(player, target);
                break;
            case INCOME:
                performIncome(player);
                break;
            case FOREIGNAID:
                performForeignAid(player);
                break;
        }
    }

    public boolean isBlockable(Types actionType){
        return actionType == Types.FOREIGNAID || actionType == Types.ASSASSINATE || actionType == Types.STEAL;

    }

    /**
     * Method that implements taxation action of card src.am.aua.coup.influences.Duke
     */
    public static void performTax(Player player) {
        player.changeWallet(3);
    }

    /**
     * Method that draws 2 influences and
     * puts 2 back of card src.am.aua.coup.influences.Ambassador
     */
    public static void performExchange(Player player) {
        System.out.println("What 2 Influences would you like to keep from these: ");
        player.influences.addAll(Deck.randomizer(Deck.deck, 2));
        if(player.isHuman){
            Character choice = player.getUserChoice(player.influences);
            player.influences.remove(choice);
            Deck.deck.add(choice);
            choice = player.getUserChoice(player.influences);
            player.influences.remove(choice);
            Deck.deck.add(choice);
        }
        else {
            player.influences.remove(Deck.randomizer(player.influences, 1).getFirst());
            player.influences.remove(Deck.randomizer(player.influences, 1).getFirst());
        }
    }

    /**
     * Implements the action of influence src.am.aua.coup.influences.Captain
     * that can steal coins from other players
     */
    public static void performSteal(Player player1, Player player2){
        if(player2.getWallet()<2){
            player1.changeWallet(player2.getWallet());
            player2.changeWallet(-player2.getWallet());
        }
        else {
            player2.changeWallet(-2);
            player1.changeWallet(2);
        }
    }

    /**
     * Method of card src.am.aua.coup.influences.Assassin that forcing
     * one player to give up influence
     */
    public static void performAssassinate(Player player1, Player player2){
        player2.influences.remove(Deck.randomizer(player2.influences, 1).getFirst());
        player1.changeWallet(-3);
    }

    /**
     * Method Income that collects one coin from the bank
     */
    public static void performIncome(Player player){
        player.changeWallet(1);
    }

    /**
     * Method ForeignAid that collects two coins from the bank
     */
    public static void performForeignAid(Player player){
        player.changeWallet(2);
    }

    /**
     * Method that cause a player to give up an influence
     */
    public static void performCoup(Player player1, Player player2){
        player1.changeWallet(-7);
        player2.influences.remove(Deck.randomizer(player2.influences, 1).getFirst());
    }

}

