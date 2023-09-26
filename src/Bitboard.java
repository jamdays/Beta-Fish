import java.util.Queue;
import java.util.Stack;

public class Bitboard {
    public static final String RED = "\u001B[31m";
    public static final String YELLOW = "\u001B[33m";
    public static final String RESET = "\u001B[0m";
    private int[] pOneboards;

    private int[] pTwoboards;
    private int h;
    private int l;
    private int[] heights;

    private int[] adds;
    boolean won;
    boolean turn;
    Stack<Integer> moves;

    public Bitboard(int h){
        turn = true;
        pOneboards = new int[h];
        pTwoboards = new int[h];
        heights = new int[h+1];
        adds = new int[h+1];
        moves = new Stack<Integer>();
        this.h = h;
        this.l = h+1;
        won = false;
        int add = 1;
        for(int i = 0; i < adds.length; i++){
            heights[i] = -1;
            adds[i] = add;
            add *= 2;
        }
    }
    public Bitboard(){
        h =6;
        turn = true;
        pOneboards = new int[h];
        pTwoboards = new int[h];
        heights = new int[h+1];
        adds = new int[h+1];
        moves = new Stack<Integer>();
        this.l = h+1;
        won = false;
        int add = 1;
        for(int i = 0; i < adds.length; i++){
            heights[i] = -1;
            adds[i] = add;
            add *= 2;
        }
    }
    public boolean isTurn() {
        return turn;
    }

    public int[] getpOneboards(){
        //no copy because I trust myself
        return pOneboards;
    }

    public int[] getpTwoboards(){
        return pTwoboards;
    }

    public boolean canPlay(int c){
        return c > 0 && c < heights.length && !won && (heights[c]+1) < h;
    }

    public int getWidth(){
        return l;
    }

