package cs3500.marblesolitaire.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

//import cs3500.marblesolitaire.controller.ControllerFeatures;
import cs3500.marblesolitaire.controller.Features;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;

public class BoardPanel extends JPanel implements IBoardPanel {
  private MarbleSolitaireModelState modelState;
  private Image emptySlot, marbleSlot, blankSlot, chosenSlot;
  private final int cellDimension;
  private int originX, originY;
  private boolean hasChosenMarble;
  private int chosenX, chosenY;
  private String typeOfModel;

  public BoardPanel(MarbleSolitaireModelState state) throws IllegalStateException {
    super();
    this.hasChosenMarble = false;
    this.chosenX = 0;
    this.chosenY = 0;
    this.modelState = state;
    this.setBackground(Color.WHITE);
    this.setLayout(new GridLayout(2, 0));
    this.setVisible(true);
    this.cellDimension = 50;
    try {
      originX = 50;
      originY = 0;
      emptySlot = ImageIO.read(new FileInputStream("res/empty.png"));
      emptySlot = emptySlot.getScaledInstance(cellDimension, cellDimension, Image.SCALE_SMOOTH);

      marbleSlot = ImageIO.read(new FileInputStream("res/marble.png"));
      marbleSlot = marbleSlot.getScaledInstance(cellDimension, cellDimension, Image.SCALE_SMOOTH);

      blankSlot = ImageIO.read(new FileInputStream("res/blank.png"));
      blankSlot = blankSlot.getScaledInstance(cellDimension, cellDimension, Image.SCALE_SMOOTH);

      chosenSlot = ImageIO.read(new FileInputStream("res/chosenMarble.png"));
      chosenSlot = chosenSlot.getScaledInstance(cellDimension, cellDimension, Image.SCALE_SMOOTH);

      this.setPreferredSize(
              new Dimension((this.modelState.getBoardSize() + 4) * cellDimension
                      , (this.modelState.getBoardSize() + 4) * cellDimension));
    } catch (IOException e) {
      throw new IllegalStateException("Icons not found!");
    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    int boardSize = this.modelState.getBoardSize();
    if (!typeOfModel.equals("triangle solitaire")) {
      for (int i = 0; i < boardSize; i++) {
        for (int j = 0; j < boardSize; j++) {
          MarbleSolitaireModelState.SlotState state = this.modelState.getSlotAt(i, j);
          if (state == MarbleSolitaireModelState.SlotState.Empty) {
            g.drawImage(emptySlot, originX + j * 50, originY + i * 50, this);
          } else if (state == MarbleSolitaireModelState.SlotState.Marble) {
            g.drawImage(marbleSlot, originX + j * 50, originY + i * 50, this);
          } else if (state == MarbleSolitaireModelState.SlotState.Invalid
                  && j < boardSize - (boardSize + 2) / 3) {
            g.drawImage(blankSlot, originX + j * 50, originY + i * 50, this);
          }
        }
      }
    } else {
      drawTriangleSolitaireHelper(g);
    }
    if (hasChosenMarble) {
      hasChosenMarble = !hasChosenMarble;
      g.drawImage(chosenSlot, chosenX, chosenY, this);
    }
  }


  private void drawTriangleSolitaireHelper(Graphics g) {
    int offset = modelState.getBoardSize();
    int offsetUI = offset * 25;
    for (int i = 0; i < this.modelState.getBoardSize(); i++) { // row
      for (int j = 0; j < i + 1; j++) { //col
        if (j == 0 && offset != 0) {
          offsetUI = offset * 25;
          offset--;
        }
        MarbleSolitaireModelState.SlotState state = this.modelState.getSlotAt(i, j);
        if (state == MarbleSolitaireModelState.SlotState.Empty) {
          g.drawImage(emptySlot, originX + j * 50 + offsetUI, originY + i * 50, this);
        } else if (state == MarbleSolitaireModelState.SlotState.Marble) {
          g.drawImage(marbleSlot, originX + j * 50 + offsetUI, originY + i * 50, this);
        } else {
          g.drawImage(blankSlot, originX + j * 50 + offsetUI, originY + i * 50, this);
        }
//        if (i != j) {
//          g.drawImage(blankSlot, originX + j * 50 + offsetUI, originY + i * 50, this);
//        }
      }
    }
  }

  public void setTypeModel(String type) {
    this.typeOfModel = type;
  }


  @Override
  public void executeFeature(Features feature) {
    class EventHandler extends MouseAdapter {
      @Override
      public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        System.out.println("mouse clicked");
        helpProcessCoordinates(e.getX(), e.getY(), feature);
      }
    }
    this.addMouseListener(new EventHandler());
  }

  public void helpProcessCoordinates(int x, int y, Features features) {
    int xC, yC;
    yC = (y - originY) / 50;
    int offset = (this.modelState.getBoardSize() - yC) * 25;
    if (typeOfModel.equals("triangle solitaire")) {
      xC = (x - originX - offset) / 50;
    }
    else {
      xC = (x - originX) / 50;
    }
    features.acceptInput(yC, xC);
    this.repaint();
  }

  public void showChosenMarble(int x, int y) {
    MarbleSolitaireModelState.SlotState state = this.modelState.getSlotAt(y, x);
    if (state == MarbleSolitaireModelState.SlotState.Empty || state == MarbleSolitaireModelState.SlotState.Invalid) {
      JOptionPane.showMessageDialog(this, "The chosen slot must be a marble!", "Inane error",
              JOptionPane.ERROR_MESSAGE);
    } else {
      hasChosenMarble = true;
      chosenX = x * 50 + originX;
      chosenY = y * 50 + originY;
      int offset = (this.modelState.getBoardSize() - y) * 25;
      if (typeOfModel.equals("triangle solitaire")) chosenX += offset;
      this.repaint();
    }
  }
}
