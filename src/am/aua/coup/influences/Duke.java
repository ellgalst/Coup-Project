package src.am.aua.coup.influences;

import src.am.aua.coup.core.Action;

/**
 * A child class of src.am.aua.coup.influences.Character.
 * action: to tax, which means to collect 3 coins from Bank.
 * counterAction: to block foreign aid, which means collecting 2 coins from Bank.
 */
public class Duke extends Character {
    /**
     * Initializes a src.am.aua.coup.influences.Duke character with its name and description.
     */
    public Duke(){
        super("Duke", "Collects 2 coins. Can block Foreign Aid.");
    }
    /**
     * The type of action this character can perform.
     */
    public Action.Types canAct () {
        return Action.Types.TAX;
    }

    @Override
    public Action.Types canBlock() {
        return null;
    }

    @Override
    public String toString() {
        return "Duke";
    }
}