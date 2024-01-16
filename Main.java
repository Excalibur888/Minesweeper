import java.util.ArrayList;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        Players players = new Players("players.json");
        while (true) {
            Player player = playerSelect(players.getPlayers());
            if (player != null) System.out.println("Player selected : " + player.getName());
            else {
                player = playerCreate(players);
                players.saveToJsonFile();
                System.out.println("Player selected : " + player.getName());
            }
            Game game = gameSelect(player.getGames());
            if (game != null) System.out.println("Game selected : " + game.getName());
            else {
                game = gameCreate(players, player);
                players.saveToJsonFile();
                System.out.println("Game selected : " + game.getName());
            }
            Map map = game.getMap();
            game.setStatus(GameStatus.RUNNING);
            while (game.getStatus() == GameStatus.RUNNING) {
                map.print();
                System.out.println("Enter coordinates (or -1 to exit):");
                Scanner input = new Scanner(System.in);
                int x = input.nextInt(), y = input.nextInt();
                if (x < 0 || y < 0){
                    game.setStatus(GameStatus.WAITING);
                    break;
                }
                map.reveal(x, y, game);
                players.saveToJsonFile();
            }
        }
    }

    private static Player playerSelect(ArrayList<Player> players) {
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

    private static Player playerCreate(Players players) {
        System.out.println("Chose a name for your player : ");
        Scanner input = new Scanner(System.in);
        Player player = new Player((input.nextLine()));
        players.addPlayer(player);
        return player;
    }

    private static Game gameSelect(ArrayList<Game> games) {
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

    private static Game gameCreate(Players players, Player player) {
        System.out.println("Chose a name for the game: ");
        Scanner input = new Scanner(System.in);
        String name = input.nextLine();
        int i = 0;
        for (GameDifficulty difficulty : GameDifficulty.values()) {
            System.out.println(++i + ".\t" + difficulty);
        }
        GameDifficulty difficulty = GameDifficulty.values()[input.nextInt() - 1];
        return player.createGame(name, difficulty);
    }
}
