//package com.kitakits.app;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.TextView;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//import com.google.firebase.auth.FirebaseAuth;
//
//import java.util.Objects;
//
//public class LoginActivity extends AppCompatActivity {
//
//    private FirebaseAuth Auth = FirebaseAuth.getInstance();
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_login);
//
//        EditText emailEditText = findViewById(R.id.edit_email);
//        EditText passwordEditText = findViewById(R.id.edit_password);
//
//        Button loginButton = findViewById(R.id.button_login);
//        TextView signUpLink = findViewById(R.id.text_signup);
//
//        loginButton.setOnClickListener(v -> {
//            String email = emailEditText.getText().toString().trim();
//            String password = passwordEditText.getText().toString().trim();
//
//            if (email.isEmpty() || password.isEmpty()) {
//                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
//                return;
//            }
//
//            Auth.signInWithEmailAndPassword(email, password)
//                    .addOnCompleteListener(task -> {
//                        if (task.isSuccessful()) {
//                            Toast.makeText(this, "Login Successful", Toast.LENGTH_SHORT).show();
//                            Intent intent = new Intent(LoginActivity.this, MainActivity.class); // ✅ Redirect to MainActivity
//                            startActivity(intent);
//                            finish();
//                        } else {
//                            Toast.makeText(this, "Login Failed: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
//                        }
//                    });
//        });
//
//        signUpLink.setOnClickListener(v -> {
//            startActivity(new Intent(LoginActivity.this, SignUpActivity.class)); // ✅ Go to Sign Up page only
//        });
//    }
//}

package com.kitakits.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Objects;

public class LoginActivity extends AppCompatActivity {

    private FirebaseAuth auth = FirebaseAuth.getInstance();
    private FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText emailEditText = findViewById(R.id.edit_email);
        EditText passwordEditText = findViewById(R.id.edit_password);
        Button loginButton = findViewById(R.id.button_login);
        TextView signUpLink = findViewById(R.id.text_signup);

        loginButton.setOnClickListener(v -> {
            String email = emailEditText.getText().toString().trim();
            String password = passwordEditText.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            String uid = Objects.requireNonNull(auth.getCurrentUser()).getUid();
                            db.collection("users").document(uid).get()
                                    .addOnSuccessListener(document -> {
                                        String userType = document.getString("userType");
                                        Intent intent;
                                        if (userType.equals("Job Seeker")) {
                                            intent = new Intent(this, MainActivity.class);
                                        } else {
                                            intent = new Intent(this, JobPosterActivity.class);
                                        }
                                        startActivity(intent);
                                        finish();
                                    });
                        } else {
                            Toast.makeText(this, "Login Failed: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        });

        signUpLink.setOnClickListener(v -> startActivity(new Intent(this, SignUpActivity.class)));
    }
}
