/**
 * A child class of Character.
 */
public class Ambassador extends Character{
    public Ambassador(){
        super("Ambassador", "Exchanges cards with the deck. Can block stealing.");
    }
    @Override
    public void action(Player player, Game game) {
        game.exchange(player);
    }

    @Override
    public boolean block(ActionType action){
        return action==ActionType.STEAL;
    }

    @Override
    public boolean isBlockable(ActionType action){
        return false;
    }
}
