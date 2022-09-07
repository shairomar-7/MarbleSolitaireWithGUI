package cs3500.marblesolitaire.view;

import java.util.List;

import cs3500.marblesolitaire.model.hw02.EnglishSolitaireModel;
import cs3500.marblesolitaire.model.hw02.MarbleSolitaireModel;
import cs3500.marblesolitaire.model.hw04.EuropeanSolitaireModel;
import cs3500.marblesolitaire.model.hw04.TriangleSolitaireModel;

public class ModelFactory {
  // args must be always equal to three and must contain the width, row, col in order
  public static MarbleSolitaireModel createModel(String modelType, int nbOfArgs, int[] args) {
    if (args.length != 3) {
      throw new IllegalArgumentException("The size of the list of arguments must be equal to 3!");
    }
    if (modelType.equals("english solitaire")) {
      if (nbOfArgs == 0) return new EnglishSolitaireModel();
      if (nbOfArgs == 1) return new EnglishSolitaireModel(args[0]);
      if (nbOfArgs == 2) return new EnglishSolitaireModel(args[1], args[2]);
      if (nbOfArgs == 3) return new EnglishSolitaireModel(args[0], args[1], args[2]);
    }
    else if (modelType.equals("triangle solitaire")) {
      if (nbOfArgs == 0) return new TriangleSolitaireModel();
      if (nbOfArgs == 1) return new TriangleSolitaireModel(args[0]);
      if (nbOfArgs == 2) return new TriangleSolitaireModel(args[1], args[2]);
      if (nbOfArgs == 3) return new TriangleSolitaireModel(args[0], args[1], args[2]);
    }
    else if (modelType.equals("european solitaire")) {
      if (nbOfArgs == 0) return new EuropeanSolitaireModel();
      if (nbOfArgs == 1) return new EuropeanSolitaireModel(args[0]);
      if (nbOfArgs == 2) return new EuropeanSolitaireModel(args[1], args[2]);
      if (nbOfArgs == 3) return new EuropeanSolitaireModel(args[0], args[1], args[2]);
    }
    throw new IllegalStateException("Unidentified modelType");
  }
}
