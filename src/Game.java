import java.time.LocalDate;
import java.util.Scanner;

/**
 * Represents a Minesweeper game.
 */
public class Game {
    private String name;
    private int score;
    private int minesCount;
    private Map map;
    private LocalDate date;
    private Timer timer;
    private GameDifficulty difficulty;
    private GameStatus status;

    /**
     * Constructs a new Minesweeper game with the specified name and difficulty.
     *
     * @param name       The name of the game.
     * @param difficulty The difficulty level of the game.
     */
    public Game(String name, GameDifficulty difficulty) {
        this.name = name;
        this.score = 0;
        this.difficulty = difficulty;
        this.date = LocalDate.now();
        this.status = GameStatus.WAITING;
        this.timer = new Timer();

        switch (this.difficulty) {
            case GameDifficulty.EASY:
                this.minesCount = 5;
                this.map = new Map(5, 5, this.minesCount);
                break;
            case GameDifficulty.NORMAL:
                this.minesCount = 20;
                this.map = new Map(10, 10, this.minesCount);
                break;
            case GameDifficulty.HARD:
                this.minesCount = 80;
                this.map = new Map(20, 20, this.minesCount);
                break;
            case GameDifficulty.PERSONALISED:
                System.out.println("Enter the map size :\n");
                Scanner input = new Scanner(System.in);
                int size = input.nextInt();
                System.out.println("Home much mines do you want ?\n");
                this.minesCount = input.nextInt();
                this.map = new Map(size, size, this.minesCount);
                break;
        }
    }

    /**
     * Constructs a new Minesweeper game with the specified name, size, and number of mines.
     *
     * @param name       The name of the game.
     * @param size       The size of the game map.
     * @param minesCount The number of mines in the game.
     */
    public Game(String name, int size, int minesCount) {
        this.name = name;
        this.score = 0;
        this.difficulty = GameDifficulty.PERSONALISED;
        this.date = LocalDate.now();
        this.status = GameStatus.WAITING;
        this.timer = new Timer();
        this.minesCount = minesCount;
        this.map = new Map(size, size, this.minesCount);
    }

    /**
     * Gets the name of the game.
     *
     * @return The name of the game.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Gets the score of the game.
     *
     * @return The score of the game.
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Gets the date when the game was played.
     *
     * @return The date of the game.
     */
    public LocalDate getDate() {
        return this.date;
    }

    /**
     * Gets the map of the game.
     *
     * @return The map of the game.
     */
    public Map getMap() {
        return this.map;
    }

    /**
     * Gets the timer used in the game.
     *
     * @return The timer of the game.
     */
    public Timer getTimer() {
        return this.timer;
    }

    /**
     * Gets the difficulty level of the game.
     *
     * @return The difficulty level of the game.
     */
    public GameDifficulty getDifficulty() {
        return this.difficulty;
    }

    /**
     * Gets the status of the game.
     *
     * @return The status of the game.
     */

    public GameStatus getStatus() {
        return this.status;
    }

    /**
     * Sets the status of the game.
     *
     * @param status The status to set for the game.
     */
    public void setStatus(GameStatus status) {
        this.status = status;
    }

    /**
     * Computes and increments the score of the game.
     */
    public void computeScore() {
        this.score++;
    }
}
