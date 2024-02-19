package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.layout.HBox;
import javafx.scene.control.Button;

import java.awt.*;
import javafx.event.ActionEvent;

public class ControlView implements FXComponent {
  private final Model model;
  private final ClassicMvcController controller;

  public ControlView(Model model, ClassicMvcController controller) {
    this.model = model;
    this.controller = controller;
  }

  @Override
  public Parent render() {
    HBox layout = new HBox();

    Button randPuzzle = new Button("Random");
    randPuzzle.setOnAction((ActionEvent event) -> controller.clickRandPuzzle());

    Button resetPuzzle = new Button("Start Over");
    resetPuzzle.setOnAction((ActionEvent event) -> controller.clickResetPuzzle());

    Button newPuzzle = new Button("Next");
    newPuzzle.setOnAction((ActionEvent event) -> controller.clickNextPuzzle());

    Button prev = new Button("Previous");
    prev.setOnAction((ActionEvent event) -> controller.clickPrevPuzzle());

    Button done = new Button("Done?");
    done.setOnAction(
        (ActionEvent event) -> {
          if (model.isSolved()) {
            done.setStyle("-fx-background-color: GREEN;");
            done.setText("Done!");
          } else {
            done.setStyle("-fx-background-color: RED;");
            done.setText("Nope");
          }
        });

    layout.getChildren().addAll(resetPuzzle, randPuzzle, prev, newPuzzle, done);

    layout.setAlignment(Pos.BASELINE_CENTER);

    return layout;
  }
}
