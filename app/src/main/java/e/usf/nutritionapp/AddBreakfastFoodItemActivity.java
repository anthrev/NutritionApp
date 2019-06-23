package e.usf.nutritionapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddBreakfastFoodItemActivity extends AppCompatActivity {

    final String apiKey = "GAWUu80GqmdItHRAt9BFJb2rqxY4hkQe1LT4m7ZW";
    final String TAG = "AddBreakfastFoodItem";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_breakfast_food_item);

        ListView search_food = findViewById(R.id.search_food);

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
                Call<FoodItemContainerContainer> call = api.getFoodItems("json", query, "n", 5, 0, apiKey);
                call.enqueue(new Callback<FoodItemContainerContainer>() {
                    @Override
                    public void onResponse(Call<FoodItemContainerContainer> call, Response<FoodItemContainerContainer> response) {
                        if (!response.isSuccessful()){
                            Log.d("Code: ", ""+response.code());
                            return;
                        }
                        FoodItemContainerContainer foodItemContainerContainer = response.body();
                            List<FoodItem> foodItemList = foodItemContainerContainer.getList().getItem();
                        for(FoodItem f: foodItemList){
                            Log.d("offset", String.valueOf(f.getOffset()));
                            Log.d("group", f.getGroup());
                            Log.d("name", f.getName());
                            Log.d("ndbno", f.getNdbno());
                            Log.d("ds", f.getDs());
                            Log.d("manu", f.getManu());
                        }
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