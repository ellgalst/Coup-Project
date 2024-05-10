package src.am.aua.coup.influences;

import src.am.aua.coup.core.Action;

/**
 * This class is the default character. It is the parent of the main 5 characters. Every src.am.aua.coup.influences.Character
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
     * Initializes a src.am.aua.coup.influences.Character with its name and description.
     *
     * @param characterName The name of the character.
     * @param s          The description of the character.
     */
    public Character(String characterName, String s) {
        name = characterName;
        description = s;
    }

    /**
     * Determines the action type this character can perform.
     *
     * @return The action type this character can perform.
     */
    public abstract Action.Types canAct();

    /**
     * Determines the action type this character can block.
     *
     * @return The action type this character can block.
     */
    public abstract Action.Types canBlock();

    /**
     * Provides a string representation of the given character.
     *
     * @return The string representation of the given character.
     */
    public abstract String toString();
}
