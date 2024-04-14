/**
 * A child class of Character.
 * action: to steal, which means to steal 2 coins from another player.
 * counterAction: to block stealing.
 */
public class Captain extends Character {
    /**
     * Initializes a Captain character with its name and description.
     */
    public Captain() {
        super("Captain", "Steals 2 coins from another player. Can block stealing.");
    }

    // Determines the action type this character can perform
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