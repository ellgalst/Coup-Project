package src;

import src.am.aua.coup.core.*;
import src.am.aua.coup.core.Action;
import src.am.aua.coup.exceptions.InvalidNumberOfPlayersException;
import src.am.aua.coup.gui.CoupGUI;
import src.am.aua.coup.perform.BasePerformer;
import src.am.aua.coup.perform.Player;

import javax.swing.*;
import java.util.ArrayList;
import java.util.Scanner;

import static src.am.aua.coup.core.Deck.deck;

public class Main {
    /**
     * The entry point of the Coup game.
     */
    public static void main(final String[] args) {
        if (args.length == 0) {
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    SwingUtilities.invokeLater(CoupGUI::new);
                }
            });
        } else {
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
                        if (myGame.players.size() == 1) {
                            break;
                        }
                        System.out.println(myGame.players);
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
                        }
                        if (challenger != null) {
                            if (!challenger.challenges(player, deck, playersChoice, true)) {
                                if (target != null) {
                                    if (!target.block(player, deck, playersChoice)) {
                                        actionWorks(player, playersChoice, target);
                                        myGame.players = new ArrayList<>(myGame.updatePlayers(player));
                                        if (myGame.players.size() == 1) {
                                            break;
                                        }
                                    }
                                } else {
                                    actionWorks(player, playersChoice, target);

                                    myGame.players = new ArrayList<>(myGame.updatePlayers(player));
                                    if (myGame.players.size() == 1) {
                                        break;
                                    }
                                }
                            } else {
                                System.out.println("Challenger succeeded! " + player.getName() + "'s action is not performed.");
                            }
                        } else {
                            actionWorks(player, playersChoice, target);
                            myGame.players = new ArrayList<>(myGame.updatePlayers(player));
                        }


                        myGame.players = new ArrayList<>(myGame.updatePlayers(player));
                        if (myGame.players.size() == 1) {
                            break;
                        }

                        myGame.printData();

                    }
                }
            } catch (InvalidNumberOfPlayersException e) {
                System.out.println("The number of players is invalid, try again");
            }
            System.out.println("The game is over!");
        }
    }
    private static void actionWorks (BasePerformer player, Action.Types playersChoice, BasePerformer target) {
        boolean works = Action.performAction(player, playersChoice, target);
        if (works) {
            System.out.println(player.getName() + "'s action is performed " + playersChoice);
        }
    }
}
