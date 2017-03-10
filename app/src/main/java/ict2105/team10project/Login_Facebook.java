package ict2105.team10project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseAuth;

/**
 * Created by ckahsheng on 9/3/2017.
 */

public class Login_Facebook extends AppCompatActivity {
    private static final int RC_SIGN_IN = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facebook);
        FirebaseAuth auth = FirebaseAuth.getInstance();
        if(auth.getCurrentUser() != null){
            //Means user is already logged on
            Log.d("HERE! FBLogin", "Name: " + auth.getCurrentUser().getDisplayName());
            Intent intent = new Intent(Login_Facebook.this, MainPage.class);
            startActivity(intent);
        } else {
            Toast.makeText(getApplicationContext(), "hey i am logged in la", Toast.LENGTH_SHORT).show();
            startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().
                    setProviders(AuthUI.FACEBOOK_PROVIDER).build(),RC_SIGN_IN);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        Toast.makeText(getApplicationContext(), "hey " + requestCode, Toast.LENGTH_LONG).show();
        if(requestCode == RC_SIGN_IN){
            if(resultCode == RESULT_OK){
                //User is logged in
                //Redirect main page
                Intent intent = new Intent(Login_Facebook.this, MainPage.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), "hey i am logged in " + requestCode, Toast.LENGTH_SHORT).show();

            }
            else{
                //User is not logged in
                //Not valid
               Intent intent = new Intent(Login_Facebook.this, Login.class);
                Toast.makeText(getApplicationContext(), "Error loading,try again", Toast.LENGTH_SHORT).show();
                startActivity(intent);

            }
        }
    }
}
