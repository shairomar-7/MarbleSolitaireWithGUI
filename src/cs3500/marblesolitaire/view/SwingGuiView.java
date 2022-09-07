package cs3500.marblesolitaire.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.*;

//import cs3500.marblesolitaire.controller.ControllerFeatures;
import cs3500.marblesolitaire.controller.Features;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModelState;

/**
 * This class represents a GUI view that is implemented using Java
 * Swing.
 */
public class SwingGuiView extends JFrame implements MarbleSolitaireGuiView {

  //the custom panel on which the board will be drawn
  private BoardPanel boardPanel;
  private GameConfigPanel gameConfigPanel;
  //the model state
  private MarbleSolitaireModelState modelState;
  //a label to display the score
  private JLabel scoreLabel;
  //a label to display any messages to the user
  private JLabel messageLabel;
  private String typeOfSolitaire;
  private JPanel mainPanel, panel, buttonsPanel, labelPanel, gameEndPanel;
  private final JButton newGameButton, homePageButton;


  public SwingGuiView() {
    super("Marble solitaire");
    this.setLayout(new GridLayout());
    mainPanel = new JPanel();
    mainPanel.setLayout(new BorderLayout());
    mainPanel.setVisible(true);
    mainPanel.setBackground(Color.WHITE);
    this.scoreLabel = new JLabel();
    scoreLabel.setFont(new Font("Ariel", Font.BOLD, 15));
    scoreLabel.setForeground(Color.RED);
    this.messageLabel = new JLabel();
    messageLabel.setFont(new Font("Ariel", Font.BOLD, 12));
    panel = new JPanel();
    panel.setLayout(new GridLayout(2,0));
    panel.add(scoreLabel);
    panel.add(messageLabel);
    buttonsPanel = new JPanel();
    buttonsPanel.setLayout(new GridLayout(0, 2));
    newGameButton = new JButton("Start Over");
    homePageButton = new JButton("Home");
    buttonsPanel.add(newGameButton);
    buttonsPanel.add(homePageButton);
    buttonsPanel.setVisible(true);
    this.goToHomePage();
    add(mainPanel);
    pack();
    setVisible(true);
  }

  public void setModel(MarbleSolitaireModelState state) {
    this.modelState = state;
  }

  public void setTypeOfView(String type) {
    typeOfSolitaire = type;
    labelPanel = new JPanel(new FlowLayout());
    JLabel label = new JLabel(typeOfSolitaire);
    label.setFont(new Font("Verdana",1,40));
    labelPanel.add(label);
    labelPanel.setVisible(true);
    if (gameConfigPanel != null)  {
      mainPanel.remove(gameConfigPanel);
      gameConfigPanel.setVisible(false);
      gameConfigPanel = null;
    }
    System.out.println("removing home");
    if (this.boardPanel != null) {
      mainPanel.remove(boardPanel);
      boardPanel = null;
    }
    mainPanel.removeAll();
    boardPanel = new BoardPanel(modelState);
    boardPanel.setTypeModel(type);
    mainPanel.add(labelPanel, BorderLayout.PAGE_START);
    mainPanel.add(boardPanel, BorderLayout.CENTER);
    mainPanel.add(panel, BorderLayout.LINE_END);
    revalidate();
    refresh();
  }

  @Override
  public void gameOverOrWon(boolean over) {
    gameEndPanel = new GameEndPanel(over);
    boardPanel.setVisible(false);
    mainPanel.add(gameEndPanel, BorderLayout.CENTER);
    validate();
    refresh();
  }

  @Override
  public void goToHomePage() {
    gameConfigPanel = new GameConfigPanel();
    if (boardPanel != null) {
//      mainPanel.remove(boardPanel);
      boardPanel = null;
//      mainPanel.remove(buttonsPanel);
//      mainPanel.remove(homePageButton);
//      mainPanel.remove(newGameButton);
//      mainPanel.remove(panel);
//      mainPanel.remove(labelPanel);
      mainPanel.removeAll();
      if (gameEndPanel != null) this.remove(gameEndPanel);
    }
    mainPanel.add(gameConfigPanel, BorderLayout.CENTER);
    mainPanel.validate();
    mainPanel.repaint();
  }

  @Override
  public void execute(Features feature) {
    if (boardPanel == null) {
      gameConfigPanel.executeFeature(feature);
    }
    else {
      boardPanel.executeFeature(feature);
      homePageButton.addActionListener(evt -> feature.playGameAgain(true));
      newGameButton.addActionListener(evt -> feature.playGameAgain(false));
      mainPanel.add(buttonsPanel, BorderLayout.PAGE_END);
    }
  }

  public void refresh() {
    //refresh the score
    this.scoreLabel.setText("Score: " + modelState.getScore());
    //this repaints the frame, which cascades to everything added
    //in the frame
    boardPanel.repaint();
    panel.repaint();
    mainPanel.validate();
    mainPanel.repaint();
    this.repaint();
  }

  @Override
  public void renderMessage(String message) {
    this.messageLabel.setText(message);
  }

  @Override
  public void showChosenMarble(int x, int y) {
    boardPanel.showChosenMarble(x, y);
  }
}

