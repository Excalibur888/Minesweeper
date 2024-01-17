import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;

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
        VBox selectPlayer = selectPlayerMenu(primaryStage, players);

        Scene scene = new Scene(selectPlayer, 400, 400);
        primaryStage.setScene(scene);

        primaryStage.show();

    }

    public static void main(String[] args) {
        launch(args);
        /*
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

    private VBox selectPlayerMenu(Stage primaryStage, Players players) {
        VBox playerSelect = new VBox(10);
        playerSelect.setPadding(new Insets(10));

        ArrayList<Player> playersList = players.getPlayers();

        if (playersList == null) throw new NullPointerException("Invalid creation or read of file");
        if (playersList.isEmpty()) return addPlayerMenu(primaryStage, players);


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

        Text selectPlayerText = new Text("Select your player :");
        selectPlayerText.setFont(new Font(20));

        playerSelect.getChildren().addAll(addPlayerButton, removePlayerButton, selectPlayerText);
        //System.out.println("id\t" + "name\t" + "level");
        for (Player player : playersList) {
            Button playerButton = new Button(player.getName() + "\t" + player.getLevel());
            playerButton.setOnAction(e -> {
                VBox selectGame = selectGameMenu(primaryStage, players, player);
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
            VBox selectPlayer = selectPlayerMenu(primaryStage, players);
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
            VBox selectPlayer = selectPlayerMenu(primaryStage, players);
            Scene selectPlayerScene = new Scene(selectPlayer, 400, 400);
            primaryStage.setScene(selectPlayerScene);
        });

        playerRemove.getChildren().addAll(removePlayersText, removePlayerField, oldPlayerButton);
        return playerRemove;
    }

    private VBox selectGameMenu(Stage primaryStage, Players players, Player player) {
        VBox gameSelect = new VBox(10);
        gameSelect.setPadding(new Insets(10));

        ArrayList<Game> gamesList = player.getGames();

        if (gamesList == null) throw new NullPointerException("Invalid creation or read of file");
        if (gamesList.isEmpty()) return addGameMenu(primaryStage, players, player);

        Button changePlayerButton = new Button("Change player");
        changePlayerButton.setOnAction(e -> {
            VBox selectPlayer = selectPlayerMenu(primaryStage, players);
            Scene selectPlayerScene = new Scene(selectPlayer, 400, 400);
            primaryStage.setScene(selectPlayerScene);
        });
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

        Text selectGameText = new Text("Select your Game :");
        selectGameText.setFont(new Font(20));

        gameSelect.getChildren().addAll(changePlayerButton, addGameButton, removeGameButton, selectGameText);
        //System.out.println("id\t" + "name\t" + "level");
        for (Game game : gamesList) {
            Button gameButton = new Button(game.getName() + "\t" + game.getDate() + "\t" + game.getDifficulty() + "\t" + game.getScore());
            gameButton.setOnAction(e -> {
                showGame(primaryStage, players, game);
            });
            gameSelect.getChildren().add(gameButton);
        }
        return gameSelect;
    }

    private VBox addGameMenu(Stage primaryStage, Players players, Player player) {
        VBox gameAdd = new VBox(10);
        gameAdd.setPadding(new Insets(10));

        Button changePlayerButton = new Button("Change player");
        changePlayerButton.setOnAction(e -> {
            VBox selectPlayer = selectPlayerMenu(primaryStage, players);
            Scene selectPlayerScene = new Scene(selectPlayer, 400, 400);
            primaryStage.setScene(selectPlayerScene);
        });

        Text addGameText = new Text("Enter new game name :");
        addGameText.setFont(new Font(20));

        TextField newGameField = new TextField();

        Button newGameButton = new Button("Create new game");

        newGameButton.setOnAction(e -> {
            VBox selectDifficulty = difficultySelectMenu(primaryStage, players, player, newGameField.getText());
            Scene selectDifficultyScene = new Scene(selectDifficulty, 400, 400);
            primaryStage.setScene(selectDifficultyScene);
        });

        gameAdd.getChildren().addAll(changePlayerButton, addGameText, newGameField, newGameButton);
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
            VBox selectGame = selectGameMenu(primaryStage, players, player);
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

        Button easyButton = new Button("Easy");
        easyButton.setOnAction(e -> {
            player.addGame(new Game(name, GameDifficulty.EASY));
            players.saveGame();
            VBox selectGame = selectGameMenu(primaryStage, players, player);
            Scene selectGameScene = new Scene(selectGame, 400, 400);
            primaryStage.setScene(selectGameScene);
        });

        Button nomalButton = new Button("Normal");
        nomalButton.setOnAction(e -> {
            player.addGame(new Game(name, GameDifficulty.NORMAL));
            players.saveGame();
            VBox selectGame = selectGameMenu(primaryStage, players, player);
            Scene selectGameScene = new Scene(selectGame, 400, 400);
            primaryStage.setScene(selectGameScene);
        });

        Button hardButton = new Button("Hard");
        hardButton.setOnAction(e -> {
            player.addGame(new Game(name, GameDifficulty.HARD));
            players.saveGame();
            VBox selectGame = selectGameMenu(primaryStage, players, player);
            Scene selectGameScene = new Scene(selectGame, 400, 400);
            primaryStage.setScene(selectGameScene);
        });


        Button personalisedButton = new Button("Personalised");
        TextField sizeField = new TextField();
        sizeField.setPromptText("Grid size");
        TextField mineCountField = new TextField();
        mineCountField.setPromptText("Number of mines");
        personalisedButton.setOnAction(e -> {
            String size = sizeField.getText();
            String mineCount = mineCountField.getText();
            if (!size.isEmpty() && isInteger(size) && !mineCount.isEmpty() && isInteger(mineCount) && Integer.parseInt(mineCount) <= (Integer.parseInt(size) * Integer.parseInt(size))) {
                player.addGame(new Game(name, Integer.parseInt(size), Integer.parseInt(mineCount)));
                players.saveGame();
                VBox selectGame = selectGameMenu(primaryStage, players, player);
                Scene selectGameScene = new Scene(selectGame, 400, 400);
                primaryStage.setScene(selectGameScene);
            }
        });

        difficultySelect.getChildren().addAll(easyButton, nomalButton, hardButton, personalisedButton, sizeField, mineCountField);

        return difficultySelect;
    }

    public void showGame(Stage primaryStage, Players players, Game game) {
        VBox gameBoard = new VBox(10);
        gameBoard.setPadding(new Insets(10));

        Map map = game.getMap();
        Timer timer = game.getTimer();
        if (game.getStatus() == GameStatus.WAITING) {
            game.setStatus(GameStatus.RUNNING);
            timer.start();
            GridPane grid = new GridPane(1, 1);
            updateGrid(grid, map);

            Button pauseButton = new Button("Pause");
            pauseButton.setOnAction(e -> {
                VBox selectPlayer = selectPlayerMenu(primaryStage, players);
                Scene selectPlayerScene = new Scene(selectPlayer, 400, 400);
                primaryStage.setScene(selectPlayerScene);
            });

            gameBoard.getChildren().addAll(pauseButton, grid);
            Scene scene = new Scene(gameBoard, 400, 400);
            primaryStage.setScene(scene);
            primaryStage.show();

            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.seconds(1), event -> {
                        game.computeScore();
                        players.saveGame();
                        updateGrid(grid, map);
                    })
            );
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();

        }

        timer.stop();
        if (game.getStatus() != GameStatus.WAITING) {
            map.print();
            System.out.println("You " + game.getStatus() + "!");
        } else System.out.println("Game saved.");
        players.saveGame();

        Scene gameScene = new Scene(gameBoard, 400, 400);
        primaryStage.setScene(gameScene);
    }

    public void updateGrid(GridPane grid, Map map) {
        for (int i = 0; i < map.getHeight(); i++) {
            for (int j = 0; j < map.getWidth(); j++) {
                Box curBox = map.getBoxes(i, j);
                Button box = new Button("\uD83D\uDFE9");
                box.setPrefSize(2, 2);
                if (curBox.isRevealed()) {
                    if (curBox.getType() == BoxType.INDICATION) {
                        box = new Button(Integer.toString(curBox.getAdjacentMineCount()));
                    } else if (curBox.getType() == BoxType.MINE) {
                        box = new Button("\uD83D\uDCA3");
                    } else {
                        box = new Button("\uD83D\uDFEB");
                    }
                } else if (curBox.isMarked()) {
                    box = new Button("\uD83D\uDEA9");
                }
                grid.add(box, i, j);
            }
        }
    }

    private boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

}