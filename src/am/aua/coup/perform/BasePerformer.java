package src.am.aua.coup.perform;

import src.am.aua.coup.core.Action;
import src.am.aua.coup.core.Deck;
import src.am.aua.coup.core.Game;
import src.am.aua.coup.influences.Character;

import java.util.*;

public abstract class BasePerformer {
    ArrayList<Character> influences = new ArrayList<Character>(2);
    private int wallet;
    private String name;
    private boolean cheat;


    // accessors
    public ArrayList<Character> getInfluences(){
        return influences;
    }
    public int getNumberOfInfluences(){
        return influences.size();
    }
    public int getWallet () {
        return wallet;
    }
    public String getName() {
        return name;
    }
    public boolean getCheat() {
        return cheat;
    }

    // mutators
    public void setInfluences(ArrayList<Character> chars){
        for(int i=0; i<influences.size(); i++){
            influences.set(i, chars.get(i));
        }
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
        ArrayList<Action.Types> availableActions = new ArrayList<>(Arrays.asList(
                Action.Types.FOREIGNAID,
                Action.Types.INCOME
        ));

        ArrayList<Action.Types> correctActions = new ArrayList<>();
        for (Character influence : influences) {
            Action.Types actionType = influence.canAct();
            if (actionType == Action.Types.ASSASSINATE) {
                if (wallet >= 3) {
                    correctActions.add(actionType);
                }
            }
            correctActions.add(actionType);
        }

        if (wallet >= 7) {
            return new ArrayList<Action.Types>(List.of(Action.Types.COUP));
        } else if (!cheat) {
            availableActions.addAll(correctActions);
        } else {
            for (Action.Types element : Action.Types.values()) {
                if (!(availableActions.contains(element) || correctActions.contains(element) || element == Action.Types.COUP)) {
                    availableActions.add(element);
                }
            }
            if (!correctActions.contains(Action.Types.ASSASSINATE) && wallet >= 3) {
                availableActions.add(Action.Types.ASSASSINATE);
            }
        }
        System.out.println(availableActions);
        return availableActions;
    }



    public boolean challenge(BasePerformer playerToChallenge, ArrayList<Character> myDeck, Action.Types action,
                             boolean isActionChallenge) {
        if (playerToChallenge.cheat) {
            playerToChallenge.influences.remove(Deck.randomizer(playerToChallenge.influences, 1).getFirst());
            System.out.println("Congratulations, " + this.name + "! You won the challenge!");
            return false;
        }
        else {
            this.influences.remove(Objects.requireNonNull(Deck.randomizer(this.influences, 1)).getFirst());
            if (isActionChallenge) {
                this.influences.remove(Objects.requireNonNull(Deck.randomizer(this.influences, 1)).getFirst());
                if (playerToChallenge.influences.getFirst().canAct() == action) {
                    playerToChallenge.influences.removeFirst();
                } else {
                    playerToChallenge.influences.removeLast();
                }
                playerToChallenge.influences.add(Deck.randomizer(myDeck, 1).getFirst());
            }
            System.out.println("Congratulations, " + playerToChallenge.getName() + "! You won the challenge!");
            return true;
        }
    }


}