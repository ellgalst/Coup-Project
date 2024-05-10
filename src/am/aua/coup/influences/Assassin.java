package src.am.aua.coup.influences;

import src.am.aua.coup.core.Action;

/**
 * A child class of src.am.aua.coup.influences.Character.
 * action: to assassinate, which means to pay and make any player lose one character.
 * counterAction: it has no counteraction
 */
public class Assassin extends Character {

    /**
     * Initializes an src.am.aua.coup.influences.Assassin character with its name and description.
     */
    public Assassin() {
        super("Assassin", "Pays 3 coins and makes another player lose 1 influence,");
    }

    /**
     * Determines the action type this character can perform.
     *
     * @return The action type this character can perform.
     */
    public Action.Types canAct () {
        return Action.Types.ASSASSINATE;
    }

    /**
     * Determines the action type this character can block.
     *
     * @return The action type this character can block.
     */
    @Override
    public Action.Types canBlock() {
        return null;
    }

    /**
     * Provides a string representation of the Assassin character.
     *
     * @return The string representation of the Assassin character.
     */
    @Override
    public String toString() {
        return "Assassin";
    }
}