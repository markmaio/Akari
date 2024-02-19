package com.comp301.a09akari.model;

import java.util.*;

public class ModelImpl implements Model {
  private final List<ModelObserver> observers = new ArrayList<>();
  private PuzzleLibrary library = new PuzzleLibraryImpl();
  private int index;
  private boolean[][] board_arr;
  private Puzzle activePuzzle;

  public ModelImpl(PuzzleLibrary library) {
    this.library = library;
    this.index = 0;
    this.board_arr =
        new boolean[library.getPuzzle(index).getHeight()][library.getPuzzle(index).getWidth()];
    this.activePuzzle = library.getPuzzle(index);
  }

  @Override
  public void addLamp(int r, int c) {
    validCorridor(r, c);
    board_arr[r][c] = true;
    notifyObservers();
  }

  @Override
  public void removeLamp(int r, int c) {
    validCorridor(r, c);
    board_arr[r][c] = false;
    notifyObservers();
  }

  @Override
  public boolean isLit(int r, int c) {
    validCorridor(r, c);
    return (isLit_helper(r, c) || isLamp(r, c));
  }

  @Override
  public boolean isLamp(int r, int c) {
    validCorridor(r, c);
    return board_arr[r][c];
  }

  @Override
  public boolean isLampIllegal(int r, int c) {
    if (!isLamp(r, c)) {
      throw new IllegalArgumentException();
    }
    return isLit_helper(r, c);
  }

  @Override
  public Puzzle getActivePuzzle() {
    return activePuzzle;
  }

  @Override
  public int getActivePuzzleIndex() {
    return index;
  }

  @Override
  public void setActivePuzzleIndex(int index) {
    this.index = index;
    activePuzzle = library.getPuzzle(index);
    board_arr = new boolean[activePuzzle.getHeight()][activePuzzle.getWidth()];
    notifyObservers();
  }

  @Override
  public int getPuzzleLibrarySize() {
    return library.size();
  }

  @Override
  public void resetPuzzle() {
    board_arr = new boolean[activePuzzle.getHeight()][activePuzzle.getWidth()];
    notifyObservers();
  }

  @Override
  public boolean isSolved() {
    int solved = 0;
    for (int i = 0; i < activePuzzle.getHeight(); i++) {
      for (int j = 0; j < activePuzzle.getWidth(); j++) {
        if (activePuzzle.getCellType(i, j) == CellType.CLUE) {
          if (!isClueSatisfied(i, j)) {
            solved = solved + 1;
          }
        } else if (activePuzzle.getCellType(i, j) == CellType.CORRIDOR) {
          if (isLamp(i, j)) {
            if (isLampIllegal(i, j)) {
              solved = solved + 1;
            }
          }
          if (!isLit(i, j)) {
            solved = solved + 1;
          }
        }
      }
    }
    return solved == 0;
  }

  @Override
  public boolean isClueSatisfied(int r, int c) {
    validClue(r, c);
    int sum = 0;
    int clue_val = activePuzzle.getClue(r, c);
    try {
      if (isLamp(r + 1, c)) {
        sum++;
      }
    } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
    }
    try {
      if (isLamp(r - 1, c)) {
        sum++;
      }
    } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
    }
    try {
      if (isLamp(r, c + 1)) {
        sum++;
      }
    } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
    }
    try {
      if (isLamp(r, c - 1)) {
        sum++;
      }
    } catch (IndexOutOfBoundsException | IllegalArgumentException e) {
    }

    return clue_val == sum;
  }

  @Override
  public void addObserver(ModelObserver observer) {
    observers.add(observer);
  }

  @Override
  public void removeObserver(ModelObserver observer) {
    observers.remove(observer);
  }

  private void notifyObservers() {
    for (ModelObserver observer : observers) {
      observer.update(this);
    }
  }

  private boolean isLit_helper(int r, int c) {
    int x = r;
    int y = c;
    while ((x <= activePuzzle.getHeight() - 1)
        && (activePuzzle.getCellType(x, y) == CellType.CORRIDOR)) // getHeight -1?
    {
      if (x == r) {
        x = x + 1;
        continue;
      }
      if (isLamp(x, y)) {
        return true;
      }
      x = x + 1;
    }

    x = r;
    y = c;
    while ((y <= activePuzzle.getWidth() - 1)
        && (activePuzzle.getCellType(x, y) == CellType.CORRIDOR)) // getWidth -1;
    {
      if (y == c) {
        y = y + 1;
        continue;
      }
      if (isLamp(x, y)) {
        return true;
      }
      y = y + 1;
    }

    x = r;
    y = c;
    while ((x >= 0) && (activePuzzle.getCellType(x, y) == CellType.CORRIDOR)) {
      if (x == r) {
        x = x - 1;
        continue;
      }
      if (isLamp(x, y)) {
        return true;
      }
      x = x - 1;
    }

    x = r;
    y = c;
    while ((y >= 0) && (activePuzzle.getCellType(x, y) == CellType.CORRIDOR)) {
      if (y == c) {
        y = y - 1;
        continue;
      }
      if (isLamp(x, y)) {
        return true;
      }
      y = y - 1;
    }

    return false;
  }

  public void validCorridor(int r, int c) {
    if (activePuzzle.getHeight() < r || activePuzzle.getWidth() < c) {
      throw new IndexOutOfBoundsException();
    }
    if (activePuzzle.getCellType(r, c) != CellType.CORRIDOR) {
      throw new IllegalArgumentException();
    }
  }

  public void validClue(int r, int c) {
    if (r > activePuzzle.getHeight() || c > activePuzzle.getWidth()) {
      throw new IndexOutOfBoundsException();
    }
    if (activePuzzle.getCellType(r, c) != CellType.CLUE) {
      throw new IllegalArgumentException();
    }
  }
}
