package e.usf.nutritionapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddBreakfastFoodItemActivity extends AppCompatActivity {

    final String apiKey = "GAWUu80GqmdItHRAt9BFJb2rqxY4hkQe1LT4m7ZW";
    final String TAG = "AddBreakfastFoodItem";

    private ArrayList<String> foodNameList;
    private ArrayList<String> foodNumberList;
    private SearchView searchView;
    private ArrayAdapter adapter;
    private ListView listFood;
    private ListView listFoodBreakfast;
    private double caloriesEaten;
    private ArrayAdapter breakfastAdapter;
    private ArrayList<FoodDetails> breakfastFoodList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_breakfast_food_item);

        listFoodBreakfast = findViewById(R.id.list_food_breakfast);
        breakfastFoodList = new ArrayList<>();
        breakfastAdapter = new FoodListAdapter(AddBreakfastFoodItemActivity.this, R.layout.food_adapter_view, breakfastFoodList);
        listFoodBreakfast.setAdapter(breakfastAdapter);
        listFoodBreakfast.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //
                // TODO
                //
            }
        });
        listFood = findViewById(R.id.list_food);
        foodNameList = new ArrayList<>();
        foodNumberList = new ArrayList<>();
        adapter = new ArrayAdapter(AddBreakfastFoodItemActivity.this, android.R.layout.simple_list_item_1, foodNameList);
        listFood.setAdapter(adapter);
        listFood.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                Log.d(TAG,"Item Selected: \nName: " +  foodNameList.get(position) + "\nNumber: " + foodNumberList.get(position));

                Retrofit retrofit2 = new Retrofit.Builder()
                        .baseUrl(Api.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();
                Api api2 = retrofit2.create(Api.class);
                Call<NutrientReportExample> call2 = api2.getNutrientDetails("json", apiKey, 203, 205, 204, 208, foodNumberList.get(position));
                call2.enqueue(new Callback<NutrientReportExample>() {
                    @Override
                    public void onResponse(Call<NutrientReportExample> call, Response<NutrientReportExample> response) {
                        if (!response.isSuccessful()){
                            Log.d("Code: ", ""+response.code());
                            return;
                        }
                        String protein = null;
                        String carbs = null;
                        String fat = null;
                        String calories = null;
                        String name = foodNameList.get(position);
                        NutrientReportExample ex = response.body();

                        List<FoodItemNutrients> foodItemNutrientsList = ex.getReport().getFoods();
                        List<Nutrient> nutrientList = foodItemNutrientsList.get(0).getNutrients();

                        for(Nutrient n:nutrientList){
                            switch(n.getNutrient_id()){
                                case "203":
                                    if(n.getValue().contains("--")){
                                        protein = "0.0";
                                        break;
                                    }
                                    protein = n.getValue();

                                case "204":
                                    if(n.getValue().contains("--")){
                                        fat = "0.0";
                                        break;
                                    }
                                    fat = n.getValue();

                                case "205":
                                    if(n.getValue().contains("--")){
                                        carbs = "0.0";
                                        break;
                                    }
                                    carbs = n.getValue();
                                    break;

                                case "208":
                                    if(n.getValue().contains("--")){
                                        calories = "0.0";
                                        break;
                                    }
                                    calories = n.getValue();
                            }
                        }

                        Intent intent = new Intent(AddBreakfastFoodItemActivity.this, AddFood.class);
                        intent.putExtra("protein", protein);
                        intent.putExtra("fat", fat);
                        intent.putExtra("carbs", carbs);
                        intent.putExtra("calories", calories);
                        intent.putExtra("name", name);
                        startActivityForResult(intent, 1);
                    }

                    @Override
                    public void onFailure(Call<NutrientReportExample> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.test_menu, menu);
        MenuItem item = menu.findItem(R.id.search_food);
        searchView = (SearchView)item.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                query(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                if(newText.length()==0 && !(foodNameList.isEmpty())){
                    resetMenu();
                    return false;
                }else if(newText.length()==0 && foodNameList.isEmpty()){
                    return false;
                }else {
                    Log.d(TAG, ".onQueryTextChange");
                    query(newText);
                    return false;
                }
            }
        });

        return super.onCreateOptionsMenu(menu);
    }

    private void query(String query) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Api api = retrofit.create(Api.class);
        Log.d(TAG, ".onQueryTextSubmit");
        Log.d(TAG, "Query submitted:" + query);
        Call<FoodItemContainerContainer> call = api.getFoodItems("json", query, "r", 5, 0, "Standard Reference", apiKey);
        call.enqueue(new Callback<FoodItemContainerContainer>() {
            @Override
            public void onResponse(Call<FoodItemContainerContainer> call, Response<FoodItemContainerContainer> response) {
                if (!response.isSuccessful()) {
                    Log.d("Code: ", "" + response.code());
                    return;
                }
                foodNameList.clear();
                foodNumberList.clear();
                FoodItemContainerContainer foodItemContainerContainer = response.body();
                if (foodItemContainerContainer.getList() == (null)) {
                    Toast.makeText(getApplicationContext(), "Your search found no results", Toast.LENGTH_SHORT).show();
                    foodNumberList.clear();
                    foodNameList.clear();
                    adapter.notifyDataSetChanged();
                    return;
                }
                List<FoodItem> foodItemList = foodItemContainerContainer.getList().getItem();
                for (FoodItem f : foodItemList) {
                    foodNumberList.add(f.getOffset(), f.getNdbno());
                    foodNameList.add(f.getOffset(), f.getName());
                }
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onFailure(Call<FoodItemContainerContainer> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1){
            if(resultCode == RESULT_OK){

                double calories = data.getDoubleExtra("calories", 0.0);
                double carbohydrates = data.getDoubleExtra("carbohydrates", 0);
                double fat = data.getDoubleExtra("fat", 0);
                double protein = data.getDoubleExtra("protein", 0);
                String name = data.getStringExtra("name");
                int servings = data.getIntExtra("calories", 0);
                FoodDetails foodDetails = new FoodDetails(calories,protein,carbohydrates, fat, name, servings);
                breakfastFoodList.add(foodDetails);
                breakfastAdapter.notifyDataSetChanged();

                searchView.setQuery("",false);
                resetMenu();
            }
            if(resultCode == RESULT_CANCELED){
                resetMenu();
            }
        }
    }

    private void resetMenu() {
        foodNumberList.clear();
        foodNameList.clear();
        searchView.clearFocus();
        adapter.notifyDataSetChanged();
    }

}


