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
     * Set game list to the player.
     *
     * @param games game list to set.
     */
    public void setGames(final ArrayList<Game> games) {
        this.games = games;
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
            for (Game g : this.games) {
                if (g.getName().equals(name)) {
                    System.out.println("Error this name is already taken.");
                    return null;
                }
            }
            Game game = new Game(name, difficulty);
            this.games.add(game);
            System.out.println("Game added.");
            return game;
        }
        return null;
    }

    public void removeGame(String name) {
        for (Game g : this.games) {
            if (g.getName().equals(name)) {
                this.games.remove(g);
                System.out.println("Game removed.");
                return;
            }
        }
        System.out.println("Error this game does not exist.");
    }
}
