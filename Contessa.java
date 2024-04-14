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
    Action.Types canAct = null;

//    @Override
//    public void action(Player player){
//
//    }
//
//    @Override
//    public boolean block(Action.Types action){
//        return action == Action.Types.ASSASSINATE;
//    }
//
//    @Override
//    public boolean isBlockable(Action.Types action){
//        return false;
//    }
}