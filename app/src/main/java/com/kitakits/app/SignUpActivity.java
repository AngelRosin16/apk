//package com.kitakits.app;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.Toast;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import com.google.firebase.auth.FirebaseAuth;
//
//import java.util.Objects;
//
//public class SignUpActivity extends AppCompatActivity {
//
//    private EditText emailInput, passwordInput, confirmPasswordInput;
//    private Button signupButton;
//    private FirebaseAuth mAuth;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.sign_up);
//
//        mAuth = FirebaseAuth.getInstance();
//
//        // Bind views
//        emailInput = findViewById(R.id.newemail);
//        passwordInput = findViewById(R.id.new_password);
//        confirmPasswordInput = findViewById(R.id.confirm_password);
//
//        signupButton = findViewById(R.id.button_register);
//
//        signupButton.setOnClickListener(v -> registerUser());
//    }
//
//    private void registerUser() {
//        String email = emailInput.getText().toString().trim();
//        String password = passwordInput.getText().toString().trim();
//        String confirmPassword = confirmPasswordInput.getText().toString().trim();
//
//        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
//            Toast.makeText(this, "All fields are required.", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if (!password.equals(confirmPassword)) {
//            Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        mAuth.createUserWithEmailAndPassword(email, password)
//                .addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        Toast.makeText(this, "Account created successfully!", Toast.LENGTH_SHORT).show();
//
//                        // ✅ Go to MainActivity after successful sign-up
//                        Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
//                        startActivity(intent);
//                        finish();
//                    } else {
//                        Toast.makeText(this, "Sign up failed: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
//                    }
//                });
//    }
//}
//


package com.kitakits.app;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {

    private EditText emailInput, passwordInput, confirmPasswordInput;
    private RadioGroup userTypeGroup;
    private Button signupButton;
    private FirebaseAuth mAuth;
    private FirebaseFirestore db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Declares connected XML
        setContentView(R.layout.sign_up);

        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        emailInput = findViewById(R.id.newemail);
        passwordInput = findViewById(R.id.new_password);
        confirmPasswordInput = findViewById(R.id.confirm_password);
        userTypeGroup = findViewById(R.id.userTypeGroup);
        signupButton = findViewById(R.id.button_register);

        signupButton.setOnClickListener(v -> registerUser());
    }

    private void registerUser() {
        String email = emailInput.getText().toString().trim();
        String password = passwordInput.getText().toString().trim();
        String confirmPassword = confirmPasswordInput.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
            Toast.makeText(this, "All fields are required.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!password.equals(confirmPassword)) {
            Toast.makeText(this, "Passwords do not match.", Toast.LENGTH_SHORT).show();
            return;
        }

        int selectedId = userTypeGroup.getCheckedRadioButtonId();
        if (selectedId == -1) {
            Toast.makeText(this, "Please select user type", Toast.LENGTH_SHORT).show();
            return;
        }

        RadioButton selectedRadio = findViewById(selectedId);
        String userType = selectedRadio.getText().toString();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser user = mAuth.getCurrentUser();
                        if (user != null) {
                            Map<String, Object> userData = new HashMap<>();
                            userData.put("email", email);
                            userData.put("userType", userType);

                            db.collection("users").document(user.getUid())
                                    .set(userData)
                                    .addOnSuccessListener(aVoid -> redirectUser(userType))
                                    .addOnFailureListener(e -> Toast.makeText(SignUpActivity.this, "Error saving user data", Toast.LENGTH_SHORT).show());
                        }
                    } else {
                        Toast.makeText(this, "Sign up failed: " + Objects.requireNonNull(task.getException()).getMessage(), Toast.LENGTH_LONG).show();
                    }
                });
    }

    private void redirectUser(String userType) {
        Intent intent;
        if (userType.equals("Job Seeker")) {
            intent = new Intent(this, MainActivity.class);
        } else {
           intent = new Intent(this, JobPosterActivity.class);
        }
        startActivity(intent);
        finish();
    }
}