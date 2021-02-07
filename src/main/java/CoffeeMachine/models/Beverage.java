package CoffeeMachine.models;

import java.util.ArrayList;

/**
 * Beverage DAO
 */
public class Beverage {
    /**
     * Name of the beverage
     */
    String name;

    /**
     * List of all the ingredients reuired for the beverage
     */
    ArrayList<Ingredient> ingredients;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }
}
