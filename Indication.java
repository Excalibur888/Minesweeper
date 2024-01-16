public class Indication extends Box {

    private int mineCount;

    /**
     * Create an indication box with default values.
     */
    public Indication() {
        super();
        this.mineCount = 0;
    }

    /**
     * Add 1 mine to the indicator.
     */
    public void addMine() {
        this.mineCount++;
    }

    /**
     * Return the number of mines around the indicator.
     * @return number of mines around the indicator.
     */
    public int getMineCount() {
        return this.mineCount;
    }
}
