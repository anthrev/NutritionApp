package e.usf.nutritionapp;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;

import java.util.Arrays;
import java.util.List;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "Log In";

    private EditText mEmailField;
    private EditText mPasswordField;

    private Button mRegisterButton;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mEmailField = findViewById(R.id.emailField);
        mPasswordField = findViewById(R.id.passwordField);

        mRegisterButton = findViewById(R.id.Register);

        mRegisterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToRegister();
            }
        });

        mAuth = FirebaseAuth.getInstance();
    }

    public void signIn(View view){

        String email = mEmailField.getText().toString().trim();

        String password = mPasswordField.getText().toString();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithEmail:success");
                            goToHome();
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void goToRegister() {

        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void goToHome() {

        FirebaseAuth firebaseAuth;
        DatabaseReference databaseReference;
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference().child(user.getUid());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String age = dataSnapshot.child("age").getValue().toString();
                String caloriesBurnedPerDay = dataSnapshot.child("caloriesBurnedPerDay").getValue().toString();
                String gender = dataSnapshot.child("gender").getValue().toString();
                String goalCalories = dataSnapshot.child("goalCalories").getValue().toString();
                String goalWeight = dataSnapshot.child("goalWeight").getValue().toString();
                String goals = dataSnapshot.child("goals").getValue().toString();
                String height = dataSnapshot.child("height").getValue().toString();
                String weeklyGoals = dataSnapshot.child("weeklyGoals").getValue().toString();
                String weight = dataSnapshot.child("weight").getValue().toString();
                Member member = new Member();
                member.setAge(Double.valueOf(age));
                member.setGoalCalories(Double.valueOf(goalCalories));
                member.setCaloriesBurnedPerDay(Double.valueOf(caloriesBurnedPerDay));
                member.setGender(gender);
                member.setGoalWeight(Float.valueOf(goalWeight));
                member.setGoals(goals);
                member.setHeight(Double.valueOf(height));
                member.setWeeklyGoals(Double.valueOf(weeklyGoals));
                member.setWeight(Float.valueOf(weight));
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                intent.putExtra("member", new Gson().toJson(member));
                startActivity(intent);
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

}
