import java.util.ArrayList;
import java.util.Queue;
import java.util.Stack;

public class Board{

    public static final String RED = "\u001B[31m";
    public static final String YELLOW = "\u001B[33m";
    public static final String RESET = "\u001B[0m";
    private int[][] board;
    private int[] heights;
    private int turn;
    private boolean won;
    private Stack<Integer> moves;
    public Board(int size){
        moves = new Stack<Integer>();
        board = new int[size-1][size];
        heights = new int[size];
        turn = 0;
    }
    public Board(Board b){
        moves = new Stack<Integer>();
        int[][] init = b.board;
        this.heights = new int[b.heights.length];
        board = new int[init.length][init.length+1];
        turn = b.getTurn() -1;
        for(int i = 0; i < init.length; i++){
            for(int j = 0; j < init[i].length; j++){
                board[i][j] = init[i][j];
                if(i == 0){
                    this.heights[j] = b.heights[j];
                }
            }
        }

    }

    public Board(){
        moves = new Stack<Integer>();
        int size = 7;
        board = new int[size-1][size];
        heights = new int[size];
        turn = 0;
    }

    public boolean canPlay(int c){
        return !isWon() && c < heights.length && c > -1 && heights[c] < board.length && board[board.length-1-heights[c]][c] == 0;
    }
    public void play(int c){
        if(!canPlay(c)){
            return;
        }
        board[board.length-1-heights[c]][c] = turn +1;
        heights[c]++;
        moves.push(c);
        won = searchVertically(board.length - heights[c], c, 0, turn) ||
                    searchHorizontally(board.length- heights[c], c, 0, turn, 1) + searchHorizontally(board.length-heights[c], c, 0, turn, -1) > 4 ||
                    searchDiagonally(board.length-heights[c], c, 0, turn, 1) + searchDiagonally(board.length-heights[c], c, 0, turn, -1) > 4 ||
                searchOTHERDiagonally(board.length-heights[c], c, 0, turn, 1) + searchOTHERDiagonally(board.length-heights[c], c, 0, turn, -1) > 4;
        turn = (turn+1)%2;
        // > 4 because it double counts the starting

    }
    public boolean isWon(){
        return won;
    }

    public void takeBack(){
        int c = moves.pop();
        board[board.length - heights[c]][c] = 0;
        heights[c]--;
        turn = (turn+1)%2;
        if(won){
            won = false;
        }
    }
    private boolean searchVertically(int row, int col, int count, int turn){
        if(count > 3){
            return true;
        } else if(row > board.length-1 || board[row][col] != turn+1){
            return false;
        }
        return searchVertically(row+1, col, count+1, turn);
    }

    private int searchHorizontally(int row, int col, int count, int turn, int dx){
        if(col < 0 || col >= board[0].length || board[row][col] != turn+1){
            return count;
        }
        return searchHorizontally(row, col +dx, count+1, turn, dx);
    }
    private int searchDiagonally(int row, int col, int count, int turn, int dx){
        if(row < 0 || row >= board.length || col < 0 || col >= board[0].length || board[row][col] != turn+1){
            return count;
        }
        return searchDiagonally(row +dx, col +dx, count+1, turn, dx);
    }
    private int searchOTHERDiagonally(int row, int col, int count, int turn, int dx){
        if(row < 0 || row >= board.length || col < 0 || col >= board[0].length || board[row][col] != turn+1){
            return count;
        }
        return searchOTHERDiagonally(row -dx, col +dx, count+1, turn, dx);
    }

    public String toString(){
        String out = "";
        for(int[] a: board){
            for(int i: a){
                out += i + " ";
            }
            out += "\n";
        }
        return out;
    }

    public String fancyBoard(){
        String out = "";
        for(int[] a: board){
            for(int i: a){
                if(i == 1)
                    out +=  RED + "1" + RESET + " ";
                else if (i == 2)
                    out += YELLOW + "2" + RESET + " ";
                else{
                    out += i + " ";
                }
            }
            out += "\n";
        }
        return out;
    }

    public int[][] getBoard() {
        int[][] out = new int[board.length][board.length+1];
        for(int r = 0; r < board.length; r++){
            for(int c = 0; c < board.length+1; c++){
                out[r][c] =  board[r][c];
            }
        }
        return out;
    }

    public int getWidth(){
        return heights.length;
    }

    public int getTurn() {
        return turn + 1;
    }

    public boolean isTurn(){
        return turn % 2 == 0;
    }
    public Iterable<Board> getChildren(){
        ArrayList<Board> children = new ArrayList<Board>();
        for(int i = 0; i < heights.length; i++){
            Board c = new Board(this);
            if(c.canPlay(i)) {
                c.play(i);
                children.add(c);
            }
            else
                children.add(null);
        }
        return children;
    }
}
