package e.usf.nutritionapp;

import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;


public class HomeFragment extends Fragment implements ExerciseFragment.OnInputSelected {

    private static final String TAG = "HomeFragment";
    private Button addMealButton;
    private Button addExerciseButton;
    private TextView goalText;
    public TextView exerciseText;
    private DatabaseReference reff;

    @Override
    public void sendInput(String input) {
        Log.d(TAG, "sendInput: found incoming input: " + input);
        double cals = Double.valueOf(exerciseText.getText().toString());
        cals += Double.valueOf(input);
        exerciseText.setText(String.valueOf(cals));
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_home,container,false);

        HomeActivity activity = (HomeActivity) getActivity();
        double goalCalories = activity.getGoalCalories();
        goalText = view.findViewById(R.id.goal_calories);
        exerciseText = view.findViewById(R.id.exercise_calories);

        goalText.setText(String.valueOf(goalCalories));

        Log.d(TAG,"onCreateView: started");

        addMealButton = view.findViewById(R.id.add_meal_button);
        addExerciseButton = view.findViewById(R.id.add_excercise_button);

        addMealButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Fragment selectedFragment = new FoodDiaryFragment();
                ((HomeActivity)getActivity()).setNavListener(selectedFragment);
            }
        });

        addExerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ExerciseFragment dialogFragment = new ExerciseFragment();
                dialogFragment.setTargetFragment(HomeFragment.this, 1);
                dialogFragment.show(getFragmentManager(), "ExerciseFragment");
            }

        });

        return view;
    }

    // bad way to do this but it will work for now
    public void setExerciseText(String text) {

        Log.d(TAG, "Setting exercise calories");
        double currentExercise = Double.valueOf(exerciseText.getText().toString());
        currentExercise += Double.valueOf(text);
        exerciseText.setText(String.valueOf(currentExercise));
    }


}
