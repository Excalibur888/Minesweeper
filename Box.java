public class Box {

    private boolean marked;

    private boolean revealed;

    private int adjacentMineCount;

    private BoxType type;

    /**
     * Create a box with default values.
     */
    public Box() {
        this.marked = false;
        this.revealed = false;
        this.adjacentMineCount = 0;
        this.type = BoxType.EMPTY;
    }

    /**
     * set the box as revealed.
     */
    public void reveal() {
        this.revealed = true;
    }

    /**
     * Return the box's mark status.
     *
     * @return true if the box is marked, false otherwise.
     */
    public boolean isMarked() {
        return this.marked;
    }

    /**
     * Return the box's reveal status.
     *
     * @return true if the box is revealed, false otherwise.
     */
    public boolean isRevealed() {
        return this.revealed;
    }

    /**
     * Set the box's mark status.
     *
     * @param marked true if the box is marked, false otherwise.
     */
    public void setMarked(final boolean marked) {
        this.marked = marked;
    }

    /**
     * Add 1 mine to the indicator.
     */
    public void addMine() {
        this.adjacentMineCount++;
    }

    /**
     * Return the number of mines around the indicator.
     *
     * @return number of mines around the indicator.
     */
    public int getAdjacentMineCount() {
        return this.adjacentMineCount;
    }

    /**
     * Return the box's type.
     * @return box's type.
     */
    public BoxType getType() {
        return this.type;
    }

    /**
     * Set the box's type.
     * @param type box's type.
     */
    public void setType(final BoxType type) {
        this.type = type;
    }
}
