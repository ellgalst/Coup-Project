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
     * @param desc          The description of the character.
     */
    public Character(String characterName, String s) {
        name = characterName;
        description = s;
    }

//
//    public abstract void action(Player player, Game game);
//    public boolean block(Action.Types action){
//        return false;
//    }
//
//    public abstract boolean isBlockable(Action.Types action);
//    public String getName(){
//        return name;
//    }
//    public String getDescription(){
//        return description;
//    }

}
