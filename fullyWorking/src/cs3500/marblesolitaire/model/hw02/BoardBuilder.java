package cs3500.marblesolitaire.model.hw02;
import java.util.ArrayList;

/**
 * Class to build the board (the list of marbles) based on the game parameters.
 * These parameters are: the arm thickness, and the position of the initial empty slot.
 */
public class BoardBuilder {

  /**
   * Builds the Arraylist of marbles based on the game parameters.
   * The Marble's state is determined by its position on the board (marble/invalid).
   * @param a int represents the armThickness of the english solitaire game
   * @return ArrayList<Marble> constructed based on its position on the board.
   */
  private ArrayList<Marble> buildMarbles(int a) {
    int boardSize = 3 * a - 2; // should be fine assuming count <= limit of int
    ArrayList<Marble> marbles = new ArrayList<Marble>();
   for (int i = 0; i < boardSize; i++) { // i -> row
     for (int j = 0; j < boardSize; j++) { // j -> col
       Position p = new Position(i, j);
       if ((j > boardSize-a || j < a-1) && ((i > boardSize - a) || (i <a -1))) {
         marbles.add(new Marble(p, MarbleSolitaireModelState.SlotState.Invalid));
       }
       else {
         marbles.add(new Marble(p, MarbleSolitaireModelState.SlotState.Marble));
       }
     }
   }
    return marbles;
  }

  /**
   * ArrayList<Marble>, representing the marbles on an englishSolitaire game
   * @param a int represents the armThickness of an englishSolitaire game
   * @param sRow int represents the row number of the empty slot of the game
   * @param sCol int represents the column number of the empty slot of the game
   * @return ArrayList of Marble that corresponds to the inputs representing game's initial state
   * @throws IllegalArgumentException if the given emptySlot coordinates are invalid (does not
   * belong to the list of valid positions), or given armThickness is not a pos odd integer >=3
   */
  public ArrayList<Marble> buildBoard(int a, int sRow, int sCol) throws IllegalArgumentException {
    ArrayList<Marble> result = this.buildMarbles(a);
    boolean soFar = false;
    if (a % 2 == 0 || a < 3) {
      throw new IllegalArgumentException("Invalid armThickness given! Should be pos odd >=3!!");
    }
    for (Marble m: result) {
      if (m.isPosEqual(sRow, sCol) ) {
        if (m.state == MarbleSolitaireModelState.SlotState.Marble) {
         m.state = MarbleSolitaireModelState.SlotState.Empty;
         soFar = true;
        }
      }
    }
    if (!soFar) {
      throw new IllegalArgumentException("Invalid empty cell position (" + sRow + "," + sCol +")");
    }
    return result;
  }
}

