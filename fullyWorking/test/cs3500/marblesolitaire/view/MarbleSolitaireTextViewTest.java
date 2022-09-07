package cs3500.marblesolitaire.view;

import org.junit.Test;

import cs3500.marblesolitaire.model.hw02.EnglishSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;

import static org.junit.Assert.*;

public class MarbleSolitaireTextViewTest {
  EnglishSolitaireModel traditional = new EnglishSolitaireModel();
  MarbleSolitaireTextView viewer = new MarbleSolitaireTextView(traditional);
  EnglishSolitaireModel nonTraditional = new EnglishSolitaireModel(5, 5, 0);
  MarbleSolitaireTextView viewer2 = new MarbleSolitaireTextView(nonTraditional);

  @Test(expected = IllegalArgumentException.class)
  public void testToString() {
    assertEquals("    O O O\n    O O O\nO O O O O O O\nO O O _ O O O\nO O O O O O O\n    O O O\n    O O O", viewer.toString());
    traditional.move(5, 3, 3, 3);
    assertEquals("    O O O\n    O O O\nO O O O O O O\nO O O O O O O\nO O O _ O O O\n    O _ O\n    O O O", viewer.toString());
    assertEquals("        O O O O O\n" +
            "        O O O O O\n" +
            "        O O O O O\n" +
            "        O O O O O\n" +
            "O O O O O O O O O O O O O\n" +
            "_ O O O O O O O O O O O O\n" +
            "O O O O O O O O O O O O O\n" +
            "O O O O O O O O O O O O O\n" +
            "O O O O O O O O O O O O O\n" +
            "        O O O O O\n" +
            "        O O O O O\n" +
            "        O O O O O\n" +
            "        O O O O O", this.viewer2.toString());
    nonTraditional.move(5, 2, 5, 0);
    assertEquals("        O O O O O\n" +
            "        O O O O O\n" +
            "        O O O O O\n" +
            "        O O O O O\n" +
            "O O O O O O O O O O O O O\n" +
            "O _ _ O O O O O O O O O O\n" +
            "O O O O O O O O O O O O O\n" +
            "O O O O O O O O O O O O O\n" +
            "O O O O O O O O O O O O O\n" +
            "        O O O O O\n" +
            "        O O O O O\n" +
            "        O O O O O\n" +
            "        O O O O O",this.viewer2.toString());
    assertEquals(MarbleSolitaireModelState.SlotState.Marble, nonTraditional.getSlotAt(5, 0));
    nonTraditional.move(5, 3, 5, 1);
  }

//  @Test(expected = IllegalArgumentException.class)
//  public  void testToString
}