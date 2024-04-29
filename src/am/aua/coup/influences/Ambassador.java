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


    // Determines the action type this character can perform
    public Action.Types canAct () {
        System.out.println(Action.Types.EXCHANGE);
        return Action.Types.EXCHANGE;
    }

    @Override
    public Action.Types canBlock(){
        return Action.Types.STEAL;
    }

    @Override
    public String toString() {
        return "Ambassador";
    }


}
