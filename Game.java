import java.time.LocalDate;
import java.util.Scanner;

public class Game {
    private String name;
    private int score;
    private int minesCount;
    private Map map;
    private LocalDate date;
    private Timer timer;
    private GameDifficulty difficulty;
    private GameStatus status;

    public Game(String name, GameDifficulty difficulty) {
        this.name = name;
        this.score = 0;
        this.difficulty = difficulty;
        this.date = LocalDate.now();
        this.status = GameStatus.WAITING;

        switch (this.difficulty) {
            case GameDifficulty.EASY:
                this.minesCount = 10;
                this.map = new Map(10, 10, this.minesCount);
                break;
            case GameDifficulty.NORMAL:
                this.minesCount = 40;
                this.map = new Map(20, 20, this.minesCount);
                break;
            case GameDifficulty.HARD:
                this.minesCount = 99;
                this.map = new Map(30, 30, this.minesCount);
                break;
            case GameDifficulty.PERSONALISED:
                System.out.println("Enter the map size :\n");
                Scanner input = new Scanner(System.in);
                int size = input.nextInt();
                this.map = new Map(size, size, this.minesCount);
                System.out.println("Home much mines do you want ?\n");
                this.minesCount = input.nextInt();
                break;
        }
    }

    public Game(Map map) {

    }

    public String getName() {
        return this.name;
    }

    public int getScore() {
        return this.score;
    }

    public LocalDate getDate() {
        return this.date;
    }

    public Map getMap() {
        return this.map;
    }

    public Timer getTimer() {
        return this.timer;
    }

    public GameDifficulty getDifficulty() {
        return this.difficulty;
    }

    public GameStatus getStatus() {
        return this.status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public void computeScore() {

    }
}
