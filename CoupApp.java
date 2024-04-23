import java.util.ArrayList;
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

        Game myGame = new Game(numberOfPlayers);
        myGame.start();

        while (myGame.isNotOver()) {
            for (Player player : myGame.players) {
                if (player.isHuman) {
                    System.out.println("Do you want to cheat? Answer yes or no!");
                    String isCorrectAction = userInput.nextLine();
                    ArrayList<Action.Types> availableActions;
                    if (isCorrectAction.equalsIgnoreCase("no")) {
                        availableActions = player.getAvailableActions(true);
                    } else if (isCorrectAction.equalsIgnoreCase("yes")) {
                        availableActions = player.getAvailableActions(false);
                    }
                    // handle this later
                    else {
                        System.out.println("Not sure what you typed. Try again.");
                    }

                    System.out.println(player.getName() + "'s available actions: " + availableActions);
                    System.out.println(player.getUserAction(availableActions));

                }
                else {
                    ArrayList<Action.Types> availableActions = player.getAvailableActions(false);
                    // random controlled players
                }
            }
        }

    }

    }

    // the Scanner class, for the user to play the game. for the action, we will need the will to cheat or not (true or false)
    // and the index from the available actions (need exception, while loop)
    // for the challenge, ask the all the users, except the current one if they want to challenge that current player (true or false)
