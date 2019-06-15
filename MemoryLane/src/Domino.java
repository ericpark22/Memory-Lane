/*
This program creates a domino for the board. Two numbers are entered and the lowest number becomes the top number and the
higher number becomes the bot number. These two numbers are "combined" into the domino object to create a domino for the board.
The dominoes also contain a revealed feature to determine if the user has guessed two dominoes correctly.
 */
public class Domino
{
    private int top, bottom;
    private boolean revealed;

    /**
     * Constructs an unrevealed Domino from x and y.
     *      The minimum of x and y is top number.
     *      The maximum of x and y is the bottom number.
     * @param x
     * @param y
     */
    public Domino(int x, int y)
    {
        top = Math.min(x, y); // minimum of the two numbers
        bottom = Math.max(x, y); //  maximum of the two numbers
        revealed = false; // all dominoes not revealed in the beginning
    }

    /**
     * @return top
     */
    public int getTop()
    {
        return top;
    }

    /**
     * @return bottom
     */
    public int getBottom()
    {
        return bottom;
    }

    /**
     * @return revealed
     */
    public boolean isRevealed()
    {
        return revealed;
    }

    /**
     * Sets revealed to the specified value
     * @param revealed
     */
    public void setRevealed(boolean revealed)
    {
        this.revealed = revealed;
    }

    /**
     * Returns true if this Domino has the same top and bottom as other
     * @param other
     * @return
     */
    public boolean equals(Domino other)
    {
        if(top == other.getTop() && bottom == other.getBottom()) // if the top and bot of this domino is the same as the other domino
            return true; // they are equal to each other
        return false;
    }
}