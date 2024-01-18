import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Locale;

import static javafx.css.SizeUnits.MS;

public class Main extends Application {
    private Stage primaryStage;
    private Player player = null;
    private Game game = null;
    private final String defaultTextStyle = "-fx-font: bold 20px \"Comic Sans MS\"; -fx-fill: white; -fx-effect: dropshadow(gaussian, saddlebrown, 10, 0.8, 0, 0)";
    private final String defaultButtonStyle = "-fx-background-color: saddlebrown; -fx-text-fill: white; -fx-min-width: 100;";
    private final String defaultBoxStyle = "-fx-background-image: url('./assets/background.png'); -fx-background-position: center center; -fx-background-size: cover; -fx-alignment: center;";

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Minesweeper");

        Players players = new Players("players.json");
        ArrayList<Player> playersList = players.getPlayers();

        this.primaryStage.setScene(selectPlayerMenu(players));
        this.primaryStage.show();
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

    private Scene selectPlayerMenu(Players players) {
        VBox selectPlayer = new VBox(10);
        selectPlayer.setPadding(new Insets(10));

        ArrayList<Player> playersList = players.getPlayers();

        if (playersList == null) throw new NullPointerException("Invalid creation or read of file");
        if (playersList.isEmpty()) {
            addPlayerMenu(selectPlayer, players);
            return new Scene(selectPlayer, 800, 800);
        }

        Button addPlayerButton = new Button("Add Player");
        addPlayerButton.setStyle(this.defaultButtonStyle);
        addPlayerButton.setOnAction(e -> addPlayerMenu(selectPlayer, players));

        Button removePlayerButton = new Button("Remove Player");
        removePlayerButton.setStyle(this.defaultButtonStyle);
        removePlayerButton.setOnAction(e -> removePlayerMenu(selectPlayer, players));

        HBox modifyPlayers = new HBox(10);
        modifyPlayers.setStyle("-fx-alignment: center");
        modifyPlayers.getChildren().setAll(addPlayerButton, removePlayerButton);

        Text selectPlayerText = new Text("Select your player :");
        selectPlayerText.setStyle(this.defaultTextStyle);

        GridPane playersGrid = new GridPane();
        playersGrid.setHgap(30);
        playersGrid.setVgap(10);
        playersGrid.setMinWidth(230);
        playersGrid.setMaxWidth(230);
        Label nameLabel = new Label("Name: ");
        nameLabel.setMinWidth(100);
        nameLabel.setMaxWidth(100);
        nameLabel.setAlignment(Pos.CENTER);
        Label levelLabel = new Label("Level: ");
        levelLabel.setMinWidth(100);
        levelLabel.setMaxWidth(100);
        levelLabel.setAlignment(Pos.CENTER);
        playersGrid.add(nameLabel, 0, 0);
        playersGrid.add(levelLabel, 1, 0);
        playersGrid.setAlignment(Pos.CENTER);
        selectPlayer.getChildren().setAll(modifyPlayers, selectPlayerText, playersGrid);

        for (Player player : playersList) {
            Label playerNameLabel = new Label(player.getName());
            playerNameLabel.setMinWidth(100);
            playerNameLabel.setMaxWidth(100);
            playerNameLabel.setTextFill(Color.WHITE);
            playerNameLabel.setAlignment(Pos.CENTER);
            Label playerLevelLabel = new Label(Integer.toString(player.getLevel()));
            playerLevelLabel.setMinWidth(100);
            playerLevelLabel.setMaxWidth(100);
            playerLevelLabel.setTextFill(Color.WHITE);
            playerLevelLabel.setAlignment(Pos.CENTER);
            HBox playersLabels = new HBox(30);
            playersLabels.getChildren().setAll(playerNameLabel, playerLevelLabel);
            playersLabels.setMinWidth(230);
            playersLabels.setMaxWidth(230);
            playersLabels.setAlignment(Pos.CENTER);
            Button playerButton = new Button();
            playerButton.setGraphic(playersLabels);
            playerButton.setStyle(this.defaultButtonStyle);
            playerButton.minWidth(230);
            playerButton.maxWidth(230);
            playerButton.setOnAction(e -> {
                this.player = player;
                this.primaryStage.setScene(selectGameMenu(players));
            });
            playersGrid.add(playerButton, 0, playersGrid.getRowCount(), 2, 1);
        }
        selectPlayer.setStyle(this.defaultBoxStyle);
        return new Scene(selectPlayer, 800, 800);
    }

