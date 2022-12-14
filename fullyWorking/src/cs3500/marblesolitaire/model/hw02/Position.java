package cs3500.marblesolitaire.model.hw02;

/**
 * Position, represents x (column) and y (row) coordinates.
 * throws IllegalArgumentException if negative x or y is given.
 */
public class Position implements Comparable<Position> {
  private int col;
  private int row;

  /**
   * Constructs a new Position with the given x and y coordinates
   * @param x integer represents an x-coordinate, or column
   * @param y integer represents an y-coordinate, or row
   * @throws IllegalArgumentException if negative value of x or y is given
   */
  public Position(int x, int y) throws IllegalArgumentException {
    if (x < 0 || y < 0) {
      throw new IllegalArgumentException("Negative Position given!");
    }
    this.col = x;
    this.row = y;
  }

  /**
   * Override equals, checks if this position is equal to the given position.
   * returns true if both x and y are equal or if given object is this
   * @param o represents the given Object to be compared for equality with this
   * @return boolean describing whether this is equal to the given object o
   */
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Position)) {
      return false;
    }
    Position c = (Position) o;
    return this.col == c.col && this.row == c.row;
  }

  /**
   * Override hashCode() in order to overide equals and check for two position equality.
   * @return an integer representing the hashcode of the sum of the x and y coordinates of this
   */
  public int hashCode() {
    Integer sum = this.col + this.row;
    return Integer.hashCode(sum);
  }

  /**
   * Checks if the distance betweeen this and the other is 2 unit away horizontally or vertically.
   * @param other Position used to check if the distance between it and this is 1 unit away
   * @return boolean saying whether the distance between this and other is 1 unit
   */
  protected boolean checkDistance(Position other) {
    int dx = Math.abs(this.col - other.col);
    int dy = Math.abs(this.row - other.row);
    return (dx == 2 && this.row == other.row) || (dy == 2 && this.col == other.col);
  }

  /**
   * Gets this position's row number.
   * @return this position's row number
   */
  public int getRow() {
    return this.row;
  }

  /**
   * Gets this position's col number.
   * @return this position's col number
   */
  public int getCol() {
    return this.col;
  }

  @Override
  public int compareTo(Position o) {
    return Integer.compare(this.row, o.row);
  }
}
