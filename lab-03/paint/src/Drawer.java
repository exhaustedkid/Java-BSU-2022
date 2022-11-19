import javafx.event.EventHandler;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;

public class Drawer {

    Drawer() {

        GraphicsContext graphicsContext = getCanvas().getGraphicsContext2D();
        GraphicsContext newLayerGraphicsContext = newLayer.getGraphicsContext2D();

        pane.getChildren().add(newLayer);
        pane.getChildren().add(paintingArea);
        paintingArea.setOnMouseDragged(e -> { // Pressed while(pressed) { handler }
            if (currentTool == Constants.Tool.brush) {
//                graphicsContext.setFill(currentColor);
                graphicsContext.setLineWidth(10);
            } else if (currentTool == Constants.Tool.pencil) {
//                graphicsContext.setFill(currentColor);
            } else if (currentTool == Constants.Tool.spray) {
//                graphicsContext.setFill(currentColor);

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
                graphicsContext.setFill(Color.WHITE);
                graphicsContext.fillRect(e.getX(), e.getY(), 10, 10);
            } else if (currentTool == Constants.Tool.line) {
                newLayer.toFront();
                newLayerGraphicsContext.clearRect(0, 0, Constants.paintingAreaWidth, Constants.paintingAreaHeight);
//                newLayerGraphicsContext.setLineWidth(1);
                newLayerGraphicsContext.strokeLine(startX, startY, e.getX(), e.getY());
                newLayer.toBack();
            } else if (currentTool == Constants.Tool.circle) {
                newLayer.toFront();
                newLayerGraphicsContext.clearRect(0, 0, Constants.paintingAreaWidth, Constants.paintingAreaHeight);
//                newLayerGraphicsContext.setLineWidth(1);
                newLayerGraphicsContext.strokeOval(Math.min(startX, e.getX()),
                        Math.min(startY, e.getY()),
                        Math.abs(startX - e.getX()),
                        Math.abs(startY - e.getY()));
                newLayer.toBack();
            } else if (currentTool == Constants.Tool.rectangle) {
                newLayer.toFront();
                newLayerGraphicsContext.clearRect(0, 0, Constants.paintingAreaWidth, Constants.paintingAreaHeight);
//                newLayerGraphicsContext.setLineWidth(1);
                newLayerGraphicsContext.strokeRect(Math.min(startX, e.getX()),
                        Math.min(startY, e.getY()),
                        Math.abs(startX - e.getX()),
                        Math.abs(startY - e.getY()));
                newLayer.toBack();
            }
        });

        paintingArea.addEventHandler(javafx.scene.input.MouseEvent.MOUSE_PRESSED,
                new EventHandler<>() {
                    @Override
                    public void handle(MouseEvent e) {
                        graphicsContext.setFill(currentColor);
                        startX = e.getX();
                        startY = e.getY();
                        prevX = e.getX();
                        prevY = e.getY();

                    }
                });
        paintingArea.addEventHandler(MouseEvent.MOUSE_RELEASED,
                new EventHandler<>() {
                    @Override
                    public void handle(MouseEvent e) {
                        endX = e.getX();
                        endY = e.getY();
                        if (currentTool == Constants.Tool.line) {
                            if (endX != 0 || endY != 0) {
                                graphicsContext.setLineWidth(1);
                                graphicsContext.strokeLine(startX, startY, e.getX(), e.getY());
                            }
                        } else if (currentTool == Constants.Tool.circle) {
                            if (endX != 0 || endY != 0) {
                                graphicsContext.setLineWidth(1);
                                graphicsContext.strokeOval(Math.min(startX, e.getX()),
                                        Math.min(startY, e.getY()),
                                        Math.abs(startX - e.getX()),
                                        Math.abs(startY - e.getY())); // pokrytiye
                            }
                        } else if (currentTool == Constants.Tool.rectangle) {
                            if (endX != 0 || endY != 0) {
                                graphicsContext.setLineWidth(1);
                                graphicsContext.strokeRect(Math.min(startX, e.getX()),
                                        Math.min(startY, e.getY()),
                                        Math.abs(startX - e.getX()),
                                        Math.abs(startY - e.getY())); // pokrytiye
                            }
                        }
                    }
                });
        paintingArea.addEventHandler(MouseEvent.MOUSE_DRAGGED,
                new EventHandler<>() {
                    @Override
                    public void handle(MouseEvent e) {
                        endX = e.getX();
                        endY = e.getY();
                        if (currentTool == Constants.Tool.brush) {
                            if (prevX != 0 || prevY != 0) {
                                graphicsContext.setLineWidth(3);
                                graphicsContext.strokeLine(prevX, prevY, e.getX(), e.getY());
                            }
                        }
                        if (currentTool == Constants.Tool.pencil) {
                            if (prevX != 0 || prevY != 0) {
                                graphicsContext.setLineWidth(1);
                                graphicsContext.strokeLine(prevX, prevY, e.getX(), e.getY());
                            }
                        }
                        prevX = e.getX();
                        prevY = e.getY();
                    }
                });
    }

    //    private void resetLayout(Layout)
    public void setTool(Constants.Tool tool) {
        currentTool = tool;
    }

    public Canvas getCanvas() {
        return paintingArea;
    }

    public Pane getPane() {
        return pane;
    }

    public Canvas getNewLayer() {
        return newLayer;
    }

    private Constants.Tool currentTool;
    private Color currentColor = Color.BLACK;
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