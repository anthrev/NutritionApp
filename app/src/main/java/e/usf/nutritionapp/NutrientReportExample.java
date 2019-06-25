package e.usf.nutritionapp;

import android.os.Parcel;
import android.os.Parcelable;

public class NutrientReportExample {
    private NutrientReport report;

    public NutrientReportExample(NutrientReport report) {
        this.report = report;
    }


    public NutrientReport getReport() {
        return report;
    }
}