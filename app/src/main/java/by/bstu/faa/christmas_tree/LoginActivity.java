package by.bstu.faa.christmas_tree;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import by.bstu.faa.christmas_tree.DB.activities.DbOptionSelectActivity;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "LoginActivity";
    private FirebaseAuth mAuth;
    EditText editPassword;
    EditText editEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mAuth = FirebaseAuth.getInstance();
        initViews();
    }

    public void onStart() {
        super.onStart();
        // Check if user is signed in and update UI for him.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    private void createAccount(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "Create user with Email: success!");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    }
                    else {
                        // If sign in fails, display a message to the user.
                        Log.d(TAG, "Create user with Email: failure!", task.getException());
                        Toast.makeText(LoginActivity.this, "Authentication failed!",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                });
    }

    private void signIn(String email, String password) {

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d(TAG, "Sign in with Email: success!");
                        FirebaseUser user = mAuth.getCurrentUser();
                        updateUI(user);
                    }
                    else {
                        // If sign in fails, display a message to the user.
                        Log.w(TAG, "Sign in with Email: failure!", task.getException());
                        Toast.makeText(LoginActivity.this, "Authentication failed.",
                                Toast.LENGTH_SHORT).show();
                        updateUI(null);
                    }
                });
    }

    private void initViews() {
        editEmail = findViewById(R.id.editEmail);
        editPassword = findViewById(R.id.editPassword);
        findViewById(R.id.button_sign_in).setOnClickListener(this);
        findViewById(R.id.button_reg).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        try {
            if (view.getId() == R.id.button_sign_in) {
                if(editEmail.getText().toString().equals("adminemail@gmail.com") &&
                editPassword.getText().toString().equals("admin12345"))
                {
                    Intent intent = new Intent(this, DbOptionSelectActivity.class);
                    startActivity(intent);
                }
                else { signIn(editEmail.getText().toString(), editPassword.getText().toString()); }
            }
            else if (view.getId() == R.id.button_reg) {
                createAccount(editEmail.getText().toString(), editPassword.getText().toString());
            }
        }
        catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUI(FirebaseUser user) {
        if(user != null) {
            Intent intent = new Intent(this, MainActivity.class);
            intent.putExtra("USER_ID", user.getUid());
            startActivity(intent);
            finish();
        }
    }
}