    private void addPlayerMenu(VBox addPlayer, Players players) {
        Text addPlayerText = new Text("Enter new player name :");
        addPlayerText.setStyle(this.defaultTextStyle);


        TextField newPlayerField = new TextField();

        Button newPlayerButton = new Button("Create new player");
        newPlayerButton.setStyle(this.defaultButtonStyle);

        addPlayer.getChildren().setAll(addPlayerText, newPlayerField, newPlayerButton);

        newPlayerButton.setOnAction(e -> {
            players.addPlayer(new Player(newPlayerField.getText()));
            players.saveGame();
            this.primaryStage.setScene(selectPlayerMenu(players));
        });
        addPlayer.setStyle(this.defaultBoxStyle);
    }

    private void removePlayerMenu(VBox removePlayer, Players players) {
        Text removePlayersText = new Text("Which player do you want to remove ?");
        removePlayersText.setStyle(this.defaultTextStyle);

        TextField removePlayerField = new TextField();
        removePlayerField.setPromptText("Enter new player name");


        Button oldPlayerButton = new Button("Remove player");
        oldPlayerButton.setStyle(this.defaultButtonStyle);

        removePlayer.getChildren().setAll(removePlayersText, removePlayerField, oldPlayerButton);

        oldPlayerButton.setOnAction(e -> {
            players.removePlayer(removePlayerField.getText());
            players.saveGame();
            this.primaryStage.setScene(selectPlayerMenu(players));
        });
        removePlayer.setStyle(this.defaultBoxStyle);
    }

    private Scene selectGameMenu(Players players) {
        VBox selectGame = new VBox(10);
        selectGame.setPadding(new Insets(10));

        ArrayList<Game> gamesList = player.getGames();

        if (gamesList == null) throw new NullPointerException("Invalid creation or read of file");
        if (gamesList.isEmpty()) {
            addGameMenu(selectGame, players);
            return new Scene(selectGame, 800, 800);
        }

        Button changePlayerButton = new Button("Change player");
        changePlayerButton.setStyle(this.defaultButtonStyle);
        changePlayerButton.setOnAction(e -> {
            this.player = null;
            primaryStage.setScene(selectPlayerMenu(players));
        });

        Button addGameButton = new Button("Add Game");
        addGameButton.setStyle(this.defaultButtonStyle);
        addGameButton.setOnAction(e -> addGameMenu(selectGame, players));

        Button removeGameButton = new Button("Remove Game");
        removeGameButton.setStyle(this.defaultButtonStyle);
        removeGameButton.setOnAction(e -> removeGameMenu(selectGame, players));

        HBox modifyGames = new HBox(10);
        modifyGames.setStyle("-fx-alignment: center");
        modifyGames.getChildren().setAll(changePlayerButton, addGameButton, removeGameButton);

        Text selectGameText = new Text("Select your Game :");
        selectGameText.setStyle(this.defaultTextStyle);

        selectGame.getChildren().setAll(modifyGames, selectGameText);

        GridPane gamesGrid = new GridPane();
        gamesGrid.setHgap(30);
        gamesGrid.setVgap(10);
        gamesGrid.setMinWidth(360);
        gamesGrid.setMaxWidth(360);
        Label nameLabel = new Label("Name: ");
        nameLabel.setMinWidth(100);
        nameLabel.setMaxWidth(100);
        nameLabel.setAlignment(Pos.CENTER);
        Label dateLabel = new Label("Date: ");
        dateLabel.setMinWidth(100);
        dateLabel.setMaxWidth(100);
        dateLabel.setAlignment(Pos.CENTER);
        Label scoreLabel = new Label("Score: ");
        scoreLabel.setMinWidth(100);
        scoreLabel.setMaxWidth(100);
        scoreLabel.setAlignment(Pos.CENTER);
        gamesGrid.add(nameLabel, 0, 0);
        gamesGrid.add(dateLabel, 1, 0);
        gamesGrid.add(scoreLabel, 2, 0);
        gamesGrid.setAlignment(Pos.CENTER);
        selectGame.getChildren().setAll(modifyGames, selectGameText, gamesGrid);

        for (Game game : gamesList) {
            Label gameNameLabel = new Label(game.getName());
            gameNameLabel.setMinWidth(100);
            gameNameLabel.setMaxWidth(100);
            gameNameLabel.setTextFill(Color.WHITE);
            gameNameLabel.setAlignment(Pos.CENTER);
            Label gameDateLabel = new Label(game.getDate().toString());
            gameDateLabel.setMinWidth(100);
            gameDateLabel.setMaxWidth(100);
            gameDateLabel.setTextFill(Color.WHITE);
            gameDateLabel.setAlignment(Pos.CENTER);
            Label gameScoreLabel = new Label(game.getDate().toString());
            gameScoreLabel.setMinWidth(100);
            gameScoreLabel.setMaxWidth(100);
            gameScoreLabel.setTextFill(Color.WHITE);
            gameScoreLabel.setAlignment(Pos.CENTER);
            HBox gamesLabels = new HBox(30);
            gamesLabels.getChildren().setAll(gameNameLabel, gameDateLabel, gameScoreLabel);
            gamesLabels.setMinWidth(360);
            gamesLabels.setMaxWidth(360);
            gamesLabels.setAlignment(Pos.CENTER);
            Button gameButton = new Button();
            gameButton.setGraphic(gamesLabels);
            gameButton.setStyle(this.defaultButtonStyle);
            gameButton.minWidth(360);
            gameButton.maxWidth(360);
            gameButton.setOnAction(e -> {
                this.game = game;
                this.primaryStage.setScene(showGame(players));
            });
            gamesGrid.add(gameButton, 0, gamesGrid.getRowCount(), 3, 1);
        }
        selectGame.setStyle(this.defaultBoxStyle);
        return new Scene(selectGame, 800, 800);
    }

