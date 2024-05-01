package src;

import src.am.aua.coup.core.*;
import src.am.aua.coup.exceptions.InvalidNumberOfPlayersException;
import src.am.aua.coup.perform.BasePerformer;

import java.util.ArrayList;
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

        Game myGame = new Game(numberOfPlayers);
        myGame.start();

        while (myGame.isNotOver()) {
            for (BasePerformer player : myGame.players) {
                // it is left to write the logic of the main and fix the detected issues in the process
            }
        }
    }
}