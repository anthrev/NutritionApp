package e.usf.nutritionapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

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
    private double caloriesEaten = 0;
    private ArrayAdapter breakfastAdapter;
    private ArrayList<FoodDetails> breakfastFoodList;
    private FoodDetails foodDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_breakfast_food_item);


        listFoodBreakfast = (ListView) findViewById(R.id.list_food_breakfast);

        breakfastFoodList = new ArrayList<>();

        //Get Food Details List from the foods that the user has eaten(Firebase Database)
        FirebaseAuth firebaseAuth;
        final DatabaseReference databaseReference;
        databaseReference = FirebaseDatabase.getInstance().getReference();
        firebaseAuth = FirebaseAuth.getInstance();
        final FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).child("breakfastFoodsEaten").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for (DataSnapshot child: children) {
                    foodDetails = child.getValue(FoodDetails.class);
                    breakfastFoodList.add(foodDetails);
                    breakfastAdapter.notifyDataSetChanged();
                    caloriesEaten += foodDetails.getCalories();
                }
                databaseReference.child(user.getUid()).child("caloriesEaten").setValue(caloriesEaten);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listFoodBreakfast = findViewById(R.id.list_food_breakfast);
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
        listFood = (ListView) findViewById(R.id.list_food);
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
        searchView.setIconifiedByDefault(false);
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

    public void goToCamera(View view) {

        Intent intent = new Intent(this, CameraActivity.class);
        startActivityForResult(intent, 2);
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
                FoodDetails foodDetailsNew = new FoodDetails(calories,protein,carbohydrates, fat, name, servings);
                breakfastFoodList.add(foodDetailsNew);
                breakfastAdapter.notifyDataSetChanged();
                searchView.setQuery("",false);
                resetMenu();

                FirebaseAuth firebaseAuth;
                DatabaseReference databaseReference;
                databaseReference = FirebaseDatabase.getInstance().getReference();
                firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser user = firebaseAuth.getCurrentUser();
                databaseReference.child(user.getUid()).child("breakfastFoodsEaten").removeValue();
                databaseReference.child(user.getUid()).child("breakfastFoodsEaten").setValue(breakfastFoodList);

                Intent resultIntent = new Intent();
                resultIntent.putExtra("calories", caloriesEaten);
                setResult(RESULT_OK, resultIntent);

                finish();

            }
            if(resultCode == RESULT_CANCELED){
                resetMenu();
            }
        }
        if(requestCode == 2)
        {
            if(resultCode == RESULT_OK)
            {
                Log.d(TAG, "got result");
                String productName = data.getStringExtra(Intent.EXTRA_TEXT);
                searchView.setQuery(productName, false);
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


