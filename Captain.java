/**
 * A child class of Character.
 * action: to steal, which means to steal 2 coins from another player.
 * counterAction: to block stealing.
 */
public class Captain extends Character {
    public Captain() {
        super("Captain", "Steals 2 coins from another player. Can block stealing.");
    }

    Action.Types canAct = Action.Types.STEAL;

//    @Override
//    public void action(Player player) {
//        Action.stealCoins(player);
//    }
//
//    @Override
//    public boolean block(Action.Types action) {
//        return action == Action.Types.STEAL;
//    }
//
//    @Override
//    public boolean isBlockable(Action.Types action) {
//        return true;
//    }
}