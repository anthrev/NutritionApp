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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MoreFragment extends Fragment {

    private static final String TAG = "MoreFragment";

    String goals;
    float weight;
    float goalWeight;
    double weeklyGoals;

    EditText weightText, goalWeightText;
    RadioButton loseWeight, gainWeight, oneGoal, halfGoal;
    Button saveButton;

    FirebaseDatabase database;
    FirebaseUser user;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_more,container,false);

        weightText = view.findViewById(R.id.set_weight_text);
        goalWeightText = view.findViewById(R.id.set_goal_weight_text);

        loseWeight = view.findViewById(R.id.set_radio_lose_weight);
        gainWeight = view.findViewById(R.id.set_radio_gain_weight);
        oneGoal = view.findViewById(R.id.set_radio_goal_weight_change_more);
        halfGoal = view.findViewById(R.id.set_radio_goal_weight_change);

        saveButton =view.findViewById(R.id.save_button);

        user = FirebaseAuth.getInstance().getCurrentUser();


        database = FirebaseDatabase.getInstance();
        DatabaseReference weightRef = database.getReference(user.getUid() + "/weight");
        DatabaseReference goalWeightRef = database.getReference(user.getUid() + "/goalWeight");
        DatabaseReference goalsRef = database.getReference(user.getUid() + "/goals");
        DatabaseReference weekGoalsRef = database.getReference(user.getUid() + "/weeklyGoals");

        weightRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int weight = dataSnapshot.getValue(int.class);
                Log.d(TAG, String.valueOf(weight));
                weightText.setText(String.valueOf(weight));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        goalWeightRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int goalWeight = dataSnapshot.getValue(int.class);
                Log.d(TAG, String.valueOf(goalWeight));
                goalWeightText.setText(String.valueOf(goalWeight));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        goalsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String goalString = dataSnapshot.getValue(String.class);
                Log.d(TAG, goalString);
                goals = goalString;
                if(goalString.equals("Lose Weight"))
                {
                    loseWeight.setChecked(true);
                }
                else if(goalString.equals("Gain Weight"))
                {
                    gainWeight.setChecked(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        weekGoalsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                double weekGoal = dataSnapshot.getValue(double.class);
                Log.d(TAG, String.valueOf(weekGoal));
                weeklyGoals = weekGoal;
                if(weekGoal == .5)
                {
                    halfGoal.setChecked(true);
                }
                else if(weekGoal == 1)
                {
                    oneGoal.setChecked(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        loseWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goals = "Lose Weight";
                loseWeight.setChecked(true);
                gainWeight.setChecked(false);
            }
        });

        gainWeight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goals = "Gain Weight";
                gainWeight.setChecked(true);
                loseWeight.setChecked(false);
            }
        });

        halfGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weeklyGoals = .5;
                halfGoal.setChecked(true);
                oneGoal.setChecked(false);
            }
        });

        oneGoal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weeklyGoals = 1.0;
                oneGoal.setChecked(true);
                halfGoal.setChecked(false);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setUserInfo();
            }
        });



        return view;
    }





    public void setUserInfo(){

        weight = Float.valueOf(String.valueOf(weightText.getText()));
        goalWeight = Float.valueOf(String.valueOf(goalWeightText.getText()));



        DatabaseReference databaseReference = database.getInstance().getReference();
        databaseReference.child(user.getUid()).child("weight").setValue(weight);
        databaseReference.child(user.getUid()).child("goalWeight").setValue(goalWeight);
        databaseReference.child(user.getUid()).child("goals").setValue(goals);
        databaseReference.child(user.getUid()).child("weeklyGoals").setValue(weeklyGoals);
        Toast.makeText(getActivity(), "Information Saved...", Toast.LENGTH_SHORT).show();
    }
}
