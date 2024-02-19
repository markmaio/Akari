package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.model.Model;
import javafx.scene.Parent;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class MessageView implements FXComponent {
  private final Model model;
  private final ClassicMvcController controller;

  public MessageView(Model model, ClassicMvcController controller) {
    this.model = model;
    this.controller = controller;
  }

  @Override
  public Parent render() {
    StackPane layout = new StackPane();
    Label label;
    if (model.isSolved()) {
      label =
          new Label(
              "Success - Puzzle: "
                  + (model.getActivePuzzleIndex() + 1)
                  + "/"
                  + model.getPuzzleLibrarySize());
    } else {
      label =
          new Label(
              "Nope - Puzzle: "
                  + (model.getActivePuzzleIndex() + 1)
                  + "/"
                  + +model.getPuzzleLibrarySize());
    }

    layout.getChildren().add(label);

    return layout;
  }
}
