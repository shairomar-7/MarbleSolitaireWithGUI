package cs3500.marblesolitaire.model.hw02;

import java.util.List;

/**
 * English Solitaire, represents a specific variant of the Marble Solitaire board.
 * It is characterized by the '+' shaped board.
 */
public class EnglishSolitaireModel implements MarbleSolitaireModel {
  private final int armThickness;
  private List<Marble> marbles;
  private final int sRow;
  private final int sCol;
  private int score;

  /**
   * This instance of english solitaire represents the traditional EnglishSoltaire game.
   * with an armThickness of 3, and the empty slot being at the center (3,3)
   */
  public EnglishSolitaireModel() {
    this(3, 3, 3);
  }

  /**
   * This instance of english solitaire represents the traditional EnglishSolitaire game.
   * With an armThickness of 3, except the empty slot is given, and not necessarily at center.
   *
   * @param sRow int represents the row number of the empty slot
   * @param sCol int represents the column number of the empty slot
   * @throws IllegalArgumentException if the given coordinates sRow and sCol of the empty slot
   *                                  are invalid coordinates for an EnglishSolitaire game with armThickness of 3
   */
  public EnglishSolitaireModel(int sRow, int sCol) throws IllegalArgumentException {
    this(3, sRow, sCol);
  }

  /**
   * This instance of english solitaire represents the game.
   * with a given arm thickness,and the empty slot is at the center.
   *
   * @param armThickness int represents the number of marbles at the top row of the board
   * @throws IllegalArgumentException if the given armThickness is not a odd integer >=3
   */
  public EnglishSolitaireModel(int armThickness) throws IllegalArgumentException {
    int center = (int) ((1.5) * (armThickness - 1));
    this.armThickness = armThickness;
    this.sRow = center;
    this.sCol = center;
    this.marbles = new BoardBuilder().buildBoard(this.armThickness, this.sRow, this.sCol);
    this.score = this.marbles.size() - 1;
  }

  /**
   * This instance of english solitaire represents the game with.
   * a given armthickness, and given coordinates sRow and sCol of the empty slot
   *
   * @param armThickness int represents the number of marbles at the top row of the board
   * @param sRow         int represents the row number of the empty slot
   * @param sCol         int represents the column number of the empty slot
   * @throws IllegalArgumentException if the given armThickness is not a odd integer >=3, or
   *                                  the given coordinates sRow and sCol of the empty slot are invalid coordinates of the board.
   */
  public EnglishSolitaireModel(int armThickness, int sRow, int sCol) throws IllegalArgumentException {
    this.armThickness = armThickness;
    this.sCol = sCol;
    this.sRow = sRow;
    this.marbles = new BoardBuilder().buildBoard(this.armThickness, this.sRow, this.sCol);
    this.score = this.marbles.size() - 1;
  }

  @Override
  public void move(int fromRow, int fromCol, int toRow, int toCol) throws IllegalArgumentException {
    int[] indices = this.checkFromToBetween(fromRow, fromCol, toRow, toCol);
    if (indices.length == 0) {
      throw new IllegalArgumentException("Either from is not a Marble, " +
              "or to is not Empty, or distance is invalid!");
    }
    this.updateBoard(indices[0], indices[1], indices[2]);
  }

  @Override
  public boolean isGameOver() {
    if (this.getScore() == 0) {
      int center = (int) ((1.5) * (this.armThickness - 1));
      if (this.getSlotAt(center, center) == SlotState.Marble) {
        System.out.println("Wow! You won, good shit!");
      }
      System.out.println("Close, but not quite!");
      return true;
    }

    for (int i = 0; i < this.marbles.size(); i++) {
      Marble m = this.marbles.get(i);
      if (m.state == SlotState.Marble) {
        int row = m.getRow();
        int col = m.getCol();
        for (int k = 0; k < 4; k++) {
          switch (k) {
            case 0: // not at upper edge and the above marble is not negative(then checks if the inbetweenmarble is valid and the moves are valid)
              if (this.checkEdge(row, col) != 1 && !this.negHuh(row - 2, col)) {
                if (this.checkFromToBetween(row, col, row - 2, col).length != 0) {
                  return false;
                }
              }
              break;
            case 1:
              if (this.checkEdge(row, col) != 2 && !this.negHuh(row, col + 2)) {
                if (this.checkFromToBetween(row, col, row, col + 2).length != 0) {
                  return false;
                }
              }
              break;
            case 2:
              if (this.checkEdge(row, col) != 3 && !this.negHuh(row, col - 2)) {
                if (this.checkFromToBetween(row, col, row, col - 2).length != 0) {
                  return false;
                }
              }
              break;
            case 3:
              if (this.checkEdge(row, col) != 4 && !this.negHuh(row + 2, col)) {
                if (this.checkFromToBetween(row, col, row + 2, col).length != 0) {
                  return false;
                }
              }
              break;
            default:
              break;
          }
        }
      }
    }
    return true;
  }

  /**
   * Returns whether the given position has a negative x or y.
   *
   * @param row int represents the row num of the marble
   * @param col int represents the col num of the marble
   * @return true if either one of the given coordinates are neg, else false
   */
  private boolean negHuh(int row, int col) {
    if (row < 0 || col < 0) {
      return true;
    }
    return false;
  }

  /**
   * Checks if the given row and col of the marble is on our board's corners.
   *
   * @param row int represents the row number of a marble
   * @param col int represents the col number of a marble
   * @return 1 if marble is at upper edge, 2 right edge, 3 left edge, 4 bottom edge, else -50
   */
  private int checkEdge(int row, int col) {
    int size = this.getBoardSize();
    int threshold2 = (this.armThickness - 1);
    int threshold = (size - this.armThickness);
    if (row == 0 || ((col > threshold || col < threshold2)
            && (row == threshold2))) {
      return 1;
    } else if (col == size || (col == threshold && (row < threshold2 || row > threshold))) {
      return 2;
    } else if (col == 0 || (col == threshold2 && (row < threshold2 || row > threshold))) {
      return 3;
    } else if (row == size - 1 || (row == threshold2 && (col > threshold2 || col < threshold))) {
      return 4;
    } else {
      return -50;
    }
  }

