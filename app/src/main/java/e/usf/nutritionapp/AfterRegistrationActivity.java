package e.usf.nutritionapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

public class AfterRegistrationActivity extends AppCompatActivity {

    String TAG = "First Time Registration: ";
    String goals,gender;
    float weight = 0;
    float goalWeight = 0;
    double weeklyGoals = 0;

    EditText weightText, goalWeightText;
    Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_after_registration);

        weightText = findViewById(R.id.weight_text);
        goalWeightText = findViewById(R.id.goal_weight_text);
        submitButton = findViewById(R.id.submit_button);

        weightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                weight = Float.valueOf(weightText.getText().toString());
            }
        });

        goalWeightText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goalWeight = Float.valueOf(weightText.getText().toString());
            }
        });

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(goals == null || gender == null || weight == 0 || goalWeight == 0 || weeklyGoals == 0){
                    Context context = getApplicationContext();
                    String text = "You are missing some fields";
                    int duration = Toast.LENGTH_SHORT;

                    Toast toast = Toast.makeText(context, text, duration);
                    toast.show();
                } else {
                    Member newMember = new Member();
                    newMember.setGender(gender);
                    newMember.setGoals(goals);
                    newMember.setGoalWeight(goalWeight);
                    newMember.setWeeklyGoals(weeklyGoals);
                    newMember.setWeight(weight);

                    String memberInfo = "Goals: " + newMember.getGoals()
                            + "\nGender: " + newMember.getGender()
                            + "\nWeight: " + newMember.getWeight()
                            + "\nGoal Weight: " + newMember.getGoalWeight()
                            + "\nWeekly Goals: " + newMember.getWeeklyGoals();
                    Log.d(TAG, "Member log successful");
                    Log.d(TAG, memberInfo);

                    Intent intent = new Intent(v.getContext(), HomeActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    public void onRadioButtonClickedGoals(View view){
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()){
            case R.id.radio_lose_weight:
                if(checked){
                    goals = "Lose Weight";
                    break;
                }
            case R.id.radio_gain_weight:
                if(checked) {
                    goals = "Gain Weight";
                    break;
                }
        }
    }

    public void onRadioButtonClickedGender(View view){
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()){
            case R.id.radio_male:
                if(checked){
                    gender = "Male";
                    break;
                }
            case R.id.radio_female:
                if(checked) {
                    gender = "Female";
                    break;
                }
        }
    }

    public void onRadioButtonClickedGoalsPerWeek(View view){
        boolean checked = ((RadioButton) view).isChecked();

        switch (view.getId()){
            case R.id.radio_goal_weight_change:
                if(checked){
                    weeklyGoals = .5;
                    break;
                }
            case R.id.radio_goal_weight_change_more:
                if(checked) {
                    weeklyGoals = 1.0;
                    break;
                }
        }
    }
}

