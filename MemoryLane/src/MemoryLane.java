/*
This program creates a matching domino game where the board is determined by the user. The user picks two numbers at the specific
index of the dominoes and they are revealed to the user. If the user guesses correctly, those two dominoes are revealed permanently.
If the user guesses incorrectly, the dominoes "flip" back over and remain hidden.
 */
import javax.lang.model.type.ArrayType;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

public class MemoryLane
{
    private Domino[] board;

    /**
     * Constructs a Memory Lane game with max^2 + max Dominoes.
     *
     *      Example: max <-- 2
     *              [1] [1] [1] [1] [2] [2]
     *              [1] [1] [2] [2] [2] [2]
     *
     *      Example: max <-- 3
     *              [1] [1] [1] [1] [1] [1] [2] [2] [2] [2] [3] [3]
     *              [1] [1] [2] [2] [3] [3] [2] [2] [3] [3] [3] [3]
     *
     * Postcondition: board is random
     *
     * @param max the largest number of pips on the Dominoes
     */
    public MemoryLane(int max)
    {
        int totalLength = (max * max) + max;

        board = new Domino[totalLength]; // domino board of total length using the formula

        ArrayList<Integer> top = new ArrayList<>(); // top domino combinations
        ArrayList<Integer> bot = new ArrayList<>(); // bot domino combinations

        for (int i = 0; i < totalLength; i++) // fills the array list with '0s' to reference the position
        {
            top.add(1);
            bot.add(1);
        }

        int count = 1; // all top combinations start with 1
        int current = 1; // all bot combinations start with 1

        /*
            top = repetition of the same number, increments when the number amount reaches the max
            bot = increasing number based on the max, starts over and repeats once max is reached

            int max = 3
            [1] [1] [1] [2] [2] [2] [3] [3] [3]
            [1] [2] [3] [1] [2] [3] [1] [2] [3]

            Removes the indices where the top is greater than the bottom in order to get the unique dominoes
            [1] [1] [1] [2] [2] [3] [3]
            [1] [2] [3] [2] [3] [2] [3]

            Copies the array list of the unique dominoes twice to make the full game board
            [1] [1] [1] [2] [2] [3] [3] [1] [1] [1] [2] [2] [3] [3]
            [1] [2] [3] [2] [3] [2] [3] [1] [2] [3] [2] [3] [2] [3]

            Shuffles the board so it is randomized

         */

        for (int i = 0; i < top.size(); i++) //  gets all possible top combinations depending on the max
        {
            if (count <= max)
            {
                top.set(i, current);
                count++;
            } else if (count > max)
            {
                count = 1;
                current++;
                top.set(i, current);
                count++;
            }
        }

        count = 1; // resets top for the bot portion
        current = 1; // resets top for the bot portion

        for (int i = 0; i < bot.size(); i++) // gets all possible bot combinations depending on the max
        {
            if (count <= max)
            {
                bot.set(i, current);
                count++;
                current++;
            } else if (count > max)
            {
                count = 1;
                current = 1;
                bot.set(i, current);
                count++;
                current++;
            }
        }

        for (int i = top.size() - 1; i >= 0; i--) //  gets rid of the top/bot set where the top is greater than the bot
        {
            if (top.get(i) > bot.get(i))
            {
                top.remove(i);
                bot.remove(i);
            }
        }

        ArrayList<Integer> topCopy = new ArrayList<>(); // ArrayList for the full board for top
        ArrayList<Integer> botCopy = new ArrayList<>(); // ArrayList for the full board for bottom

        for (int k = 0; k < 2; k++) // gets correct board length (unique values * 2)
        {
            for (int i = 0; i < top.size(); i++)
            {
                topCopy.add(top.get(i));
                botCopy.add(bot.get(i));
            }
        }


        int position = 0; // position of the board to add the domino

        for(int k = 0; k < topCopy.size(); k++)
        {
            Domino add = new Domino(topCopy.get(k), botCopy.get(k)); // gets the domino based on topCopy number and botCopy number
            board[position] = add; // adds it to board
            position++; // position of board increments
        }

        shuffle(); // shuffles the board
    }

    /**
     * Returns true if board contains other
     * Note: elements may be null
     *
     * @param other
     * @return
     */
    private boolean contains(Domino other)
    {
        for(int i = 0; i < board.length; i++)
        {
            if(board[i] != null) // checks for null
            {
                if(other.equals(board[i])) // if Domino other equals Domino at board[i]
                    return true;
            }
        }
        return false;
    }

