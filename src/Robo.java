import java.util.HashSet;
import java.util.Scanner;

public class Robo {
    private Board b;
    public Robo(Board b){
        this.b = b;
    }
    //RESOLVED: fix play(B) so that it uses takebacks and one board, and ints
    //RESOLVED: alpha beta pruning.
    //RESOLVED ? : it will be faster if I use a hashset to keep track of collisions.
    // TO - DO: fix order so it happens from middle out, instead of LR. Better for Alpha-Beta pruning probably
    public int play(int depth, Bitboard b){
        int move = -1;
        boolean playerOne = b.isTurn();
        if(playerOne){
            int k = b.getWidth()/2;
            int max = Integer.MIN_VALUE;
            for(int i = 0; i < b.getWidth(); i++) {
                int c = i;
                if (i %2 == 0){
                    c = -1*i;
                }
                int temp =  Integer.MIN_VALUE;
                if(b.canPlay(k+c)) {
                    b.play(k+c);
                    temp = minimax(b, depth,false, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    b.takeBack();
                }
                k = k +c;
                System.out.println(k+ ": " +temp);
                if(temp > max) {
                    max = temp;
                    move = k;
                }
            }
            return move;
        }else{
            int k = b.getWidth()/2;
            int min = Integer.MAX_VALUE;
            for(int i = 0; i < b.getWidth(); i++){
                int temp = Integer.MAX_VALUE;
                int c = i;
                if (i %2 == 0){
                    c = -1*i;
                }
                if(b.canPlay(k+c)) {
                    b.play(k+c);
                    temp = minimax(b, depth, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    b.takeBack();
                }
                k = k+c;
                System.out.println(k+ ": " +temp);
                if(temp < min) {
                    min = temp;
                    move = k;
                }
            }
            return move;
        }
    }
    public int play(int depth, Board b){
        int move = -1;
        boolean playerOne = b.isTurn();
        if(playerOne){
            int k = b.getWidth()/2;
            int max = Integer.MIN_VALUE;
            for(int i = 0; i < b.getWidth(); i++) {
                int c = i;
                if (i %2 == 0){
                    c = -1*i;
                }
                int temp =  Integer.MIN_VALUE;
                if(b.canPlay(k+c)) {
                    b.play(k+c);
                    temp = minimax(b, depth,false, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    b.takeBack();
                }
                k = k +c;
                System.out.println(k+ ": " +temp);
                if(temp > max) {
                    max = temp;
                    move = k;
                }
            }
            return move;
        }else{
            int k = b.getWidth()/2;
            int min = Integer.MAX_VALUE;
            for(int i = 0; i < b.getWidth(); i++){
                int temp = Integer.MAX_VALUE;
                int c = i;
                if (i %2 == 0){
                    c = -1*i;
                }
                if(b.canPlay(k+c)) {
                    b.play(k+c);
                    temp = minimax(b, depth, true, Integer.MIN_VALUE, Integer.MAX_VALUE);
                    b.takeBack();
                }
                k = k+c;
                System.out.println(k+ ": " +temp);
                if(temp < min) {
                    min = temp;
                    move = k;
                }
            }
            return move;
        }
    }
    public int playwithHash(int depth){
        int move = 0;
        boolean playerOne = b.getTurn() == 1;
        if(playerOne){
            int max = Integer.MIN_VALUE;
            for(int i = 0; i < b.getWidth(); i++) {
                int temp =  Integer.MIN_VALUE;
                if(b.canPlay(i)) {
                    HashSet<int[][]> boards = new HashSet<int[][]>();
                    b.play(i);
                    temp = minimax(b,depth,false, boards);
                    b.takeBack();
                }
                System.out.println(temp);
                if(temp > max) {
                    max = temp;
                    move = i;
                }
            }
            return move;
        }else{
            int min = Integer.MAX_VALUE;
            for(int i = 0; i < b.getWidth(); i++){
                int temp = Integer.MAX_VALUE;
                if(b.canPlay(i)) {
                    HashSet<int[][]> boards = new HashSet<int[][]>();
                    b.play(i);
                    temp = minimax(b, depth, true, boards);
                    b.takeBack();
                }
                System.out.println(temp);
                if(temp < min) {
                    min = temp;
                    move = i;
                }
            }
            return move;
        }
    }
    //player one == true is positive
    public double minimaxWITHOUTTAKEBACKS(Board b, int depth, boolean playerOne){
        if(depth == 0 || b.isWon()){
            return eval(b);
        }
        if(playerOne){
            double max = Double.MIN_VALUE;
            for(Board c: b.getChildren()) {
                double temp = Double.MIN_VALUE;
                if(c != null)
                    temp = minimax(c, depth - 1, false);
                if(temp > max)
                    max = temp;
            }
            return max;
        }else{
            double min = Double.MAX_VALUE;
            for(Board c: b.getChildren()){
                double temp = Double.MAX_VALUE;
                if(c != null)
                    temp = minimax(c, depth-1, true);
                if(temp < min)
                    min = temp;
            }
            return min;
        }
    }
    public int minimax(Board b, int depth, boolean playerOne){
        if(depth == 0 || b.isWon()){
            return eval(b);
        }
        if(playerOne){
            int max = Integer.MIN_VALUE;
            for(int i = 0; i < b.getWidth(); i++) {
                int temp = Integer.MIN_VALUE;
                if(b.canPlay(i)) {
                    b.play(i);
                    temp = minimax(b, depth - 1, false);
                    b.takeBack();
                }
                if(temp > max)
                    max = temp;
            }
            return max;
        }else{
            int min = Integer.MAX_VALUE;
            for(int i = 0; i < b.getWidth(); i++){
                int temp = Integer.MAX_VALUE;
                if(b.canPlay(i)) {
                    b.play(i);
                    temp = minimax(b, depth - 1, true);
                    b.takeBack();
                }
                if(temp < min)
                    min = temp;
            }
            return min;
        }
    }


    public int minimax(Bitboard b, int depth, boolean playerOne, int alpha, int beta){
        if(depth == 0 || b.isWon()){
            return eval(b);
        }
        if(playerOne){
            int max = Integer.MIN_VALUE;
            int k = b.getWidth()/2;
            for(int i = 0; i < b.getWidth(); i++) {
                int temp = Integer.MIN_VALUE;
                int c = i;
                if (i %2 == 0){
                    c = -1*i;
                }
                if(b.canPlay(k+c)) {
                    b.play(k+c);
                    temp = minimax(b, depth - 1, false, alpha, beta);
                    b.takeBack();
                    if(temp >= beta) {
                        max = temp;
                        break;
                    }
                }
                k = k+c;
                if(temp > max) {
                    //Fatal mistake, I was missing min = temp because otherwise max = integer.minvalue which sends the message of forced win for the other player
                    max = temp;
                    alpha = max;
                }
            }
            return max;
        }else{
            int min = Integer.MAX_VALUE;
            int k = b.getWidth()/2;
            for(int i = 0; i < b.getWidth(); i++){
                int temp = Integer.MAX_VALUE;
                int c = i;
                if (i %2 == 0){
                    c = -1*i;
                }
                if(b.canPlay(k+c)) {
                    b.play(k+c);
                    temp = minimax(b, depth - 1, true, alpha, beta);
                    b.takeBack();
                    if(temp <= alpha) {
                        //Fatal mistake, I was missing min = temp because otherwise min = integer.maxvalue which sends the message of forced win for the other player
                        min = temp;
                        break;
                    }
                }
                k = k+c;
                if(temp < min) {
                    min = temp;
                    beta = min;
                }
            }
            return min;
        }
    }
    public int minimax(Board b, int depth, boolean playerOne, int alpha, int beta){
        if(depth == 0 || b.isWon()){
            return eval(b);
        }
        if(playerOne){
            int max = Integer.MIN_VALUE;
            int k = b.getWidth()/2;
            for(int i = 0; i < b.getWidth(); i++) {
                int temp = Integer.MIN_VALUE;
                int c = i;
                if (i %2 == 0){
                    c = -1*i;
                }
                if(b.canPlay(k+c)) {
                    b.play(k+c);
                    temp = minimax(b, depth - 1, false, alpha, beta);
                    b.takeBack();
                    if(temp >= beta) {
                        max = temp;
                        break;
                    }
                }
                k = k+c;
                if(temp > max) {
                    //Fatal mistake, I was missing min = temp because otherwise max = integer.minvalue which sends the message of forced win for the other player
                    max = temp;
                    alpha = max;
                }
            }
            return max;
        }else{
            int min = Integer.MAX_VALUE;
            int k = b.getWidth()/2;
            for(int i = 0; i < b.getWidth(); i++){
                int temp = Integer.MAX_VALUE;
                int c = i;
                if (i %2 == 0){
                    c = -1*i;
                }
                if(b.canPlay(k+c)) {
                    b.play(k+c);
                    temp = minimax(b, depth - 1, true, alpha, beta);
                    b.takeBack();
                    if(temp <= alpha) {
                        //Fatal mistake, I was missing min = temp because otherwise min = integer.maxvalue which sends the message of forced win for the other player
                        min = temp;
                        break;
                    }
                }
                k = k+c;
                if(temp < min) {
                    min = temp;
                    beta = min;
                }
            }
            return min;
        }
    }

    public int minimax(Board b, int depth, boolean playerOne, HashSet<int[][]> boards){
        if(depth == 0 || b.isWon()){
            return eval(b);
        }
        if(playerOne){
            int max = Integer.MIN_VALUE;
            for(int i = 0; i < b.getWidth(); i++) {
                int temp = Integer.MIN_VALUE;
                if(b.canPlay(i) && !boards.contains(b.getBoard())) {
                    boards.add(b.getBoard());
                    b.play(i);
                    temp = minimax(b, depth - 1, false, boards);
                    b.takeBack();
                }
                if(temp > max)
                    max = temp;
            }
            return max;
        }else{
            int min = Integer.MAX_VALUE;
            for(int i = 0; i < b.getWidth(); i++){
                int temp = Integer.MAX_VALUE;
                if(b.canPlay(i) && !boards.contains(b.getBoard())) {
                    boards.add(b.getBoard());
                    b.play(i);
                    temp = minimax(b, depth - 1, true, boards);
                    b.takeBack();
                }
                if(temp < min)
                    min = temp;
            }
            return min;
        }
    }
    /*
    4/17/2023
    The problem with the eval function is that I want it to choose the one with the shortest path,
    but instead it chooses the first forced win it sees.
    4/20/2023
    I upgraded the eval function so that it considers 3 in a row in its evaluation
    also I changed evaluation from double to int for speed
     */
    public int eval(Board b){
        int evaluation = 0;
        int[][] cboard = b.getBoard();
        if(b.isWon()){
            //turn changes before it is won
            if(b.getTurn() == 2)
                return Integer.MAX_VALUE;
            else
                return Integer.MIN_VALUE;
        } else{
            for(int r = 0; r < b.getBoard().length; r++){
                for(int c = 0; c < b.getBoard()[r].length; c++){
                    int x = c - cboard[r].length/2;
                    int y = r - cboard.length/2;
                    if(cboard[r][c] == 1){
                        evaluation += 100/(x*x+y*y+2);
                        //System.out.println(1/(x*x+y*y+2));
                    } else if (cboard[r][c] == 2){
                        evaluation -= 100/(x*x+y*y+2);
                       // System.out.println(-1/((x*x)+y*y+2));
                    } else{
                        cboard[r][c] = 1;
                        if(searchOTHERDiagonally(r,c,0,0, 1, cboard) +searchOTHERDiagonally(r,c,0,0, -1, cboard) > 4
                                ||searchDiagonally(r,c,0,0, 1, cboard) +searchDiagonally(r,c,0,0, -1, cboard) > 4
                                ||searchHorizontally(r,c,0,0, 1, cboard) +searchHorizontally(r,c,0,0, -1, cboard) > 4){
                            if(c % 2 == 0){
                                evaluation += 125 - r*2;
                            } else{
                                evaluation += 100 - r*2;
                            }
                        }
                        cboard[r][c] = 2;
                        //greater than 4 because of double count
                        if(searchOTHERDiagonally(r,c,0,1, 1, cboard) +searchOTHERDiagonally(r,c,0,1, -1, cboard) > 4
                                ||searchDiagonally(r,c,0,1, 1, cboard) +searchDiagonally(r,c,0,1, -1, cboard) > 4
                                ||searchHorizontally(r,c,0,1, 1, cboard) +searchHorizontally(r,c,0,1, -1, cboard) > 4){
                            if(c % 2 == 1){
                                //even-odd strategy, also weighted so that the closer it is to the bottom, the better.
                                evaluation -= 125+ r*2;
                            } else{
                                evaluation -= 100+ r*2;
                            }
                        }
                        cboard[r][c] = 0;
                    }
                }
            }

        }
        //System.out.println(evaluation + ":board eval");
        return evaluation;
    }

    public int eval(Bitboard b){
        int evaluation =0;
        if(b.isWon()) {
            //turn changes before it is won
            if (!b.isTurn())
                return Integer.MAX_VALUE;
            else
                return Integer.MIN_VALUE;
        }
        else{
            for (int r = 0; r < b.getWidth()-1; r++){
                int y = r - (b.getWidth()-1)/2;
                for(int c = 0; c < b.getWidth(); c++){
                    int i = 1;
                    // if the int is less when & with the bit then there is a bit at that spot.
                    int x = c - b.getWidth()/2;
                    int[] board = b.getpOneboards();
                    int temp = board[r];
                    if((temp ^ i) < board[r]){
                        evaluation += 100/(x*x+y*y+2);
                    } else {
                        board = b.getpTwoboards();
                        temp = board[r];
                        if ((temp ^ i) < board[r]) {
                            evaluation -= 100 / (x * x + y * y + 2);
                        }
                    }

                    i *=2;
                }

            }
        }
        return evaluation;

    }
    private int searchHorizontally(int row, int col, int count, int turn, int dx, int[][] board){
        if(col < 0 || col >= board[0].length || board[row][col] != turn+1){
            return count;
        }
        return searchHorizontally(row, col +dx, count+1, turn, dx, board);
    }
    private int searchDiagonally(int row, int col, int count, int turn, int dx, int[][] board){
        if(row < 0 || row >= board.length || col < 0 || col >= board[0].length || board[row][col] != turn+1){
            return count;
        }
        return searchDiagonally(row +dx, col +dx, count+1, turn, dx, board);
    }
    private int searchOTHERDiagonally(int row, int col, int count, int turn, int dx, int[][] board){
        if(row < 0 || row >= board.length || col < 0 || col >= board[0].length || board[row][col] != turn+1){
            return count;
        }
        return searchOTHERDiagonally(row -dx, col +dx, count+1, turn, dx, board);
    }

    public static void main(String[] args){
        Board b = new Board();
        Bitboard bitB = new Bitboard();
        Robo robot = new Robo(b);
        Scanner s = new Scanner(System.in);
        System.out.println(b);
        while(!bitB.isWon()){
            int k = robot.play(8, b);
            bitB.play(k);
            b.play(k);
            System.out.println(bitB.fancyBoard());
            int c = robot.play(9, bitB);
            System.out.println((c+1) + " " +bitB.isWon());
            bitB.play(c);
            b.play(c);

            System.out.println(bitB.fancyBoard());
            if(c == -1 || k == -1)
                break;
        }
        System.out.println(b.getTurn());
    }
}
