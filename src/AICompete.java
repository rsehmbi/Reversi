import java.util.ArrayList;
import java.util.HashSet;

public class AICompete {

    private static ArrayList<Long> MCTSTime = new ArrayList<>();
    private static ArrayList<Long> WHeurisctics = new ArrayList<>();
    private static char BLACK  = 'B';
    private static char WHITE = 'W';

    // Made this function public and static so we can use it for the other classes without initializing the class.
    static boolean checkResult(int result, Board reversiBoard) {
        boolean Break = false;
        if (result == 0) {
            Break = true;
            System.out.println("Score Draw. No one Wins");
        } else if (result == 1) {
            reversiBoard.printScore();
            System.out.println("White Wins");
            Break = true;
        } else if (result == 2) {
            reversiBoard.printScore();
            System.out.println("Black Wins");
            Break = true;
        }
        return Break;
    }

    // Class that helps to compete the algorithms against each other
    public static void WeightHeuristicVSMCS(Board reversiBoard) {
        int result;
        Boolean skip;
        System.out.println("MCTS goes first");
        while (true) {
            skip = false;

            HashSet<Point> blackPlaceableLocations = reversiBoard.getPlaceableLocations(BLACK, WHITE);
            HashSet<Point> whitePlaceableLocations = reversiBoard.getPlaceableLocations(WHITE, BLACK);

            reversiBoard.showPlaceableLocations(blackPlaceableLocations);
            result = reversiBoard.gameResult();

            if (checkResult(result, reversiBoard)) {
                break;
            }

            if (blackPlaceableLocations.isEmpty()) {
                System.out.println("Black needs to skip");
                System.out.println("Passing to White");
                skip = true;
            }

            if (!skip) {
                MonteCarloSearch monteCarloSearch = new MonteCarloSearch();
                long mstartTime = System.currentTimeMillis();
                reversiBoard = monteCarloSearch.findNextMove(reversiBoard, 2);
                long mendTime = System.currentTimeMillis();
                long mtimeElapsed = mendTime - mstartTime;
                MCTSTime.add(mtimeElapsed);
                reversiBoard.updateScores();
                reversiBoard.printScore();
            }
            skip = false;

            whitePlaceableLocations = reversiBoard.getPlaceableLocations(WHITE, BLACK);
            blackPlaceableLocations = reversiBoard.getPlaceableLocations(BLACK, WHITE);

            reversiBoard.showPlaceableLocations(whitePlaceableLocations);
            result = reversiBoard.gameResult();

            if (checkResult(result, reversiBoard)) {
                break;
            }

            if (whitePlaceableLocations.isEmpty()) {
                System.out.println("White(Weighted Heurisctic) needs to skip");
                System.out.println("Passing to MCTS(Black)");
                skip = true;
            }

            if (!skip) {
                System.out.println("Place move Weighted Heuristic(White): ");
                long startTime = System.currentTimeMillis();
                WeightHeuristic wH = new WeightHeuristic();
                reversiBoard = wH.findNextMove(reversiBoard, 2);
                long endTime = System.currentTimeMillis();
                long timeElapsed = endTime - startTime;
                WHeurisctics.add(timeElapsed);
                reversiBoard.updateScores();
                reversiBoard.printScore();
            }
        }

        // Showing the Times after the game ends
        System.out.println("\n");
        System.out.println("MCTS Time (in Milliseconds)");
        for (int i = 0; i < MCTSTime.size(); i++) {
            System.out.print(MCTSTime.get(i)+ " ");
        }
        System.out.println("\n");
        System.out.println("Weight Heuristic Time (in Milliseconds)");
        for (int i = 0; i < WHeurisctics.size(); i++) {
            System.out.print(WHeurisctics.get(i)+ " ");
        }

    }
}
