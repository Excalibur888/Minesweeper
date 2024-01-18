/**
 * The Timer class provides functionality to measure elapsed time.
 */
public class Timer {
    private long elapsedTime;
    private long startTime;

    /**
     * Constructs a Timer with initial elapsed time set to zero.
     */
    public Timer() {
        this.elapsedTime = 0;
    }

    /**
     * Starts the timer by recording the current system time.
     */
    public void start() {
        this.startTime = System.currentTimeMillis();
    }

    /**
     * Stops the timer and adds the elapsed time to the total elapsed time.
     */
    public void stop() {
        this.elapsedTime += System.currentTimeMillis() - startTime;
    }

    /**
     * Gets the total elapsed time in seconds.
     *
     * @return The total elapsed time in seconds.
     */
    public long elapsed() {
        return ((System.currentTimeMillis() - this.startTime) + elapsedTime) / 1000;
    }

    /**
     * Gets the elapsed seconds part of the total elapsed time.
     *
     * @return The elapsed seconds.
     */
    public long elapsedSeconds() {
        return this.elapsed() % 60;
    }

    /**
     * Gets the elapsed minutes part of the total elapsed time.
     *
     * @return The elapsed minutes.
     */
    public long elapsedMinutes() {
        return this.elapsed() / 60;
    }

    /**
     * Displays the elapsed time in the format "minutes : seconds".
     */
    public void display() {
        System.out.format("%d : %02d\t", elapsedMinutes(), elapsedSeconds());
    }
}
