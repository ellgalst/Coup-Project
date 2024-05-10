package src.am.aua.coup.perform;

import src.am.aua.coup.core.Action;
import src.am.aua.coup.core.Deck;
import src.am.aua.coup.core.Game;
import src.am.aua.coup.influences.Character;

import java.util.*;

/**
 * An abstract class representing a performer in the coup game.
 */
public abstract class BasePerformer {
    private ArrayList<Character> influences = new ArrayList<Character>(2);
    private int wallet;
    private String name;
    private boolean cheat;

    // accessors
    public ArrayList<Character> getInfluences() {
        return influences;
    }

    public String getInfluencesString() {
        return influences.getFirst() + ", " + influences.getLast();
    }

    public int getWallet() {
        return wallet;
    }

    public String getName() {
        return name;
    }

    public boolean getCheat() {
        return cheat;
    }

    // mutators
    public void setInfluences(ArrayList<Character> chars) {
        influences = new ArrayList<>(chars);
    }

    public void setWallet(int coins) {
        this.wallet = coins;
    }

    public void setName(String update) {
        name = update;
    }

    public void setCheat(boolean value) {
        this.cheat = value;
    }

    @Override
    public String toString() {
        return this.getName();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof BasePerformer otherObject)) {
            return false;
        }
        return this.getClass() == other.getClass() && this.getName().equals(otherObject.getName());
    }


    /**
     * Method gets the player to challenge, the deck, the action and whether the challenged action is action or block.
     *
     * @param playerToChallenge player to challenge.
     * @param myDeck            the deck.
     * @param action            the action to challenge.
     * @param isActionChallenge boolean flag of challenged block or action.
     * @return boolean success or no.
     */
    public boolean challenge(BasePerformer playerToChallenge, ArrayList<Character> myDeck, Action.Types action,
                             boolean isActionChallenge) {
        if (playerToChallenge.cheat) {
            ArrayList<Character> charactersToRemove;
            charactersToRemove = Deck.randomizer(playerToChallenge.influences, 1);
            playerToChallenge.influences.removeAll(charactersToRemove);
            Deck.addToDeck(charactersToRemove);
            System.out.println("Congratulations, " + this.name + "! You won the challenge!");
            return true;
        } else {
            this.influences.removeAll(Deck.randomizer(this.influences, 1));
            if (isActionChallenge) {
                boolean enteredIf = false;
                for (Character influence : playerToChallenge.influences) {
                    if (influence.canAct() == action) {
                        ArrayList<Character> found = new ArrayList<Character>(Collections.singleton(influence));
                        playerToChallenge.influences.removeAll(found);
                        Deck.addToDeck(found);
                        enteredIf = true;
                        break;
                    }
                }
                if (enteredIf) {
                    playerToChallenge.influences.addAll(Deck.randomizer(myDeck, 1));
                }
            }
            System.out.println("Congratulations, " + playerToChallenge + "! You won the challenge!");
            return false;
        }
    }

    /**
     * Abstract method to be implemented by subclasses to handle challenges.
     *
     * @param playerToChallenge The player to challenge.
     * @param myDeck            The deck.
     * @param action            The action to challenge.
     * @param isActionChallenge True if challenging an action, false if challenging a block.
     * @return True if the challenge is successful, false otherwise.
     */
    public abstract boolean challenges(BasePerformer playerToChallenge, ArrayList<Character> myDeck, Action.Types action, boolean isActionChallenge);


    /**
     * Abstract method to be implemented by subclasses to handle blocking actions.
     *
     * @param blocked The player attempting to block.
     * @param myDeck  The deck.
     * @param action  The action to block.
     * @return True if the block is successful, false otherwise.
     */
    public abstract boolean block(BasePerformer blocked, ArrayList<Character> myDeck, Action.Types action);

    /**
     * Abstract method to be implemented by subclasses to perform an action in the game.
     *
     * @param myGame The current game state.
     * @return The type of action to perform.
     */
    public abstract Action.Types act(Game myGame);

}