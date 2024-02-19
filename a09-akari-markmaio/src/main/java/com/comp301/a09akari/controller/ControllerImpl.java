package com.comp301.a09akari.controller;

import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;

import java.util.Random;

public class ControllerImpl implements ClassicMvcController {
  private final Model model;

  public ControllerImpl(Model model) {
    if (model == null) {
      throw new IllegalArgumentException("Model cannot be null");
    }
    this.model = model;
  }

  @Override
  public void clickNextPuzzle() {
    if (model.getActivePuzzleIndex() != model.getPuzzleLibrarySize() - 1) {
      model.setActivePuzzleIndex(model.getActivePuzzleIndex() + 1);
    }
  }

  @Override
  public void clickPrevPuzzle() {
    if (model.getActivePuzzleIndex() != 0) {
      model.setActivePuzzleIndex(model.getActivePuzzleIndex() - 1);
    }
  }

  @Override
  public void clickRandPuzzle() {
    int upperBound = model.getPuzzleLibrarySize();
    Random rand = new Random();
    int randInt = rand.nextInt(upperBound);

    while (randInt == model.getActivePuzzleIndex()) {
      randInt = rand.nextInt(upperBound);
    }

    model.setActivePuzzleIndex(randInt);
  }

  @Override
  public void clickResetPuzzle() {
    model.resetPuzzle();
  }

  @Override
  public void clickCell(int r, int c) {
    if (model.getActivePuzzle().getCellType(r, c) == CellType.CORRIDOR) {
      if (model.isLamp(r, c)) {
        model.removeLamp(r, c);
      } else if (!model.isLamp(r, c)) {
        model.addLamp(r, c);
      }
    }
  }
}