    private void addGameMenu(VBox addGame, Players players) {
        Button changePlayerButton = new Button("Change player");
        changePlayerButton.setStyle(this.defaultButtonStyle);
        changePlayerButton.setOnAction(e -> {
            this.player = null;
            primaryStage.setScene(selectPlayerMenu(players));
        });

        Text addGameText = new Text("Enter new game name :");
        addGameText.setStyle(this.defaultTextStyle);

        TextField newGameField = new TextField();

        Button newGameButton = new Button("Create new game");
        newGameButton.setStyle(this.defaultButtonStyle);

        newGameButton.setOnAction(e -> selectDifficultyMenu(addGame, players, newGameField.getText()));
        addGame.getChildren().setAll(changePlayerButton, addGameText, newGameField, newGameButton);
        addGame.setStyle(this.defaultBoxStyle);

    }

    private void removeGameMenu(VBox removeGame, Players players) {
        Text removeGameText = new Text("Which game do you want to remove ?");
        removeGameText.setStyle(this.defaultTextStyle);

        TextField removeGameField = new TextField();
        removeGameField.setPromptText("Enter new game name");

        Button oldGameButton = new Button("Remove game");
        oldGameButton.setStyle(this.defaultButtonStyle);
        oldGameButton.setOnAction(e -> {
            player.removeGame(removeGameField.getText());
            players.saveGame();
            this.primaryStage.setScene(selectGameMenu(players));
        });

        removeGame.getChildren().setAll(removeGameText, removeGameField, oldGameButton);
        removeGame.setStyle(this.defaultBoxStyle);
    }

