package e.usf.nutritionapp;

import java.util.List;

public class FoodItemNutrients {
    private String ndbno;
    private String name;
    private double weight;
    private String measure;
    private List<Nutrient> nutrients;

    public FoodItemNutrients(String ndbno, String name, double weight, String measure, List<Nutrient> nutrients) {
        this.ndbno = ndbno;
        this.name = name;
        this.weight = weight;
        this.measure = measure;
        this.nutrients = nutrients;
    }

    public String getNdbno() {
        return ndbno;
    }

    public String getName() {
        return name;
    }

    public double getWeight() {
        return weight;
    }

    public String getMeasure() {
        return measure;
    }

    public List<Nutrient> getNutrients() {
        return nutrients;
    }
}
