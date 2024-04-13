/**
 * A child class of Character.
 * action: no action.
 * counterAction: to block assasination.
 */
public class Contessa extends Character{
    public Contessa(){
        super("Contessa", "Blocks assassination attempts.");
    }

    @Override
    public void action(Player player, Game game){

    }

    @Override
    public boolean block(ActionType action){
        return action == ActionType.ASSASSINATE;
    }

    @Override
    public boolean isBlockable(ActionType action){
        return false;
    }
}