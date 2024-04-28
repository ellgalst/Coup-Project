import java.util.*;

/**
 * A separate class to keep track of all players.
 */
public class Player {
    ArrayList<Character> influences = new ArrayList<Character>(2);
    /**
     * The initial number of coins in the player's wallet.
     */
    private int wallet;
    /**
     * The number of character influences (cards) the player possesses.
     */
    private int theNumberOfInfluences;
    private String playerName;
    public boolean isHuman;
    public boolean cheat;
    public String getName() {
        return playerName;
    }
    public void setName(String update) {
        playerName = update;
    }

    public Player () {
        this.playerName = "Catrin";
        this.wallet = 2;
        this.isHuman = false;
    }
    /**
     * Constructor method.
     * Initializes a player with the specified initial number of coins in their wallet. If the player is a bot
     * initializes its name.
     * @param initialCoins The initial number of coins in the player's wallet.
     */
    public Player(String playerName, int initialCoins, boolean isHuman) {
        this.playerName = playerName;
        this.wallet = initialCoins;
        this.isHuman = isHuman;
        if(!isHuman){
            playerName = "BOT";
        }
    }

    /**
     * Changes the player's wallet balance by the specified amount.
     *
     * @param update The amount by which to update the player's wallet balance.
     */
    public void changeWallet(int update) {
        this.wallet += update;
    }

    public int getWallet () {
        return wallet;
    }
    /**
     * Retrieves the number of character influences (cards) the player possesses.
     *
     * @return The number of character influences (cards) the player possesses.
     */
    public int getTheNumberOfInfluences() {
        return influences.size();
    }

    // override equals method. checks the equality based on the name of the player.
    public boolean equals(Player other) {
        if (other == null || !other.getClass().getSimpleName().equals("Player")) {
            return false;
        }
        return this.getName().equals(other.getName());
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
        System.out.println(influences);
        for (Character influence : influences) {
            System.out.println(influence);
            Action.Types actionType = influence.canAct();
            if (actionType == Action.Types.ASSASSINATE && wallet >= 3) {
                System.out.println(actionType);
                correctActions.add(actionType);
            }
            System.out.println(actionType);
            correctActions.add(actionType);
        }

        if (wallet >= 7) {
            return new ArrayList<Action.Types>(List.of(Action.Types.COUP));
        } else if (!cheat) {
            availableActions.addAll(correctActions);
            System.out.println(availableActions);
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
        return availableActions;
    }

    /**
     * A method to return the action user wants to do from the given list of available actions.
     * @param available arrayList of available actions.
     * @return action the user wants to do.
     */
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


    public boolean challenge(Player playerToChallenge, ArrayList<Character> myDeck, Action.Types action, boolean isActionChallenge) {
        if (playerToChallenge.cheat) {
            playerToChallenge.influences.remove(Deck.randomizer(playerToChallenge.influences, 1).getFirst());
            System.out.println("Congratulations, " + this.playerName + "! You won the challenge!");
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
            System.out.println("Congratulations, " + playerToChallenge.playerName + "! You won the challenge!");
            return true;
        }

    }

    public boolean performBlock (Player blocked, ArrayList<Character> myDeck, Action.Types action, Game myGame, ArrayList<Player> players) {
        Scanner userInput = new Scanner(System.in);
        System.out.println(blocked.getName() + ", do you want to challenge " + this.getName() + "? Answer yes or no.");

        if (userInput.nextLine().equalsIgnoreCase("yes")) {
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