    private void selectDifficultyMenu(VBox selectDifficulty, Players players, String name) {
        Text selectDifficultyText = new Text("Set game difficulty :");
        selectDifficultyText.setStyle(this.defaultTextStyle);

        Button easyButton = new Button("Easy");
        easyButton.setStyle(this.defaultButtonStyle);
        easyButton.setOnAction(e -> {
            player.addGame(new Game(name, GameDifficulty.EASY));
            players.saveGame();
            primaryStage.setScene(selectGameMenu(players));
        });

        Button nomalButton = new Button("Normal");
        nomalButton.setStyle(this.defaultButtonStyle);
        nomalButton.setOnAction(e -> {
            player.addGame(new Game(name, GameDifficulty.NORMAL));
            players.saveGame();
            primaryStage.setScene(selectGameMenu(players));
        });

        Button hardButton = new Button("Hard");
        hardButton.setStyle(this.defaultButtonStyle);
        hardButton.setOnAction(e -> {
            player.addGame(new Game(name, GameDifficulty.HARD));
            players.saveGame();
            primaryStage.setScene(selectGameMenu(players));
        });


        Button personalisedButton = new Button("Personalised");
        personalisedButton.setStyle(this.defaultButtonStyle);
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
                primaryStage.setScene(selectGameMenu(players));
            }
        });

        selectDifficulty.getChildren().setAll(selectDifficultyText, easyButton, nomalButton, hardButton, personalisedButton, sizeField, mineCountField);
        selectDifficulty.setStyle(this.defaultBoxStyle);
    }

    public Scene showGame(Players players) {
        VBox showGame = new VBox(10);
        showGame.setPadding(new Insets(10));
        Timer timer = this.game.getTimer();
        Button pauseButton = new Button("Pause");
        pauseButton.setStyle(this.defaultButtonStyle);
        pauseButton.setOnAction(e -> {
            timer.stop();
            this.game.setStatus(GameStatus.WAITING);
            this.primaryStage.setScene(selectGameMenu(players));
        });

        GridPane grid = new GridPane();
        grid.setStyle("-fx-alignment: center");


        if (this.game.getStatus() == GameStatus.WAITING) {
            this.game.setStatus(GameStatus.RUNNING);
            timer.start();
            updateGrid(players, grid);

            showGame.getChildren().addAll(pauseButton, grid);

            game.computeScore();
            players.saveGame();
        }
        timer.stop();

        if (game.getStatus() != GameStatus.WAITING) {
            Button exitButton = new Button("Exit");
            exitButton.setStyle(this.defaultButtonStyle);
            exitButton.setOnAction(e -> {
                this.primaryStage.setScene(selectGameMenu(players));
            });
            Text endStateText = new Text("You " + game.getStatus() + "!");
            endStateText.setStyle(this.defaultTextStyle);
            updateGrid(players, grid);
            showGame.getChildren().setAll(exitButton, endStateText, grid);
        } else System.out.println("Game saved.");
        players.saveGame();
        showGame.setStyle(this.defaultBoxStyle);
        return new Scene(showGame, 800, 800);
    }

    public void updateGrid(Players players, GridPane grid) {
        Map map = this.game.getMap();
        for (int i = 0; i < map.getHeight(); i++) {
            for (int j = 0; j < map.getWidth(); j++) {
                curBox curBox = new curBox(map.getBoxes(i, j), i, j);
                Button box = new Button();
                box.setAlignment(Pos.CENTER);
                box.setStyle("-fx-background-color: #21d111; -fx-border-width: 4px; -fx-border-color: #128508;");
                if (curBox.box.isRevealed()) {
                    if (curBox.box.getType() == BoxType.INDICATION) {
                        box = new Button(Integer.toString(curBox.box.getAdjacentMineCount()));
                        box.setStyle("-fx-background-color: #9c5a00; -fx-border-width: 4px; -fx-border-color: #7c4801; -fx-text-fill: white;");
                    } else if (curBox.box.getType() == BoxType.MINE) {
                        box = new Button("\uD83D\uDCA3");
                        box.setStyle("-fx-background-color: #9c5a00; -fx-border-width: 4px; -fx-border-color: #7c4801;");
                    } else {
                        box = new Button();
                        box.setStyle("-fx-background-color: #9c5a00; -fx-border-width: 4px; -fx-border-color: #7c4801;");
                    }
                } else if (curBox.box.isMarked()) {
                    box = new Button("\uD83D\uDEA9");
                    box.setStyle("-fx-background-color: #21d111; -fx-border-width: 4px; -fx-border-color: #128508;");
                }
                box.setPadding(new Insets(0));
                box.setMinSize(30, 30);
                box.setMaxSize(30, 30);
                box.setOnMouseClicked(e -> {
                    MouseButton button = e.getButton();
                    if (button == MouseButton.PRIMARY) {
                        map.reveal(curBox.x, curBox.y, this.game);
                        players.saveGame();
                        if (game.getStatus() == GameStatus.RUNNING) updateGrid(players, grid);
                        else this.primaryStage.setScene(showGame(players));
                    } else if (button == MouseButton.SECONDARY) {
                        map.mark(curBox.x, curBox.y);
                        updateGrid(players, grid);
                    }

                    players.saveGame();
                    updateGrid(players, grid);
                });
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