package e.usf.nutritionapp;

import android.os.Parcel;
import android.os.Parcelable;

public class Nutrient implements Parcelable {
    private String nutrient_id;
    private String nutrient;
    private String unit;
    private String value;
    private double gm;

    public Nutrient(String nutrient_id, String nutrient, String unit, String value, double gm) {
        this.nutrient_id = nutrient_id;
        this.nutrient = nutrient;
        this.unit = unit;
        this.value = value;
        this.gm = gm;
    }

    protected Nutrient(Parcel in) {
        nutrient_id = in.readString();
        nutrient = in.readString();
        unit = in.readString();
        value = in.readString();
        gm = in.readDouble();
    }

    public static final Creator<Nutrient> CREATOR = new Creator<Nutrient>() {
        @Override
        public Nutrient createFromParcel(Parcel in) {
            return new Nutrient(in);
        }

        @Override
        public Nutrient[] newArray(int size) {
            return new Nutrient[size];
        }
    };

    public String getNutrient_id() {
        return nutrient_id;
    }

    public String getNutrient() {
        return nutrient;
    }

    public String getUnit() {
        return unit;
    }

    public String getValue() {
        return value;
    }

    public double getGm() {
        return gm;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(nutrient_id);
        dest.writeString(nutrient);
        dest.writeString(unit);
        dest.writeString(value);
        dest.writeDouble(gm);
    }
}
