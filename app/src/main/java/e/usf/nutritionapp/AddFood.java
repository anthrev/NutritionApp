package e.usf.nutritionapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.TextView;

import java.util.List;

public class AddFood extends AppCompatActivity {

    private int servingAmount = 1;

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

        final TextView foodCalories = findViewById(R.id.food_calories_view);
        final TextView foodCarbs = findViewById(R.id.food_carbs_view);
        final TextView foodProtein = findViewById(R.id.food_protein_view);
        final TextView foodFat = findViewById(R.id.food_fat_view);
        final TextView foodName = findViewById(R.id.food_name_view);
        final TextView servings = findViewById(R.id.serving_text_box);

        foodProtein.setText(protein);
        foodCarbs.setText(carbs);
        foodCalories.setText(calories);
        foodFat.setText(fat);
        foodName.setText(name);



        servings.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String newServings = servings.getText().toString();
                servingAmount = Integer.parseInt(newServings);

                foodProtein.setText(Integer.parseInt(protein) * servingAmount);
                foodCarbs.setText(Integer.parseInt(carbs) * servingAmount);
                foodCalories.setText(Integer.parseInt(calories) * servingAmount);
                foodFat.setText(Integer.parseInt(fat) * servingAmount);
            }
        });

    }
}
