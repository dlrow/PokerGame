# PokerGame

## How to run on intellij
1. Main.java is the start point of the code, which can be executed in an IDE.

###Assumptions
1. In case of any error or exception the program will skip that line of input and continue to evaluate winners for the remaining
2. In case of tie same point gets added to the players.
3. For now the program does not validate if the cards in the input are from the same deck. 

### How to change input
1. `Main.java` has two constants  `INPUT_FILE` and `NUMBER_OF_PLAYERS` which can be altered to test different scenarios
2. Every player gets 5 cards so for n players every line in the input should have 5*n cards or else the validation fails and that input line is skipped
3. For n players the point gained by each player is in order of their winning.
   If **5 players** are playing then
   * 1st winner gets 4 points
   * 2nd winner gets 3 points
   * 3rd winner gets 2 point
   * 4th winner gets 1 point
   * and last one gets 0 point