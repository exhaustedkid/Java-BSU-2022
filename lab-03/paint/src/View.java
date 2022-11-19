import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Labeled;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.canvas.Canvas;


import java.io.InputStream;
import java.util.Objects;

public class View extends Application {
    public static void launch() {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Paint");
        InputStream iconStream = getClass().getResourceAsStream("/pictures/icon.jpg");
        assert iconStream != null;
        Image image = new Image(iconStream);
        primaryStage.getIcons().add(image);

        final Drawer drawer = new Drawer();


        ToggleButton brushButton = new ToggleButton("brush");
        ToggleButton circleButton = new ToggleButton("circle");
        ToggleButton lineButton = new ToggleButton("line");
        ToggleButton pencilButton = new ToggleButton("pencil");
        ToggleButton rectangleButton = new ToggleButton("rectangle");
        ToggleButton rubButton = new ToggleButton("rub");
        ToggleButton sprayButton = new ToggleButton("spray");
        ToggleButton textButton = new ToggleButton("text");

        FlowPane tools = new FlowPane(
                brushButton,
                circleButton,
                lineButton,
                pencilButton,
                rectangleButton,
                rubButton,
                sprayButton,
                textButton);

        tools.setOrientation(Orientation.HORIZONTAL);
//        tools.setOrientation(Orientation.VERTICAL);
//        tools.setSpacing()

//        tools.setColumnHalignment(HPos.CENTER);
//        tools.setPadding(new Insets(0.1));
//        tools.setPrefWrapLength(10);
        for (Node tool : tools.getChildren()) {
            String pictures = "/pictures/";
            String filename = pictures.concat(((Labeled) (tool)).getText());
            String nameWithExtension = filename.concat(".JPG");
            ((ToggleButton) tool).setGraphic(new ImageView(Objects.requireNonNull(getClass()
                            .getResource(nameWithExtension))
                    .toExternalForm()));
            ((ToggleButton) tool).setText(null);
//            ((ToggleButton) tool).setScaleX(0.35);
//            ((ToggleButton) tool).setScaleY(0.35);
//            tool.resize(100, 100);
//            ((ToggleButton) tool).widthProperty();
//            ((ToggleButton) tool).setPrefWidth(0.1);
//            ((ToggleButton) tool).setMaxHeight(10);


        }

        for (Node tool : tools.getChildren()) {
            FlowPane.setMargin(tool, new Insets(0, 0, 0, 0));
        }
//        tools.setHgap(25);
//        tools.setVgap(0);
//        tools.setPrefWrapLength(50);
        tools.setMaxHeight(400);


        BorderPane borderPane = new BorderPane();
//        borderPane.setCenter(drawer.getCanvas());
        borderPane.setCenter(drawer.getPane());
        borderPane.setLeft(tools);

        brushButton.setOnMousePressed(e -> drawer.setTool(Constants.Tool.brush));
        pencilButton.setOnMousePressed(e -> drawer.setTool(Constants.Tool.pencil));
        rubButton.setOnMousePressed(e -> drawer.setTool(Constants.Tool.rub));
        circleButton.setOnMousePressed(e -> drawer.setTool(Constants.Tool.circle));
        lineButton.setOnMousePressed(e -> drawer.setTool(Constants.Tool.line));
        rectangleButton.setOnMousePressed(e -> drawer.setTool(Constants.Tool.rectangle));
        sprayButton.setOnMousePressed(e -> drawer.setTool(Constants.Tool.spray));
        textButton.setOnMousePressed(e -> drawer.setTool(Constants.Tool.text));



//        tools.setOnMousePressed(e->{
//            System.out.println(5);
//            if (brushButton.isSelected()) {
//                drawer.setTool(Constants.Tool.brush);
//                System.out.println(30);
//            }
//            if (brushButton.isPressed()) {
//                drawer.setTool(Constants.Tool.brush);
//                System.out.println(40);
//            }
//        });

        Scene scene = new Scene(borderPane, Constants.windowWidth, Constants.windowHeight);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
