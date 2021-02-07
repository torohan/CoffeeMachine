package CoffeeMachine.models;

import java.util.ArrayList;

/**
 * Machine DAO
 */
public class Machine {
    /**
     * Number of outlets from the machine
     */
    int number_of_outlets;

    /**
     * List of beverage which can be served via machine
     */
    ArrayList<Beverage> beverages_available;

    /**
     * List of the ingredient available in the machine
     */
    ArrayList<Ingredient> ingredients_available;

    public int getNumber_of_outlets() {
        return number_of_outlets;
    }

    public void setNumber_of_outlets(int number_of_outlets) {
        this.number_of_outlets = number_of_outlets;
    }

    public ArrayList<Beverage> getBeverages_available() {
        return beverages_available;
    }

    public void setBeverages_available(ArrayList<Beverage> beverages_available) {
        this.beverages_available = beverages_available;
    }

    public ArrayList<Ingredient> getIngredients_available() {
        return ingredients_available;
    }

    public void setIngredients_available(ArrayList<Ingredient> ingredients_available) {
        this.ingredients_available = ingredients_available;
    }

    public Machine() {

    }

    public Machine(int number_of_outlets, ArrayList<Beverage> beverages_available,
            ArrayList<Ingredient> ingredients_available) {
        this.number_of_outlets = number_of_outlets;
        this.beverages_available = beverages_available;
        this.ingredients_available = ingredients_available;
    }

}
