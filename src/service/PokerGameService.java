package service;

import model.card.Card;
import model.player.Hand;
import model.player.Player;
import model.poker.Combination;
import util.CombinationIdentifier;
import util.InputParser;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class PokerGameService {

    /**
     * number players playing the game
     * */
    private final int numOfPlayers;

    private static final int CARDS_IN_ONE_HAND = 5;

    private List<Player> players;

    public PokerGameService(int numOfPlayers) {
        this.numOfPlayers = numOfPlayers;
        players = new ArrayList<>();
        IntStream.range(0, numOfPlayers).forEach(i->players.add(new Player("Player " + (i+1))));
    }

    /**
     * takes input of a single game and evaluates the winner
     * */
    public void startGame(String st) {
        Queue<Card> cards = InputParser.parseCards(st);
        validateInput(cards);
        distributeCardsToPlayers(cards);
        Map<Player, Combination> playerCombinationMap = getPlayerCombinationMap();
        List<Player> sortedPlayer = sortPlayerInWinningOrder(playerCombinationMap);
        addPointsToPlayerInWinningOrder(sortedPlayer, playerCombinationMap);
    }


    private Map<Player, Combination> getPlayerCombinationMap() {
        Map<Player, Combination> playerCombinationMap = new HashMap<>();
        for(Player p : players){
            playerCombinationMap.put(p, CombinationIdentifier.getCombination(p.getHand().getCards()));
        }
        return playerCombinationMap;
    }

    /**
     * sorts the player in their winning order by comparing the  {@link Combination} of card they hace
     * @param playerCombinationMap
     */
    private List<Player> sortPlayerInWinningOrder(Map<Player, Combination> playerCombinationMap) {
        return playerCombinationMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

    /**
     * adds current match point to player point
     * input : players sorted by winning order
     * result : if 5 players are playing then
     *             1st winner gets 4 points
     *             2nd winner gets 3 points
     *             3rd winner gets 2 point
     *             4th winner gets 1 point
     *             and last one gets 0 point added
     *       e.g.
     *      *  Players -> P1 P2 P3 P4 P5
     *      *  Position-> 5  4  3  2  1
     *      *  Points  -> 0  1  2  3  4
     *
     *  in case of tie same point gets added to the players
     *  e.g.
     *  Players -> P1 P2 P3 P4 P5
     *  Position-> 3  2  2  2  1
     *  Points  -> 0  1  1  1  4
     */
    private void addPointsToPlayerInWinningOrder(List<Player> sortedPlayer, Map<Player, Combination> playerCombinationMap) {
        int pt = 0;
        Player currPlayer;
        for(int i = 0; i<sortedPlayer.size(); i++){
            pt = i;
            currPlayer = sortedPlayer.get(i);
            while (i < sortedPlayer.size()-1 &&
                    playerCombinationMap.get(sortedPlayer.get(i)).compareTo(playerCombinationMap.get(sortedPlayer.get(i+1)))==0){
                currPlayer.setPoint(currPlayer.getPoint()+pt);
                i++;
                currPlayer = sortedPlayer.get(i);
            }
            currPlayer.setPoint(currPlayer.getPoint()+pt);
        }
    }

    /**
     * sets hand of the player with the current game cards
     * */
    private void distributeCardsToPlayers(Queue<Card> cards) {
        for (Player player : players) {
            List<Card> cardsInHand = new ArrayList<>();
            IntStream.range(0, CARDS_IN_ONE_HAND).forEach(i -> cardsInHand.add(cards.poll()));
            player.setHand(new Hand(cardsInHand));
        }
    }

    /**
     * validates if the number of parsed cards is divided equally among number of players
     * */
    private void validateInput(Queue<Card> cards) {
        if (cards.size() % CARDS_IN_ONE_HAND != 0 || cards.size() / CARDS_IN_ONE_HAND != numOfPlayers) {
           throw new RuntimeException("Invalid number of cards");
        }
    }

    /**
     * displays the result after all matches
     * */
    public void displayResults() {
        players.forEach(player -> System.out.println(player.getName() + ": " + player.getPoint()));
    }
}
