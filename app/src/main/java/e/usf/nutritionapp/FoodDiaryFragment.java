package e.usf.nutritionapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FoodDiaryFragment extends Fragment {

    TextView addBreakfastItem;
    TextView addLunchItem;
    TextView addDinnerItem;
    TextView addSnackItem;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_diary,container,false);

        addBreakfastItem = view.findViewById(R.id.add_food_breakfast);
        addLunchItem = view.findViewById(R.id.add_food_lunch);
        addDinnerItem = view.findViewById(R.id.add_food_dinner);
        addSnackItem = view.findViewById(R.id.add_food_snack);

        addBreakfastItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        addLunchItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        addDinnerItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        addSnackItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        return view;
    }
}
