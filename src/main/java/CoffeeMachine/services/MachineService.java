package CoffeeMachine.services;

import java.util.ArrayList;
import java.util.List;

import CoffeeMachine.models.Beverage;
import CoffeeMachine.models.Ingredient;
import CoffeeMachine.models.Machine;

/**
 * Machine Service class taking care of all the functinalities of the machine.
 */
public class MachineService {
    Machine machine;

    private static String NOT_FOUND = "NOT_FOUND";
    private static String IN_LESS_QUANTITY = "IN_LESS_QUANTITY";
    private static String IN_ADEQUATE_QUANTITY = "IN_ADEQUATE_QUANTITY";

    public MachineService(Machine machine) {
        this.machine = machine;
    }

    /**
     * To check whether the ingredient asked is in adequate quantity or not.
     *
     * @param ingredient the ingredient to confirm.
     * @return (String) NOT_FOUND, IN_LESS_QUANTITY, IN_ADEQUATE_QUANTITY
     */
    private String isAdequateSuppyAvailable(Ingredient ingredient) {
        ArrayList<Ingredient> ingredients_available = machine.getIngredients_available();
        Boolean ingredientFound = false;
        String ingredient_name = ingredient.getName();
        for (int i = 0; i < ingredients_available.size(); i++) {
            if (ingredients_available.get(i).getName().equalsIgnoreCase(ingredient_name)) {
                ingredientFound = true;
                if (Math.subtractExact(ingredients_available.get(i).getQuantity(), ingredient.getQuantity()) < 0) {
                    return IN_LESS_QUANTITY;
                }
                break;
            }
        }
        if (!ingredientFound) {
            return NOT_FOUND;
        }
        return IN_ADEQUATE_QUANTITY;
    }

    /**
     * To change the value of ingredients in the machine.
     *
     * @param ingredients: the arraylist of the ingredients, which needed to be
     *                     updated with their quantity
     * @param to_add:      whether to add the quantity or subtract, from the already
     *                     available one.
     * @return Updated ingredient list
     */
    public ArrayList<Ingredient> updateAvailableIngredients(ArrayList<Ingredient> ingredients, Boolean to_add) {
        ArrayList<Ingredient> ingredients_available = machine.getIngredients_available();
        int left_quantity;
        Boolean ingredientFound = false;
        for (Ingredient ingredient : ingredients) {
            String ingredient_name = ingredient.getName();
            ingredientFound = false;
            for (int i = 0; i < ingredients_available.size(); i++) {
                if (ingredients_available.get(i).getName().equals(ingredient_name)) {
                    if (to_add) {
                        left_quantity = Math.addExact(ingredients_available.get(i).getQuantity(),
                                ingredient.getQuantity());
                    } else {
                        left_quantity = Math.subtractExact(ingredients_available.get(i).getQuantity(),
                                ingredient.getQuantity());
                    }
                    ingredients_available.get(i).setQuantity(left_quantity);
                    ingredientFound = true;
                    break;
                }
            }

            if (!ingredientFound && to_add) {

                ingredients_available.add(ingredient);
            }
        }
        machine.setIngredients_available(ingredients_available);
        return machine.getIngredients_available();
    }

    /**
     * Function to return all the ingredients required for making a beverage
     *
     * @param beverage: The name of the beverage to get ingredient for.
     * @return
     */
    private ArrayList<Ingredient> getIngredientsRequiredForBeverage(String beverage) {
        ArrayList<Ingredient> ingredients_required = new ArrayList<Ingredient>();
        ArrayList<Beverage> beverages_available = machine.getBeverages_available();
        for (Beverage beverageObj : beverages_available) {
            if (beverageObj.getName().equalsIgnoreCase(beverage)) {
                ingredients_required = beverageObj.getIngredients();
                break;
            }
        }
        return ingredients_required;
    }

    /**
     * The main function which accept the list of beverages to be prepared in one
     * shot
     *
     * @param beverages
     * @return: the list of response string
     */
    public List<String> prepareBeverages(List<String> beverages) {
        List<String> response_list = new ArrayList<String>();
        if (beverages.size() > machine.getNumber_of_outlets()) {
            response_list.add("Cannot serve more than " + machine.getNumber_of_outlets() + " beverages");
            return response_list;
        }

        ArrayList<Ingredient> ingredients_required;
        for (String beverage : beverages) {
            ingredients_required = getIngredientsRequiredForBeverage(beverage);
            if (ingredients_required.size() > 0) {
                Boolean is_possible_to_prepare = true;
                for (Ingredient ingredient : ingredients_required) {
                    String response = isAdequateSuppyAvailable(ingredient);
                    if (response.equals(IN_LESS_QUANTITY)) {
                        response_list.add(beverage + " cannot be prepared because " + ingredient.getName()
                                + " is not sufficient");
                        is_possible_to_prepare = false;
                        break;
                    } else if (response.equals(NOT_FOUND)) {
                        response_list.add(
                                beverage + " cannot be prepared because " + ingredient.getName() + " is not available");
                        is_possible_to_prepare = false;
                        break;
                    }
                }
                if (is_possible_to_prepare) {
                    response_list.add(beverage + " is prepared");
                    updateAvailableIngredients(ingredients_required, false);
                } else {
                    break;
                }
            } else {
                response_list.add("Machine do not serve " + beverage);
            }
        }
        return response_list;
    }
}
