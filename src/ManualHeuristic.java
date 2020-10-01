import java.util.HashSet;
import java.util.Scanner;

public class ManualHeuristic {
    private static int result;
    private static Boolean skip;
    private static String input;
    private static char WHITE = 'W';
    private static char BLACK = 'B';

    // Getting the input from command line from the user
    private static Point getInput(Board reversiBoard, HashSet<Point> validLocations) {
        Scanner scanner = new Scanner(System.in);
        Point move = new Point();

        System.out.println("Place User Move White: ");
        input = scanner.next();
        move.setY(reversiBoard.validateUserInput(input.charAt(0)));
        move.setX(Integer.parseInt(input.charAt(1) + "") - 1);

        while (!validLocations.contains(move)) {
            System.out.println("Invalid Move Detected !\n");
            System.out.println("\n");
            System.out.println("Please place new Valid move (White): ");
            input = scanner.next();
            move.setY(reversiBoard.validateUserInput(input.charAt(0)));
            move.setX(Integer.parseInt(input.charAt(1) + "") - 1);
        }
        return move;
    }

    // Class which helps to play reversi with MCTS
    public static void playreversiMatch(Board reversiBoard) {
        System.out.println("Computer Moves First.");

        while (true) {
            skip = false;

            HashSet<Point> blackPlaceableLocations = reversiBoard.getPlaceableLocations(BLACK, WHITE);
            HashSet<Point> whitePlaceableLocations = reversiBoard.getPlaceableLocations(WHITE, BLACK);

            reversiBoard.showPlaceableLocations(blackPlaceableLocations);
            result = reversiBoard.gameResult();

            if (AICompete.checkResult(result, reversiBoard)) {
                break;
            }

            if (blackPlaceableLocations.isEmpty()) {
                System.out.println("Black(Computer) needs to skip\n");
                System.out.println("Passing move to White(Human) \n");
                skip = true;
            }

            if (!skip) {
                MonteCarloSearch mcts = new MonteCarloSearch();
                reversiBoard = mcts.findNextMove(reversiBoard, 2);
                reversiBoard.updateScores();
                reversiBoard.printScore();
            }
            skip = false;

            whitePlaceableLocations = reversiBoard.getPlaceableLocations(WHITE, BLACK);
            blackPlaceableLocations = reversiBoard.getPlaceableLocations(BLACK, WHITE);

            reversiBoard.showPlaceableLocations(whitePlaceableLocations);
            result = reversiBoard.gameResult();

            if (AICompete.checkResult(result, reversiBoard)) {
                break;
            }

            if (whitePlaceableLocations.isEmpty()) {
                System.out.println("White(Human) needs to skip\n");
                System.out.println("Passing move to Black(Computer) \n");
                skip = true;
            }

            Point userMove = new Point();
            if (!skip) {
                userMove = getInput(reversiBoard, whitePlaceableLocations);
                reversiBoard.placeMove(userMove, WHITE, BLACK);
                reversiBoard.updateScores();
                reversiBoard.printScore();
            }
        }
    }
}