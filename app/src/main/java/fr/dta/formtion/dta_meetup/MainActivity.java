package fr.dta.formtion.dta_meetup;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.login.LoginManager;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private Button disconnectButton;
    private FirebaseAuth mAuth;
    private TextView userName;
    private TextView userMail;
    private FirebaseUser currentUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Get firebase instance
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        userName = findViewById(R.id.nomTextView);
        userMail = findViewById(R.id.mailTextView);

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
        else{
            userMail.setText(currentUser.getEmail());
            userName.setText(currentUser.getDisplayName());
        }



        disconnectButton = findViewById(R.id.disconnectButton);
        disconnectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    // Firebase sign out

                FacebookSdk.sdkInitialize(getApplicationContext());
                LoginManager.getInstance().logOut();
                 mAuth.signOut();

                    // Google sign out
                Intent intent = new Intent (MainActivity.this, LoginActivity.class);
                startActivity(intent);

                }
            });


    }
    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        Log.d("On start","le user est " + currentUser.getDisplayName());
    }
}
