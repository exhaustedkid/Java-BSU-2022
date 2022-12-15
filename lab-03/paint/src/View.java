import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Rectangle2D;
import javafx.scene.Node;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.Labeled;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.Scene;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.AbstractMap.SimpleEntry;
import java.util.Map.Entry;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.Glow;
import javafx.scene.effect.SepiaTone;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
public class View extends Application {
    public static void launch() {
        Application.launch();
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Paint");
        InputStream iconStream = getClass().getResourceAsStream("/pictures/icon.jpg");
        assert iconStream != null;
        Image mainImage = new Image(iconStream);
        primaryStage.getIcons().add(mainImage);

        final Drawer drawer = new Drawer();

        ToggleButton brushButton = new ToggleButton("brush");
        ToggleButton circleButton = new ToggleButton("circle");
        ToggleButton lineButton = new ToggleButton("line");
        ToggleButton pencilButton = new ToggleButton("pencil");
        ToggleButton rectangleButton = new ToggleButton("rectangle");
        ToggleButton rubButton = new ToggleButton("rub");
        ToggleButton sprayButton = new ToggleButton("spray");
        ToggleButton textButton = new ToggleButton("text");

        TilePane tools = new TilePane(
                brushButton,
                circleButton,
                lineButton,
                pencilButton,
                rectangleButton,
                rubButton,
                sprayButton,
                textButton);

        tools.setOrientation(Orientation.HORIZONTAL);
        for (Node tool : tools.getChildren()) {
            String pictures = "/pictures/";
            String filename = pictures.concat(((Labeled) (tool)).getText());
            String nameWithExtension = filename.concat(".JPG");
            ((ToggleButton) tool).setGraphic(new ImageView(Objects.requireNonNull(getClass()
                            .getResource(nameWithExtension))
                    .toExternalForm()));
            ((ToggleButton) tool).setText(null);
            ChangeButtonSize(tool, 0.5);
        }

        ToggleButton blackButton = new ToggleButton("black");
        ToggleButton blueButton = new ToggleButton("blue");
        ToggleButton greenButton = new ToggleButton("green");
        ToggleButton redButton = new ToggleButton("red");
        ToggleButton whiteButton = new ToggleButton("white");
        ToggleButton yellowButton = new ToggleButton("yellow");

        HBox colors = new HBox(
                blackButton,
                blueButton,
                greenButton,
                redButton,
                whiteButton,
                yellowButton);

        ArrayList<ColorButton> colorButtonsWithText = new ArrayList<>();
        for (Node color : colors.getChildren()) {
            colorButtonsWithText.add(new ColorButton((ToggleButton) color));
            ChangeButtonSize(color, 0.75);
        }

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

        VBox sizes = new VBox(
                smallSizeButton,
                middleSizeButton,
                hugeSizeButton);

        for (Node size : sizes.getChildren()) {
            String nameWithExtension = "/pictures/sizes/" + ((ToggleButton) size).getText() + ".JPG";
            ((ToggleButton) size).setGraphic(new ImageView(Objects.requireNonNull(getClass()
                            .getResource(nameWithExtension))
                    .toExternalForm()));
            ((ToggleButton) size).setText(null);
            ChangeButtonSize(size, 0.75);
        }

        ToggleButton noFillButton = new ToggleButton("no_fill");
        ToggleButton currentColorFillButton = new ToggleButton("first_color_fill");
        ToggleButton secondColorFillButton = new ToggleButton("second_color_fill");

        VBox fills = new VBox(
                noFillButton,
                currentColorFillButton,
                secondColorFillButton);
        for (Node fill_type : fills.getChildren()) {
            String nameWithExtension = "/pictures/fills/" + ((ToggleButton) fill_type).getText() + "_not_pressed.JPG";
            ((ToggleButton) fill_type).setGraphic(new ImageView(Objects.requireNonNull(getClass()
                            .getResource(nameWithExtension))
                    .toExternalForm()));
            ((ToggleButton) fill_type).setText(null);
            ChangeButtonSize(fill_type, 0.75);
        }

        ToggleButton interfacePicture = new ToggleButton();
        interfacePicture.setGraphic(new ImageView(Objects.requireNonNull(getClass()
                        .getResource("pictures/painter.jpg"))
                .toExternalForm()));
        ChangeButtonSize(interfacePicture, 0.9);

        ColorSwitchButton colorSwitchButton = new ColorSwitchButton();

        tools.setVgap(50);
        tools.setHgap(50);
        tools.setPrefColumns(4);
        tools.setPrefRows(2);
        colors.setSpacing(30);
        sizes.setSpacing(45);
        fills.setSpacing(40);
        fills.setPadding(new Insets(50, 25, 150, 40));
        sizes.setPadding(new Insets(40, 25, 25, 40));
        colors.setPadding(new Insets(25, 25, 25, 25));
        tools.setPadding(new Insets(50, 25, 25, 25));
        interfacePicture.setPadding(new Insets(20, 25, 25, 200));
        HBox sizesAndFills = new HBox(sizes, fills);
        sizesAndFills.setSpacing(30);

        ChangeButtonSize(colorSwitchButton.getColorSwitcherButton(), 0.75);
        colorSwitchButton.getColorSwitcherButton().setPadding(new Insets(50, 50, 50, 150));

        VBox panel = new VBox();
        panel.setBackground(Background.fill(Color.GRAY));
        panel.setMaxWidth(200);
        panel.setSpacing(30);
        panel.setPadding(new Insets(10, 25, 10, 25));
        panel.getChildren().add(tools);
        panel.getChildren().add(colors);
        panel.getChildren().add(colorSwitchButton.getColorSwitcherButton());
        panel.getChildren().add(sizesAndFills);
        panel.getChildren().add(interfacePicture);

        colorSwitchButton.getColorSwitcherButton().setOnMousePressed(e -> {
            colorSwitchButton.switchColors();
            drawer.setCurrentColor(colorSwitchButton.getFirstColor());
            drawer.setSecondaryColor(colorSwitchButton.getSecondColor());
        });

        for (ColorButton colorButton : colorButtonsWithText) {
            colorButton.getColorButton().setOnMousePressed(
                    e -> {
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

        noFillButton.setOnMousePressed(e -> {
            drawer.setFigureFill(Constants.FigureFills.NO);
            noFillButton.setGraphic(new ImageView(Objects.requireNonNull(getClass()
                            .getResource("pictures/fills/no_fill_pressed.JPG"))
                    .toExternalForm()));
            currentColorFillButton.setGraphic(new ImageView(Objects.requireNonNull(getClass()
                            .getResource("pictures/fills/first_color_fill_not_pressed.JPG"))
                    .toExternalForm()));
            secondColorFillButton.setGraphic(new ImageView(Objects.requireNonNull(getClass()
                            .getResource("pictures/fills/second_color_fill_not_pressed.JPG"))
                    .toExternalForm()));
        });
        currentColorFillButton.setOnMousePressed(e -> {
            drawer.setFigureFill(Constants.FigureFills.EQUAL);
            noFillButton.setGraphic(new ImageView(Objects.requireNonNull(getClass()
                            .getResource("pictures/fills/no_fill_not_pressed.JPG"))
                    .toExternalForm()));
            currentColorFillButton.setGraphic(new ImageView(Objects.requireNonNull(getClass()
                            .getResource("pictures/fills/first_color_fill_pressed.JPG"))
                    .toExternalForm()));
            secondColorFillButton.setGraphic(new ImageView(Objects.requireNonNull(getClass()
                            .getResource("pictures/fills/second_color_fill_not_pressed.JPG"))
                    .toExternalForm()));
        });
        secondColorFillButton.setOnMousePressed(e -> {
            drawer.setFigureFill(Constants.FigureFills.NOT_EQUAL);
            noFillButton.setGraphic(new ImageView(Objects.requireNonNull(getClass()
                            .getResource("pictures/fills/no_fill_not_pressed.JPG"))
                    .toExternalForm()));
            currentColorFillButton.setGraphic(new ImageView(Objects.requireNonNull(getClass()
                            .getResource("pictures/fills/first_color_fill_not_pressed.JPG"))
                    .toExternalForm()));
            secondColorFillButton.setGraphic(new ImageView(Objects.requireNonNull(getClass()
                            .getResource("pictures/fills/second_color_fill_pressed.JPG"))
                    .toExternalForm()));
        });

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

        MenuBar menuBar = new MenuBar();

        Menu menuFile = new Menu("File");
        MenuItem save = new MenuItem("save");
        MenuItem open = new MenuItem("open");
        MenuItem exit = new MenuItem("close");
        exit.setOnAction(t -> System.exit(0));
        open.setOnAction(t -> {
            FileChooser fileChooser = new FileChooser();
            File file = fileChooser.showOpenDialog(borderPane.getScene().getWindow());
                try {
                    drawer.getPane().getChildren().add(new ImageView(file.toURI().toURL().toExternalForm()));
                } catch (MalformedURLException e) {
                    throw new RuntimeException(e);
                }
        });
        save.setOnAction(t -> {
            SnapshotParameters params = new SnapshotParameters();
            params.setViewport(
                    new Rectangle2D(
                            borderPane.getLeft().getBoundsInLocal().getWidth(),
                            borderPane.getTop().getBoundsInLocal().getHeight(),
                            Constants.paintingAreaWidth - 50,
                            Constants.paintingAreaHeight - borderPane.getTop().getBoundsInLocal().getHeight()));

            WritableImage image = borderPane.snapshot(params, null);
            BufferedImage bufferedImage = SwingFXUtils.fromFXImage(image, null);
            BufferedImage imageRGB = new BufferedImage(
                    bufferedImage.getWidth(), bufferedImage.getHeight(), BufferedImage.OPAQUE);
            Graphics2D graphics = imageRGB.createGraphics();
            graphics.drawImage(bufferedImage, 0, 0, null);
            File file = new File("src/saved_images/Untitled.jpg");
            try {
                ImageIO.write(imageRGB, "jpg", file);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });

        menuFile.getItems().addAll(new SeparatorMenuItem(), open, save, exit);
        menuBar.getMenus().addAll(menuFile);

        Scene scene = new Scene(borderPane, Constants.windowWidth, Constants.windowHeight);
        ((BorderPane) scene.getRoot()).setTop(menuBar);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private JMenu createFileMenu()
    {
        return new JMenu("File");
    }

    private void ChangeButtonSize(Node button, double multiplier) {
        button.setScaleX(multiplier);
        button.setScaleY(multiplier);
        ((ToggleButton)button).setMaxWidth(multiplier * ((ToggleButton)button).getMaxWidth());
        ((ToggleButton)button).setMinWidth(multiplier * ((ToggleButton)button).getMinWidth());
        ((ToggleButton)button).setPrefWidth(multiplier * ((ToggleButton)button).getPrefWidth());
        ((ToggleButton)button).setMaxHeight(multiplier * ((ToggleButton)button).getMaxWidth());
        ((ToggleButton)button).setMinHeight(multiplier * ((ToggleButton)button).getMinWidth());
        ((ToggleButton)button).setPrefHeight(multiplier * ((ToggleButton)button).getPrefWidth());
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

        private final ToggleButton colorButton;
        private final String color;
    }
}
