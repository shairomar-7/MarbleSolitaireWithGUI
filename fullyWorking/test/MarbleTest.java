import org.junit.Test;

import cs3500.marblesolitaire.model.hw02.Marble;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;
import cs3500.marblesolitaire.model.hw02.Position;

import static org.junit.Assert.*;

public class MarbleTest {
  MarbleSolitaireModelState.SlotState marble =  MarbleSolitaireModelState.SlotState.Marble;
  MarbleSolitaireModelState.SlotState empty =  MarbleSolitaireModelState.SlotState.Empty;
  MarbleSolitaireModelState.SlotState invalid =  MarbleSolitaireModelState.SlotState.Invalid;

  Marble m1 = new Marble(new Position(0, 0), marble);
  Marble m2 = new Marble(new Position(2, 2), marble);
  Marble m3 = new Marble(new Position(3, 3), empty);
  Marble m4 = new Marble(new Position(7, 6), invalid);
  Marble m5 = new Marble(new Position(2, 2), empty);
  Marble m6 = new Marble(new Position(2, 2), marble);

  @Test
  public void testEquals() {
    assertEquals(true, m2.equals(m5));
    assertEquals(true, m2.equals(m6));
    assertEquals(false, m2.equals(m3));
  }

  @Test
  public void testHash() {
    assertEquals(0, this.m1.hashCode());
    assertEquals(4, this.m2.hashCode());
    assertEquals(4, m5.hashCode());
    assertEquals(4, m6.hashCode());
    assertEquals(m5.hashCode(), m6.hashCode());
  }

  @Test
  public void testIsPosEqual() {
    assertEquals(true, m2.isPosEqual(2, 2));
    assertEquals(false, m2.isPosEqual(2, 1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testAboveException() {
    assertEquals(new IllegalArgumentException(), m2.isPosEqual(-1, 2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testException() {
    assertEquals(new IllegalArgumentException(), new Marble(new Position(-1, 2), empty));
  }
}