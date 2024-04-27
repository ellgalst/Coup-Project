import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
// the main class to run the project
public class CoupApp {
    /**
     * The entry point of the Coup game.
     *
     * @throws InvalidNumberOfPlayers If the number of players is invalid.
     */
    public static void main(final String[] args) throws InvalidNumberOfPlayers {
        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter a number from 2 to 7, which will be the number of players you want to play today!");
        int numberOfPlayers = userInput.nextByte();
        final double CHALLENGE_PROBABILITY = 0.5;
        Random random = new Random();

        Game myGame = new Game(numberOfPlayers);
        myGame.start();

        while (myGame.isNotOver()) {
            for (Player player : myGame.players) {
                boolean correctAnswer = false;
                while(!correctAnswer) {
                    if (player.isHuman) {
                        System.out.println("Do you want to cheat? Answer yes or no!");
                        String isCorrectAction = userInput.nextLine();
                        ArrayList<Action.Types> availableActions;
                        if (isCorrectAction.equalsIgnoreCase("no")) {
                            player.cheat = false;
                            availableActions = player.getAvailableActions();
                            correctAnswer = true;
                        } else if (isCorrectAction.equalsIgnoreCase("yes")) {
                            player.cheat = true;
                            availableActions = player.getAvailableActions();
                            correctAnswer = true;
                        } else {
                            System.out.println("Not sure what you typed. Try again.");
                            continue;
                        }

                        System.out.println(player.getName() + "'s available actions: " + availableActions);
                        Action.Types chosenAction = player.getUserChoice(availableActions);
                        System.out.println(chosenAction);
                        Player target = null;

                        if (chosenAction == Action.Types.STEAL || chosenAction == Action.Types.ASSASSINATE || chosenAction == Action.Types.COUP) {
                            System.out.println("Please, choose the player you want to perform the action on: ");
                            System.out.println(myGame.players);
                            target = player.getUserChoice(myGame.players);
                        }

                        System.out.println(player.getName() + " wants to perform " + chosenAction + "!");
                        if (random.nextDouble() >= CHALLENGE_PROBABILITY) {
                            Player challenger = myGame.choosePlayerFromBots(myGame.players, random);
                            System.out.println(challenger.getName() + " decided to challenge " + player.getName() + ".");
                            if (!challenger.challenge(player, Deck.deck, chosenAction)) {
                                Action.performAction(player, chosenAction, target);
                            }
                        }
                        // here will be implemented the logic of "blocking"

                        // turn is going to the next player

                    } else {
                        ArrayList<Action.Types> availableActions = player.getAvailableActions();
                        // random controlled players
                    }
                }
            }
        }

    }
}