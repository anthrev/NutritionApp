package e.usf.nutritionapp;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.mancj.materialsearchbar.MaterialSearchBar;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class AddBreakfastFoodItemActivity extends AppCompatActivity {

    SearchView mySearchView;
    String searchItem;
    ArrayAdapter<String> adapter;
    ListView search_food;
    Api api;

    final String TAG = "AddBreakfastFoodItem";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_breakfast_food_item);

        search_food = findViewById(R.id.search_food);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        api = retrofit.create(Api.class);

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

                Log.d(TAG, ".onQueryTextSubmit");
                Log.d(TAG, "Query submitted:" + query);

                Call<List<FoodItem>> call = api.getFoodItems(query);
                call.enqueue(new Callback<List<FoodItem>>() {
                    @Override
                    public void onResponse(Call<List<FoodItem>> call, Response<List<FoodItem>> response) {
                        List<FoodItem> foodItems = response.body();
                        for(FoodItem f: foodItems){
                            Log.d("offset", String.valueOf(f.getOffset()));
                            Log.d("group", f.getGroup());
                            Log.d("name", f.getName());
                            Log.d("ndbno", f.getNdbno());
                            Log.d("ds", f.getDs());
                            Log.d("manu", f.getManu());
                        }
                    }

                    @Override
                    public void onFailure(Call<List<FoodItem>> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

                /*
                ArrayList<String> arrayFood = new ArrayList<>();
                arrayFood.addAll(Arrays.asList(getResources().getStringArray(R.array.my_foods)));

                adapter = new ArrayAdapter<String>(
                        AddBreakfastFoodItemActivity.this,
                        android.R.layout.simple_list_item_1,
                        arrayFood
                );

                search_food.setAdapter(adapter);

                */

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