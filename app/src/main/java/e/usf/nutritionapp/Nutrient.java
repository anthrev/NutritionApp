package e.usf.nutritionapp;

public class Nutrient {

    private String nutrient_id;

    private String nutrient;

    private String unit;

    private String value;

    private String gm;


    public String getNutrient_id() {
        return nutrient_id;
    }

    public void setNutrient_id(String nutrientId) {
        this.nutrient_id = nutrientId;
    }


    public String getNutrient() {
        return nutrient;
    }

    public void setNutrient(String nutrient) {
        this.nutrient = nutrient;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getGm() {
        return gm;
    }

    public void setGm(String gm) {
        this.gm = gm;
    }

}