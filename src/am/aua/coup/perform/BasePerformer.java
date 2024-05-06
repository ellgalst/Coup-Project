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

    /**
     * Gets the actions available to the player based on their wish to cheat or be honest.
     *
     * @return The list of available actions for the player.
     */
    public ArrayList<Action.Types> getAvailableActions() {
        if (wallet >= 7) {
            return new ArrayList<Action.Types>(List.of(Action.Types.COUP));
        }
        ArrayList<Action.Types> availableActions = new ArrayList<Action.Types>();

        ArrayList<Action.Types> correctActions = new ArrayList<Action.Types>(Arrays.asList(
                Action.Types.FOREIGN_AID,
                Action.Types.INCOME
        ));
        for (Character influence : influences) {
            Action.Types actionType = influence.canAct();
            if (actionType == Action.Types.ASSASSINATE) {
                if (wallet >= 3 && !correctActions.contains(actionType)) {
                    correctActions.add(actionType);
                }
            } else if (actionType != Action.Types.COUP && !correctActions.contains(actionType)) {
                correctActions.add(actionType);
            }
        }
        if (!cheat) {
            availableActions.addAll(correctActions);
        }
        else {
            for (Action.Types action : Action.Types.values()) {
                if (action == Action.Types.ASSASSINATE) {
                    if (wallet >= 3 && !correctActions.contains(Action.Types.ASSASSINATE) && !availableActions.contains(action)) {
                        availableActions.add(Action.Types.ASSASSINATE);
                    }
                }
                else if (!correctActions.contains(action) && action != Action.Types.COUP && !availableActions.contains(action)) {
                    availableActions.add(action);
                }
            }
        }


        return availableActions;
    }


    public boolean challenge(BasePerformer playerToChallenge, ArrayList<Character> myDeck, Action.Types action,
                             boolean isActionChallenge) {
        if (playerToChallenge.cheat) {
            Character characterToRemove = Deck.randomizer(playerToChallenge.influences, 1).getFirst();
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