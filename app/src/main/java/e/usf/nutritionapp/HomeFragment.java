package e.usf.nutritionapp;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;


public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    private Button addMealButton;
    private TextView goalText;
    private DatabaseReference reff;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home,container,false);

        HomeActivity activity = (HomeActivity) getActivity();
        double goalCalories = activity.getGoalCalories();
        goalText = view.findViewById(R.id.goal_calories);
        goalText.setText(String.valueOf(goalCalories));

        Log.d(TAG,"onCreateView: started");

        addMealButton = view.findViewById(R.id.add_meal_button);

        addMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedFragment = new FoodDiaryFragment();
                ((HomeActivity)getActivity()).setNavListener(selectedFragment);
            }
        });

        return view;
    }

}
