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

        JSONArray cargosJArray = (JSONArray) jObject.get("cargos");
        List<Cargo> cargos = Collections.synchronizedList(new ArrayList<>());
        List<Product> hobosProducts = Collections.synchronizedList(new ArrayList<>());
        List<Cargo> dockCargos = Collections.synchronizedList(new ArrayList<>());
        for (Object cargo : cargosJArray) {
            cargos.add(new Cargo((String) ((JSONObject) cargo).get("cargo"), (Long) ((JSONObject) cargo).get("count")));
            hobosProducts.add(new Cargo((String) ((JSONObject) cargo).get("cargo"), 0));
            dockCargos.add(new Cargo((String) ((JSONObject) cargo).get("cargo"), 0));
        }

        List<Product> ingredients = new ArrayList<>();
        JSONArray ingredientsJArray = (JSONArray) jObject.get("ingredients_count");
        for (Object product : ingredientsJArray) {
            ingredients.add(new Cargo((String) ((JSONObject) product).get("cargo"), (Long) ((JSONObject) product).get("count")));
        }

        JSONArray shipsJArray = (JSONArray) jObject.get("ships");
        List<Ship> ships = Collections.synchronizedList(new ArrayList<>());
        for (Object ship : shipsJArray) {
            ships.add(new Ship((String) ((JSONObject) ship).get("name"),
                    (Long) ((JSONObject) ship).get("capacity"),
                    new Cargo(cargos.get(0).getName(),
                            Math.min(cargos.get(0).getCount(),
                                    (Long) ((JSONObject) ship).get("capacity")))));
            cargos.get(0).DecreaseCount(Math.min(cargos.get(0).getCount(),
                    (Long) ((JSONObject) ship).get("capacity")));
            if (cargos.get(0).getCount() == 0) {
                cargos.remove(0);
            }
        }

        JSONArray hobosJArray = (JSONArray) jObject.get("hobos");
        List<Hobo> hobos = Collections.synchronizedList(new ArrayList<>());
        for (Object hobo : hobosJArray) {
            hobos.add(new Hobo((String) ((JSONObject) hobo).get("name"), (Long) ((JSONObject) hobo).get("stealing_time")));
        }

        Controller controller = new Controller((Long) jObject.get("generating_time"));
        controller.getDock().setEatingTime((Long) jObject.get("eating_time"));
        controller.setShips(ships);
        controller.getDock().setHobos(hobos);
        controller.getDock().setHobosProducts(hobosProducts);
        controller.getDock().setIngredients(ingredients);
        controller.getDock().setCargosInDocks(dockCargos);
    }

    public static void main(String[] args) throws IOException, ParseException, InterruptedException {
        InitializeObjects();
    }
}