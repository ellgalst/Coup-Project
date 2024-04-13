/**
 * A child class of Character.
 */
public class Ambassador extends Character {
    public Ambassador() {
        super("Ambassador", "Exchanges cards with the deck. Can block stealing.");
    }

    Action.Types canAct = Action.Types.EXCHANGE;

//    @Override
//    public void action(Player player, Action action) {
//        action.exchange(player);
//    }
//
//    @Override
//    public boolean block(Action.Types action) {
//        return action == Action.Types.STEAL;
//    }
//
//    @Override
//    public boolean isBlockable(Action.Types action) {
//        return false;
//    }
}
