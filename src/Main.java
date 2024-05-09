package src;

import src.am.aua.coup.core.*;
import src.am.aua.coup.exceptions.InvalidNumberOfPlayersException;
import src.am.aua.coup.perform.BasePerformer;
import src.am.aua.coup.perform.Player;

import java.util.ArrayList;
import java.util.Scanner;

import static src.am.aua.coup.core.Deck.deck;

public class Main {
    /**
     * The entry point of the Coup game.
     */
    public static void main(final String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter a number from 2 to 6, which will be the number of players you want to play today!");
            int numberOfPlayers = scanner.nextInt();
            if (numberOfPlayers >= 7)
                throw new InvalidNumberOfPlayersException("invalid Number of players, try again");

            Game myGame = new Game(numberOfPlayers);
            myGame.start();

            while (myGame.isNotOver()) {
                for (BasePerformer player : myGame.players) {
                    myGame.players = new ArrayList<>(myGame.updatePlayers(player));
                    System.out.println(myGame.players);
                    if (myGame.players.size() == 1) {
                        break;
                    }
                    System.out.println("Player " + player + "'s turn!");
                    Action.Types playersChoice = player.act(myGame);
                    BasePerformer target = null;
                    if (playersChoice == Action.Types.STEAL || playersChoice == Action.Types.ASSASSINATE || playersChoice == Action.Types.COUP) {
                        if (player instanceof Player) {
                            System.out.println("Please, choose the player you want to perform the action on: ");
                            target = Player.getTarget(myGame.players);
                        } else {
                            ArrayList<BasePerformer> currentPlayers = new ArrayList<>(myGame.players);
                            int randomIndex = (int) (Math.random() * (currentPlayers.size()));
                            target = currentPlayers.get(randomIndex);
                        }
                    }
                    BasePerformer challenger = null;
                    if (!(playersChoice == Action.Types.FOREIGN_AID || playersChoice == Action.Types.INCOME)) {
                         challenger = Game.chooseChallenger(myGame.players, player);
                        System.out.println("challenger in main: "+ challenger);
                    }

                    // challenger tries to challenge the player

                    // fix the logic of the challenge
                    if (challenger != null) {
                        if (challenger.challenges(player, deck, playersChoice, true)) {
                            if (target != null) {
                                // If there was a target for the action, target may try to block
                                if (!target.block(player, deck, playersChoice)) {
                                    // If the target fails the block, action IS performed
                                    boolean works = Action.performAction(player, playersChoice, target);
                                    if (works) {
                                        System.out.println(player.getName() + "'s action is performed " + playersChoice);
                                    }
                                    myGame.players = new ArrayList<>(myGame.updatePlayers(player));
                                    System.out.println(myGame.players);
                                    if (myGame.players.size() == 1) {
                                        break;
                                    }
                                }
                            } else {
                                // If there is no target and the challenger fails, the action IS performed
                                boolean works = Action.performAction(player, playersChoice, target);
                                if (works) {
                                    System.out.println(player.getName() + "'s action is performed " + playersChoice);
                                }
                                myGame.players = new ArrayList<>(myGame.updatePlayers(player));
                                System.out.println(myGame.players);
                                if (myGame.players.size() == 1) {
                                    break;
                                }
                            }
                        } else {
                            // If the challenger succeeds, the action is NOT performed
                            System.out.println("Challenger succeeded! " + player.getName() + "'s action is not performed.");
                        }
                    } else {
                        // If there is no challenger or if the action is income or foreign aid, perform the action
                        boolean works = Action.performAction(player, playersChoice, target);
                        if (works) {
                            System.out.println(player.getName() + "'s action is performed " + playersChoice);
                        }
                        myGame.players = new ArrayList<>(myGame.updatePlayers(player));
                    }



                    // if the challenger succeeds, the action is NOT performed


                    myGame.players = new ArrayList<>(myGame.updatePlayers(player));
                    System.out.println(myGame.players);
                    if (myGame.players.size() == 1) {
                        break;
                    }

                    // the state of the players after one turn
                    myGame.printData();

                    // the turn goes to the next player
                }
            }
        } catch (InvalidNumberOfPlayersException e) {
            System.out.println("The number of players is invalid, try again");
        }
        System.out.println("The game is over!");
    }
}