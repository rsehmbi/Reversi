// Code logic http://www.samsoft.org.uk/reversi/strategy.htm, http://www.codebytes.in/2014/12/reversi-two-players-java-program.html
import java.util.HashSet;
import java.util.Set;

class Board {
    public char[][] board = new char[8][8];
    public static int DRAW = 0;
    public static int IN_PROGRESS = -1;
    public static int WHITEWON = 1;
    public int WhiteScore;
    public static int BLACKWON = 2;
    public int remScore;
    private char VALIDEMPTYPOSITION = '*';
    private char EMPTY = '-';
    private char WHITE = 'W';
    public int BlackScore;
    private char BLACK = 'B';
    private int NUM_ROWS = 8;
    private int NUM_COLUMNS = 8;

    // Default Construtor for Board class.
    public Board(){
        for (int iteratori = 0; iteratori < NUM_ROWS; iteratori++)
            for (int iteratorj = 0; iteratorj < NUM_COLUMNS; iteratorj++) {
                this.board[iteratori][iteratorj] = EMPTY;

                if (iteratori == 3 && iteratorj == 3) {
                    this.board[iteratori][iteratorj] = WHITE;
                }

                if (iteratori == 3 && iteratorj == 4) {
                    this.board[iteratori][iteratorj] = BLACK;
                }

                if (iteratori == 4 && iteratorj == 3) {
                    this.board[iteratori][iteratorj] = BLACK;
                }

                if (iteratori == 4 && iteratorj == 4) {
                    this.board[iteratori][iteratorj] = WHITE;
                }
            }
    }

    Board(Board board) {
        for (int i = 0; i < NUM_ROWS; i++) {
            for (int j = 0; j < NUM_COLUMNS; j++) {
                this.board[i][j] = board.board[i][j];
            }
        }

        this.BlackScore = board.BlackScore;
        this.WhiteScore = board.WhiteScore;
        this.remScore = board.remScore;
    }

    // Finding all the valid locations according to player and opponent.
    private HashSet<Point> findPlaceableLocations(char player, char opponent) {
        HashSet<Point> placeablePositions = new HashSet<>();
        for (int iteratori = 0; iteratori < 8; ++iteratori) {
            for (int iteratorj = 0; iteratorj < 8; ++iteratorj) {
                if (board[iteratori][iteratorj] == opponent) {
                    int validI = iteratori, validJ = iteratorj;
                    if (iteratori - 1 >= 0 && iteratorj - 1 >= 0 && board[iteratori - 1][iteratorj - 1] == EMPTY) {
                        iteratori += 1;
                        iteratorj += 1;
                        while (iteratori < 7 && iteratorj < 7 && board[iteratori][iteratorj] == opponent) {
                            iteratori++;
                            iteratorj++;
                        }
                        if (iteratori <= 7 && iteratorj <= 7 && board[iteratori][iteratorj] == player) placeablePositions.add(new Point(validI - 1, validJ - 1));
                    }
                    iteratori = validI;
                    iteratorj = validJ;
                    if (iteratori - 1 >= 0 && board[iteratori - 1][iteratorj] == EMPTY) {
                        iteratori = iteratori + 1;
                        while (iteratori < 7 && board[iteratori][iteratorj] == opponent) iteratori++;
                        if (iteratori <= 7 && board[iteratori][iteratorj] == player) placeablePositions.add(new Point(validI - 1, validJ));
                    }
                    iteratori = validI;
                    if (iteratori - 1 >= 0 && iteratorj + 1 <= 7 && board[iteratori - 1][iteratorj + 1] == EMPTY) {
                        iteratori += 1;
                        iteratorj -= 1;
                        while (iteratori < 7 && iteratorj > 0 && board[iteratori][iteratorj] == opponent) {
                            iteratori++;
                            iteratorj--;
                        }
                        if (iteratori <= 7 && iteratorj >= 0 && board[iteratori][iteratorj] == player) placeablePositions.add(new Point(validI - 1, validJ + 1));
                    }
                    iteratori = validI;
                    iteratorj = validJ;
                    if (iteratorj - 1 >= 0 && board[iteratori][iteratorj - 1] == EMPTY) {
                        iteratorj = iteratorj + 1;
                        while (iteratorj < 7 && board[iteratori][iteratorj] == opponent) iteratorj++;
                        if (iteratorj <= 7 && board[iteratori][iteratorj] == player) placeablePositions.add(new Point(validI, validJ - 1));
                    }
                    iteratorj = validJ;
                    if (iteratorj + 1 <= 7 && board[iteratori][iteratorj + 1] == EMPTY) {
                        iteratorj = iteratorj - 1;
                        while (iteratorj > 0 && board[iteratori][iteratorj] == opponent) iteratorj--;
                        if (iteratorj >= 0 && board[iteratori][iteratorj] == player) placeablePositions.add(new Point(validI, validJ + 1));
                    }
                    iteratorj = validJ;
                    if (iteratori + 1 <= 7 && iteratorj - 1 >= 0 && board[iteratori + 1][iteratorj - 1] == EMPTY) {
                        iteratori = iteratori - 1;
                        iteratorj = iteratorj + 1;
                        while (iteratori > 0 && iteratorj < 7 && board[iteratori][iteratorj] == opponent) {
                            iteratori = iteratori - 1;
                            iteratorj = iteratorj + 1;
                        }
                        if (iteratori >= 0 && iteratorj <= 7 && board[iteratori][iteratorj] == player) placeablePositions.add(new Point(validI + 1, validJ - 1));
                    }
                    iteratori = validI;
                    iteratorj = validJ;
                    if (iteratori + 1 <= 7 && board[iteratori + 1][iteratorj] == EMPTY) {
                        iteratori = iteratori - 1;
                        while (iteratori > 0 && board[iteratori][iteratorj] == opponent) iteratori--;
                        if (iteratori >= 0 && board[iteratori][iteratorj] == player) placeablePositions.add(new Point(validI + 1, validJ));
                    }
                    iteratori = validI;
                    if (iteratori + 1 <= 7 && iteratorj + 1 <= 7 && board[iteratori + 1][iteratorj + 1] == EMPTY) {
                        iteratori = iteratori - 1;
                        iteratorj = iteratorj - 1;
                        while (iteratori > 0 && iteratorj > 0 && board[iteratori][iteratorj] == opponent) {
                            iteratori = iteratori - 1;
                            iteratorj = iteratorj - 1;
                        }
                        if (iteratori >= 0 && iteratorj >= 0 && board[iteratori][iteratorj] == player) placeablePositions.add(new Point(validI + 1, validJ + 1));
                    }
                    iteratori = validI;
                    iteratorj = validJ;
                }
            }
        }
        return placeablePositions;
    }

