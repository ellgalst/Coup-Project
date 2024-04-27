import java.util.Objects;

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

    /**
     * Method that implements taxation action of card Duke
     */
    public static void performTax(Player player) {
        player.wallet+=3;
    }

    /**
     * Method that draws 2 influences and
     * puts 2 back of card Ambassador
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
     * Implements the action of influence Captain
     * that can steal coins from other players
     */
    public static void performSteal(Player player1, Player player2){
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
    public static void performAssassinate(Player player1, Player player2){
        player2.influences.remove(Deck.randomizer(player2.influences, 1).getFirst());
        player1.wallet -= 3;
    }

    /**
     * Method Income that collects one coin from the bank
     */
    public static void performIncome(Player player){
        player.wallet+=1;
    }

    /**
     * Method ForeignAid that collects two coins from the bank
     */
    public static void performForeignAid(Player player){
        player.wallet+=2;
    }

    /**
     * Method that cause a player to give up an influence
     */
    public static void performCoup(Player player1, Player player2){
        player1.wallet -= 7;
        player2.influences.remove(Deck.randomizer(player2.influences, 1).getFirst());
    }

}

