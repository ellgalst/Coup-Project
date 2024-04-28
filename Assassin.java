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
    public Action.Types canAct () {
        System.out.println(Action.Types.ASSASSINATE);
        return Action.Types.ASSASSINATE;
    }

    @Override
    public Action.Types canBlock() {
        return null;
    }
}