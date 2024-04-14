import java.security.PublicKey;

/**
 * A child class of Character.
 * action: to tax, which means to collect 3 coins from Bank.
 * counterAction: to block foreign aid, which means collecting 2 coins from Bank.
 */
public class Duke extends Character{
    /**
     * Initializes a Duke character with its name and description.
     */
    public Duke(){
        super("Duke", "Collects 2 coins. Can block Foreign Aid.");
    }
    /**
     * The type of action this character can perform.
     */
    public Action.Types canAct = Action.Types.TAX;

//
//    @Override
//    public void action(Player player, Action action){
//        action.tax(player);
//    }
//
//    @Override
//    public boolean block(){
//        return false;
//    }
//
//    @Override
//    public boolean isBlockable(Action.Types action){
//        return true;
//    }
}