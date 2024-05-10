package src.am.aua.coup.influences;

import src.am.aua.coup.core.Action;

/**
 * A child class of src.am.aua.coup.influences.Character.
 * action: to steal, which means to steal 2 coins from another player.
 * counterAction: to block stealing.
 */
public class Captain extends Character {
    /**
     * Initializes a src.am.aua.coup.influences.Captain character with its name and description.
     */
    public Captain() {
        super("Captain", "Steals 2 coins from another player. Can block stealing.");
    }

    /**
     * Determines the action type this character can perform.
     *
     * @return The action type this character can perform.
     */
    @Override
    public Action.Types canAct () {
        return Action.Types.STEAL;
    }

    /**
     * Determines the action type this character can block.
     *
     * @return The action type this character can block.
     */
    @Override
    public Action.Types canBlock() {
        return Action.Types.STEAL;
    }

    /**
     * Provides a string representation of the Captain character.
     *
     * @return The string representation of the Captain character.
     */
    @Override
    public String toString() {
        return "Captain";
    }
}