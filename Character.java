/**
 * This class is the default character. It is the parent of the main 5 characters. Every Character
 * has 3 main methods(+ some other helping methods): influence, action and counterAction.
 * influence: returns the character type.
 * action: a method to take an action with a corresponding effect.
 * counterAction: a method to block the action.
 */
public abstract class Character{
    public abstract void action();
    public abstract void counterAction();
    public String getCharacter(){
        return this.getClass().getSimpleName();
    }
}
