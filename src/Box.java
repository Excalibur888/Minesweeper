/**
 * Represents a single cell (box) in the Minesweeper game grid.
 */
public class Box {
    private boolean marked;
    private boolean revealed;
    private int adjacentMineCount;
    private BoxType type;

    /**
     * Constructs a new Box with default values.
     */
    public Box() {
        this.marked = false;
        this.revealed = false;
        this.adjacentMineCount = 0;
        this.type = BoxType.EMPTY;
    }

    /**
     * Reveals the content of the box.
     */
    public void reveal() {
        this.revealed = true;
    }

    /**
     * Checks if the box is marked.
     *
     * @return True if the box is marked, false otherwise.
     */
    public boolean isMarked() {
        return this.marked;
    }

    /**
     * Checks if the box is revealed.
     *
     * @return True if the box is revealed, false otherwise.
     */
    public boolean isRevealed() {
        return this.revealed;
    }

    /**
     * Sets the mark status of the box.
     *
     * @param toMark True to mark the box, false to unmark.
     */
    public void mark(final boolean toMark) {
        this.marked = toMark;
    }

    /**
     * Increments the count of adjacent mines for the box.
     */
    public void addMine() {
        this.adjacentMineCount++;
    }

    /**
     * Gets the count of adjacent mines for the box.
     *
     * @return The count of adjacent mines.
     */
    public int getAdjacentMineCount() {
        return this.adjacentMineCount;
    }

    /**
     * Gets the type of the box.
     *
     * @return The type of the box (EMPTY, MINE, INDICATION).
     */
    public BoxType getType() {
        return this.type;
    }

    /**
     * Sets the type of the box.
     *
     * @param type The type of the box (EMPTY, MINE, INDICATION).
     */
    public void setType(final BoxType type) {
        this.type = type;
    }
}
