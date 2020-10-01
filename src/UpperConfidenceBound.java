import java.util.Collections;
import java.util.Comparator;

public class UpperConfidenceBound {

    public static double getConfidenceBound(int N, double w, int n) {
        if (n == 0)
        {
            return Integer.MAX_VALUE;
        }
        // The confidence bound is calculated using the formula
        // w/n + 2^0.5 * ( ln N/n)^0.5
        // Exploration and exploitation
        // https://en.wikipedia.org/wiki/Monte_Carlo_tree_search
        double bound = (w / (double) n) + 1.41 * Math.sqrt(Math.log(N) / (double) n);
        return bound;
    }

    static Node getMaxUpperBound(Node node) {
        int parentVisit = node.getGameState().getVisitCount();
        return Collections.max(
                node.getChildArray(),
                Comparator.comparing(c -> getConfidenceBound(parentVisit, c.getGameState().getWinScore(), c.getGameState().getVisitCount())));
    }
}