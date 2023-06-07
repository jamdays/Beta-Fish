import java.util.Queue;
import java.util.Stack;

public class Bitboard {
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
        this.h = h;
        this.l = h+1;
        won = false;
        int add = 1;
        for(int i = 0; i < adds.length; i++){
            adds[i] = add;
            add *= 2;
        }
    }

    public boolean isTurn() {
        return turn;
    }

    public boolean canPlay(int c){
        return c > 0 && c < heights.length && !won && heights[c] < h;
    }

    public int getWidth(){
        return l;
    }

    public void play(int c){
        if(heights[c] < h && !won) {
            if(turn)
                pOneboards[heights[c] + 1] = pOneboards[heights[c] + 1] | adds[c];
            else
                pTwoboards[heights[c] + 1] = pTwoboards[heights[c] + 1] | adds[c];
            turn = !turn;
            moves.push(c);
        }
        //turn has already changed, so it was actually ptwo who just played
        if(turn){
            if ((pTwoboards[heights[c]] & pTwoboards[heights[c]] >> 1 & pTwoboards[heights[c]]>>2 & pTwoboards[heights[c]]>>3) > 0){
                won = true;
            } else if (heights[c] > 3 && (pTwoboards[heights[c]] & pTwoboards[heights[c]-1] & pTwoboards[heights[c]-2] & pTwoboards[heights[c]-3]) > 0){
                won = true;
            } else if (
                    (heights[c] > 3 && (pTwoboards[heights[c]] & pTwoboards[heights[c]-1]>>1 & pTwoboards[heights[c]-2]>>2 & pTwoboards[heights[c]-3]>>3) > 0) ||
                            heights[c] > 2 && heights[c] < h-1 &&((pTwoboards[heights[c]] & pTwoboards[heights[c]-1]>>1 & pTwoboards[heights[c]-2]>>2 & pTwoboards[heights[c]+1]>>3) > 0) ||
                            heights[c] > 1 && heights[c] < h-2 && ((pTwoboards[heights[c]] & pTwoboards[heights[c]-1]>>1 & pTwoboards[heights[c]+2]>>2 & pTwoboards[heights[c]+1]>>3) > 0 )||
                            heights[c] > 0 && heights[c] < h-3 &&((pTwoboards[heights[c]] & pTwoboards[heights[c]+1]>>1 & pTwoboards[heights[c]+2]>>2 & pTwoboards[heights[c]+3]>>3) > 0) ||
                            heights[c] > 3 && ((pTwoboards[heights[c]] & pTwoboards[heights[c]-1]<<1 & pTwoboards[heights[c]-2]<<2 & pTwoboards[heights[c]-3]<<3) > 0) ||
                            (heights[c] > 2 && heights[c] < h-1 && ((pTwoboards[heights[c]] & pTwoboards[heights[c]-1]<<1 & pTwoboards[heights[c]-2]<<2 & pTwoboards[heights[c]+1]<<3) > 0)) ||
                            (heights[c] > 1 && heights[c] < h-2 && ((pTwoboards[heights[c]] & pTwoboards[heights[c]-1]<<1 & pTwoboards[heights[c]+2]>>2 & pTwoboards[heights[c]+1]<<3) > 0) )||
                            (heights[c] > 0 && heights[c] < h-3 &&((pTwoboards[heights[c]] & pTwoboards[heights[c]+1]<<1 & pTwoboards[heights[c]+2]<<2 & pTwoboards[heights[c]+3]<<3) > 0))

            ){
                won = true;
            }
        }
        else{
            if ((pOneboards[heights[c]] & pOneboards[heights[c]] >> 1 & pOneboards[heights[c]]>>2 & pOneboards[heights[c]]>>3) > 0){
                won = true;
            } else if (heights[c] > 3 && (pOneboards[heights[c]] & pOneboards[heights[c]-1] & pOneboards[heights[c]-2] & pOneboards[heights[c]-3]) > 0){
                won = true;
            } else if (
                    (heights[c] > 3 && (pOneboards[heights[c]] & pOneboards[heights[c]-1]>>1 & pOneboards[heights[c]-2]>>2 & pOneboards[heights[c]-3]>>3) > 0) ||
                            heights[c] > 2 && heights[c] < h-1 &&((pOneboards[heights[c]] & pOneboards[heights[c]-1]>>1 & pOneboards[heights[c]-2]>>2 & pOneboards[heights[c]+1]>>3) > 0) ||
                            heights[c] > 1 && heights[c] < h-2 && ((pOneboards[heights[c]] & pOneboards[heights[c]-1]>>1 & pOneboards[heights[c]+2]>>2 & pOneboards[heights[c]+1]>>3) > 0 )||
                            heights[c] > 0 && heights[c] < h-3 &&((pOneboards[heights[c]] & pOneboards[heights[c]+1]>>1 & pOneboards[heights[c]+2]>>2 & pOneboards[heights[c]+3]>>3) > 0) ||
                            heights[c] > 3 && ((pOneboards[heights[c]] & pOneboards[heights[c]-1]<<1 & pOneboards[heights[c]-2]<<2 & pOneboards[heights[c]-3]<<3) > 0) ||
                            (heights[c] > 2 && heights[c] < h-1 && ((pOneboards[heights[c]] & pOneboards[heights[c]-1]<<1 & pOneboards[heights[c]-2]<<2 & pOneboards[heights[c]+1]<<3) > 0)) ||
                            (heights[c] > 1 && heights[c] < h-2 && ((pOneboards[heights[c]] & pOneboards[heights[c]-1]<<1 & pOneboards[heights[c]+2]>>2 & pOneboards[heights[c]+1]<<3) > 0) )||
                            (heights[c] > 0 && heights[c] < h-3 &&((pOneboards[heights[c]] & pOneboards[heights[c]+1]<<1 & pOneboards[heights[c]+2]<<2 & pOneboards[heights[c]+3]<<3) > 0))


            ){
                won = true;
            }
        }

    }


    public void takeBack(){
        int c = moves.pop();
        if(turn)
            pTwoboards[heights[c]] = pTwoboards[heights[c]] & adds[c];
        else
            pOneboards[heights[c] + 1] = pOneboards[heights[c] + 1] & adds[c];
        turn = !turn;
        won = false;
    }

    public boolean isWon(){
        return won;
    }
}
