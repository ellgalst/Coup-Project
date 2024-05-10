package src.am.aua.coup.influences;
import src.am.aua.coup.core.Action;

/**
 * A child class of src.am.aua.coup.influences.Character.
 */
public class Ambassador extends Character {
    /**
     * Initializes a src.am.aua.coup.influences.Ambassador character with its name and description.
     */
    public Ambassador() {
        super("Ambassador", "Exchanges cards with the deck. Can block stealing.");
    }

    /**
     * Determines the action type this character can perform.
     *
     * @return The action type this character can perform.
     */
    @Override
    public Action.Types canAct () {
        return Action.Types.EXCHANGE;
    }


    /**
     * Determines the action type this character can block.
     *
     * @return The action type this character can block.
     */
    @Override
    public Action.Types canBlock(){
        return Action.Types.STEAL;
    }

    /**
     * Provides a string representation of the Ambassador character.
     *
     * @return The string representation of the Ambassador character.
     */
    @Override
    public String toString() {
        return "Ambassador";
    }


}
