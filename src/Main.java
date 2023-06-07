import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Board connectFour = new Board();
        Scanner in = new Scanner(System.in);
        while(!connectFour.isWon()){
            connectFour.play(in.nextInt());
            System.out.println(connectFour.fancyBoard());
            System.out.println(connectFour.isWon());
        }
        System.out.println(connectFour);
    }
}