import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.control.Labeled;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
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
        tools.setMaxHeight(400);

        ToggleButton blackButton = new ToggleButton("black");
        ToggleButton blueButton = new ToggleButton("blue");
        ToggleButton greenButton = new ToggleButton("green");
        ToggleButton redButton = new ToggleButton("red");
        ToggleButton whiteButton = new ToggleButton("white");
        ToggleButton yellowButton = new ToggleButton("yellow");


        FlowPane colors = new FlowPane(
                blackButton,
                blueButton,
                greenButton,
                redButton,
                whiteButton,
                yellowButton);

        ArrayList<ColorButton> colorButtonsWithText = new ArrayList<>();
        for (Node color : colors.getChildren()) {
            colorButtonsWithText.add(new ColorButton((ToggleButton) color));
        }

        colors.setOrientation(Orientation.HORIZONTAL);

        for (ColorButton colorButton : colorButtonsWithText) {
            String nameWithExtension = "/pictures/colors/" + colorButton.getColor() + ".JPG";
            colorButton.getColorButton().setGraphic(new ImageView(Objects.requireNonNull(getClass()
                            .getResource(nameWithExtension))
                    .toExternalForm()));
            colorButton.getColorButton().setText(null);
        }

        ToggleButton smallSizeButton = new ToggleButton("small_size");
        ToggleButton middleSizeButton = new ToggleButton("middle_size");
        ToggleButton hugeSizeButton = new ToggleButton("huge_size");

        FlowPane sizes = new FlowPane(
                smallSizeButton,
                middleSizeButton,
                hugeSizeButton);

        sizes.setOrientation(Orientation.VERTICAL);
        for (Node size : sizes.getChildren()) {
            String nameWithExtension = "/pictures/sizes/" + ((ToggleButton) size).getText() + ".JPG";
            ((ToggleButton) size).setGraphic(new ImageView(Objects.requireNonNull(getClass()
                            .getResource(nameWithExtension))
                    .toExternalForm()));
            ((ToggleButton) size).setText(null);
        }


        ToggleButton noFillButton = new ToggleButton("no_fill");
        ToggleButton currentColorFillButton = new ToggleButton("first_color_fill");
        ToggleButton secondColorFillButton = new ToggleButton("second_color_fill");

        FlowPane fills = new FlowPane(
                noFillButton,
                currentColorFillButton,
                secondColorFillButton);

        fills.setOrientation(Orientation.VERTICAL);
        for (Node fill_type : fills.getChildren()) {
            String nameWithExtension = "/pictures/fills/" + ((ToggleButton) fill_type).getText() + ".JPG";
            ((ToggleButton) fill_type).setGraphic(new ImageView(Objects.requireNonNull(getClass()
                            .getResource(nameWithExtension))
                    .toExternalForm()));
            ((ToggleButton) fill_type).setText(null);
        }


        ColorSwitchButton colorSwitchButton = new ColorSwitchButton();

        VBox panel = new VBox();
        panel.setBackground(Background.fill(Color.GRAY));
        panel.getChildren().add(tools);
        panel.getChildren().add(colors);
        panel.getChildren().add(sizes);
        panel.getChildren().add(fills);
        panel.getChildren().add(colorSwitchButton.getColorSwitcherButton());

        colorSwitchButton.getColorSwitcherButton().setOnMousePressed(e -> {
            colorSwitchButton.switchColors();
            drawer.setCurrentColor(colorSwitchButton.getFirstColor());
            drawer.setSecondaryColor(colorSwitchButton.getSecondColor());
        });

        for (ColorButton colorButton : colorButtonsWithText) {
            colorButton.getColorButton().setOnMousePressed(
                    e -> {
                        System.out.println(colorButton.getColor());
                        if (e.isPrimaryButtonDown()) {
                            drawer.setCurrentColor(ColorsDecoder.Decode(colorButton.getColor()));
                            colorSwitchButton.changeColors(colorButton.getColor(), false);
                        } else {
                            drawer.setSecondaryColor(ColorsDecoder.Decode(colorButton.getColor()));
                            colorSwitchButton.changeColors(false, (colorButton.getColor()));
                        }
                    });
        }

        BorderPane borderPane = new BorderPane();
        drawer.getPane().setBackground(Background.fill(Color.WHITE));
        borderPane.setCenter(drawer.getPane());
        borderPane.setLeft(panel);

        smallSizeButton.setOnMousePressed(e -> drawer.setSize(Constants.smallSize));
        middleSizeButton.setOnMousePressed(e -> drawer.setSize(Constants.middleSize));
        hugeSizeButton.setOnMousePressed(e -> drawer.setSize(Constants.hugeSize));

        noFillButton.setOnMousePressed(e -> drawer.setFigureFill(Constants.FigureFills.NO));
        currentColorFillButton.setOnMousePressed(e -> drawer.setFigureFill(Constants.FigureFills.EQUAL));
        secondColorFillButton.setOnMousePressed(e -> drawer.setFigureFill(Constants.FigureFills.NOT_EQUAL));

        brushButton.setOnMousePressed(e -> drawer.setTool(Constants.Tool.brush));
        pencilButton.setOnMousePressed(e -> drawer.setTool(Constants.Tool.pencil));
        rubButton.setOnMousePressed(e -> drawer.setTool(Constants.Tool.rub));
        circleButton.setOnMousePressed(e -> drawer.setTool(Constants.Tool.circle));
        lineButton.setOnMousePressed(e -> drawer.setTool(Constants.Tool.line));
        rectangleButton.setOnMousePressed(e -> drawer.setTool(Constants.Tool.rectangle));
        sprayButton.setOnMousePressed(e -> drawer.setTool(Constants.Tool.spray));
        textButton.setOnMousePressed(e -> {
            try {
                Desktop.getDesktop().browse(new URL("https://www.youtube.com/watch?v=dQw4w9WgXcQ").toURI());
            } catch (IOException | URISyntaxException ex) {
                throw new RuntimeException(ex);
            }
        });

        Scene scene = new Scene(borderPane, Constants.windowWidth, Constants.windowHeight);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private class ColorSwitchButton {
        public ColorSwitchButton() {
            colorSwitcher.setGraphic(new ImageView(Objects.requireNonNull(getClass()
                    .getResource("/pictures/fills/black-white.JPG")).toExternalForm()));
        }

        private void RefreshPicture() {
            colorSwitcher.setGraphic(new ImageView(Objects.requireNonNull(getClass()
                    .getResource("/pictures/fills/" + firstColor + "-" + secondColor + ".JPG")).toExternalForm()));
        }

        public void switchColors() {
            String bufString = firstColor;
            firstColor = secondColor;
            secondColor = bufString;
            RefreshPicture();
        }

        public void changeColors(boolean dont, String newColor) {
            secondColor = newColor;
            RefreshPicture();
        }

        public void changeColors(String newColor, boolean dont) {
            firstColor = newColor;
            RefreshPicture();
        }

        public void changeColors(String firstNewColor, String secondNewColor) {
            firstColor = firstNewColor;
            secondColor = secondNewColor;
            RefreshPicture();
        }

        public ToggleButton getColorSwitcherButton() {
            return colorSwitcher;
        }

        public Color getFirstColor() {
            return ColorsDecoder.Decode(firstColor);
        }

        public Color getSecondColor() {
            return ColorsDecoder.Decode(secondColor);
        }

        private final ToggleButton colorSwitcher = new ToggleButton();
        private String firstColor = "black";
        private String secondColor = "white";
    }

    private class ColorButton {
        ColorButton(ToggleButton button) {
            colorButton = button;
            color = button.getText();
        }

        public ToggleButton getColorButton() {
            return colorButton;
        }

        public String getColor() {
            return color;
        }

        private ToggleButton colorButton;
        private final String color;
    }
}
