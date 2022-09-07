package cs3500.marblesolitaire.view;

//import cs3500.marblesolitaire.controller.ControllerFeatures;

import cs3500.marblesolitaire.controller.Features;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;

/**
 * This interface represents a GUI view for the game of marble solitaire.
 */
public interface MarbleSolitaireGuiView {

  void execute(Features feature);

  /**
   * Refresh the screen. This is called when the something on the
   * screen is updated and therefore it must be redrawn.
   */
  void refresh();

  /**
   * Display a message in a suitable area of the GUI.
   * @param message the message to be displayed
   */
  void renderMessage(String message);

  void showChosenMarble(int x, int y);


  void setModel(MarbleSolitaireModelState model);

  void setTypeOfView(String typeOfView);

  void gameOverOrWon(boolean over);

  void goToHomePage();
}
