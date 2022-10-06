import service.PokerGameService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {

    static final String INPUT_FILE = "input.txt";
    static final int  NUMBER_OF_PLAYERS = 2;

    public static void main(String[] args) throws IOException {
        PokerGameService pokerGameService = new PokerGameService(NUMBER_OF_PLAYERS);
        File file = new File(INPUT_FILE);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String st;
        while ((st = br.readLine()) != null) {
            try {
                pokerGameService.startGame(st);
            }catch (Exception e){
                System.out.println("error for game with input " + st + " : " + e);
            }
        }
        pokerGameService.displayResults();
    }
}
