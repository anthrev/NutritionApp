package e.usf.nutritionapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import static android.app.Activity.RESULT_CANCELED;
import static android.app.Activity.RESULT_OK;

public class FoodDiaryFragment extends Fragment {

    TextView addBreakfastItem;
    TextView addLunchItem;
    TextView addDinnerItem;
    TextView addSnackItem;
    TextView goalCalories;
    TextView caloriesEaten;
    TextView remainingCalories;

    private FoodDetails foodDetails;
    private String calories = "0";

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food_diary,container,false);

        HomeActivity activity = (HomeActivity) getActivity();
        String goalCals = String.valueOf(activity.getGoalCalories());

        goalCalories = view.findViewById(R.id.goal_calories);
        goalCalories.setText(goalCals);

        remainingCalories = view.findViewById(R.id.remaining_cals);
        remainingCalories.setText(goalCals);

        caloriesEaten = view.findViewById(R.id.calories_eaten);
        caloriesEaten.setText(calories);

        addBreakfastItem = view.findViewById(R.id.add_food_breakfast);
        addLunchItem = view.findViewById(R.id.add_food_lunch);
        addDinnerItem = view.findViewById(R.id.add_food_dinner);
        addSnackItem = view.findViewById(R.id.add_food_snack);

        addBreakfastItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(getContext(), AddBreakfastFoodItemActivity.class);
                startActivityForResult(intent, 1);
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

    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
            if (resultCode == RESULT_OK) {
                double calories = data.getDoubleExtra("calories", 0);
                caloriesEaten.setText(String.valueOf(calories));
                String stringGCals = goalCalories.getText().toString();
                String stringECals = caloriesEaten.getText().toString();
                double doubleGCals = Double.parseDouble(stringGCals);
                double doubleECals = Double.parseDouble(stringECals);
                double doubleRCals = doubleGCals - doubleECals;

                remainingCalories.setText(String.valueOf(doubleRCals));
            }
            if (resultCode == RESULT_CANCELED) {

            }
        }
    }
}
