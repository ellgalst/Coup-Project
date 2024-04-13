/**
 * A child class of Character.
 * action: to assassinate, which means to pay and make any player lose one character.
 * counterAction: it has no counteraction
 */
public class Assassin extends Character {

    public Assassin() {
        super("Assassin", "Pays 3 coins and makes another player lose 1 influence,");
    }

    public void action(Player player, Game game) {
        if (player.getCoins() >= 3) {
            player.changeCoins(-3);
            game.assasinate(player);
        }
    }

    public boolean block(ActionType action) {
        return action == ActionType.ASSASINATION;
    }

    @Override
    public boolean isBlockable(ActionType action) {
        return true;
    }
}