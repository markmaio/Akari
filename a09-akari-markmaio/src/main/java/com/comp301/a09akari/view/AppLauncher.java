package com.comp301.a09akari.view;

import com.comp301.a09akari.SamplePuzzles;
import com.comp301.a09akari.controller.ClassicMvcController;
import com.comp301.a09akari.controller.ControllerImpl;
import com.comp301.a09akari.model.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class AppLauncher extends Application {
  @Override
  public void start(Stage stage) {
    PuzzleLibrary puzzleLibrary = new PuzzleLibraryImpl();

    Puzzle puz1 = new PuzzleImpl(SamplePuzzles.PUZZLE_01);
    Puzzle puz2 = new PuzzleImpl(SamplePuzzles.PUZZLE_02);
    Puzzle puz3 = new PuzzleImpl(SamplePuzzles.PUZZLE_03);
    Puzzle puz4 = new PuzzleImpl(SamplePuzzles.PUZZLE_04);
    Puzzle puz5 = new PuzzleImpl(SamplePuzzles.PUZZLE_05);

    puzzleLibrary.addPuzzle(puz1);
    puzzleLibrary.addPuzzle(puz2);
    puzzleLibrary.addPuzzle(puz3);
    puzzleLibrary.addPuzzle(puz4);
    puzzleLibrary.addPuzzle(puz5);

    Model model = new ModelImpl(puzzleLibrary);

    ClassicMvcController controller = new ControllerImpl(model);

    View view = new View(model, controller);

    Scene scene = new Scene(view.render(), 500, 500);
    scene.getStylesheets().add("main.css");
    stage.setScene(scene);

    model.addObserver(
        (Model m) -> {
          scene.setRoot(view.render());
          stage.sizeToScene();
        });

    stage.setTitle("Akari");
    stage.show();
  }
}
