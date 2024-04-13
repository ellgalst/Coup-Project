/**
 * A child class of Character.
 * action: to steal, which means to steal 2 coins from another player.
 * counterAction: to block stealing.
 */
public class Captain extends Character{
    public Captain(){
        super("Captain", "Steals 2 coins from another player. Can block stealing.");
    }

    @Override
    public void action(Player player, Game game){
        game.stealCoins(player);
    }

    @Override
    public boolean block(ActionType action){
        return action = ActionType.STEAL;
    }

    @Override
    public boolean isBlockable(ActionType){
        return true;
    }
}