package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.CellType;
import com.comp301.a09akari.model.Model;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;

public class PuzzleView implements FXComponent {
  private final Model model;
  private final ClassicMvcController controller;

  public PuzzleView(Model model, ClassicMvcController controller) {
    this.model = model;
    this.controller = controller;
  }

  @Override
  public Parent render() {
    GridPane grid = new GridPane();

    grid.setPadding(new Insets(10, 10, 10, 10));
    grid.setVgap(4);
    grid.setHgap(4);

    for (int r = 0; r < model.getActivePuzzle().getHeight(); r++) {
      for (int c = 0; c < model.getActivePuzzle().getWidth(); c++) {

        Button cell;
        cell = new Button();
        cell.setMinSize(30, 30);
        cell.setMaxSize(30, 30);
        cell.setPrefSize(30, 30);

        if (model.getActivePuzzle().getCellType(r, c) == CellType.CORRIDOR) {
          cell = new Button("   ");

          cell.setMinSize(30, 30);
          cell.setMaxSize(30, 30);
          cell.setPrefSize(30, 30);
          grid.add(cell, r, c);
          final int i = r;
          final int j = c;
          cell.setOnAction((ActionEvent event) -> controller.clickCell(i, j));
          if (model.isLit(r, c)) {
            cell.setStyle("-fx-background-color: #90ee90;");

            cell.setMinSize(30, 30);
            cell.setMaxSize(30, 30);
            cell.setPrefSize(30, 30);
          }
          if (model.isLamp(r, c)) {
            cell.setText("\uD83D\uDC80");
            // cell.setStyle("-fx-font-size: 14px");

            cell.setMinSize(30, 30);
            cell.setMaxSize(30, 30);
            cell.setPrefSize(30, 30);
          }
          if (model.isLamp(r, c) && model.isLampIllegal(r, c)) {
            cell.setStyle("-fx-background-color: YELLOW;");

            cell.setMinSize(30, 30);
            cell.setMaxSize(30, 30);
            cell.setPrefSize(30, 30);
          }
        } else if (model.getActivePuzzle().getCellType(r, c) == CellType.WALL) {
          cell = new Button("   ");
          cell.setStyle("-fx-background-color: BLACK;");

          cell.setMinSize(30, 30);
          cell.setMaxSize(30, 30);
          cell.setPrefSize(30, 30);

          grid.add(cell, r, c);
        } else if (model.getActivePuzzle().getCellType(r, c) == CellType.CLUE) {
          cell = new Button(String.valueOf(model.getActivePuzzle().getClue(r, c)));
          cell.setStyle("-fx-background-color: #CBC3E3;");
          cell.setTextFill(Color.WHITE);

          cell.setMinSize(30, 30);
          cell.setMaxSize(30, 30);
          cell.setPrefSize(30, 30);

          if (model.isClueSatisfied(r, c)) {
            cell.setStyle("-fx-background-color: GREEN;");
          }

          grid.add(cell, r, c);
        }
        cell = new Button();
      }
    }

    return grid;
  }
}
