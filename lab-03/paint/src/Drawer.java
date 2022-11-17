import javafx.event.Event;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;

import javafx.scene.paint.Color;


public class Drawer {

    Drawer() {
        GraphicsContext graphicsContext = getCanvas().getGraphicsContext2D();
        graphicsContext.strokeLine(10, 10, 40, 40);
        graphicsContext.setFill(Color.BLUE);
        graphicsContext.fillRect(0, 0, paintingArea.getWidth(), paintingArea.getHeight());
//        paintingArea.setOnMousePressed(e -> {
//            System.out.println(10);
//            if (currentTool == Constants.Tool.brush) {
//                graphicsContext.setFill(currentColor);
//                graphicsContext.fillOval(e.getX(), e.getY(), 10, 10);
//
//                System.out.println(100);
//            }
//        });
        paintingArea.setOnMouseDragged(e -> { // Pressed while(pressed) { handler }
            if (currentTool == Constants.Tool.brush) {
                graphicsContext.setFill(currentColor);
                graphicsContext.fillOval(e.getX(), e.getY(), 10, 10);
            } else if (currentTool == Constants.Tool.pencil) {
                graphicsContext.setFill(currentColor);
                graphicsContext.fillOval(e.getX(), e.getY(), 2, 2);
            } else if (currentTool == Constants.Tool.spray) {
                graphicsContext.setFill(currentColor);

                for (int i = 0; i < 3; ++i) {
                    graphicsContext.fillOval(e.getX() + Math.random() * 5 * currentMultiplier, e.getY() + Math.random() * 5 * currentMultiplier, 2, 2);
                }
                for (int i = 0; i < 3; ++i) {
                    graphicsContext.fillOval(e.getX() + Math.random() * 5 * currentMultiplier, e.getY() - Math.random() * 5 * currentMultiplier, 2, 2);
                }
                for (int i = 0; i < 3; ++i) {
                    graphicsContext.fillOval(e.getX() - Math.random() * 5 * currentMultiplier, e.getY() + Math.random() * 5 * currentMultiplier, 2, 2);
                }
                for (int i = 0; i < 3; ++i) {
                    graphicsContext.fillOval(e.getX() - Math.random() * 5 * currentMultiplier, e.getY() - Math.random() * 5 * currentMultiplier, 2, 2);
                }
            } else if (currentTool == Constants.Tool.rub) {
                graphicsContext.setFill(currentColor);
                graphicsContext.rect(e.getX(), e.getY(), 10, 10);
            }
        });
    }

    public void setTool(Constants.Tool tool) {
        currentTool = tool;
    }

    public Canvas getCanvas() {
        return paintingArea;
    }

    private Constants.Tool currentTool;
    private Color currentColor = Color.BLACK;
    private double currentMultiplier = Constants.smallSize;
    private final Canvas paintingArea = new Canvas(400, 300);
}