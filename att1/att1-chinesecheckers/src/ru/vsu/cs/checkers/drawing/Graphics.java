package ru.vsu.cs.checkers.drawing;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.shape.*;
import javafx.stage.*;

public class Graphics extends Application {

    private final int START_SIZE = 850;

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Checkers game");
        primaryStage.setWidth(START_SIZE);
        primaryStage.setHeight(START_SIZE);
        Double x = 600.0;
        Group root = new Group();
        Canvas canvas = new Canvas(800, 800);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.strokePolygon(new double[]{ x/2 + 100, x/3 + 100, 0.0 + 100, x/6 + 100, 0.0 + 100, x/3 + 100,
                        x/2 + 100, 2*x/3 + 100, x + 100, 5*x/6 + 100, x + 100, 2*x/3 + 100},
                         new double[]{ 0.0, x/3, x/3, 2*x/3, x, x, 4*x/3, x, x, 2*x/3, x/3, x/3}, 12);
        primaryStage.show();
        root.getChildren().add(canvas);
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
    }
}
