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
                System.out.println("AFTER WHILE");
                System.out.println("BEFORE FORRRRR");
                for (BasePerformer player : myGame.players) {
                    System.out.println("AFTER FORRRR");
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

                    BasePerformer challenger = myGame.chooseChallenger(player);

                    // challenger tries to challenge the player

                    if (challenger != null && (!challenger.challenges(player, deck, playersChoice, true))) {
                        myGame.players = new ArrayList<>(myGame.updatePlayers(player));
                        System.out.println(myGame.players);
                        if (myGame.players.size() == 1) {
                            break;
                        }
                        if (target != null) {
                            // if the challenger doesn't succeed and there was a target for the action, target may try to block
                            if (!target.block(player, deck, playersChoice)) {
                                // if the target fails the block, action IS performed
                                Action.performAction(player, playersChoice, target);
                                myGame.players = new ArrayList<>(myGame.updatePlayers(player));
                                System.out.println(myGame.players);
                                if (myGame.players.size() == 1) {
                                    break;
                                }
                            }
                        }
                        // if there is no target and the challenger fails, the action IS performed
                        Action.performAction(player, playersChoice, target);
                        myGame.players = new ArrayList<>(myGame.updatePlayers(player));
                        System.out.println(myGame.players);
                        if (myGame.players.size() == 1) {
                            break;
                        }
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