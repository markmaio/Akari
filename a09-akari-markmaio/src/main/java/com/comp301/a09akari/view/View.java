package com.comp301.a09akari.view;

import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.Model;
import javafx.scene.Parent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;

public class View implements FXComponent {
  private final ClassicMvcController controller;
  private final Model model;

  public View(Model model, ClassicMvcController controller) {
    this.model = model;
    this.controller = controller;
  }

  @Override
  public Parent render() {
    BorderPane layout = new BorderPane();

    MessageView messageView = new MessageView(model, controller);
    layout.setTop(messageView.render());

    PuzzleView puzzleView = new PuzzleView(model, controller);
    layout.setCenter(puzzleView.render());

    ControlView controlView = new ControlView(model, controller);
    layout.setBottom(controlView.render());

    return layout;
  }
}
