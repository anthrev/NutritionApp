package e.usf.nutritionapp;

import java.util.List;

public class NutrientReport {

    private String sr;
    private String groups;
    private String subset;
    private int end;
    private int start;
    private int total;
    private List<FoodItemNutrients> foods;

    public NutrientReport(String sr, String groups, String subset, int end, int start, int total, List<FoodItemNutrients> foods) {
        this.sr = sr;
        this.groups = groups;
        this.subset = subset;
        this.end = end;
        this.start = start;
        this.total = total;
        this.foods = foods;
    }

    public String getSr() {
        return sr;
    }

    public String getGroups() {
        return groups;
    }

    public String getSubset() {
        return subset;
    }

    public int getEnd() {
        return end;
    }

    public int getStart() {
        return start;
    }

    public int getTotal() {
        return total;
    }

    public List<FoodItemNutrients> getFoods() {
        return foods;
    }
}
