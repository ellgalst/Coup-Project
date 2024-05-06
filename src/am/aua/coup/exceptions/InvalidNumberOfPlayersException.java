package src.am.aua.coup.exceptions;

public class InvalidNumberOfPlayersException extends Exception {
    /**
     * Constructs a new InvalidNumberOfPlayers exception with a default error message.
     */
    public InvalidNumberOfPlayersException() {
        super("The number of players should be more than 1 and less than 6!");
    }
    /**
     * Constructs a new InvalidNumberOfPlayers exception with a custom error message.
     *
     * @param newMessage The custom error message to be displayed.
     */
    public InvalidNumberOfPlayersException(String newMessage) {
        super(newMessage);
    }
}
