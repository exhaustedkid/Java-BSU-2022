import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public class Drawer {

    Drawer() {

        GraphicsContext graphicsContext = getCanvas().getGraphicsContext2D();
        GraphicsContext newLayerGraphicsContext = newLayer.getGraphicsContext2D();

        pane.getChildren().add(newLayer);
        pane.getChildren().add(paintingArea);
        paintingArea.setOnMouseDragged(e -> {
            if (currentTool == Constants.Tool.spray) {
                for (int i = 0; i < 3 * currentMultiplier; ++i) {
                    graphicsContext.fillOval(e.getX() + Math.random() * 5 * currentMultiplier, e.getY() + Math.random() * 5 * currentMultiplier, 2, 2);
                }
                for (int i = 0; i < 3 * currentMultiplier; ++i) {
                    graphicsContext.fillOval(e.getX() + Math.random() * 5 * currentMultiplier, e.getY() - Math.random() * 5 * currentMultiplier, 2, 2);
                }
                for (int i = 0; i < 3 * currentMultiplier; ++i) {
                    graphicsContext.fillOval(e.getX() - Math.random() * 5 * currentMultiplier, e.getY() + Math.random() * 5 * currentMultiplier, 2, 2);
                }
                for (int i = 0; i < 3 * currentMultiplier; ++i) {
                    graphicsContext.fillOval(e.getX() - Math.random() * 5 * currentMultiplier, e.getY() - Math.random() * 5 * currentMultiplier, 2, 2);
                }
            } else if (currentTool == Constants.Tool.rub) {
                graphicsContext.setFill(Color.WHITE);
                graphicsContext.fillRect(e.getX(), e.getY(), 10, 10);
            } else if (currentTool == Constants.Tool.line) {
                newLayer.toFront();
                newLayerGraphicsContext.clearRect(0, 0, Constants.paintingAreaWidth, Constants.paintingAreaHeight);
                newLayerGraphicsContext.strokeLine(startX, startY, e.getX(), e.getY());
                newLayer.toBack();
            } else if (currentTool == Constants.Tool.circle) {
                newLayer.toFront();
                newLayerGraphicsContext.clearRect(0, 0, Constants.paintingAreaWidth, Constants.paintingAreaHeight);
                newLayerGraphicsContext.strokeOval(Math.min(startX, e.getX()),
                        Math.min(startY, e.getY()),
                        Math.abs(startX - e.getX()),
                        Math.abs(startY - e.getY()));
                newLayer.toBack();
            } else if (currentTool == Constants.Tool.rectangle) {
                newLayer.toFront();
                newLayerGraphicsContext.clearRect(0, 0, Constants.paintingAreaWidth, Constants.paintingAreaHeight);
                newLayerGraphicsContext.strokeRect(Math.min(startX, e.getX()),
                        Math.min(startY, e.getY()),
                        Math.abs(startX - e.getX()),
                        Math.abs(startY - e.getY()));
                newLayer.toBack();
            }
        });

        paintingArea.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_PRESSED, e -> {
            graphicsContext.setStroke(currentColor);
            newLayerGraphicsContext.setStroke(currentColor);
            if (figureFill == Constants.FigureFills.EQUAL) {
                graphicsContext.setFill(currentColor);
            } else if (figureFill == Constants.FigureFills.NOT_EQUAL) {
                graphicsContext.setFill(secondaryColor);
            } else if (figureFill == Constants.FigureFills.NO) {
                graphicsContext.setFill(Color.TRANSPARENT);
            }
            if (currentTool == Constants.Tool.brush) {
                graphicsContext.setLineWidth(3 * currentMultiplier);
                newLayerGraphicsContext.setLineWidth(3 * currentMultiplier);
            } else if (currentTool == Constants.Tool.pencil) {
                graphicsContext.setLineWidth(1);
                newLayerGraphicsContext.setLineWidth(1);
            } else {
                graphicsContext.setLineWidth(2 * currentMultiplier);
                newLayerGraphicsContext.setLineWidth(2 * currentMultiplier);
            }
            startX = e.getX();
            startY = e.getY();
            prevX = e.getX();
            prevY = e.getY();
        });
        paintingArea.addEventHandler(MouseEvent.MOUSE_RELEASED, e -> {
            endX = e.getX();
            endY = e.getY();
            if (currentTool == Constants.Tool.line) {
                if (endX != 0 || endY != 0) {
                    graphicsContext.strokeLine(startX, startY, e.getX(), e.getY());
                }
            } else if (currentTool == Constants.Tool.circle) {
                if (endX != 0 || endY != 0) {
                    graphicsContext.strokeOval(Math.min(startX, e.getX()),
                            Math.min(startY, e.getY()),
                            Math.abs(startX - e.getX()),
                            Math.abs(startY - e.getY()));
                    graphicsContext.fillOval(Math.min(startX, e.getX()),
                            Math.min(startY, e.getY()),
                            Math.abs(startX - e.getX()),
                            Math.abs(startY - e.getY()));
                }
            } else if (currentTool == Constants.Tool.rectangle) {
                if (endX != 0 || endY != 0) {
                    graphicsContext.strokeRect(Math.min(startX, e.getX()),
                            Math.min(startY, e.getY()),
                            Math.abs(startX - e.getX()),
                            Math.abs(startY - e.getY()));
                    graphicsContext.fillRect(Math.min(startX, e.getX()),
                            Math.min(startY, e.getY()),
                            Math.abs(startX - e.getX()),
                            Math.abs(startY - e.getY()));
                }
            }
        });
        paintingArea.addEventHandler(MouseEvent.MOUSE_DRAGGED, e -> {
            endX = e.getX();
            endY = e.getY();
            if (currentTool == Constants.Tool.brush) {
                if (prevX != 0 || prevY != 0) {
                    graphicsContext.strokeLine(prevX, prevY, e.getX(), e.getY());
                }
            }
            if (currentTool == Constants.Tool.pencil) {
                if (prevX != 0 || prevY != 0) {
                    graphicsContext.strokeLine(prevX, prevY, e.getX(), e.getY());
                }
            }
            prevX = e.getX();
            prevY = e.getY();
        });
    }

    public void setTool(Constants.Tool tool) {
        currentTool = tool;
    }

    public void setCurrentColor(Color color) {
        currentColor = color;
    }

    public void setSecondaryColor(Color color) {
        secondaryColor = color;
    }

    public void setSize(double size) {
        currentMultiplier = size;
    }

    public void setFigureFill(Constants.FigureFills fill) {
        figureFill = fill;
    }

    public Canvas getCanvas() {
        return paintingArea;
    }

    public Pane getPane() {
        return pane;
    }

    private Constants.Tool currentTool;
    private Color currentColor = Color.BLACK;
    private Color secondaryColor = Color.WHITE;
    private Constants.FigureFills figureFill = Constants.FigureFills.NOT_EQUAL;
    private double currentMultiplier = Constants.smallSize;
    private final Canvas paintingArea = new Canvas(Constants.paintingAreaWidth, Constants.paintingAreaHeight);
    private final Canvas newLayer = new Canvas(Constants.paintingAreaWidth, Constants.paintingAreaHeight);
    private final Pane pane = new Pane();
    private double startX;
    private double startY;
    private double endX = 0;
    private double endY = 0;
    private double prevX = 0;
    private double prevY = 0;
}