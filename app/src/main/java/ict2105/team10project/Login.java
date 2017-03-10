package ict2105.team10project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;

import com.facebook.Profile;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends Activity implements View.OnClickListener {
    // Variables List
    private Button signinBtn, signupBtn, skipBtn;
    private EditText emailInput, passwordInput;
    //    CallbackManager callbackManager;
    Button fbButton;

    final static private String TAG = "LOGIN!";

    private FirebaseAuth mAuth;
    //    private FirebaseAuth fbAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

//    private static final int RC_SIGN_IN=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FacebookSdk.sdkInitialize(getApplicationContext());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        // Buttons
        signinBtn = (Button) findViewById(R.id.signin_btn);
        signupBtn = (Button) findViewById(R.id.signup_btn);
        skipBtn = (Button) findViewById(R.id.skip_btn);
        fbButton = (Button) findViewById(R.id.facebook_btn);
        signinBtn.setOnClickListener(this);
        signupBtn.setOnClickListener(this);
        skipBtn.setOnClickListener(this);
        fbButton.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();

//        callbackManager=CallbackManager.Factory.create();
//        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
//            @Override
//            public void onSuccess(LoginResult loginResult) {
////                Profile profile= Profile.getCurrentProfile();
////                String userName=profile.getName();
////                String userID=profile.getId();
////
////                String id=loginResult.getAccessToken().getUserId();
////
////                Log.i("Succeed","Liao"+id);
//                startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().
//                        setProviders(AuthUI.FACEBOOK_PROVIDER).build(),1);
//                Intent intent = new Intent(Login.this, MainPage.class);
//                startActivity(intent);
//            }
//
//            @Override
//            public void onCancel() {
//
//            }
//
//            @Override
//            public void onError(FacebookException error) {
//
//            }
//        });

        // EditTexts
        emailInput = (EditText) findViewById(R.id.email_input);
        passwordInput = (EditText) findViewById(R.id.password_input);

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    // User is signed in
                    Log.d(TAG, "onAuthStateChanged:signed_in:" + user.getUid());
                } else {
                    // User is signed out
                    Log.d(TAG, "onAuthStateChanged:signed_out");
                }
            }
        };
    }


    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }


    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    /**
     * Sign in function:
     * - call validateForm()
     * - connect with firebase auth
     * @param email email
     * @param password password
     */
    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if (!validateForm()) {
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithEmail", task.getException());
                            Toast.makeText(Login.this, "Authentication failed!",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(Login.this, "Login Successful!",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            Log.d(TAG, "user id: " + uid);
            Intent intent = new Intent(Login.this, MainPage.class);
            startActivity(intent);
        }
    }

    private void signUp(String email, String password) {
        Log.d(TAG, "signUp:" + email);
        if (!validateForm()) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "createUserWithEmail:onComplete:" + task.isSuccessful());

                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Toast.makeText(Login.this, "Sign up failed, Email already existed!",
                                    Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(Login.this, "Account created!",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            String uid = user.getUid();
            Log.d(TAG, "user id: " + uid);
        }
    }

    /**
     * Validate form to check for invalid email address or password
     * - Valid email address
     * - Password > 6 characters
     * @return true or false
     */
    private boolean validateForm() {
        boolean valid = true;
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (email.equals("")) {
            emailInput.setError("Required.");
            return false;
        } else if (!email.matches("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+.[a-zA-Z]{2,6}$")) {
            emailInput.setError("A valid email is required.");
            return false;
        }

        if (password.equals("")) {
            passwordInput.setError("Required.");
            return false;
        } else if (!password.matches("^.{6,}$")) {
            passwordInput.setError("Password must be at least 6 characters.");
            return false;
        }
        return valid;
    }

    @Override
    public void onClick(View view) {
        String email = emailInput.getText().toString();
        String password = passwordInput.getText().toString();

        if (view == signinBtn) {
            signIn(email, password);
        } else if (view == signupBtn) {
            signUp(email, password);
        } else if (view == skipBtn) {
            Intent intent = new Intent(Login.this, MainPage.class);
            startActivity(intent);
        } else if (view == fbButton) {
            Intent intent = new Intent(Login.this, Login_Facebook.class);
            startActivity(intent);
        }
    }
}

