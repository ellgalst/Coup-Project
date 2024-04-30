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
    public boolean cheat;
    public String getName() {
        return name;
    }
    public void setName(String update) {
        name = update;
    }
    public void changeWallet(int update) {
        this.wallet += update;
    }

    public int getWallet () {
        return wallet;
    }
    public ArrayList<Character> getInfluences(){
        return influences;
    }
    public void setInfluences(ArrayList<Character> chars){
        for(int i=0; i<influences.size(); i++){
            influences.set(i, chars.get(i));
        }
    }

    public int getNumberOfInfluences(){
        return influences.size();
    }
    public ArrayList<Action.Types> getAvailableActions() {
        ArrayList<Action.Types> availableActions = new ArrayList<>(Arrays.asList(
                Action.Types.FOREIGNAID,
                Action.Types.INCOME
        ));
        ArrayList<Action.Types> correctActions = new ArrayList<>();
        for (Character influence : influences) {
            Action.Types actionType = influence.canAct();
            if (actionType == Action.Types.ASSASSINATE && wallet >= 3) {
                correctActions.add(actionType);
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

    public <T> T getUserChoice(ArrayList<T> available) {
        Scanner userInput = new Scanner(System.in);
        int index;

        System.out.println("Available: ");
        for (int i = 0; i < available.size(); i++) {
            System.out.println("The item behind index " + i + " is: " + available.get(i));
        }

        while (true) {
            System.out.print("Enter an index for the preferred item: ");
            if (userInput.hasNextInt()) {
                index = userInput.nextInt();
                if (index >= 0 && index < available.size()) {
                    return available.get(index);
                }
                else {
                    System.out.println("Please, enter a number within the range of the provided items.");
                }
            }
            else {
                System.out.println("Please enter a NUMBER!");
                userInput.next();
            }
        }
    }
    public String toString() {
        return this.getName();
    }


    // modify
    public boolean challenge(Player playerToChallenge, ArrayList<Character> myDeck, Action.Types action, boolean isActionChallenge) {
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

    // modify
    public boolean performBlock (BasePerformer blocked, ArrayList<Character> myDeck, Action.Types action,
                                 Game myGame, ArrayList<BasePerformer> players) {

        // ********************************     fix this shit   *********************************
        Scanner userInput = new Scanner(System.in);
        System.out.println(blocked.getName() + ", do you want to challenge " + this.getName() + "? Answer yes or no.");
        if (userInput.nextLine().equalsIgnoreCase("yes")){
            System.out.println(blocked.getName() + " decided to challenge " + this.getName() + "'s block!");
            return blocked.challenge(this, myDeck, action, false);
        }
        else {
            Player challenger = myGame.choosePlayerFromBots(players);
            if (!challenger.equals(this)) {
                System.out.println(challenger.getName() + " decided to challenge " + this.getName() + "'s block!");
                return blocked.challenge(challenger, myDeck, action, false);
            }
        }

        System.out.println(this.getName() + " successfully blocked " + blocked.getName() + "'s action!");
        return true;
    }

}