import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {
    private Stage primaryStage;
    private Player player = null;
    private Game game = null;

    private Background background;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("Minesweeper");

        Image image = new Image("https://www.google.com/url?sa=i&url=https%3A%2F%2Fstock.adobe.com%2Fsearch%3Fk%3Dforest&psig=AOvVaw2WYInXhC2f-J_UngShsyUf&ust=1705682466742000&source=images&cd=vfe&ved=0CBIQjRxqFwoTCODey8Kw54MDFQAAAAAdAAAAABAE");
        BackgroundImage backgroundImage = new BackgroundImage(
                image,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT);

        this.background = new Background(backgroundImage);

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
            selectPlayer.getChildren().setAll(addPlayerMenu(players));
            return new Scene(selectPlayer, 400, 400);
        }

        Button addPlayerButton = new Button("Add Player");
        addPlayerButton.setOnAction(e -> selectPlayer.getChildren().setAll(addPlayerMenu(players)));

        Button removePlayerButton = new Button("Remove Player");
        removePlayerButton.setOnAction(e -> selectPlayer.getChildren().setAll(removePlayerMenu(players)));

        Text selectPlayerText = new Text("Select your player :");
        selectPlayerText.setFont(new Font(20));

        selectPlayer.getChildren().addAll(addPlayerButton, removePlayerButton, selectPlayerText);
        //System.out.println("id\t" + "name\t" + "level");
        for (Player player : playersList) {
            Button playerButton = new Button(player.getName() + "\t" + player.getLevel());
            playerButton.setOnAction(e -> {
                this.player = player;
                this.primaryStage.setScene(selectGameMenu(players));
            });
            selectPlayer.getChildren().add(playerButton);
        }
        return new Scene(selectPlayer, 400, 400);
    }

    private VBox addPlayerMenu(Players players) {
        VBox addPlayer = new VBox(10);
        addPlayer.setPadding(new Insets(10));

        Text addPlayerText = new Text("Enter new player name :");
        addPlayerText.setFont(new Font(20));

        TextField newPlayerField = new TextField();

        Button newPlayerButton = new Button("Create new player");
        newPlayerButton.setOnAction(e -> {
            players.addPlayer(new Player(newPlayerField.getText()));
            players.saveGame();
            addPlayer.getChildren().setAll(selectPlayerMenu(players).getRoot());
        });

        addPlayer.getChildren().addAll(addPlayerText, newPlayerField, newPlayerButton);
        return addPlayer;
    }

    private VBox removePlayerMenu(Players players) {
        VBox removePlayer = new VBox(10);
        removePlayer.setPadding(new Insets(10));

        Text removePlayersText = new Text("Which player do you want to remove ?");
        removePlayersText.setFont(new Font(20));

        TextField removePlayerField = new TextField();
        removePlayerField.setPromptText("Enter new player name");

        Button oldPlayerButton = new Button("Remove player");
        oldPlayerButton.setOnAction(e -> {
            players.removePlayer(removePlayerField.getText());
            players.saveGame();
            removePlayer.getChildren().setAll(selectPlayerMenu(players).getRoot());

        });

        removePlayer.getChildren().addAll(removePlayersText, removePlayerField, oldPlayerButton);
        return removePlayer;
    }

    private Scene selectGameMenu(Players players) {
        VBox selectGame = new VBox(10);
        selectGame.setPadding(new Insets(10));

        ArrayList<Game> gamesList = player.getGames();

        if (gamesList == null) throw new NullPointerException("Invalid creation or read of file");
        if (gamesList.isEmpty()) {
            selectGame.getChildren().setAll(addGameMenu(players));
            return new Scene(selectGame, 400, 400);
        }

        Button changePlayerButton = new Button("Change player");
        changePlayerButton.setOnAction(e -> {
            this.player = null;
            primaryStage.setScene(selectPlayerMenu(players));
        });

        Button addGameButton = new Button("Add Game");
        addGameButton.setOnAction(e -> selectGame.getChildren().setAll(addGameMenu(players)));

        Button removeGameButton = new Button("Remove Game");
        removeGameButton.setOnAction(e -> selectGame.getChildren().setAll(removeGameMenu(players)));

        Text selectGameText = new Text("Select your Game :");
        selectGameText.setFont(new Font(20));

        selectGame.getChildren().addAll(changePlayerButton, addGameButton, removeGameButton, selectGameText);
        //System.out.println("id\t" + "name\t" + "level");
        for (Game game : gamesList) {
            Button gameButton = new Button(game.getName() + "\t" + game.getDate() + "\t" + game.getDifficulty() + "\t" + game.getScore());
            gameButton.setOnAction(e -> {
                this.game = game;
                this.primaryStage.setScene(showGame(players));
            });
            selectGame.getChildren().add(gameButton);
        }
        return new Scene(selectGame, 400, 400);
    }

    private VBox addGameMenu(Players players) {
        VBox addGame = new VBox(10);
        addGame.setPadding(new Insets(10));

        Button changePlayerButton = new Button("Change player");
        changePlayerButton.setOnAction(e -> {
            this.player = null;
            primaryStage.setScene(selectPlayerMenu(players));
        });

        Text addGameText = new Text("Enter new game name :");
        addGameText.setFont(new Font(20));

        TextField newGameField = new TextField();

        Button newGameButton = new Button("Create new game");

        newGameButton.setOnAction(e -> addGame.getChildren().setAll(selectDifficultyMenu(players, newGameField.getText())));
        addGame.getChildren().addAll(changePlayerButton, addGameText, newGameField, newGameButton);
        return addGame;
    }

    private VBox removeGameMenu(Players players) {
        VBox removeGame = new VBox(10);
        removeGame.setPadding(new Insets(10));

        Text removeGameText = new Text("Which game do you want to remove ?");
        removeGameText.setFont(new Font(20));

        TextField removeGameField = new TextField();
        removeGameField.setPromptText("Enter new game name");

        Button oldGameButton = new Button("Remove game");
        oldGameButton.setOnAction(e -> {
            player.removeGame(removeGameField.getText());
            players.saveGame();
            removeGame.getChildren().setAll(selectGameMenu(players).getRoot());
        });

        removeGame.getChildren().addAll(removeGameText, removeGameField, oldGameButton);
        return removeGame;
    }

    private VBox selectDifficultyMenu(Players players, String name) {
        VBox selectDifficulty = new VBox(10);
        selectDifficulty.setPadding(new Insets(10));

        Text addGameText = new Text("Set game difficulty :");
        addGameText.setFont(new Font(20));
        selectDifficulty.getChildren().add(addGameText);

        Button easyButton = new Button("Easy");
        easyButton.setOnAction(e -> {
            player.addGame(new Game(name, GameDifficulty.EASY));
            players.saveGame();
            selectDifficulty.getChildren().setAll(selectGameMenu(players).getRoot());
        });

        Button nomalButton = new Button("Normal");
        nomalButton.setOnAction(e -> {
            player.addGame(new Game(name, GameDifficulty.NORMAL));
            players.saveGame();
            selectDifficulty.getChildren().setAll(selectGameMenu(players).getRoot());
        });

        Button hardButton = new Button("Hard");
        hardButton.setOnAction(e -> {
            player.addGame(new Game(name, GameDifficulty.HARD));
            players.saveGame();
            selectDifficulty.getChildren().setAll(selectGameMenu(players).getRoot());
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
                selectDifficulty.getChildren().setAll(selectGameMenu(players).getRoot());
            }
        });

        selectDifficulty.getChildren().addAll(easyButton, nomalButton, hardButton, personalisedButton, sizeField, mineCountField);

        return selectDifficulty;
    }

    public Scene showGame(Players players) {
        VBox showGame = new VBox(10);
        showGame.setPadding(new Insets(10));
        Timer timer = this.game.getTimer();
        Button pauseButton = new Button("Pause");
        pauseButton.setOnAction(e -> {
            timer.stop();
            this.game.setStatus(GameStatus.WAITING);
            this.primaryStage.setScene(selectGameMenu(players));
        });

        GridPane grid = new GridPane();

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
            exitButton.setOnAction(e -> {
                this.primaryStage.setScene(selectGameMenu(players));
            });
            Text endStateText = new Text("You " + game.getStatus() + "!");
            endStateText.setFont(new Font(20));
            updateGrid(players, grid);
            showGame.getChildren().setAll(exitButton, endStateText, grid);
        } else System.out.println("Game saved.");
        players.saveGame();

        return new Scene(showGame, 400, 400);
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