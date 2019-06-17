package e.usf.nutritionapp;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity";
    BottomNavigationView bottomNav;
    private double goalCalories;
    private double weight;
    private double goal_weight;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        String jsonMember=null;
        Bundle extras = getIntent().getExtras();
        if(extras != null) {
            jsonMember = extras.getString("member");
        }
        Member member = new Gson().fromJson(jsonMember, Member.class);

        goalCalories = member.getGoalCalories();
        weight = member.getWeight();
        goal_weight = member.getGoalWeight();

        bottomNav = findViewById(R.id.bottom_nav);
        Log.d(TAG, "onCreate: started");

        bottomNav.setOnNavigationItemSelectedListener(navListener);

        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                new HomeFragment()).commit();
    }



    private  BottomNavigationView.OnNavigationItemSelectedListener navListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {

                    Fragment selectedFragment = null;

                    switch(menuItem.getItemId()){
                        case R.id.navigation_food_diary:
                            selectedFragment = new FoodDiaryFragment();
                            break;
                        case R.id.navigation_goals:
                            selectedFragment = new GoalsFragment();
                            break;
                        case R.id.navigation_home:
                            selectedFragment = new HomeFragment();
                            break;
                        case R.id.navigation_more:
                            selectedFragment = new MoreFragment();
                            break;
                    }

                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                            selectedFragment).commit();
                    return true;
                }
            };

    public void setNavListener(Fragment fragment){
        bottomNav.setSelectedItemId(fragment.getId());
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                fragment).commit();
    }

    public double getGoalCalories(){
        return goalCalories;
    }

    public double getWeight(){
        return weight;
    }

    public double getGoalWeight(){
        return goal_weight;
    }
}
