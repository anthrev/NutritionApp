package e.usf.nutritionapp;

import android.os.Parcel;
import android.os.Parcelable;

public class NutrientReportExample implements Parcelable {
    private NutrientReport report;

    public NutrientReportExample(NutrientReport report) {
        this.report = report;
    }

    protected NutrientReportExample(Parcel in) {
    }

    public static final Creator<NutrientReportExample> CREATOR = new Creator<NutrientReportExample>() {
        @Override
        public NutrientReportExample createFromParcel(Parcel in) {
            return new NutrientReportExample(in);
        }

        @Override
        public NutrientReportExample[] newArray(int size) {
            return new NutrientReportExample[size];
        }
    };

    public NutrientReport getReport() {
        return report;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
