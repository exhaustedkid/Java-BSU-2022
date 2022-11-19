import javafx.scene.paint.Color;
import java.util.Objects;

public class ColorsDecoder {
    static public Color Decode(String color) {
        if (Objects.equals(color, "black")) {
            return Color.BLACK;
        } else if (Objects.equals(color, "blue")) {
            return Color.BLUE;
        } else if (Objects.equals(color, "red")) {
            return Color.RED;
        } else if (Objects.equals(color, "yellow")) {
            return Color.YELLOW;
        } else if (Objects.equals(color, "green")) {
            return Color.GREEN;
        } else if (Objects.equals(color, "white")) {
            return Color.WHITE;
        }
        return null;
    }
}
