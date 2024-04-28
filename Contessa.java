/**
 * A child class of Character.
 * action: no action.
 * counterAction: to block assasination.
 */
public class Contessa extends Character{
    /**
     * Initializes a Contessa character with its name and description.
     */
    public Contessa(){
        super("Contessa", "Blocks assassination attempts.");
    }

    // The Contessa character has no action
    public Action.Types canAct () {
        return null;
    }

    @Override
    public Action.Types canBlock() {
        return Action.Types.ASSASSINATE;
    }
}