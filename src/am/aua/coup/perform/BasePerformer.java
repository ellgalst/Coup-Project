package src.am.aua.coup.perform;

import src.am.aua.coup.core.Action;
import src.am.aua.coup.core.Deck;
import src.am.aua.coup.core.Game;
import src.am.aua.coup.influences.Character;

import java.util.*;

public abstract class BasePerformer {
    private ArrayList<Character> influences = new ArrayList<Character>(2);
    private int wallet;
    private String name;
    private boolean cheat;

    // accessors
    public ArrayList<Character> getInfluences() {
        return influences;
    }

    public int getNumberOfInfluences() {
        return influences.size();
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

    // override toString method for the more understandable output
    public String toString() {
        return this.getName();
    }

    // override equals method
    public boolean equals(Object other) {
        if (!(other instanceof BasePerformer otherObject)) {
            return false;
        }
        return this.getClass() == other.getClass() && this.getName().equals(otherObject.getName());
    }


    public boolean challenge(BasePerformer playerToChallenge, ArrayList<Character> myDeck, Action.Types action,
                             boolean isActionChallenge) {
        if (playerToChallenge.cheat) {
            Character characterToRemove;
            if (playerToChallenge.influences.size() == 1) {
                characterToRemove = playerToChallenge.influences.getFirst();
            } else {
                characterToRemove = Deck.randomizer(playerToChallenge.influences, 1).getFirst();
            }
            playerToChallenge.influences.remove(characterToRemove);
            Deck.addToDeck(characterToRemove);
            System.out.println("Congratulations, " + this.name + "! You won the challenge!");
            return true;
        } else {
            this.influences.remove(Deck.randomizer(this.influences, 1).getFirst());
            if (isActionChallenge) {
                boolean enteredIf = false;
                for (Character influence : playerToChallenge.influences) {
                    if (influence.canAct() == action) {
                        playerToChallenge.influences.remove(influence);
                        Deck.addToDeck(influence);
                        enteredIf = true;
                        break;
                    }
                }
                if (enteredIf) {
                    playerToChallenge.influences.add(Deck.randomizer(myDeck, 1).getFirst());
                }
            }
            System.out.println("Congratulations, " + playerToChallenge + "! You won the challenge!");
            return false;
        }
    }

    public abstract boolean challenges(BasePerformer playerToChallenge, ArrayList<Character> myDeck, Action.Types action, boolean isActionChallenge);

    public abstract boolean block(BasePerformer blocked, ArrayList<Character> myDeck, Action.Types action);

    public abstract Action.Types act(Game myGame);

}