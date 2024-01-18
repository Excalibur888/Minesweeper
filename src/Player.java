import java.util.ArrayList;

public class Player {
    private String name;
    private ArrayList<Game> games;

    public Player(String name) {
        this.name = name;
        this.games = new ArrayList<Game>();
    }

    /**
     * return the player's name.
     *
     * @return player's name.
     */
    public String getName() {
        return name;
    }

    /**
     * Return the player's level.
     *
     * @return player's level.
     */
    public int getLevel() {
        int level = 0;
        for (Game game : this.games) {
            level += game.getScore();
        }
        return level / 10;
    }

    /**
     * Return the player's games.
     *
     * @return player's games.
     */
    public ArrayList<Game> getGames() {
        return games;
    }

    /**
     * Set the player's name.
     *
     * @param name player's name.
     */
    public void setName(final String name) {
        this.name = name;
    }


    /**
     * Create a new game.
     *
     * @param name       name of the game.
     * @param difficulty difficulty of the game.
     * @return the created game.
     */
    public Game createGame(final String name, final GameDifficulty difficulty) {
        if (!name.isEmpty()) {
            for (Game game : this.games) {
                if (game.getName().equals(name)) {
                    System.out.println("Error this name is already taken.");
                    return null;
                }
            }
            Game game = new Game(name, difficulty);
            this.games.add(game);
            System.out.println("Game added.");
            return game;
        }
        System.out.println("Error empty name.");
        return null;
    }

    /**
     * Add an existing game to the player's list of games.
     *
     * @param newGame The game to be added.
     */
    public void addGame(Game newGame) {
        if (!newGame.getName().isEmpty()) {
            for (Game game : this.games) {
                if (game.getName().equals(newGame.getName())) {
                    System.out.println("Error this name is already taken.");
                    return;
                }
            }
            this.games.add(newGame);
            System.out.println("Game added.");
            return;
        }
        System.out.println("Error empty name.");
    }

    /**
     * Remove a game with the given name from the player's list of games.
     *
     * @param name The name of the game to be removed.
     */
    public void removeGame(String name) {
        if (!name.isEmpty()) {
            for (Game g : this.games) {
                if (g.getName().equals(name)) {
                    this.games.remove(g);
                    System.out.println("Game removed.");
                    return;
                }
            }
            System.out.println("Error this game does not exist.");
            return;
        }
        System.out.println("Error empty name.");
    }
}
