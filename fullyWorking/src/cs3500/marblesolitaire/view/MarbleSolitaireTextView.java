package cs3500.marblesolitaire.view;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;

/**
 * Question1: how do I draw the list of marbles?
 * Each Marble has a position and a
 */
public class MarbleSolitaireTextView implements MarbleSolitaireView {
  private MarbleSolitaireModelState state;
  private StringBuilder board;
  private final int boardSize;
  private final int armThickness;

  /**
   * Constructs the viewer for the Marble Solitaire game.
   *
   * @param state MarbleSolitaireModelState represents the state of the marble solitaire game
   * @throws IllegalArgumentException if the given state is null.
   */
  public MarbleSolitaireTextView(MarbleSolitaireModelState state) throws IllegalArgumentException {
    if (state == null) {
      throw new IllegalArgumentException("null state");
    }
    this.state = state;
    this.boardSize = this.state.getBoardSize();
    this.armThickness = (int) ((this.boardSize + 2) / 3);
    this.board = new StringBuilder();
  }

  @Override
  public String toString() {
    this.board = new StringBuilder();
    for (int i = 0; i < this.boardSize; i++) {
      for (int j = 0; j < this.boardSize; j++) {
        MarbleSolitaireModelState.SlotState state = this.state.getSlotAt(i, j);
        if (state == MarbleSolitaireModelState.SlotState.Empty) {
          this.board.append("_");
        } else if (state == MarbleSolitaireModelState.SlotState.Marble) {
          this.board.append("O");
        } else if (state == MarbleSolitaireModelState.SlotState.Invalid
                && j < this.boardSize - this.armThickness) {
          this.board.append(" ");
        }
        if (this.checkPos(i, j)) {
          this.board.append(" ");
        }
      }
      if (i != (this.boardSize - 1)) {
        this.board.append("\n");
      }
    }
    return this.board.toString();
  }

  /**
   * Checks if the given position is not at any of the right edges of the board.
   *
   * @param i int represents the row number of a certain marble
   * @param j int represents the row number of a certain marble
   * @return whether the given position is not at any of the right edges of the board.
   */
  private boolean checkPos(int i, int j) {
    return (!(j >= (this.boardSize - this.armThickness)
            && ((i > this.boardSize - this.armThickness) || (i < this.armThickness - 1)))
            && j != this.boardSize - 1);
  }
}
