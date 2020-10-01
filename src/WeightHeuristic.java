import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class WeightHeuristic {
    private char PLAYER = 'B';
    private char OPPONENT = 'W';
    public WeightHeuristic() {

    }

    // Placing move according to the point passed and returning the new state.
    public Board placeMove(Point p, char player, char opponent, Board board) {
        Board newBoardState = new Board(board);
        int xCordinate = p.getX();
        int yCordinate = p.getY();
        newBoardState.board[xCordinate][yCordinate] = player;

        int validI = xCordinate;
        int validJ = yCordinate;

        if (xCordinate - 1 >= 0 && yCordinate - 1 >= 0 && newBoardState.board[xCordinate - 1][yCordinate - 1] == opponent) {
            xCordinate = xCordinate - 1;
            yCordinate = yCordinate - 1;
            while (xCordinate > 0 && yCordinate > 0 && newBoardState.board[xCordinate][yCordinate] == opponent) {
                xCordinate--;
                yCordinate--;
            }
            if (xCordinate >= 0 && yCordinate >= 0 && newBoardState.board[xCordinate][yCordinate] == player) {
                while (xCordinate != validI - 1 && yCordinate != validJ - 1) newBoardState.board[++xCordinate][++yCordinate] = player;
            }
        }
        xCordinate = validI;
        yCordinate = validJ;
        if (xCordinate - 1 >= 0 && newBoardState.board[xCordinate - 1][yCordinate] == opponent) {
            xCordinate = xCordinate - 1;
            while (xCordinate > 0 && newBoardState.board[xCordinate][yCordinate] == opponent) xCordinate--;
            if (xCordinate >= 0 && newBoardState.board[xCordinate][yCordinate] == player) {
                while (xCordinate != validI - 1) newBoardState.board[++xCordinate][yCordinate] = player;
            }
        }
        xCordinate = validI;
        if (xCordinate - 1 >= 0 && yCordinate + 1 <= 7 && newBoardState.board[xCordinate - 1][yCordinate + 1] == opponent) {
            xCordinate = xCordinate - 1;
            yCordinate = yCordinate + 1;
            while (xCordinate > 0 && yCordinate < 7 && newBoardState.board[xCordinate][yCordinate] == opponent) {
                xCordinate--;
                yCordinate++;
            }
            if (xCordinate >= 0 && yCordinate <= 7 && newBoardState.board[xCordinate][yCordinate] == player) {
                while (xCordinate != validI - 1 && yCordinate != validJ + 1) newBoardState.board[++xCordinate][--yCordinate] = player;
            }
        }
        xCordinate = validI;
        yCordinate = validJ;
        if (yCordinate - 1 >= 0 && newBoardState.board[xCordinate][yCordinate - 1] == opponent) {
            yCordinate = yCordinate - 1;
            while (yCordinate > 0 && newBoardState.board[xCordinate][yCordinate] == opponent) yCordinate--;
            if (yCordinate >= 0 && newBoardState.board[xCordinate][yCordinate] == player) {
                while (yCordinate != validJ - 1) newBoardState.board[xCordinate][++yCordinate] = player;
            }
        }
        yCordinate = validJ;
        if (yCordinate + 1 <= 7 && newBoardState.board[xCordinate][yCordinate + 1] == opponent) {
            yCordinate = yCordinate + 1;
            while (yCordinate < 7 && newBoardState.board[xCordinate][yCordinate] == opponent) yCordinate++;
            if (yCordinate <= 7 && newBoardState.board[xCordinate][yCordinate] == player) {
                while (yCordinate != validJ + 1) newBoardState.board[xCordinate][--yCordinate] = player;
            }
        }
        yCordinate = validJ;
        if (xCordinate + 1 <= 7 && yCordinate - 1 >= 0 && newBoardState.board[xCordinate + 1][yCordinate - 1] == opponent) {
            xCordinate = xCordinate + 1;
            yCordinate = yCordinate - 1;
            while (xCordinate < 7 && yCordinate > 0 && newBoardState.board[xCordinate][yCordinate] == opponent) {
                xCordinate++;
                yCordinate--;
            }
            if (xCordinate <= 7 && yCordinate >= 0 && newBoardState.board[xCordinate][yCordinate] == player) {
                while (xCordinate != validI + 1 && yCordinate != validJ - 1) newBoardState.board[--xCordinate][++yCordinate] = player;
            }
        }
        xCordinate = validI;
        yCordinate = validJ;
        if (xCordinate + 1 <= 7 && newBoardState.board[xCordinate + 1][yCordinate] == opponent) {
            xCordinate = xCordinate + 1;
            while (xCordinate < 7 && newBoardState.board[xCordinate][yCordinate] == opponent) xCordinate++;
            if (xCordinate <= 7 && newBoardState.board[xCordinate][yCordinate] == player) {
                while (xCordinate != validI + 1) newBoardState.board[--xCordinate][yCordinate] = player;
            }
        }
        xCordinate = validI;

        if (xCordinate + 1 <= 7 && yCordinate + 1 <= 7 && newBoardState.board[xCordinate + 1][yCordinate + 1] == opponent) {
            xCordinate = xCordinate + 1;
            yCordinate = yCordinate + 1;
            while (xCordinate < 7 && yCordinate < 7 && newBoardState.board[xCordinate][yCordinate] == opponent) {
                xCordinate++;
                yCordinate++;
            }
            if (xCordinate <= 7 && yCordinate <= 7 && newBoardState.board[xCordinate][yCordinate] == player)
                while (xCordinate != validI + 1 && yCordinate != validJ + 1) newBoardState.board[--xCordinate][--yCordinate] = player;
        }

        newBoardState.updateScores();
        return newBoardState;
    }

    // Wighted Search
    public Board findNextMove(Board board, int playerNo) {
        Board tempBoard = board;
        char player;
        char opponent;

        if (playerNo == 1) {
            player = PLAYER;
            opponent = OPPONENT;
        } else {
            player = OPPONENT;
            opponent = PLAYER;
        }

        ArrayList<Board> possibleStates = new ArrayList<>();
        HashSet<Point> placeablePositions = tempBoard.getPlaceableLocations(player, opponent);

        for (Point p : placeablePositions) {
            possibleStates.add(placeMove(p, player, opponent, tempBoard));
        }

        ArrayList<Integer> Scores = new ArrayList<Integer>();
        int size = possibleStates.size();
        for (int i = 0; i < size; i++) {
            Board b = possibleStates.get(i);
            int score = 0;
            int iteration = 0;
            while (iteration < 800) {
                int gameScore = playCompleteGame(b, player, opponent);
                score += gameScore;
                iteration++;
            }
            Scores.add(score);
        }

        int largest = 0;
        for (int i = 1; i < Scores.size(); i++) {
            if (Scores.get(i) > Scores.get(largest)) {
                largest = i;
            }
        }
        return possibleStates.get(largest);
    }

    // Making a new Move according to the given state
    public Board makeNewMove(Board board, char player, char opponent) {
        HashSet<Point> placeablePositions = board.getPlaceableLocations(player, opponent);
        ArrayList<Point> pP = new ArrayList<>(placeablePositions);
        if (!placeablePositions.isEmpty()) {
            Random rand = new Random();
            int randomIndex = rand.nextInt(placeablePositions.size());
            Point p = pP.get(randomIndex);
            return placeMove(p, player, opponent, board);
        }
        return board;
    }

    // Playing the complete game
    public int playCompleteGame(Board board, char player, char opponent) {
        while (board.remScore != 0) {
            // Make move on our side
            board = makeNewMove(board, opponent, player);
            if (board.gameResult() == Board.WHITEWON) {
                return 5;
            }
            // Make move from computer side
            board = makeNewMove(board, player, opponent);
            if (board.gameResult() == Board.BLACKWON) {
                return -20;
            }

            if(board.gameResult() == Board.DRAW)
            {
                return 0;
            }
        }
        return 0;
    }
}
