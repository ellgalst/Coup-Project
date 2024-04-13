import java.security.PublicKey;

/**
 * A child class of Character.
 * action: to tax, which means to collect 3 coins from Bank.
 * counterAction: to block foreign aid, which means collecting 2 coins from Bank.
 */
public class Duke extends Character{
    public Duke(){
        super("Duke", "Collects 2 coins. Can block Foreign Aid.");
    }

    @Override
    public void action(Player player, Game game){
        game.tax(player);
    }

    @Override
    public boolean block(ActionType action){
        return action==ActionType.FOREIGNAID;
    }

    @Override
    public boolean isBlockable(ActionType action){
        return true;
    }
}