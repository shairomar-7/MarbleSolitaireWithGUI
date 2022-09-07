package cs3500.marblesolitaire.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.*;

import cs3500.marblesolitaire.controller.Features;
import cs3500.marblesolitaire.model.hw02.EnglishSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw04.EuropeanSolitaireModel;
import cs3500.marblesolitaire.model.hw04.TriangleSolitaireModel;

public class GameConfigPanel extends JPanel implements IBoardPanel {
  private final JLabel welcomeLabel;
  private final JTextPane marbleSolitaireLabel;
  private final JPanel comboBoxPanel;
  private final JComboBox combobox;
  private final JButton goButton;
  private String selectedType;
  private int thickness, row, col;

  public GameConfigPanel() {
    super();
    this.thickness = -1;
    row = -1;
    col = -1;
    this.setBackground(Color.WHITE);
    this.setLayout(new BorderLayout());
    this.setVisible(true);
    welcomeLabel = new JLabel("Welcome to Peg Solitaire!");
    welcomeLabel.setFont(new Font("Verdana",1,20));
    welcomeLabel.setForeground(Color.RED);
    String message = "OVERVIEW:" +
            "\n\nPeg solitaire, or Marble Solitaire is a board game for one player involving movement of pegs on a board with holes." +
            "\n\nThe game is known as solitaire in Britain and as peg solitaire in the US where 'solitaire' is now the common name for patience." +
            "\n\nTriangle Solitaire is a version of Peg Solitaire that I created which involves jumping diagonally, and a slightly different board set-up." +
            "\n\n\n\nHISTORY:" +
            "\n\nThe first evidence of the game can be traced back to the court of Louis XIV, and the specific date of 1697, with an engraving made" +
            "\n\n ten years later by Claude Auguste Berey of Anne de Rohan-Chabot, Princess of Soubise, with the puzzle by her side." +
            "\n\n\n\nHOW TO PLAY/WIN:" +
            "\n\nA valid move is to jump a peg orthogonally over an adjacent peg into a hole two positions away." +
            "\n\nThe goal is to eliminate as much marbles as you can and to be left with 1 marble in the initially chosen empty slot." +
            "\n\n\n\nHOW TO CUSTOMIZE BOARD:" +
            "\n\nJust choose a version of peg solitaire from the drop-down menu below and go with the flow!" +
            "\n\n\n\nHave fun! Developed by Omar Shair.";
    marbleSolitaireLabel = new JTextPane();
    marbleSolitaireLabel.setForeground(Color.DARK_GRAY);
    marbleSolitaireLabel.setText(message);
    marbleSolitaireLabel.setFont(new Font("Times New Roman", Font.ITALIC, 18));
    marbleSolitaireLabel.setForeground(Color.black);
    add(marbleSolitaireLabel, BorderLayout.LINE_END);
    add(welcomeLabel, BorderLayout.PAGE_START);
    comboBoxPanel = new JPanel();
    combobox = new JComboBox<String>();
    comboBoxPanel.setBorder(BorderFactory.createTitledBorder("Type of Marble Solitaire"));
    comboBoxPanel.setBorder(BorderFactory.createDashedBorder(Color.BLUE));
    comboBoxPanel.setLayout(new BoxLayout(comboBoxPanel, BoxLayout.PAGE_AXIS));
    String[] options = {"english solitaire", "european solitaire", "triangle solitaire"};
    for (int i = 0; i < options.length; i++) {
      combobox.addItem(options[i]);
    }
    comboBoxPanel.add(combobox);
    goButton = new JButton("GO!");
    goButton.setBorder(BorderFactory.createLineBorder(Color.BLUE));
    goButton.setForeground(Color.BLUE);
    add(comboBoxPanel, BorderLayout.CENTER);
    add(goButton, BorderLayout.PAGE_END);
    setVisible(true);
  }

  @Override
  public void executeFeature(Features feature) {
    combobox.addActionListener(evt -> setSelectedSolitaire((String) combobox.getSelectedItem()));
    goButton.addActionListener(evt -> askForOtherDataAndInitGame(feature));
  }

  private void setSelectedSolitaire(String type) {
    selectedType = type;
    System.out.println("setting!");
  }

  private void askForOtherDataAndInitGame(Features features) {
    if (selectedType == null) {
      JOptionPane.showMessageDialog(this,
              "You must choose the type of marble solitaire!" , "Inane error",
              JOptionPane.ERROR_MESSAGE);
      return;
    }
    String inputThickness = JOptionPane.showInputDialog("Enter the desired width of the board:");
    askForThickness(inputThickness);
    String inputEmptySlotRow = JOptionPane.showInputDialog("Enter the desired row of the empty slot (1 -> n)");
    askForRow(inputEmptySlotRow);
    String inputEmptySlotCol = JOptionPane.showInputDialog("Enter the desired column of the empty slot (1 -> n)");
    askForCol(inputEmptySlotCol);
    MarbleSolitaireModel model = modelFactory();
    if (model != null) {
      features.configureGame(model, selectedType, thickness, row, col);
      System.out.println("test test");
    }
  }

  // if -1 is returned then we know the user put the row but not col or col but not row
  private int determineNumArgs() {
    if (thickness == -1 && row == -1 && col == -1) {
      return 0;
    }
    if (thickness != -1 && row == -1 && col == -1) {
      return 1;
    }
    else if (thickness == -1 && row != -1 && col != -1) {
      return 2;
    }
    else if (thickness != -1 && row != -1 && col != -1) {
      return 3;
    }
    return -1;
  }


  private MarbleSolitaireModel modelFactory() {
    try {
      return ModelFactory.createModel(selectedType, determineNumArgs(), new int[] {thickness, row, col});
    }
    catch (IllegalArgumentException e) {
      JOptionPane.showMessageDialog(this,
              e.getMessage() + "\n You must modify your customization according to the above error!" , "Inane error",
              JOptionPane.ERROR_MESSAGE);
      selectedType = null;
      row = -1;
      col = -1;
      return null;
    }
  }

  private void askForThickness(String input) {
    int thickness = -1;
    try {
      thickness = Integer.parseInt(input);
    }
    catch (NumberFormatException n) {
        if (input != null) JOptionPane.showMessageDialog(this,
                "Must be an integer!" , "Inane error",
                JOptionPane.ERROR_MESSAGE);
    }
    this.thickness = thickness;
  }

  private void askForRow(String input) {
    int thickness = -1;
    try {
      thickness = Integer.parseInt(input) - 1;
    }
    catch (NumberFormatException n) {
      if (input != null) JOptionPane.showMessageDialog(this,
              "Must be an integer!" , "Inane error",
              JOptionPane.ERROR_MESSAGE);
    }
    this.row = thickness;
  }

  private void askForCol(String input) {
    int thickness = -1;
    try {
      thickness = Integer.parseInt(input) - 1;
    }
    catch (NumberFormatException n) {
      if (input != null) JOptionPane.showMessageDialog(this,
              "Must be an integer!" , "Inane error",
              JOptionPane.ERROR_MESSAGE);
    }
    this.col = thickness;
  }
}
