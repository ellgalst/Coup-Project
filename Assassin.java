/**
 * A child class of Character.
 * action: to assassinate, which means to pay and make any player lose one character.
 * counterAction: it has no counteraction
 */
public class Assassin extends Character {

    /**
     * Initializes an Assassin character with its name and description.
     */
    public Assassin() {
        super("Assassin", "Pays 3 coins and makes another player lose 1 influence,");
    }

    // Determines the action type this character can perform
    Action.Types canAct = Action.Types.ASSASSINATE;

//    public void action(Player player) {
//        if (player.getCoins() >= 3) {
//            player.changeCoins(-3);
//            Action.assasinate(player);
//        }
//    }

//    public boolean block(Action.Types action) {
//        return action == Action.Types.ASSASSINATE;
//    }
//
//    @Override
//    public boolean isBlockable(Action.Types action) {
//        return true;
//    }
}