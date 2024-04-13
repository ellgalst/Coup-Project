public class InvalidNumberOfPlayers extends Exception {
    public InvalidNumberOfPlayers() {
        super("The number of players should be more than 1 and less than 7!");
    }
    public InvalidNumberOfPlayers(String newMessage) {
        super(newMessage);
    }
}
