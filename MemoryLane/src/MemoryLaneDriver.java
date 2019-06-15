/**
 * Created by Eric Park on 11/14/2017.
 */
import java.util.Scanner;

public class MemoryLaneDriver
{
    public static void main(String[] args)
    {
        String message = "Welcome to Memory Lane!" + "\n" +
                "Choose two indexes to reveal the corresponding dominoes." + "\n" +
                "If the dominoes match, they stay revealed." + "\n" +
                "Reveal all the dominoes to win the game!" + "\n";

        System.out.println(message);

        Scanner input = new Scanner(System.in);

        MemoryLane game = new MemoryLane(2);

        long start = System.currentTimeMillis();

        while(!game.gameOver())
        {
            System.out.println(game);

            System.out.print("First:  ");
            int first = input.nextInt();

            System.out.print("Second: ");
            int second = input.nextInt();

            game.guess(first, second);
            System.out.println(game.peek(first, second) + "\n");
        }

        long stop = System.currentTimeMillis();
        long elapsed = (stop - start) / 1000;

        System.out.println(game);

        System.out.println("\nYou win!");
        System.out.println("Total time: " + elapsed + "s");

    }
}