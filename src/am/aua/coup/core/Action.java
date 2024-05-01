package src.am.aua.coup.core;

import src.am.aua.coup.influences.Character;
import src.am.aua.coup.perform.BasePerformer;
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

    public static void performAction(BasePerformer player, Action.Types action, BasePerformer target) {
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
        System.out.println("What 2 Influences would you like to keep from these: ");
        player.getInfluences().addAll(Deck.randomizer(Deck.deck, 2));
        if(player instanceof Player current){
            Character choice = current.getUserChoice(player.getInfluences());
            player.getInfluences().remove(choice);
            Deck.deck.add(choice);
            choice = current.getUserChoice(player.getInfluences());
            player.getInfluences().remove(choice);
            Deck.deck.add(choice);
        }
        else {
            player.getInfluences().remove(Deck.randomizer(player.getInfluences(), 1).getFirst());
            player.getInfluences().remove(Deck.randomizer(player.getInfluences(), 1).getFirst());
        }
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
        player2.getInfluences().remove(Deck.randomizer(player2.getInfluences(), 1).getFirst());
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
        player2.getInfluences().remove(Deck.randomizer(player2.getInfluences(), 1).getFirst());
    }
}
