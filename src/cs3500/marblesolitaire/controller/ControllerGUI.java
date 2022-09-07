package cs3500.marblesolitaire.controller;

import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.view.MarbleSolitaireGuiView;
import cs3500.marblesolitaire.view.ModelFactory;
import cs3500.marblesolitaire.view.SwingGuiView;

/**
 * Controller component of the marble solitaire game. This component handles the communication
 * between the model (logic of the game and data storage) and the view (GUI). Thus, this controller
 * is specific to the fact that the view is a gui and not text based. However, this controller is
 * absolutely not specific to java swing gui, and
 */
public class ControllerGUI implements Features {
  private MarbleSolitaireModel model;
  private MarbleSolitaireGuiView view;
  private int fromRow, fromCol;
  private int thickness, sRow, sCol;
  private String chosenGame;

  public ControllerGUI() {
    this.view = new SwingGuiView();
    view.execute(this);
    fromRow = -1;
    fromCol = -1;
    thickness = -1;
    sRow = -1;
    sCol = -1;
  }

  @Override
  public void configureGame(MarbleSolitaireModel model, String chosenGame,
                            int thickness, int row, int col) {
    this.model = model;
    view.setModel(model);
    view.setTypeOfView(chosenGame);
    view.execute(this);
    this.thickness = thickness;
    this.sRow = row;
    this.sCol = col;
    this.chosenGame = chosenGame;
  }

  @Override
  public void acceptInput(int row, int col) {
    if (model.isGameOver()) {
      if (model.getScore() == 1) view.gameOverOrWon(false);
      else view.gameOverOrWon(true);
      return;
    }
    if (fromRow == -1 && fromCol == -1) {
      fromRow = row;
      fromCol = col;
      view.showChosenMarble(col, row);
    }
    else {
      try {
        model.move(fromRow, fromCol, row, col);
        view.refresh();
        fromRow = -1;
        fromCol = -1;
      }
      catch (IllegalArgumentException e) {
        view.renderMessage(e.getMessage());
        fromRow = -1;
        fromCol = -1;
      }
    }
  }

  @Override
  public void playGameAgain(boolean customHuh) {
    if (customHuh) {
      view.goToHomePage();
      view.execute(this);
    }
    else {
      System.out.println("playgamAgain controller function");
      this.model = ModelFactory.createModel(chosenGame, determineNumArgs(), new int[]{thickness, sRow, sCol});
      configureGame(this.model, chosenGame, thickness, sRow, sCol);
    }
  }

  private int determineNumArgs() {
    if (thickness == -1 && sRow == -1 && sCol == -1) {
      return 0;
    }
    if (thickness != -1 && sRow == -1 && sCol == -1) {
      return 1;
    }
    else if (thickness == -1 && sRow != -1 && sCol != -1) {
      return 2;
    }
    else if (thickness != -1 && sRow != -1 && sCol != -1) {
      return 3;
    }
    return -1;
  }
}
