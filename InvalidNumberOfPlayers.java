public class InvalidNumberOfPlayers extends Exception {
    /**
     * Constructs a new InvalidNumberOfPlayers exception with a default error message.
     */
    public InvalidNumberOfPlayers() {
        super("The number of players should be more than 1 and less than 7!");
    }
    /**
     * Constructs a new InvalidNumberOfPlayers exception with a custom error message.
     *
     * @param newMessage The custom error message to be displayed.
     */
    public InvalidNumberOfPlayers(String newMessage) {
        super(newMessage);
    }
}
