package com.example.sastafy;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity {

    GoogleSignInOptions googleSignInOptions;
    GoogleSignInClient googleSignInClient;
    ImageView google_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        google_button = findViewById(R.id.google_button);

        googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).requestEmail().build();
        googleSignInClient = GoogleSignIn.getClient(this, googleSignInOptions);

        GoogleSignInAccount googleSignInAccount = GoogleSignIn.getLastSignedInAccount(this);

        if(googleSignInAccount != null){
            navigateToSecondActivity();
        }

        google_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });
    }

    void signIn(){
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, 1000);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 9001){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                task.getResult(ApiException.class);
                navigateToSecondActivity();
            } catch (ApiException e) {
                Toast.makeText(getApplicationContext(), "Something went wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    void navigateToSecondActivity(){
        finish();
        Intent intent = new Intent(MainActivity.this, SecondActivity.class);
        startActivity(intent);
    }
}