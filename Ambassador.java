/**
 * A child class of Character.
 */
public class Ambassador extends Character {
    /**
     * Initializes an Ambassador character with its name and description.
     */
    public Ambassador() {
        super("Ambassador", "Exchanges cards with the deck. Can block stealing.");
    }


    // Determines the action type this character can perform
    public Action.Types canAct () {
        System.out.println(Action.Types.EXCHANGE);
        return Action.Types.EXCHANGE;
    }

    @Override
    public Action.Types canBlock(){
        return Action.Types.STEAL;
    }


}
