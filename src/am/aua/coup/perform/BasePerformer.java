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


    // some issue with challenge
    public boolean challenge(BasePerformer playerToChallenge, ArrayList<Character> myDeck, Action.Types action,
                             boolean isActionChallenge) {
        // if opponent cheats
        if (playerToChallenge.cheat) {
            Character characterToRemove;
            // if opponent's influences are 1, we choose it
            if (playerToChallenge.influences.size() == 1) {
                characterToRemove = playerToChallenge.influences.getFirst();
            } else {
                // if not, we randomly choose one
                characterToRemove = Deck.randomizer(playerToChallenge.influences, 1).getFirst();
            }
            // eventually we remove the chosen influence
            playerToChallenge.influences.remove(characterToRemove);
            // then, we add that card back to the deck
            Deck.addToDeck(characterToRemove);
            System.out.println("Congratulations, " + this.name + "! You won the challenge!");
            return true;
        } else {
            // if the opponent is honest, we remove a random influence from the challenger's influences
            this.influences.remove(Deck.randomizer(this.influences, 1).getFirst());
            // if the challenged thing was an action
            if (isActionChallenge) {
                // we find it in the influences and remove it, returning the card to the deck
                boolean enteredIf = false;
                for (Character influence : playerToChallenge.influences) {
                    if (influence.canAct() == action) {
                        playerToChallenge.influences.remove(influence);
                        Deck.addToDeck(influence);
                        enteredIf = true;
                        break;
                    }
                }
                // if the card is removed, the opponent gets another card
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