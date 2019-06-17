package e.usf.nutritionapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class GoalsFragment extends Fragment {

    TextView startingWeight;
    TextView goalWeight;
    TextView change;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_goals,container,false);

        HomeActivity activity = (HomeActivity) getActivity();

        startingWeight = view.findViewById(R.id.starting_weight);
        goalWeight = view.findViewById(R.id.goal_weight);
        change = view.findViewById(R.id.change);

        startingWeight.setText(String.valueOf(activity.getWeight()));
        goalWeight.setText(String.valueOf(activity.getGoalWeight()));
        change.setText(String.valueOf(activity.getGoalWeight()-activity.getWeight()));

        return view;
    }
}
