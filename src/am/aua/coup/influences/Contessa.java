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

    /**
     * Determines the action type this character can perform.
     *
     * @return The action type this character can perform.
     */
    public Action.Types canAct () {
        return null;
    }

    /**
     * Determines the action type this character can block.
     *
     * @return The action type this character can block.
     */
    @Override
    public Action.Types canBlock() {
        return Action.Types.ASSASSINATE;
    }

    /**
     * Provides a string representation of the Contessa character.
     *
     * @return The string representation of the Contessa character.
     */
    @Override
    public String toString() {
        return "Contessa";
    }
}