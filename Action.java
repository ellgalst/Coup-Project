import java.util.ArrayList;
import java.util.Scanner;

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


    /**
     * Method that implements taxation action of card Duke
     */
    public void PerformTax(Player player) {
        player.wallet+=3;
    }

    /**
     * Method that draws 2 influences and
     * puts 2 back of card Ambassador
     */
    public void PerformExchange(Player player) {
        System.out.println("What 2 Influences would you like to keep from these: ");
        player.influences.addAll(Deck.randomizer(Deck.deck, 2));
        int[] chosenIndices = null;
        if(player.isHuman){
            //influences should be seen here and the user chooses two influences HERE
            Deck.deck.add(player.influences.remove(chosenIndices[0]));
            Deck.deck.add(player.influences.remove(chosenIndices[1]));
        }
        else {
            player.influences.remove(Deck.randomizer(player.influences, 1).getFirst());
            player.influences.remove(Deck.randomizer(player.influences, 1).getFirst());
        }
    }

    /**
     * Implements the action of influence Captain
     * that can steal coins from other players
     */
    public void PerformSteal(Player player1, Player player2){
        if(player2.wallet<2){
            player1.wallet+=player2.wallet;
            player2.wallet = 0;
        }
        else {
            player2.wallet -= 2;
            player1.wallet += 2;
        }
    }

    /**
     * Method of card Assassin that forcing
     * one player to give up influence
     */
    public void PerformAssassinate(Player player1, Player player2){
        player2.influences.remove(Deck.randomizer(player2.influences, 1));
        player1.wallet -= 3;
    }

    /**
     * Method Income that collects one coin from the bank
     */
    public void PerformIncome(Player player){
        player.wallet+=1;
    }

    /**
     * Method ForeignAid that collects two coins from the bank
     */
    public  void PerformForeignAid(Player player){
        player.wallet+=2;
    }

    /**
     * Method that cause a player to give up an influence
     */
    public void PerformCoup(Player player1, Player player2){
        player1.wallet -= 7;
        player2.influences.remove(Deck.randomizer(player2.influences, 1));
    }


}

