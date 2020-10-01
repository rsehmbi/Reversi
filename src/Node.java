import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// Helper class for Tree class which helps MCTS
public class Node {
    State gameState;
    Node parentNode;
    List<Node> childArray;


    public Node() {
        this.gameState = new State();
        this.childArray = new ArrayList<Node>();
    }

    public Node(State gameState) {
        this.gameState = gameState;
        childArray = new ArrayList<>();
    }

    public Node(State gameState, Node parentNode, List<Node> childArray) {
        this.gameState = gameState;
        this.parentNode = parentNode;
        this.childArray = childArray;
    }

    public Node(Node node) {
        this.childArray = new ArrayList<>();
        this.gameState = new State(node.getGameState());

        if (node.getParentNode() != null)
            this.parentNode = node.getParentNode();

        List<Node> childArray = node.getChildArray();

        for (Node child : childArray) {
            this.childArray.add(new Node(child));
        }
    }

    public State getGameState() {
        return gameState;
    }

    public void setGameState(State gameState) {
        this.gameState = gameState;
    }

    public Node getParentNode() {
        return parentNode;
    }

    public void setParentNode(Node parentNode) {
        this.parentNode = parentNode;
    }

    public List<Node> getChildArray() {
        return childArray;
    }

    public void setChildArray(List<Node> childArray) {
        this.childArray = childArray;
    }

    public Node getRandomChildNode() {
        int noOfPossibleMoves = this.childArray.size();
        int selectRandom = (int) (Math.random() * noOfPossibleMoves);
        return this.childArray.get(selectRandom);
    }

    public Node getChildWithMaxScore() {
        return Collections.max(this.childArray, Comparator.comparing(c -> {
            return c.getGameState().getVisitCount();
        }));
    }

}