    /**
     * Shuffles board
     * Precondition: board does not contain null elements
     *
     * @return
     */
    private void shuffle()
    {
        ArrayList<Domino> shuffleDominos = new ArrayList<>(); // makes ArrayList to shuffle board easier

        for(int i = 0; i < board.length; i++)
        {
            shuffleDominos.add(board[i]); // adds all the elements in board to ArrayList shuffleDominos
        }

        Collections.shuffle(shuffleDominos); // shuffle dominoes using Collections

        for(int i = 0; i < shuffleDominos.size(); i++)
        {
            board[i] = shuffleDominos.get(i); // re-adds the elements back to board in their random order
        }
    }

    /**
     * Reveals the Dominoes at index i and k if they match
     *
     * @param i
     * @param k
     * @return true if the Dominoes at i and k match; false otherwise
     */
    public boolean guess(int i, int k)
    {
        if(board[i].equals(board[k])) // if the domino at board[i] equals the domino at board[k]
        {
            Domino first = board[i]; // gets the domino at board[i]
            Domino second = board[k]; // get the domino at board[k]
            first.setRevealed(true); // sets the domino at board[i] to true (revealed)
            second.setRevealed(true); // sets the domino at board[k] to true (revealed)

            return true;
        }

        return false;
    }

    /**
     * Returns a String representation of the Dominoes at the specified indexes
     *      in the following format:
     *
     *          [top] [top] [top]
     *          [bot] [bot] [bot]
     *
     * Precondition: the elements at the specified indexes are not null
     *
     * @param indexes variable arguments; any number of indexes;
     *                indexes if effectively an int[]
     * @return
     */
    public String peek(int... indexes)
    {
        String rtnTop = ""; // top return statement
        String rtnBot = ""; // bot return statement

        for(int index : indexes) // each index entered by the user
        {
            Domino getPeek = board[index]; // domino user chose
            int getTop = getPeek.getTop(); // gets top number of the domino
            int getBot = getPeek.getBottom(); // gets bot number of the domino
            rtnTop = rtnTop + "[" + getTop + "] "; // adds top to string
            rtnBot = rtnBot + "[" + getBot + "] "; // adds bot to string
        }

        String rtn = rtnTop + "\n" + rtnBot; // returns the combined string of top and bottom

        return rtn;
    }

    /**
     * @return true if the number of revealed Dominoes is equal to the
     *          length of the board
     */
    public boolean gameOver()
    {
        for(int i = 0; i < board.length; i++)
        {
            Domino checkReveal = board[i]; // gets domino at board[i]
            boolean revealed = checkReveal.isRevealed(); //gets boolean value of the domino

            if(revealed == false) // if the domino is not revealed yet (false)
                return false; // game is not over
        }

        return true;
    }

    /**
     * Returns a String representation of the board in the following format:
     *
     *        0     1     2     3     4     5
     *      [top] [   ] [top] [   ] [top] [top]
     *      [bot] [   ] [bot] [   ] [bot] [bot]
     *
     * Note: the top and bottom numbers of an unrevealed Domino are represented with
     *          an empty space
     *
     * @return
     */

    public String toString()
    {
        String placeValues = ""; // place value of dominoes
        String topValue = ""; // top values of dominoes
        String botValue = ""; // bot values of dominoes

        for(int i = 0; i < board.length; i++)
        {
            placeValues = placeValues + " " + i + "  ";

            Domino getDominoBoard = board[i]; // gets domino at board[i]

            int top = getDominoBoard.getTop(); // gets top of domino
            int bot = getDominoBoard.getBottom(); // gets bot of domino

            if(getDominoBoard.isRevealed() == true) // if the domino is revealed
            {
                topValue = topValue + "[" + top + "] "; // adds top number to string
                botValue = botValue + "[" + bot + "] "; // adds bot number to string
            }

            else if(getDominoBoard.isRevealed() == false) // if the domino is not revealed
            {
                topValue = topValue + "[  ]"; // adds nothing to the string
                botValue = botValue + "[  ]";
            }
        }

        return placeValues + "\n" + topValue + "\n" + botValue; // returns all of the strings together
    }
}