    public void displayBoard(Board reversiBoard) {
        char topRow[] = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};

        System.out.print("\n  ");
        for (int i = 0; i < NUM_ROWS; i++) {
            System.out.print(topRow[i] + " ");
        }
        System.out.println();
        for (int iteratori = 0; iteratori < NUM_ROWS; ++iteratori) {
            System.out.print((iteratori + 1) + " ");
            for (int iteratorj = 0; iteratorj < NUM_COLUMNS; ++iteratorj)
                System.out.print(reversiBoard.board[iteratori][iteratorj] + " ");
            System.out.println();
        }
        System.out.println();
    }

    public int gameResult() {
        Set<Point> whitePlaceableLocations = this.getPlaceableLocations(WHITE, BLACK);
        Set<Point> blackPlaceableLocations = this.getPlaceableLocations(BLACK, WHITE);
        updateScores();

        if (remScore == 0) {
            if (WhiteScore > BlackScore) return WHITEWON;
            else if (BlackScore > WhiteScore) return BLACKWON;
            else return DRAW; //Draw
        }
        if (WhiteScore == 0 || BlackScore == 0) {
            if (WhiteScore > 0) return WHITEWON;
            else if (BlackScore > 0) return BLACKWON;
        }
        if (whitePlaceableLocations.isEmpty() && blackPlaceableLocations.isEmpty()) {
            if (WhiteScore > BlackScore) return WHITEWON;
            else if (BlackScore > WhiteScore) return BLACKWON;
            else return DRAW; //Draw
        }
        return IN_PROGRESS;
    }

    public HashSet<Point> getPlaceableLocations(char player, char opponent) {
        HashSet<Point> placeablePositions = findPlaceableLocations(player, opponent);
        return placeablePositions;
    }

    public void showPlaceableLocations(HashSet<Point> locations) {
        for (Point p : locations)
            board[p.getX()][p.getY()] = VALIDEMPTYPOSITION;
        displayBoard(this);
        for (Point p : locations)
            board[p.getX()][p.getY()] = EMPTY;
    }

    // Code inspired from youtube video https://www.youtube.com/watch?v=7zkl31VtHG4
    public void placeMove(Point p, char player, char opponent) {
        int Cordinatei = p.getX();
        int Cordinatej = p.getY();
        board[Cordinatei][Cordinatej] = player;
        int validI = Cordinatei;
        int validJ = Cordinatej;

        if (Cordinatei - 1 >= 0 && Cordinatej - 1 >= 0 && board[Cordinatei - 1][Cordinatej - 1] == opponent) {
            Cordinatei = Cordinatei - 1;
            Cordinatej = Cordinatej - 1;
            while (Cordinatei > 0 && Cordinatej > 0 && board[Cordinatei][Cordinatej] == opponent) {
                Cordinatei--;
                Cordinatej--;
            }
            if (Cordinatei >= 0 && Cordinatej >= 0 && board[Cordinatei][Cordinatej] == player) {
                while (Cordinatei != validI - 1 && Cordinatej != validJ - 1) board[++Cordinatei][++Cordinatej] = player;
            }
        }
        Cordinatei = validI;
        Cordinatej = validJ;
        if (Cordinatei - 1 >= 0 && board[Cordinatei - 1][Cordinatej] == opponent) {
            Cordinatei = Cordinatei - 1;
            while (Cordinatei > 0 && board[Cordinatei][Cordinatej] == opponent) Cordinatei--;
            if (Cordinatei >= 0 && board[Cordinatei][Cordinatej] == player) {
                while (Cordinatei != validI - 1) board[++Cordinatei][Cordinatej] = player;
            }
        }
        Cordinatei = validI;
        if (Cordinatei - 1 >= 0 && Cordinatej + 1 <= 7 && board[Cordinatei - 1][Cordinatej + 1] == opponent) {
            Cordinatei = Cordinatei - 1;
            Cordinatej = Cordinatej + 1;
            while (Cordinatei > 0 && Cordinatej < 7 && board[Cordinatei][Cordinatej] == opponent) {
                Cordinatei--;
                Cordinatej++;
            }
            if (Cordinatei >= 0 && Cordinatej <= 7 && board[Cordinatei][Cordinatej] == player) {
                while (Cordinatei != validI - 1 && Cordinatej != validJ + 1) board[++Cordinatei][--Cordinatej] = player;
            }
        }
        Cordinatei = validI;
        Cordinatej = validJ;
        if (Cordinatej - 1 >= 0 && board[Cordinatei][Cordinatej - 1] == opponent) {
            Cordinatej = Cordinatej - 1;
            while (Cordinatej > 0 && board[Cordinatei][Cordinatej] == opponent) Cordinatej--;
            if (Cordinatej >= 0 && board[Cordinatei][Cordinatej] == player) {
                while (Cordinatej != validJ - 1) board[Cordinatei][++Cordinatej] = player;
            }
        }
        Cordinatej = validJ;
        if (Cordinatej + 1 <= 7 && board[Cordinatei][Cordinatej + 1] == opponent) {
            Cordinatej = Cordinatej + 1;
            while (Cordinatej < 7 && board[Cordinatei][Cordinatej] == opponent) Cordinatej++;
            if (Cordinatej <= 7 && board[Cordinatei][Cordinatej] == player) {
                while (Cordinatej != validJ + 1) board[Cordinatei][--Cordinatej] = player;
            }
        }
        Cordinatej = validJ;
        if (Cordinatei + 1 <= 7 && Cordinatej - 1 >= 0 && board[Cordinatei + 1][Cordinatej - 1] == opponent) {
            Cordinatei = Cordinatei + 1;
            Cordinatej = Cordinatej - 1;
            while (Cordinatei < 7 && Cordinatej > 0 && board[Cordinatei][Cordinatej] == opponent) {
                Cordinatei++;
                Cordinatej--;
            }
            if (Cordinatei <= 7 && Cordinatej >= 0 && board[Cordinatei][Cordinatej] == player) {
                while (Cordinatei != validI + 1 && Cordinatej != validJ - 1) board[--Cordinatei][++Cordinatej] = player;
            }
        }
        Cordinatei = validI;
        Cordinatej = validJ;
        if (Cordinatei + 1 <= 7 && board[Cordinatei + 1][Cordinatej] == opponent) {
            Cordinatei = Cordinatei + 1;
            while (Cordinatei < 7 && board[Cordinatei][Cordinatej] == opponent) Cordinatei++;
            if (Cordinatei <= 7 && board[Cordinatei][Cordinatej] == player) {
                while (Cordinatei != validI + 1) board[--Cordinatei][Cordinatej] = player;
            }
        }
        Cordinatei = validI;

        if (Cordinatei + 1 <= 7 && Cordinatej + 1 <= 7 && board[Cordinatei + 1][Cordinatej + 1] == opponent) {
            Cordinatei = Cordinatei + 1;
            Cordinatej = Cordinatej + 1;
            while (Cordinatei < 7 && Cordinatej < 7 && board[Cordinatei][Cordinatej] == opponent) {
                Cordinatei++;
                Cordinatej++;
            }
            if (Cordinatei <= 7 && Cordinatej <= 7 && board[Cordinatei][Cordinatej] == player) while (Cordinatei != validI + 1 && Cordinatej != validJ + 1) board[--Cordinatei][--Cordinatej] = player;
        }
    }

    public void updateScores() {
        WhiteScore = 0;
        BlackScore = 0;
        remScore = 0;
        for (int iterator = 0; iterator < NUM_ROWS; iterator++) {
            for (int iteratorj = 0; iteratorj < NUM_COLUMNS; iteratorj++) {
                if (board[iterator][iteratorj] == WHITE) {
                    WhiteScore = WhiteScore + 1;
                }
                else if (board[iterator][iteratorj] == BLACK) {
                    BlackScore = BlackScore + 1;
                }
                else remScore++;
            }
        }
    }
    // Validating the user inputs
    public int validateUserInput(char humanInput) {
        char availableChoices[] = new char[]{'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H'};
        for (int index = 0; index < 8; ++index) {
            if (availableChoices[index] == Character.toLowerCase(humanInput) || availableChoices[index] == Character.toUpperCase(humanInput)) {
                return index;
            }
        }
        return -1;
    }

    public void printScore()
    {
        System.out.println("\nWhite: " + this.WhiteScore + " Black: " + this.BlackScore);
    }
}