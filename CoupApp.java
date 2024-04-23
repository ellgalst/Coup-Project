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
        int numberOfPlayers = userInput.nextByte();

        Game myGame = new Game(numberOfPlayers);
        myGame.start();

        while (myGame.isNotOver()) {
            for (Player player : myGame.players) {
                if (player.isHuman) {
                    ArrayList<Action.Types> availableActions = player.getAvailableActions(true);
                    System.out.println(player.getName() + "'s available actions: " + availableActions);
                    // user input handling

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
