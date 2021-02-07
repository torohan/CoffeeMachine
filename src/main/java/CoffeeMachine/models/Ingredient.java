package CoffeeMachine.models;

/**
 * Ingredient DAO
 */
public class Ingredient {
    /**
     * Name of the ingredient
     */
    String name;

    /**
     * Quantity of the ingredient
     */
    int quantity;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Ingredient(String name, int quantity) {
        this.name = name;
        this.quantity = quantity;
    }

}
