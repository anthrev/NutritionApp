package e.usf.nutritionapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class AddFood extends AppCompatActivity {

    private int servingAmount;
    private TextView servings;
    private double caloriesValue;
    private double carbsValue;
    private double fatValue;
    private double proteinValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_food);

        Intent intent = getIntent();
        final String protein = intent.getExtras().getString("protein");
        final String fat = intent.getExtras().getString("fat");
        final String carbs = intent.getExtras().getString("carbs");
        final String calories = intent.getExtras().getString("calories");
        final String name = intent.getExtras().getString("name");
        proteinValue = Double.valueOf(protein);
        fatValue = Double.valueOf(fat);
        carbsValue = Double.valueOf(carbs);
        caloriesValue = Double.valueOf(calories);


        final TextView foodCalories = findViewById(R.id.food_calories_view);
        final TextView foodCarbs = findViewById(R.id.food_carbs_view);
        final TextView foodProtein = findViewById(R.id.food_protein_view);
        final TextView foodFat = findViewById(R.id.food_fat_view);
        final TextView foodName = findViewById(R.id.food_name_view);
        servings = findViewById(R.id.serving_text_box);
        TextView submitFood = findViewById(R.id.submit_food_button);

        submitFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                double returnCalories = servingAmount * caloriesValue;
                double returnCarbs = servingAmount * carbsValue;
                double returnFats = servingAmount * fatValue;
                double returnProtein = servingAmount * proteinValue;
                Intent resultIntent = new Intent();
                resultIntent.putExtra("calories", returnCalories);
                resultIntent.putExtra("carbohydrates", returnCarbs);
                resultIntent.putExtra("fat", returnFats);
                resultIntent.putExtra("protein", returnProtein);
                resultIntent.putExtra("name", name);
                resultIntent.putExtra("servings", servingAmount);


                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });


        foodProtein.setText(protein+"g");
        foodCarbs.setText(carbs+"g");
        foodCalories.setText(calories+"kcal");
        foodFat.setText(fat+"g");
        foodName.setText(name);



        servings.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(servings.getText().toString().length() == 0){
                    servings.setText("0");
                    servingAmount = 0;
                }
                String newServings = servings.getText().toString();
                servingAmount = Integer.parseInt(newServings);



                foodProtein.setText(String.valueOf(proteinValue * servingAmount) + "g");
                foodCarbs.setText(String.valueOf(carbsValue * servingAmount) + "g");
                foodCalories.setText(String.valueOf(caloriesValue * servingAmount) + "kcal");
                foodFat.setText(String.valueOf(fatValue * servingAmount) + "g");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
}
