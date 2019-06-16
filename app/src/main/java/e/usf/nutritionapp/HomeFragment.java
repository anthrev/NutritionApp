package e.usf.nutritionapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment {

    private static final String TAG = "HomeFragment";
    private Button addMealButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home,container,false);
        addMealButton = view.findViewById(R.id.add_meal_button);

        Log.d(TAG,"onCreateView: started");

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
