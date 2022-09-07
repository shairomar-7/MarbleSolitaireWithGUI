package cs3500.marblesolitaire.controller;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;

public interface Features {
  void configureGame(MarbleSolitaireModel model, String selectedGame, int thickness, int row, int col);
  void acceptInput(int row, int col);
  void playGameAgain(boolean customHuh);
}
