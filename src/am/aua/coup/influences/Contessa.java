package src.am.aua.coup.influences;

import src.am.aua.coup.core.Action;

/**
 * A child class of src.am.aua.coup.influences.Character.
 * action: no action.
 * counterAction: to block assasination.
 */
public class Contessa extends Character {
    /**
     * Initializes a src.am.aua.coup.influences.Contessa character with its name and description.
     */
    public Contessa(){
        super("Contessa", "Blocks assassination attempts.");
    }

    // The src.am.aua.coup.influences.Contessa character has no action
    public Action.Types canAct () {
        return null;
    }

    @Override
    public Action.Types canBlock() {
        return Action.Types.ASSASSINATE;
    }

    @Override
    public String toString() {
        return "Contessa";
    }
}