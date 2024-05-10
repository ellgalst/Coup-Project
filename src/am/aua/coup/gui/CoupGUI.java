package src.am.aua.coup.gui;
import src.am.aua.coup.core.Action;
import src.am.aua.coup.core.Deck;
import src.am.aua.coup.core.Game;
import src.am.aua.coup.exceptions.InvalidNumberOfPlayersException;
import src.am.aua.coup.perform.BasePerformer;
import src.am.aua.coup.perform.Bot;
import src.am.aua.coup.perform.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


//********************* as the influences don't get initialized properly, the app crashes. So need to check out that part.
public class CoupGUI extends JFrame {
    private JLabel turn;
    private int numPlayers;
    private JPanel boardPanel;
    private String playerName;
    public ArrayList<BasePerformer> players;
    private ArrayList<JLabel> playerLabels;
    private JPanel centerPanel;
    private JPanel actionPanel;
    private BasePerformer player;
    ArrayList<BasePerformer> playerList = new ArrayList<BasePerformer>(numPlayers);

    public CoupGUI() {

        setTitle("COUP");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800); // Set fixed size 800x800
        setResizable(false); // Prevent the frame from being resized
        setLocationRelativeTo(null); // Center the window on the screen
        setLayout(new BorderLayout());
        askForUserName();

        // Ask for the number of players
        boolean validInput = false;
        while (!validInput) {
            String input = JOptionPane.showInputDialog(this, "Enter the number of players (2-6):", "Player Count", JOptionPane.QUESTION_MESSAGE);
            try {
                numPlayers = Integer.parseInt(input);
                if (numPlayers < 2 || numPlayers > 6) {
                    JOptionPane.showMessageDialog(this, "Number of players must be between 2 and 6.", "Invalid Input", JOptionPane.ERROR_MESSAGE);
                } else {
                    validInput = true; // Exit the loop if input is valid
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
        Game game = new Game(numPlayers);
        Player humanPlayer = new Player(playerName, 2);
        humanPlayer.setName(playerName);
        playerList.add(humanPlayer);
        for (int i = 0; i < numPlayers - 1; i++) {
            Bot bot = new Bot(game.getDefaultNamesForPlayers()[i], 2);
            playerList.add(bot);
        }
        Deck myDeck = new Deck();
        players = myDeck.distributeCards(playerList);

        setupBoard(humanPlayer);
        setupActions(humanPlayer);
        setLocationRelativeTo(null); // Center on screen
        setVisible(true);

    }

    private void setupActions(BasePerformer humanPlayer){
        JButton actionButton = null;
        actionPanel = new JPanel(new GridLayout(1, 7));
        for(Action.Types action: Action.actionsArray()){
            boolean isAvailable;
            if(Action.getAvailableActions(humanPlayer).contains(action)) {
                isAvailable = true;
                actionButton = new JButton(action.toString());
                actionButton.setOpaque(true);
                actionButton.setBorderPainted(false);
                actionButton.setBackground(Color.GREEN);
            }
            else{
                isAvailable = false;
                actionButton = new JButton(action.toString());
                actionButton.setOpaque(true);
                actionButton.setBorderPainted(false);
                actionButton.setBackground(Color.RED);
            }


            actionButton.addActionListener(e -> handleActionClick(action, humanPlayer));

            actionPanel.add(actionButton);
        }

        add(actionPanel, BorderLayout.NORTH);
        boolean cheat = askForCheating();

    }
    private void handleActionClick(Action.Types action, BasePerformer player) {
        BasePerformer playerToAttack = null;
        switch (action) {
            case COUP:
            case STEAL:
            case ASSASSINATE:
                playerToAttack = getPlayerToAttack(); // Get the target player from user
                if (playerToAttack == null) return; // Exit if no player was selected (cancelled)
        }
        switch (action){
            case TAX:
                Action.performTax(player);
                break;
            case INCOME:
                Action.performIncome(player);
                break;
            case FOREIGN_AID:
                Action.performForeignAid(player);
                break;
            case COUP:

                Action.performCoup(player, playerToAttack);
                break;
            case STEAL:
                Action.performSteal(player, playerToAttack);
                break;
            case EXCHANGE:
                Action.performExchange(player);
                break;
            case ASSASSINATE:
                Action.performAssassinate(player, playerToAttack);
                break;
        }

    }
    private BasePerformer getPlayerToAttack() {
        Object[] options = new List[]{playerList.subList(1, playerList.size() - 1)};
        BasePerformer selectedPlayer = (BasePerformer) JOptionPane.showInputDialog(
                this,
                "Select a player to attack:",
                "Choose Target",
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]
        );
        return selectedPlayer;
    }

    private boolean askForCheating() {
        int response = JOptionPane.showConfirmDialog(this, "Do you want to cheat in this turn?", "Cheating", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
        return response == JOptionPane.YES_OPTION;
    }
    private void askForUserName() {
        playerName = JOptionPane.showInputDialog(this, "Please enter your name:", "Player Name", JOptionPane.QUESTION_MESSAGE);
        if (playerName == null || playerName.trim().isEmpty()) {
            playerName = "Human Player";  // Default name if none provided
        }

    }
    private void setupBoard(BasePerformer humanPlayer) {
        boardPanel = new JPanel();
        boardPanel.setLayout(new BorderLayout());

        setupHumanPlayer(humanPlayer);
        setupBots();
        add(boardPanel, BorderLayout.CENTER);
        turn = new JLabel("Your turn!", JLabel.CENTER);
        add(turn, BorderLayout.SOUTH);
    }

    private void setupHumanPlayer(BasePerformer player) {
        JPanel humanPanel = new JPanel();
        humanPanel.setLayout(new BoxLayout(humanPanel, BoxLayout.Y_AXIS));

        JLabel humanPlayer = new JLabel(playerName, JLabel.CENTER);
        humanPlayer.setPreferredSize(new Dimension(100, 100));
        humanPlayer.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        JLabel influenceLabel = new JLabel("Influences: "+ player.getInfluencesString(), JLabel.CENTER);
        humanPanel.add(humanPlayer);
        humanPanel.add(influenceLabel);
        boardPanel.add(humanPanel, BorderLayout.SOUTH);
    }

    private void setupBots() {
        centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 50, 10));
        for (int i = 1; i < numPlayers; i++) {
            JPanel botPanel = new JPanel();
            botPanel.setLayout(new BoxLayout(botPanel, BoxLayout.Y_AXIS));
            JLabel botLabel = new JLabel("Bot"+(i+1), JLabel.CENTER);
            botLabel.setPreferredSize(new Dimension(100, 100)); // Set preferred size for bot blocks
            botLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
            JLabel botInfluences = new JLabel("Influences: " + playerList.get(i).getInfluencesString());
            botPanel.add(botLabel);
            botPanel.add(botInfluences);

            centerPanel.add(botPanel);
        }

        boardPanel.add(centerPanel, BorderLayout.CENTER);
    }

/*
try {


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
                                System.out.println("challenger in main: " + challenger);
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
 */
}

