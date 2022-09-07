package cs3500.marblesolitaire.view;

import java.awt.*;

import javax.swing.*;

import cs3500.marblesolitaire.controller.Features;

public class GameEndPanel extends JPanel {
  JLabel overLabel;

  public GameEndPanel(boolean isOver) {
    super();
    setLayout(new FlowLayout());
    if (isOver) {
      overLabel = new JLabel("GAME OVER! Nice try :)");
    }
    else {
      overLabel = new JLabel("GAME WON! Congratulations!!");
    }
    overLabel.setFont(new Font("Verdana",1,40));
    overLabel.setForeground(Color.pink);
    add(overLabel);
    setVisible(true);
  }
}
