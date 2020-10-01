import java.util.Scanner;

public class Main {

    private static void startProgram()
    {
        Scanner scan = new Scanner(System.in);
        int valid1 = 1;
        int valid2 = 2;
        int Input;
        while (!scan.equals(valid1) || scan.equals(valid2)) {
            System.out.println("\n");
            System.out.println("~ For Playing with the computer enter 1 ~");
            System.out.println("~ For Algorithms to play with each other enter 2 ~");
            System.out.println("~ To Exit, press 0 ~");
            System.out.println("\nEnter your Input: ");
            Input = scan.nextInt();

            if (Input == valid1) {
                Board reversiBoard = new Board();
                ManualHeuristic.playreversiMatch(reversiBoard);
            }
            else if(Input == valid2)
            {
                Board reversiBoard = new Board();
                AICompete.WeightHeuristicVSMCS(reversiBoard);
            }
            else if (Input == 0)
            {
                System.exit(0);
            }
            else {
                System.out.println("Please Enter Valid Input");
            }
        }
    }

    public static void main(String[] args) {
        System.out.println("****************** Welcome to Reversi ******************");
        startProgram();
    }
}
