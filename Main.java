import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws Exception{
        Players players = new Players("players.json");
        while (true) {
            Player player = null;
            while (player == null) {
                player = playerSelect(players.getPlayers());
                if (player == null) {
                    playerCreate(players);
                    players.saveGame();
                }
            }
            System.out.println("Player selected : " + player.getName());
            Game game = null;
            while (game == null) {
                game = gameSelect(player.getGames());
                if (game == null) {
                    gameCreate(players, player);
                    players.saveGame();
                }
            }
            System.out.println("Game selected : " + game.getName());

            Map map = game.getMap();
            Timer timer = game.getTimer();
            if (game.getStatus() == GameStatus.WAITING) {
                game.setStatus(GameStatus.RUNNING);
                timer.start();
                while (game.getStatus() == GameStatus.RUNNING) {
                    map.print();
                    Scanner input = new Scanner(System.in);
                    String choice = "";
                    while (!choice.equals("F") && !choice.equals("R") && !choice.equals("P")) {
                        timer.display();
                        System.out.println("Enter [F-R-P]:");
                        choice = input.nextLine();
                    }
                    int x, y;
                    if (choice.equals("P")) {
                        game.setStatus(GameStatus.WAITING);
                        System.out.println("1. Exit\n2. Continue");
                        x = input.nextInt();
                        if (x == 1) break;
                        game.setStatus(GameStatus.RUNNING);
                        timer.start();
                        continue;
                    }
                    if (choice.equals("F")) {
                        System.out.println("Enter coordinates:");
                        x = input.nextInt();
                        y = input.nextInt();
                        map.mark(x, y);
                    }
                    if (choice.equals("R")) {
                        System.out.println("Enter coordinates:");
                        x = input.nextInt();
                        y = input.nextInt();
                        map.reveal(x, y, game);
                    }
                    game.computeScore();
                    players.saveGame();
                }
            }
            timer.stop();
            if (game.getStatus() != GameStatus.WAITING) {
                map.print();
                System.out.println("You " + game.getStatus() + "!");
            } else System.out.println("Game saved.");
            players.saveGame();
        }
    }

    private static Player playerSelect(ArrayList<Player> players) {
        if (players == null) throw new NullPointerException("Invalid creation or read of file");
        if (players.isEmpty()) {
            System.out.println("No players created");
            return null;
        }
        System.out.println("Select your player:\n");
        System.out.println("id\t" + "name\t" + "level");
        Scanner input = new Scanner(System.in);
        int i = 0;
        System.out.println(i++ + ".\tcreate new player");
        for (Player player : players) {
            System.out.println(i++ + ".\t" + player.getName() + "\t" + player.getLevel());
        }
        i = input.nextInt();
        if (i == 0) return null;
        return players.get(i - 1);
    }

    private static void playerCreate(Players players) {
        System.out.println("Chose a name for your player : ");
        Scanner input = new Scanner(System.in);
        Player player = new Player((input.nextLine()));
        players.addPlayer(player);
    }

    private static Game gameSelect(ArrayList<Game> games) {
        if (games == null) throw new NullPointerException("Invalid creation or read of file");
        if (games.isEmpty()) {
            System.out.println("No games created for this player");
            return null;
        }
        System.out.println("Select your game:\n");
        System.out.println("id\t" + "name\t" + "date\t" + "difficulty\t" + "score");
        Scanner input = new Scanner(System.in);
        int i = 0;
        System.out.println(i++ + ".\tcreate new game");
        for (Game game : games) {
            System.out.println(i++ + ".\t" + game.getName() + "\t" + game.getDate() + "\t" + game.getDifficulty() + "\t" + game.getScore());

        }
        i = input.nextInt();
        if (i == 0) return null;
        return games.get(i - 1);
    }

    private static void gameCreate(Players players, Player player) {
        System.out.println("Chose a name for the game: ");
        Scanner input = new Scanner(System.in);
        String name = input.nextLine();
        int i = 0;
        for (GameDifficulty difficulty : GameDifficulty.values()) {
            System.out.println(++i + ".\t" + difficulty);
        }
        GameDifficulty difficulty = GameDifficulty.values()[input.nextInt() - 1];
        player.createGame(name, difficulty);
    }
}
