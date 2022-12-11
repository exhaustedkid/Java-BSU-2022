import java.io.IOException;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.*;

public class ShipsManager {
    private static void InitializeObjects() throws IOException, ParseException, InterruptedException {
        JSONParser jsonParser = new JSONParser();

        FileReader reader = new FileReader("src/config.json");
        JSONObject jObject = (JSONObject) jsonParser.parse(reader);
        JSONArray shipsJArray = (JSONArray) jObject.get("ships");

//        List<Ship> ships = new ArrayList<>();
        List<Ship> ships = Collections.synchronizedList(new ArrayList<>());
        for (Object ship : shipsJArray) {
                ships.add(new Ship((String) ((JSONObject)ship).get("name"), (Long) ((JSONObject)ship).get("capacity"), new ArrayList<>()));
        }

        for (Ship s : ships) {
            System.out.println(s.getName());
            System.out.println(s.getCapacity());
        }

        Controller controller = new Controller((Long) jObject.get("generating_time"));
        controller.setShips(ships);
    }

    public static void main(String[] args) throws IOException, ParseException, InterruptedException {
        InitializeObjects();
    }
}