    public void play(int c){
        if((heights[c]+1) < h && !won) {
            if(turn)
                pOneboards[heights[c] + 1] = pOneboards[heights[c] + 1] | adds[c];
            else
                pTwoboards[heights[c] + 1] = pTwoboards[heights[c] + 1] | adds[c];
            turn = !turn;
            moves.push(c);
            heights[c]++;
        }
        //turn has already changed, so it was actually ptwo who just played
        if(turn){
            if (((pTwoboards[heights[c]] & (pTwoboards[heights[c]] >> 1) & (pTwoboards[heights[c]]>>2) & (pTwoboards[heights[c]]>>3)) > 0) ||
                    ((pTwoboards[heights[c]] & (pTwoboards[heights[c]] << 1) & (pTwoboards[heights[c]]<<2) & (pTwoboards[heights[c]]<<3)) > 0)){
                //lr
                won = true;
            } else if (heights[c] > 2 && (pTwoboards[heights[c]] & pTwoboards[heights[c]-1] & pTwoboards[heights[c]-2] & pTwoboards[heights[c]-3]) > 0){
                //up-down
                won = true;
            } else if (
                    //diagonals
                 (heights[c] > 2 && (pTwoboards[heights[c]] & (pTwoboards[heights[c]-1]>>1) & (pTwoboards[heights[c]-2]>>2) & (pTwoboards[heights[c]-3]>>3)) > 0) ||
                            heights[c] > 1 && heights[c] < h-1 &&((pTwoboards[heights[c]] & (pTwoboards[heights[c]-1]>>1) & (pTwoboards[heights[c]-2]>>2) & (pTwoboards[heights[c]+1]<<1)) > 0) ||
                            heights[c] > 0 && heights[c] < h-2 && ((pTwoboards[heights[c]] & (pTwoboards[heights[c]-1]>>1) & (pTwoboards[heights[c]+1]<<1) & (pTwoboards[heights[c]+2]<<2)) > 0 )||
                            heights[c] > -1 && heights[c] < h-3 &&((pTwoboards[heights[c]] & (pTwoboards[heights[c]+1]<<1) & (pTwoboards[heights[c]+2]<<2) & (pTwoboards[heights[c]+3]<<3)) > 0) ||
                            heights[c] > 2 && ((pTwoboards[heights[c]] & (pTwoboards[heights[c]-1]<<1) & (pTwoboards[heights[c]-2]<<2) & (pTwoboards[heights[c]-3]<<3)) > 0) ||
                            (heights[c] > 1 && heights[c] < h-1 && ((pTwoboards[heights[c]] & (pTwoboards[heights[c]-1]<<1) & (pTwoboards[heights[c]-2]<<2) & (pTwoboards[heights[c]+1]>>1)) > 0)) ||
                            (heights[c] > 0 && heights[c] < h-2 && ((pTwoboards[heights[c]] & (pTwoboards[heights[c]-1]<<1) & (pTwoboards[heights[c]+2]>>2) & (pTwoboards[heights[c]+1]>>1)) > 0) )||
                            (heights[c] > -1 && heights[c] < h-3 &&((pTwoboards[heights[c]] & (pTwoboards[heights[c]+1]>>1) & (pTwoboards[heights[c]+2]>>2) & (pTwoboards[heights[c]+3]>>3)) > 0))
            ){
                won = true;
            }
        }
        else{
            if (((pOneboards[heights[c]] & (pOneboards[heights[c]] >> 1) & (pOneboards[heights[c]]>>2) & (pOneboards[heights[c]]>>3)) > 0) ||
                    ((pOneboards[heights[c]] & (pOneboards[heights[c]] << 1) & (pOneboards[heights[c]]<<2) & (pOneboards[heights[c]]<<3)) > 0)){
                won = true;
            } else if (heights[c] > 2 && (pOneboards[heights[c]] & pOneboards[heights[c]-1] & pOneboards[heights[c]-2] & pOneboards[heights[c]-3]) > 0){
                won = true;
            } else if (
                   (heights[c] > 2 && (pOneboards[heights[c]] & (pOneboards[heights[c]-1]>>1) & (pOneboards[heights[c]-2]>>2) & (pOneboards[heights[c]-3]>>3)) > 0) ||
                            heights[c] > 1 && heights[c] < h-1 &&((pOneboards[heights[c]] & (pOneboards[heights[c]-1]>>1) & (pOneboards[heights[c]-2]>>2) & (pOneboards[heights[c]+1]<<1)) > 0) ||
                            heights[c] > 0 && heights[c] < h-2 && ((pOneboards[heights[c]] & (pOneboards[heights[c]-1]>>1) & (pOneboards[heights[c]+2]<<2) & (pOneboards[heights[c]+1]<<1)) > 0 )||
                            heights[c] > -1 && heights[c] < h-3 &&((pOneboards[heights[c]] & (pOneboards[heights[c]+1]<<1) & (pOneboards[heights[c]+2]<<2) & (pOneboards[heights[c]+3]<<3)) > 0) ||
                            heights[c] > 2 && ((pOneboards[heights[c]] & (pOneboards[heights[c]-1]<<1) & (pOneboards[heights[c]-2]<<2) & (pOneboards[heights[c]-3]<<3)) > 0) ||
                            (heights[c] > 1 && heights[c] < h-1 && ((pOneboards[heights[c]] & (pOneboards[heights[c]-1]<<1) & (pOneboards[heights[c]-2]<<2) & (pOneboards[heights[c]+1]>>1)) > 0)) ||
                            (heights[c] > 0 && heights[c] < h-2 && ((pOneboards[heights[c]] & (pOneboards[heights[c]-1]<<1) & (pOneboards[heights[c]+2]>>2) & (pOneboards[heights[c]+1]>>1)) > 0) )||
                            (heights[c] > -1 && heights[c] < h-3 &&((pOneboards[heights[c]] & (pOneboards[heights[c]+1]>>1) & (pOneboards[heights[c]+2]>>2) & (pOneboards[heights[c]+3]>>3)) > 0))
            ){
                won = true;
            }
        }

    }


    public void takeBack(){
        int c = moves.pop();
        if(turn)
            pTwoboards[heights[c]] = pTwoboards[heights[c]] ^ adds[c];
        else
            pOneboards[heights[c]] = pOneboards[heights[c]] ^ adds[c];
        turn = !turn;
        won = false;
        heights[c]--;
    }

    public String fancyBoard(){
        String out = "";
        for(int r = pTwoboards.length-1; r > -1; r--){
            int i = 1;
            for(int c = 0; c < heights.length; c++){
                if((pOneboards[r] ^ i) < pOneboards[r]) {
                    out += RED + "1" + RESET + " ";
                }
                else if ((pTwoboards[r] ^ i) < pTwoboards[r])
                    out += YELLOW + "2" + RESET + " ";
                else{
                    out += 0 + " ";
                }
                i *=2;
            }
            out += "\n";
        }
        return out;
    }

    public boolean isWon(){
        return won;
    }
}
