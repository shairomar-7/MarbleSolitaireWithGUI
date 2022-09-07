import org.junit.Test;

import cs3500.marblesolitaire.model.hw02.Position;

import static org.junit.Assert.*;

public class PositionTest {
  Position pos1 = new Position(1, 1);
  Position pos2 = new Position(2, 2);
  Position pos3 = new Position(3, 3);
  Position pos4 = new Position(2, 2);

  @Test
  public void testEquals() {
    assertEquals(true, pos2.equals(pos4));
    assertEquals(false, pos2.equals(pos3));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testException() {
    assertEquals(new IllegalArgumentException(), new Position(-1, 2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testException2() {
    assertEquals(new IllegalArgumentException(), new Position(1, -2));
  }


}