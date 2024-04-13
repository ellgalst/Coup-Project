/**
 * This class is the default character. It is the parent of the main 5 characters. Every Character
 * has 3 main methods(+ some other helping methods): influence, action and counterAction.
 * influence: returns the character type.
 * action: a method to take an action with a corresponding effect.
 */
public abstract class Character{
    protected String name;
    protected String description;

    public Character(String characterName, String s) {
        name = characterName;
        description = s;
    }

    public abstract void action(Player player, Game game);
    public boolean block(ActionType action){
        return false;
    }

    public abstract boolean isBlockable(ActionType action);
    public String getName(){
        return name;
    }
    public String getDescription(){
        return description;
    }

}
