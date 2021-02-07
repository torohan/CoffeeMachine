package CoffeeMachine;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import CoffeeMachine.models.Ingredient;
import CoffeeMachine.models.Machine;
import CoffeeMachine.services.MachineService;
import CoffeeMachine.utils.InputParser;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

/**
 * Unit test for simple App.
 */
class AppTest {
    /**
     * Rigorous Test.
     */

    @Test
    void prepareBeveragesTestWtihEmptyInput() {
        Machine machine = Mockito.mock(Machine.class);
        MachineService machineService = new MachineService(machine);
        List<String> beverage = new ArrayList<String>();
        List<String> response = machineService.prepareBeverages(beverage);
        when(machine.getNumber_of_outlets()).thenReturn(3);
        assertEquals(0, response.size());
    }

    @Test
    void prepareBeveragesTest() {
        InputParser inputParser = new InputParser();
        MachineService machineService = new MachineService(inputParser.getMachine());
        List<String> beverage = new ArrayList<String>();
        beverage.add("hot_tea");
        beverage.add("hot_tea");
        beverage.add("black_tea");
        List<String> response = machineService.prepareBeverages(beverage);
        // Asserting we are getting 3 strings in response...
        assertEquals(3, response.size());
        // Asserting the response strings...
        assertEquals(response.get(0), "hot_tea is prepared");
        assertEquals(response.get(1), "hot_tea is prepared");
        assertEquals(response.get(2), "black_tea cannot be prepared because hot_water is not sufficient");

        beverage.clear();
        beverage.add("green_tea");
        response = machineService.prepareBeverages(beverage);
        // Asserting the not available case...
        assertEquals(response.get(0), "green_tea cannot be prepared because green_mixture is not available");
        beverage.clear();
        beverage.add("random_drink");
        response = machineService.prepareBeverages(beverage);
        // Asserting the not available case...
        assertEquals(response.get(0), "Machine do not serve random_drink");
    }

    @Test
    void prepareBeveragesTestWhenAskingForMoreThanPossibleDrinksInParrallel() {
        InputParser inputParser = new InputParser();
        MachineService machineService = new MachineService(inputParser.getMachine());
        List<String> beverage = new ArrayList<String>();
        beverage.add("hot_tea");
        beverage.add("hot_tea");
        beverage.add("black_tea");
        beverage.add("black_tea");
        List<String> response = machineService.prepareBeverages(beverage);
        assertEquals(1, response.size());
        assertEquals(response.get(0), "Cannot serve more than 3 beverages");
    }

    @Test
    void updateAvailableIngredientsTestWithEmptyIngredients() {
        InputParser inputParser = new InputParser();
        Machine machine = inputParser.getMachine();
        MachineService machineService = new MachineService(machine);
        ArrayList<Ingredient> ingredients_available = machine.getIngredients_available();
        ArrayList<Ingredient> response = machineService.updateAvailableIngredients(new ArrayList<Ingredient>(), true);
        assertEquals(true, ingredients_available.equals(response));
    }

    @Test
    void updateAvailableIngredientsTest() {
        InputParser inputParser = new InputParser();
        Machine machine = inputParser.getMachine();
        MachineService machineService = new MachineService(machine);
        ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
        ingredients.add(new Ingredient("hot_water", 200));
        ingredients.add(new Ingredient("green_mixture", 400));
        ArrayList<Ingredient> response = machineService.updateAvailableIngredients(ingredients, true);
        int updated_hot_water = 0;
        int updated_green_mixture = 0;
        for (Ingredient ing : response) {
            if (ing.getName().equals("hot_water")) {
                updated_hot_water = ing.getQuantity();
            } else if (ing.getName().equals("green_mixture")) {
                updated_green_mixture = ing.getQuantity();
            }
        }
        assertEquals(700, updated_hot_water);
        assertEquals(400, updated_green_mixture);
    }

    @Test
    void prepareBeveragesAfterUpdateAvailableIngredientsIntegTest() {
        InputParser inputParser = new InputParser();
        Machine machine = inputParser.getMachine();
        MachineService machineService = new MachineService(machine);
        List<String> beverage = new ArrayList<String>();
        beverage.add("green_tea");
        assertEquals(machineService.prepareBeverages(beverage).get(0),
                "green_tea cannot be prepared because green_mixture is not available");
        ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
        ingredients.add(new Ingredient("green_mixture", 400));
        machineService.updateAvailableIngredients(ingredients, true);
        assertEquals(machineService.prepareBeverages(beverage).get(0), "green_tea is prepared");
    }
}