  @Override
  public int getBoardSize() {
    return 3 * this.armThickness - 2;
  }

  @Override
  public MarbleSolitaireModelState.SlotState getSlotAt(int row, int col) throws IllegalArgumentException {
    boolean soFar = false;
    if (row < 0 || col < 0 || row >= this.getBoardSize() || col >= this.getBoardSize()) {
      throw new IllegalArgumentException("cell position beyond board dimensions" +
              " (" + row + "," + col + ")");
    }
    //System.out.println(col * this.getBoardSize() + col);
    return this.marbles.get(col * this.getBoardSize() + row).state; ////WHy not working?? gives me right index but wrong values??
//    for (Marble m : this.marbles) {
//      if (m.isPosEqual(row, col)) {
//        return m.state;
//      }
//    }
//    return SlotState.Invalid;
  }

  @Override
  public int getScore() {
    int score = 0;
    for (Marble m : this.marbles) {
      if (m.state == SlotState.Marble) {
        score++;
      }
    }
    return score;
  }

  /**
   * Checks whether the given coordinates representing the pos of the marble is valid or not.
   *
   * @param sRow represents the row number of the given marble
   * @param sCol represents the column number of the given marble
   * @return true if the given position is a valid position
   * @throws IllegalArgumentException if the given position is not a valid pos of this board
   */
  private boolean checkPosValid(int sRow, int sCol) throws IllegalArgumentException {
    for (Marble m : this.marbles) {
      if (m.isPosEqual(sRow, sCol)) {
        return true;
      }
    }
    throw new IllegalArgumentException("Invalid position!");
  }


  /**
   * Computes a list of ints representing the index of the 3 marbles involved in a "move".
   *
   * @param fromRow int represents the from's row number
   * @param fromCol int represents the from's col number
   * @param toRow   int represents the to's row number
   * @param toCol   int represents the to's col number
   * @return int[] of size 3 representing fromIndex, inBetweenIndex, and toIndex repectively.
   * returns an empty int[] if this is not a valid move (from -> to)
   * @throws IllegalArgumentException if the given from and to coordinates are the same
   */
  private int[] checkFromToBetween(int fromRow, int fromCol, int toRow, int toCol)
          throws IllegalArgumentException {
    Position pos1 = new Position(fromRow, fromCol);
    Position pos2 = new Position(toRow, toCol);
    if (pos1.equals(pos2)) {
      throw new IllegalArgumentException("from and to can't be the same, idiot!");
    }
    int fromIndex = -1;
    int toIndex = -1;
    for (int j = 0; j < this.marbles.size(); j++) {
      Marble m = this.marbles.get(j);
      if (m.isPosEqual(fromRow, fromCol)) {
        fromIndex = j;
      }
      if (m.isPosEqual(toRow, toCol)) {
        toIndex = j;
      }
    }
    if (fromIndex != -1 && toIndex != -1) {
      if (this.marbles.get(fromIndex).state == SlotState.Marble &&
              this.marbles.get(toIndex).state == SlotState.Empty &&
              pos1.checkDistance(pos2)) {
        int inBetweenIndex = this.getInBetween(fromRow, fromCol, toRow, toCol);
        if (this.marbles.get(inBetweenIndex).state == SlotState.Marble) {
          return new int[]{fromIndex, inBetweenIndex, toIndex};
        }
      }
    }
    return new int[]{};
  }

  /**
   * EFFECT: Mutates the board according to the move.
   * (the from and inbetween would be empty, and the to becomes a marble)
   *
   * @param fromIndex      int represents the index of the marble that will jump
   * @param inBetweenIndex int represents the index of the marble that will be jumped on top of
   * @param toIndex        int represents the index of the marble that will be jumped to/on
   */
  private void updateBoard(int fromIndex, int inBetweenIndex, int toIndex) {
    Marble from = this.marbles.get(fromIndex);
    Marble to = this.marbles.get(toIndex);
    Marble inBetween = this.marbles.get(inBetweenIndex);
    from.state = SlotState.Empty;
    to.state = SlotState.Marble;
    inBetween.state = SlotState.Empty;
  }

  /**
   * Gets the index of the element inBetween the fromMarble and the toMarble.
   *
   * @param fromRow int represents the row num of the marble that will jump
   * @param fromCol int represents the col num of the marble that will jump
   * @param toRow   int represents the row num of the marble that will be jumped to/on
   * @param toCol   int represents the col num of the marble that will be jumped to/on
   * @return int representing index of marble in between the given positions
   * @throws IllegalArgumentException if the
   */
  private int getInBetween(int fromRow, int fromCol, int toRow, int toCol) throws IllegalArgumentException {
    Position pos;
    if (fromRow == toRow) {
      if (fromCol < toCol) {
        pos = new Position(toCol - 1, fromRow);
      } else {
        pos = new Position(fromCol - 1, fromRow);
      }
    } else {
      if (fromRow < toRow) {
        pos = new Position(toCol, toRow - 1);
      } else {
        pos = new Position(fromCol, fromRow - 1);
      }
    }
    for (int i = 0; i < this.marbles.size(); i++) {
      Marble m = this.marbles.get(i);
      if (m.isPosEqual(pos.getRow(), pos.getCol())) {
        return i;
      }
    }
    throw new IllegalArgumentException("something went wrong, we couldn't find the inBetween!");
  }
}
