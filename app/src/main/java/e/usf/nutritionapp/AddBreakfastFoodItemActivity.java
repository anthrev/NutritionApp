package e.usf.nutritionapp;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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

import com.google.gson.Gson;

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
    private ArrayAdapter adapter;
    private ListView listFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_breakfast_food_item);

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
                Call<NutrientReportExample> call2 = api2.getNutrientDetails("json", apiKey, 203, 205, 204, 208, Integer.parseInt(foodNumberList.get(position)));
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
                                    protein = n.getValue() + "g";

                                case "204":
                                    fat = n.getValue() + "g";

                                case "205":
                                    carbs = n.getValue() + "g";
                                    break;

                                case "208":
                                    calories = n.getValue() +"kcal";
                            }
                        }

                        Intent intent = new Intent(AddBreakfastFoodItemActivity.this, AddFood.class);
                        intent.putExtra("protein", protein);
                        intent.putExtra("fat", fat);
                        intent.putExtra("carbs", carbs);
                        intent.putExtra("calories", calories);
                        intent.putExtra("name", name);
                        startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<NutrientReportExample> call, Throwable t) {

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
        SearchView searchView = (SearchView)item.getActionView();


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Api.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                Api api = retrofit.create(Api.class);
                Log.d(TAG, ".onQueryTextSubmit");
                Log.d(TAG, "Query submitted:" + query);
                Call<FoodItemContainerContainer> call = api.getFoodItems("json", query, "r", 5, 0, apiKey);
                call.enqueue(new Callback<FoodItemContainerContainer>() {
                    @Override
                    public void onResponse(Call<FoodItemContainerContainer> call, Response<FoodItemContainerContainer> response) {
                        if (!response.isSuccessful()){
                            Log.d("Code: ", ""+response.code());
                            return;
                        }
                        foodNameList.clear();
                        foodNumberList.clear();
                        FoodItemContainerContainer foodItemContainerContainer = response.body();
                        List<FoodItem> foodItemList = foodItemContainerContainer.getList().getItem();
                        for(FoodItem f: foodItemList){
                            foodNumberList.add(f.getOffset(),f.getNdbno());
                            foodNameList.add(f.getOffset(),f.getName());
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(Call<FoodItemContainerContainer> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d(TAG, ".onQueryTextChange");
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }


}