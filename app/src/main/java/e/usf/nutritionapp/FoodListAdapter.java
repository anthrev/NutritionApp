package e.usf.nutritionapp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import static android.view.LayoutInflater.from;

public class FoodListAdapter extends ArrayAdapter<FoodDetails> {

    private Context mContext;
    int mResource;

    public FoodListAdapter(Context context, int resource, ArrayList<FoodDetails> objects){
        super(context, resource, objects);
        mContext = context;
        mResource = resource;

    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = from((mContext));
        String name = getItem(position).getName();
        double carbohydrates = getItem(position).getCarbohydrates();
        double protein = getItem(position).getProtein();
        double calories = getItem(position).getCalories();
        double fat = getItem(position).getFat();
        int servings = getItem(position).getServings();


        FoodDetails foodDetails = new FoodDetails(calories, protein, carbohydrates, fat, name, servings);


        LayoutInflater inflator = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent, false);

        TextView foodName = convertView.findViewById(R.id.food_name_text_view);
        TextView calorieCount = convertView.findViewById(R.id.food_details1);
        TextView proteinCount = convertView.findViewById(R.id.food_details2);

        foodName.setText(name);
        calorieCount.setText(String.valueOf(calories));
        proteinCount.setText(String.valueOf(protein));

        return convertView;
    }
}
