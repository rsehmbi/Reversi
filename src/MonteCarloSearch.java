// Code inspired from Blog post of Tic Tac Toe Monte Carlo Tree Search https://www.baeldung.com/java-monte-carlo-tree-search
import java.util.List;


public class MonteCarloSearch {

    private static final int WIN_SCORE = 10;
    private int level;
    private int opponent;

    public MonteCarloSearch() {
        this.level = 3;
    }

    private int gettimeforLevel() {
        return this.level + this.level - 1;
    }

    public Board findNextMove(Board board, int playerNo) {
        // Setting the time for the algorithm to run
        long start = System.currentTimeMillis();
        long end = start + 500 * gettimeforLevel();

        opponent = 3 - playerNo;
        Tree tree = new Tree();
        Node rootNode = tree.getRoot();
        rootNode.getGameState().setBoard(board);
        rootNode.getGameState().setPlayerNo(opponent);

        // Running till the time is over. According to project requirements set to 2.5 seconds half of max 5
        while (System.currentTimeMillis() < end) {
            // Selection
            Node promisingNode = Select(rootNode);
            // Expansion
            if (promisingNode.getGameState().getBoard().gameResult() == Board.IN_PROGRESS)
            {
                Expand(promisingNode);
            }
            // Simulation
            Node nodeToExplore = promisingNode;
            if (promisingNode.getChildArray().size() > 0)
            {
                nodeToExplore = promisingNode.getRandomChildNode();
            }
            int playoutResult = simulateRandomPlayout(nodeToExplore);
            // Update
            Propagate(nodeToExplore, playoutResult);
        }

        // Getting the child with the maximum score.
        Node winnerNode = rootNode.getChildWithMaxScore();
        tree.setRoot(winnerNode);
        // Returning new board.
        return winnerNode.getGameState().getBoard();
    }

    // finding the best node using UpperConfidenceBound
    private Node Select(Node rootNode) {
        Node node = rootNode;
        while (node.getChildArray().size() != 0) {
            node = UpperConfidenceBound.getMaxUpperBound(node);
        }
        return node;
    }

    // Expanding to nodes to get the states
    private void Expand(Node node) {
        List<State> possibleStates = node.getGameState().getAllPossibleStates();
        possibleStates.forEach(state -> {
            Node newNode = new Node(state);
            newNode.setParentNode(node);
            newNode.getGameState().setPlayerNo(node.getGameState().getOpponent());
            node.getChildArray().add(newNode);
        });
    }

    // getting the best node
    private void Propagate(Node nodeToExplore, int playerNo) {
        Node tempNode = nodeToExplore;
        while (tempNode != null) {
            tempNode.getGameState().incrementVisit();
            if (tempNode.getGameState().getPlayerNo() == playerNo)
                tempNode.getGameState().addScore(WIN_SCORE);
            tempNode = tempNode.getParentNode();
        }
    }

    // Playing the game until of them wins the game.
    private int simulateRandomPlayout(Node node) {
        Node tempNode = new Node(node);
        State tempState = tempNode.getGameState();
        int boardStatus = tempState.getBoard().gameResult();

        if (boardStatus == opponent) {
            tempNode.getParentNode().getGameState().setWinScore(Integer.MIN_VALUE);
            return boardStatus;
        }
        while (boardStatus == Board.IN_PROGRESS) {
            tempState.switchTurns();
            tempState.randomPlay();
            boardStatus = tempState.getBoard().gameResult();
        }

        return boardStatus;
    }

}