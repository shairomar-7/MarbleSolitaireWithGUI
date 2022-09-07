package cs3500.marblesolitaire.view;

import java.awt.*;

import javax.swing.*;

import cs3500.marblesolitaire.controller.Features;

public interface IBoardPanel {
  /**
   * Executes a feature on the board panel.
   * @param feature
   */
  void executeFeature(Features feature);
}
