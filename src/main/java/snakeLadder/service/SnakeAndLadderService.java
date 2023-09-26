package snakeLadder.service;

import snakeLadder.model.Ladder;
import snakeLadder.model.Player;
import snakeLadder.model.Snake;
import snakeLadder.model.SnakeLadderBoard;

import java.util.*;

public class SnakeAndLadderService {
    private SnakeLadderBoard snakeLadderBoard;
    private int initialNumberOfPlayers;
    private Queue<Player>players;
    private boolean isGameCompleted;
    private int noOfDice;
    private boolean shouldGameContinueTillLastPlayer;
    private boolean shouldAllowMultipleDiceRollOnSix;
    private static final int DEFAULT_BOARD_SIZE = 100;
    private static final int DEFAULT_NO_OF_DICES = 1;

    public SnakeAndLadderService(int boardSize) {
        this.snakeLadderBoard = new SnakeLadderBoard(boardSize);
        this.players = new LinkedList<Player>();
        this.noOfDice = SnakeAndLadderService.DEFAULT_NO_OF_DICES;
    }

    public SnakeAndLadderService() {
        this(SnakeAndLadderService.DEFAULT_BOARD_SIZE);
    }

    public void setNoOfDice(int noOfDice) {
        this.noOfDice = noOfDice;
    }

    public void setShouldGameContinueTillLastPlayer(boolean shouldGameContinueTillLastPlayer) {
        this.shouldGameContinueTillLastPlayer = shouldGameContinueTillLastPlayer;
    }

    public void setShouldAllowMultipleDiceRollOnSix(boolean shouldAllowMultipleDiceRollOnSix) {
        this.shouldAllowMultipleDiceRollOnSix = shouldAllowMultipleDiceRollOnSix;
    }


    public void setPlayers(List<Player> players) {
        this.players = new LinkedList<Player>();
        this.initialNumberOfPlayers = players.size();
        Map<String, Integer>playerPieces = new HashMap<String, Integer>();
        for (Player player : players){
            this.players.add(player);
            playerPieces.put(player.getId(), 0);
        }
        snakeLadderBoard.setPlayerPieces(playerPieces);
    }

    public void setSnakes(List<Snake>snakes) {

        snakeLadderBoard.setSnakes(snakes);
    }

    public void setLadder(List<Ladder>ladders) {

        snakeLadderBoard.setLadders(ladders);
    }
    private int getNewPositionAfterGoingThroughSnakeAnaLadder(int newPosition){
        int previousPosition;
        do {
            previousPosition = newPosition;
            for(Snake snake : snakeLadderBoard.getSnakes()){
                if(snake.getStart() == newPosition) {
                    newPosition = snake.getEnd();
                }
            }
            for(Ladder ladder : snakeLadderBoard.getLadders()){
                if(ladder.getStart() == newPosition) {
                    newPosition = ladder.getEnd();
                }
            }
        }while (newPosition != previousPosition);
        return newPosition;
    }
    private void movePlayer(Player player, int positions) {
        int oldPosition = snakeLadderBoard.getPlayerPieces().get(player.getId());
        int newPosition = oldPosition+positions;
        int boardSize = snakeLadderBoard.getSize();
        if(newPosition > boardSize) {
            newPosition = oldPosition;
        }else {
            newPosition = getNewPositionAfterGoingThroughSnakeAnaLadder(newPosition);
        }
        snakeLadderBoard.getPlayerPieces().put(player.getId(), newPosition);
        System.out.println(player.getName() +" rolled a "+positions+" and moved from "+ oldPosition +" to "+ newPosition);
    }

    private int getToltalValueAfterDiceRolls(){
        return DiceService.roll();
    }
    private boolean hasPlayerWon(Player player) {
        int playerPosition = snakeLadderBoard.getPlayerPieces().get(player.getId());
        int winningPosition = snakeLadderBoard.getSize();
        return playerPosition == winningPosition;
    }
    private boolean isGameCompleted() {
        int currentNumberOfPlayers = players.size();
        System.out.println("initial Number of palyer"+ initialNumberOfPlayers);
        return currentNumberOfPlayers < initialNumberOfPlayers;
    }
    public  void startGame() {
        while (!isGameCompleted()) {
          int totalDiceValue = getToltalValueAfterDiceRolls();
          Player currentPlayer = players.poll();
          movePlayer(currentPlayer, totalDiceValue);
          if(hasPlayerWon(currentPlayer)) {
              System.out.println(currentPlayer.getName() + " wins the game");
          }else {
              players.add(currentPlayer);
          }
        }
    }
}





