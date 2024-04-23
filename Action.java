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
    public void PerformTax() {
    }

    /**
     * Method that drawing 2 influence and
     * putting 2 back of card Ambassador
     */
    public void PerformExchange() {}

    /**
     * Implements the action of influence Captain
     * that can steal coins from other players
     */
    public void PerformSteal(){

    }

    /**
     * Method of card Assassin that forcing
     * one player to give up influence
     */
    public void PerformAssassinate(){}

    /**
     * Method Income that collects one coin from the bank
     */
    public void PerformIncome(){}

    /**
     * Method ForeignAid that collects two coins from the bank
     */
    public  void PerformForeignAid(){}

    /**
     * Method that cause a player to give up an influence
     */
    public void PerformCoup(){}


}

