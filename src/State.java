import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

// Player1 = Black
// Player2 = White

public class State {
    private Board board;
    private int playerNo;
    private int visitCount;
    private double winScore;
    private char BLACK = 'B';
    private char WHITE = 'W';

    public State()
    {
        board = new Board();
    }

    public State(State state) {
        this.board = new Board(state.getBoard());
        this.playerNo = state.getPlayerNo();
        this.visitCount = state.getVisitCount();
        this.winScore = state.getWinScore();
    }

    public State(Board board)
    {
        this.board = new Board(board);
    }

    Board getBoard()
    {
        return board;
    }

    void setBoard(Board board)
    {
        this.board = board;
    }

    int getPlayerNo()
    {
        return playerNo;
    }

    void setPlayerNo(int playerNo)
    {
        this.playerNo = playerNo;
    }

    int getOpponent()
    {
        return 3 - playerNo;
    }

    public int getVisitCount()
    {
        return visitCount;
    }

    public void setVisitCount(int visitCount)
    {
        this.visitCount = visitCount;
    }

    double getWinScore()
    {
        return winScore;
    }

    void setWinScore(double winScore)
    {
        this.winScore = winScore;
    }

    private char determinaPlayer(int PlayerNumber)
    {
        if(PlayerNumber == 1)
        {
            return BLACK;
        }
        else
        {
            return WHITE;
        }
    }
    public List<State> getAllPossibleStates() {
        List<State> possibleStates = new ArrayList<>();
        // We want player and opponent here
        char player = determinaPlayer(this.playerNo);
        char opponent = determinaPlayer(3-this.playerNo);

        HashSet<Point> availablePosition = this.board.getPlaceableLocations(player,opponent);
        ArrayList<Point> availablePositions = new ArrayList<Point>(availablePosition);

        availablePositions.forEach(p -> {
            State newState = new State(this.board);
            newState.setPlayerNo(this.playerNo);
            int playerNos = newState.getPlayerNo();
            char newPlayer = determinaPlayer(playerNos);
            char newOpponent = determinaPlayer(3-playerNos);
            newState.getBoard().placeMove(p,newPlayer,newOpponent);
            possibleStates.add(newState);
        });
        return possibleStates;
    }

    void incrementVisit() {
        this.visitCount++;
    }

    void addScore(double score) {
        if (this.winScore != Integer.MIN_VALUE)
            this.winScore += score;
    }

    void randomPlay() {
        char player = determinaPlayer(this.playerNo);
        char opponent = determinaPlayer(3-this.playerNo);

        HashSet<Point> availablePosition = this.board.getPlaceableLocations(player,opponent);
        ArrayList<Point> availablePositions = new ArrayList<Point>(availablePosition);

        int totalPossibilities = availablePositions.size();

        if (totalPossibilities == 0 )
        {
            this.board.gameResult();
            return;
        }

        int selectRandom = (int) (Math.random() * totalPossibilities);
        Point randomPoint = availablePositions.get(selectRandom);
        this.board.placeMove(randomPoint, player,opponent );
    }

    void switchTurns()
    {
        this.playerNo = 3 - this.playerNo;
    }
}
