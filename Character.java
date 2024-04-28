/**
 * This class is the default character. It is the parent of the main 5 characters. Every Character
 * has 3 main methods(+ some other helping methods): influence, action and counterAction.
 * influence: returns the character type.
 * action: a method to take an action with a corresponding effect.
 */
public abstract class Character{

    /**
     * The name of the character.
     */
    protected String name;
    /**
     * The description of the character.
     */
    protected String description;
    /**
     * The type of action this character can perform.
     */
    public Action.Types canAct;

    /**
     * Initializes a Character with its name and description.
     *
     * @param characterName The name of the character.
     * @param s          The description of the character.
     */
    public Character(String characterName, String s) {
        name = characterName;
        description = s;
    }

    public abstract Action.Types canAct();
    public abstract Action.Types canBlock();
}
