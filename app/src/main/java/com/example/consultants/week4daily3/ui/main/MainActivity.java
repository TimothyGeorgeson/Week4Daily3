package com.example.consultants.week4daily3.ui.main;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.consultants.week4daily3.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity implements MainContract.View, UserAuthenticator.Callback {
    public static final String TAG = MainActivity.class.getSimpleName() + "_TAG";

    private UserAuthenticator userAuthenticator;
    private EditText etUserEmail;
    private EditText etPassword;
    private SignInButton btnGoogle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etUserEmail = findViewById(R.id.etUserEmail);
        etPassword = findViewById(R.id.etPassword);
        btnGoogle = findViewById(R.id.btnGoogle);
        userAuthenticator = new UserAuthenticator(this, new CompleteListener(this));

        btnGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userAuthenticator.googleSignIn(etUserEmail.getText().toString(),
                        etPassword.getText().toString());
            }
        });

    }

    public void emailSignIn(View view) {
        userAuthenticator.emailSignIn(etUserEmail.getText().toString(),
                etPassword.getText().toString());
    }

    public void emailSignUp(View view) {
        userAuthenticator.emailSignUp(etUserEmail.getText().toString(),
                etPassword.getText().toString());
    }

    @Override
    public void onUserValidated(FirebaseUser user) {
        Toast.makeText(this, "Success", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUserInvalidated() {
        Toast.makeText(this, "Failure", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onSignIn(String email, String password) {

    }

    @Override
    public void onSignUp(String email, String password) {

    }

    @Override
    public void showError(String error) {

    }
}
