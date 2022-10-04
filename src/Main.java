import service.PokerGameService;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        String inputFileName = "input.txt";
        PokerGameService pokerGameService = new PokerGameService(2);
        File file = new File(inputFileName);
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
