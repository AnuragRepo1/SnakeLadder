package snakeLadder;

import snakeLadder.model.Ladder;
import snakeLadder.model.Player;
import snakeLadder.model.Snake;
import snakeLadder.service.SnakeAndLadderService;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int noOfSnakes = sc.nextInt();
        List<Snake> snakes = new ArrayList<>();
        for (int i = 0; i<noOfSnakes; i++) {
            snakes.add(new Snake(sc.nextInt(), sc.nextInt()));
        }
        int noOfLadder = sc.nextInt();
        List<Ladder> ladders = new ArrayList<>();
        for (int i = 0; i<noOfLadder; i++) {
            ladders.add(new Ladder(sc.nextInt(), sc.nextInt()));
        }
        int noOfPlayer = sc.nextInt();
        List<Player> players = new ArrayList<>();
        for (int i = 0; i<noOfPlayer; i++){
            players.add(new Player(sc.next()));
        }
        SnakeAndLadderService snakeAndLadderService = new SnakeAndLadderService();
        snakeAndLadderService.setLadder(ladders);
        snakeAndLadderService.setSnakes(snakes);
        snakeAndLadderService.setPlayers(players);
        snakeAndLadderService.startGame();
    }
}
