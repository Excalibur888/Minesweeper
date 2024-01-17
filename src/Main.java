import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Scanner;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Minesweeper");

        Players players = new Players("players.json");
        ArrayList<Player> playersList = players.getPlayers();
        if (playersList == null) throw new NullPointerException("Invalid creation or read of file");
        if (playersList.isEmpty()) {
            VBox addPlayer = addPlayerMenu(primaryStage, players);
            Scene scene = new Scene(addPlayer, 400, 400);
            primaryStage.setScene(scene);
            primaryStage.show();
        }
        VBox selectPlayer = playerSelectMenu(primaryStage, players);

        Scene scene = new Scene(selectPlayer, 400, 400);
        primaryStage.setScene(scene);

        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
        /*Players players = new Players("players.json");
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
        }*/
    }

    private VBox playerSelectMenu(Stage primaryStage, Players players) {
        VBox playerSelect = new VBox(10);
        playerSelect.setPadding(new Insets(10));

        ArrayList<Player> playersList = players.getPlayers();

        if (playersList == null) throw new NullPointerException("Invalid creation or read of file");
        if (playersList.isEmpty()) return addPlayerMenu(primaryStage, players);

        Text selectPlayer = new Text("Select your player :");
        selectPlayer.setFont(new Font(20));
        playerSelect.getChildren().add(selectPlayer);
        Button addPlayerButton = new Button("Add Player");
        addPlayerButton.setOnAction(e -> {
            VBox addPlayer = addPlayerMenu(primaryStage, players);
            Scene addPlayerScene = new Scene(addPlayer, 400, 400);
            primaryStage.setScene(addPlayerScene);
        });
        Button removePlayerButton = new Button("Remove Player");
        removePlayerButton.setOnAction(e -> {
            VBox removePlayer = removePlayerMenu(primaryStage, players);
            Scene removePlayerScene = new Scene(removePlayer, 400, 400);
            primaryStage.setScene(removePlayerScene);
        });
        playerSelect.getChildren().addAll(addPlayerButton, removePlayerButton);
        //System.out.println("id\t" + "name\t" + "level");
        for (Player player : playersList) {
            Button playerButton = new Button(player.getName() + "\t" + player.getLevel());
            playerButton.setOnAction(e -> {
                VBox selectGame = gameSelectMenu(primaryStage, players, player);
                Scene selectGameScene = new Scene(selectGame, 400, 400);
                primaryStage.setScene(selectGameScene);
            });
            playerSelect.getChildren().add(playerButton);
        }
        return playerSelect;
    }

    private VBox addPlayerMenu(Stage primaryStage, Players players) {
        VBox playerAdd = new VBox(10);
        playerAdd.setPadding(new Insets(10));

        Text addPlayerText = new Text("Enter new player name :");
        addPlayerText.setFont(new Font(20));

        TextField newPlayerField = new TextField();

        Button newPlayerButton = new Button("Create new player");
        newPlayerButton.setOnAction(e -> {
            players.addPlayer(new Player(newPlayerField.getText()));
            players.saveGame();
            VBox selectPlayer = playerSelectMenu(primaryStage, players);
            Scene selectPlayerScene = new Scene(selectPlayer, 400, 400);
            primaryStage.setScene(selectPlayerScene);
        });

        playerAdd.getChildren().addAll(addPlayerText, newPlayerField, newPlayerButton);
        return playerAdd;
    }

    private VBox removePlayerMenu(Stage primaryStage, Players players) {
        VBox playerRemove = new VBox(10);
        playerRemove.setPadding(new Insets(10));

        Text removePlayersText = new Text("Which player do you want to remove ?");
        removePlayersText.setFont(new Font(20));

        TextField removePlayerField = new TextField();
        removePlayerField.setPromptText("Enter new player name");

        Button oldPlayerButton = new Button("Remove player");
        oldPlayerButton.setOnAction(e -> {
            players.removePlayer(removePlayerField.getText());
            players.saveGame();
            VBox selectPlayer = playerSelectMenu(primaryStage, players);
            Scene selectPlayerScene = new Scene(selectPlayer, 400, 400);
            primaryStage.setScene(selectPlayerScene);
        });

        playerRemove.getChildren().addAll(removePlayersText, removePlayerField, oldPlayerButton);
        return playerRemove;
    }

    private VBox gameSelectMenu(Stage primaryStage, Players players, Player player) {
        VBox gameSelect = new VBox(10);
        gameSelect.setPadding(new Insets(10));

        ArrayList<Game> gamesList = player.getGames();

        if (gamesList == null) throw new NullPointerException("Invalid creation or read of file");
        if (gamesList.isEmpty()) return addGameMenu(primaryStage, players, player);

        Text selectGame = new Text("Select your Game :");
        selectGame.setFont(new Font(20));
        gameSelect.getChildren().add(selectGame);
        Button addGameButton = new Button("Add Game");
        addGameButton.setOnAction(e -> {
            VBox addGame = addGameMenu(primaryStage, players, player);
            Scene addGameScene = new Scene(addGame, 400, 400);
            primaryStage.setScene(addGameScene);
        });
        Button removeGameButton = new Button("Remove Game");
        removeGameButton.setOnAction(e -> {
            VBox removeGame = removeGameMenu(primaryStage, players, player);
            Scene removeGameScene = new Scene(removeGame, 400, 400);
            primaryStage.setScene(removeGameScene);
        });
        gameSelect.getChildren().addAll(addGameButton, removeGameButton);
        //System.out.println("id\t" + "name\t" + "level");
        for (Game game : gamesList) {
            Button gameButton = new Button(game.getName() + "\t" + game.getDate() + "\t" + game.getDifficulty() + "\t" + game.getScore());
            gameButton.setOnAction(e -> {
                //showGame(primaryStage);
            });
            gameSelect.getChildren().add(gameButton);
        }
        return gameSelect;
    }

    private VBox addGameMenu(Stage primaryStage, Players players, Player player) {
        VBox gameAdd = new VBox(10);
        gameAdd.setPadding(new Insets(10));

        Text addGameText = new Text("Enter new game name :");
        addGameText.setFont(new Font(20));

        TextField newGameField = new TextField();

        Button newGameButton = new Button("Create new game");

        newGameButton.setOnAction(e -> {
            VBox selectDifficulty = difficultySelectMenu(primaryStage, players, player, newGameField.getText());
            Scene selectDifficultyScene = new Scene(selectDifficulty, 400, 400);
            primaryStage.setScene(selectDifficultyScene);
        });

        gameAdd.getChildren().addAll(addGameText, newGameField, newGameButton);
        return gameAdd;
    }

    private VBox removeGameMenu(Stage primaryStage, Players players, Player player) {
        VBox gameRemove = new VBox(10);
        gameRemove.setPadding(new Insets(10));

        Text removeGameText = new Text("Which game do you want to remove ?");
        removeGameText.setFont(new Font(20));

        TextField removeGameField = new TextField();
        removeGameField.setPromptText("Enter new game name");

        Button oldGameButton = new Button("Remove game");
        oldGameButton.setOnAction(e -> {
            player.removeGame(removeGameField.getText());
            players.saveGame();
            VBox selectGame = gameSelectMenu(primaryStage, players, player);
            Scene selectGameScene = new Scene(selectGame, 400, 400);
            primaryStage.setScene(selectGameScene);
        });

        gameRemove.getChildren().addAll(removeGameText, removeGameField, oldGameButton);
        return gameRemove;
    }

    private VBox difficultySelectMenu(Stage primaryStage, Players players, Player player, String name) {
        VBox difficultySelect = new VBox(10);
        difficultySelect.setPadding(new Insets(10));

        Text addGameText = new Text("Set game difficulty :");
        addGameText.setFont(new Font(20));
        difficultySelect.getChildren().add(addGameText);
        for (GameDifficulty difficulty : GameDifficulty.values()) {
            Button gameButton = new Button(difficulty.toString());
            gameButton.setOnAction(e -> {
                player.createGame(name, difficulty);
                players.saveGame();
                VBox selectGame = gameSelectMenu(primaryStage, players, player);
                Scene selectGameScene = new Scene(selectGame, 400, 400);
                primaryStage.setScene(selectGameScene);
            });
            difficultySelect.getChildren().add(gameButton);
        }
        return difficultySelect;
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
