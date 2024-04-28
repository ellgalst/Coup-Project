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
    public Action.Types canAct () {
        System.out.println(Action.Types.TAX);
        return Action.Types.TAX;
    }

    @Override
    public Action.Types canBlock() {
        return Action.Types.FOREIGNAID;
    }
}