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
    public Action.Types canAct () {
        System.out.println(Action.Types.STEAL);
        return Action.Types.STEAL;
    }

    @Override
    public Action.Types canBlock() {
        return Action.Types.STEAL;
    }
}