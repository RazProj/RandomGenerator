
import java.security.SecureRandom;
import java.util.ArrayList;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import java.awt.Point;


// Controller class for the RandomCanvas FXML application.
public class RandomCanvasController {
    @FXML
    private VBox layOutOne;
    @FXML
    Canvas canvas;
    @FXML
    HBox layOutTwo;

    private GraphicsContext gc;
    private static final SecureRandom randomNumbers = new SecureRandom();
    private ArrayList<Point> points;


    // Method that handles the "Generate" button action defined in FXML with onAction="#handleGenerate"
    @FXML
    private void handleGenerate(ActionEvent event) {
        gc = canvas.getGraphicsContext2D();
        setCanvas();
        colorCanvas();
    }

    // Method called after all FXML-annotated fields have been injected, used for initialization.
    public void initialize() {
        setCanvasSize();
    }

    // Method to dynamically set the canvas size based on the VBox layout size.
    public void setCanvasSize() {
        layOutOne.heightProperty().addListener((observable, newVal, oldVal) -> {
            double heightOfLayOutOne = Math.floor(layOutOne.getHeight() - layOutTwo.getHeight());
            heightOfLayOutOne = heightOfLayOutOne - (heightOfLayOutOne % 10);
            canvas.setHeight(heightOfLayOutOne);
        });
        layOutOne.widthProperty().addListener((observable, newVal, oldVal) -> {
            double widthOfLayOutOne = Math.floor(layOutOne.getWidth());
            widthOfLayOutOne = widthOfLayOutOne - (widthOfLayOutOne % 10);
            canvas.setWidth(widthOfLayOutOne);
        });

    }

    // Method to set up the canvas for drawing by creating a grid.
    public void setCanvas() {
        double height = canvas.getHeight();
        double width = canvas.getWidth();
        int x, y;
        points = new ArrayList<>((int) Math.floor((height / 10 * width / 10)));

        gc.clearRect(0, 0, width, height);

        if (height < 10 || width < 10) {
            return; // Exit if the canvas is too small
        }

        gc.setStroke(Color.BLACK);

        // Draw horizontal lines across the canvas and collect points for coloring.
        for (y = 0; y <= height; y += 10) {
            gc.strokeLine(0, y, width, y);
            for (x = 0; x <= width; x += 10) {
                if (y == height) {
                    break;
                }
                points.add(new Point(x, y));
            }

            for (x = 0; x <= width; x += 10) {
                gc.strokeLine(x, 0, x, height);
            }
        }
    }

    // Method to color random squares within the grid on the canvas.
    public void colorCanvas() {
        double height = canvas.getHeight();
        double width = canvas.getWidth();
        int numberOfSquaresToColor = (int) Math.floor(0.1 * (height / 10 * width / 10));

        // Color random squares within the grid.
        for (int i = 0; i <= numberOfSquaresToColor; i++) {
            int rand = randomNumbers.nextInt(points.size());
            Point random = points.get(rand);
            double x1 = random.getX();
            double y1 = random.getY();
            gc.fillRect(x1, y1, 10, 10);
        }
    }
}
