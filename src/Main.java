package src;

import src.am.aua.coup.core.*;
import src.am.aua.coup.exceptions.InvalidNumberOfPlayersException;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
public class Main {
    /**
     * The entry point of the Coup game.
     *
     * @throws InvalidNumberOfPlayersException If the number of players is invalid.
     */
    public static void main(final String[] args) throws InvalidNumberOfPlayersException {
        Scanner userInput = new Scanner(System.in);
        System.out.println("Enter a number from 2 to 7, which will be the number of players you want to play today!");
        int numberOfPlayers = userInput.nextByte();
        final double CHALLENGE_PROBABILITY = 0.5;
        final double BLOCK_PROBABILITY = 0.5;
        Random random = new Random();

        Game myGame = new Game(numberOfPlayers);
        myGame.start();

        while (myGame.isNotOver()) {
            for (Player player : myGame.players) {
                for (Player current : myGame.players) {
                    System.out.println("Say Hi to " + current.getName() + ".");
                }

                boolean correctAnswer = false;
                while(!correctAnswer) {
                    if (player.isHuman) {
                        System.out.println("Do you want to cheat? Answer yes or no!");
                        String isCorrectAction = userInput.next();
                        ArrayList<Action.Types> availableActions;
                        if (isCorrectAction.equalsIgnoreCase("no")) {
                            player.cheat = false;
                            availableActions = player.getAvailableActions();
                            correctAnswer = true;
                        } else if (isCorrectAction.equalsIgnoreCase("yes")) {
                            player.cheat = true;
                            availableActions = player.getAvailableActions();
                            correctAnswer = false;
                        } else {
                            System.out.println("Not sure what you typed. Try again.");
                            continue;
                        }

                        System.out.println(player.getName() + "'s available actions: " + availableActions);
                        Action.Types chosenAction = player.getUserChoice(availableActions);
                        System.out.println(chosenAction);
                        Player target = new Player();

                        if (chosenAction == Action.Types.STEAL || chosenAction == Action.Types.ASSASSINATE || chosenAction == Action.Types.COUP) {
                            System.out.println("Please, choose the player you want to perform the action on: ");
                            ArrayList<Player> currentBots = new ArrayList<>(myGame.players);
                            currentBots.remove(player);
                            target = player.getUserChoice(currentBots);

                        }

                        System.out.println(player.getName() + " wants to perform " + chosenAction + "!");
                        if (random.nextDouble() >= CHALLENGE_PROBABILITY) {
                            Player challenger = myGame.choosePlayerFromBots(myGame.players);
                            System.out.println(challenger.getName() + " decided to challenge " + player.getName() + ".");
                            if (challenger.challenge(player, Deck.deck, chosenAction, true)) {
                                System.out.println("You lost your turn:");
                                break;
                            }
                        }

                        if(random.nextDouble() >= BLOCK_PROBABILITY) {
                            if (!target.performBlock(player, Deck.deck, chosenAction, myGame, myGame.players)) {
                                Action.performAction(player, chosenAction, target);
                            }
                        }
                        break;


                    } else {
                        ArrayList<Action.Types> availableActions = player.getAvailableActions();
                        // random controlled players
                    }
                }
            }
        }

    }
}