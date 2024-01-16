public class Map {
    private int height;

    private int width;

    private Box[][] boxes;

    /**
     * Create a map with the entered size and mine count.
     *
     * @param height    height of the map
     * @param width     width of the map
     * @param mineCount number of mines in the map
     */
    public Map(final int height, final int width, final int mineCount) {
        // Initialize map
        this.height = height;
        this.width = width;
        this.boxes = new Box[height][width];
        // Generate empty boxes
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                this.boxes[i][j] = new Box();
            }
        }
        // Check if mine count is possible for the size of the map
        if (mineCount > height * width) {
            System.out.println("Too many mines");
            return;
        }
        // Generate mines at random position and indications around them
        for (int i = 0; i < mineCount; i++) {
            int x = (int) (Math.random() * height);
            int y = (int) (Math.random() * width);
            if (this.boxes[x][y] instanceof Mine) {
                i--;
            } else {
                this.boxes[x][y] = new Mine();
                for (int dx = -1; dx <= 1; dx++) {
                    for (int dy = -1; dy <= 1; dy++) {
                        int nx = x + dx;
                        int ny = y + dy;
                        if (nx >= 0 && nx < this.height && ny >= 0 && ny < this.width) {
                            if (!(this.boxes[nx][ny] instanceof Mine)) {
                                if (this.boxes[nx][ny] instanceof Indication) {
                                    ((Indication) this.boxes[nx][ny]).addMine();
                                } else if (!(this.boxes[nx][ny] instanceof Mine)) {
                                    this.boxes[nx][ny] = new Indication();
                                    ((Indication) this.boxes[nx][ny]).addMine();
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * Mark if the box is not revealed, unmark if it is already marked.
     *
     * @param x position x of the box to mark/unmark.
     * @param y position y of the box to mark/unmark.
     */
    public void mark(final int x, final int y) {
        if (x < 0 || x >= this.height || y < 0 || y >= this.width) {
            System.out.println("Invalid position");
            return;
        }
        if (!this.boxes[x][y].isRevealed()) {
            System.out.println("Box already revealed");
        } else {
            this.boxes[x][y].setMarked(!this.boxes[x][y].isMarked());
        }
    }

    /**
     * Reveal the box in entered position.
     * If the box is empty, reveal adjacent boxes.
     * If the box is a mine, reveal all boxes and end the game.
     *
     * @param x position x of the box to reveal
     * @param y position y of the box to reveal
     */
    public void reveal(final int x, final int y) {
        // Invalid position if the box is outside the map
        if (x < 0 || x >= this.height || y < 0 || y >= this.width) {
            System.out.println("Invalid position.");
            return;
        }
        //reveal the box
        this.boxes[x][y].reveal();
        // Game over if the box is a mine
        if (this.boxes[x][y] instanceof Mine) {
            // Reveal all boxes if the box is a mine
            for (int i = 0; i < this.height; i++) {
                for (int j = 0; j < this.width; j++) {
                    this.boxes[i][j].reveal();
                }
            }
        } else if (!(this.boxes[x][y] instanceof Indication)) {
            // Reveal adjacent boxes if the box is empty
            for (int dx = -1; dx <= 1; dx++) {
                for (int dy = -1; dy <= 1; dy++) {
                    int nx = x + dx;
                    int ny = y + dy;
                    if (nx >= 0 && nx < this.height && ny >= 0 && ny < this.width) {
                        if (!this.boxes[nx][ny].isRevealed()) {
                            reveal(nx, ny);
                        }
                    }
                }
            }
        }
    }

    /**
     * Print the map in the console.
     * X : box not revealed
     * - : box revealed and empty
     * F : box marked
     * * : box revealed and mine
     * 1-8 : box revealed and indication
     */
    public void print() {
        System.out.println();
        for (int i = 0; i < this.height; i++) {
            for (int j = 0; j < this.width; j++) {
                if (this.boxes[i][j].isRevealed()) {
                    if (this.boxes[i][j] instanceof Indication) {
                        printNumbers(((Indication) this.boxes[i][j]).getMineCount());
                    } else if (this.boxes[i][j] instanceof Mine) {
                        System.out.print("\uD83D\uDCA3");
                    } else {
                        System.out.print("\uD83D\uDFEB");
                    }
                } else if (this.boxes[i][j].isMarked()) {
                    System.out.print("\uD83D\uDEA9");
                } else {
                    System.out.print("\uD83D\uDFE9");
                }
            }
            System.out.println();
        }
    }

    public void printNumbers(int i) {
        switch (i) {
            case 1:
                System.out.print("1\uFE0F⃣");
                break;
            case 2:
                System.out.print("2\uFE0F⃣");
                break;
            case 3:
                System.out.print("3\uFE0F⃣");
                break;
            case 4:
                System.out.print("4\uFE0F⃣");
                break;
            case 5:
                System.out.print("5\uFE0F⃣");
                break;
            case 6:
                System.out.print("6\uFE0F⃣");
                break;
            case 7:
                System.out.print("7\uFE0F⃣");
                break;
            case 8:
                System.out.print("8\uFE0F⃣");
                break;
        }
    }

    /**
     * Return the height of the map.
     *
     * @return height of the map.
     */
    public int getHeight() {
        return this.height;
    }

    /**
     * Return the width of the map.
     *
     * @return width of the map.
     */
    public int getWidth() {
        return this.width;
    }

    /**
     * Return the box in the entered position.
     *
     * @param x position x of the box to return.
     * @param y position y of the box to return.
     * @return box in the entered position.
     */
    public Box getBox(final int x, final int y) {
        return this.boxes[x][y];
    }
}
