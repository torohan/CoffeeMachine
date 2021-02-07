package CoffeeMachine.utils;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import CoffeeMachine.models.Beverage;
import CoffeeMachine.models.Ingredient;
import CoffeeMachine.models.Machine;

public class InputParser {
    private static final String filePath = "src/main/java/CoffeeMachine/input.json";

    private static int returnIntValueFromObject(Object obj) {
        return ((Long) obj).intValue();
    }

    private static ArrayList<Ingredient> giveIngredientsFromObject(JSONObject jsonObject) {
        ArrayList<Ingredient> ingredients_available = new ArrayList<Ingredient>();
        Iterator<?> s = jsonObject.keySet().iterator();
        String temp;
        while (s.hasNext()) {
            temp = (String) s.next();
            Ingredient ingredient = new Ingredient(temp, returnIntValueFromObject(jsonObject.get(temp)));
            ingredients_available.add(ingredient);
        }
        return ingredients_available;
    }

    private static Machine getMachineObject() {
        String temp;
        try (FileReader reader = new FileReader(filePath)) {
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(reader);
            JSONObject json = (JSONObject) jsonObject.get("machine");

            // Outlets...
            jsonObject = (JSONObject) json.get("outlets");
            int total_outlets = returnIntValueFromObject(jsonObject.get("count_n"));

            // total_items_quantity...
            ArrayList<Ingredient> ingredients_available;
            ingredients_available = giveIngredientsFromObject((JSONObject) json.get("total_items_quantity"));

            // beverages...
            ArrayList<Beverage> beverages_available = new ArrayList<Beverage>();

            jsonObject = (JSONObject) json.get("beverages");
            Iterator<?> beverage_iterator = jsonObject.keySet().iterator();
            while (beverage_iterator.hasNext()) {
                Beverage beverage = new Beverage();
                temp = (String) beverage_iterator.next();
                // System.out.println(temp);
                beverage.setName(temp);
                beverage.setIngredients(giveIngredientsFromObject((JSONObject) jsonObject.get(temp)));
                beverages_available.add(beverage);
            }
            return new Machine(total_outlets, beverages_available, ingredients_available);
        } catch (Exception e) {
            System.out.println("Reading File Exceptiom" + e.getLocalizedMessage());
            return new Machine();
        }
    }

    public Machine getMachine() {
        Machine machine = new Machine();
        machine = getMachineObject();
        return machine;
    }

}
