public class Box {

    private boolean marked;

    private boolean revealed;

    /**
     * Create a box with default values.
     */
    public Box() {
        this.marked = false;
        this.revealed = false;
    }

    /**
     * set the box as revealed.
     */
    public void reveal() {
        this.revealed = true;
    }

    /**
     * Return the box's mark status.
     * @return true if the box is marked, false otherwise.
     */
    public boolean isMarked() {
        return this.marked;
    }

    /**
     * Return the box's reveal status.
     * @return true if the box is revealed, false otherwise.
     */
    public boolean isRevealed() {
        return this.revealed;
    }

    /**
     * Set the box's mark status.
     * @param marked true if the box is marked, false otherwise.
     */
    public void setMarked(final boolean marked) {
        this.marked = marked;
    }
}
