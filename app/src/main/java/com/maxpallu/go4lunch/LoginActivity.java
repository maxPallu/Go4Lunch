package com.maxpallu.go4lunch;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.Nullable;


import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.OAuthProvider;
import com.google.firebase.auth.TwitterAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.maxpallu.go4lunch.api.User;
import com.maxpallu.go4lunch.api.UserHelper;
import com.maxpallu.go4lunch.base.BaseActivity;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.DefaultLogger;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.TwitterSession;
import com.twitter.sdk.android.core.identity.TwitterLoginButton;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import butterknife.OnClick;


public class LoginActivity extends BaseActivity {

    private int RC_SIGN_IN_GOOGLE = 123;
    private int RC_SIGN_IN_FACEBOOK = 456;
    private int RC_SIGN_IN_EMAIL = 789;
    private int RC_SIGN_IN_TWIITER = 246;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private FirebaseAuth mAuth;
    private Button alreadyConnected;
    private Button facebook;
    private Button google;
    private Button email;
    private TwitterLoginButton twitter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alreadyConnected = findViewById(R.id.button_already_connected);
        twitter = findViewById(R.id.twitterLogin);
        email = findViewById(R.id.emailSignIn);

        TwitterAuthConfig authConfig = new TwitterAuthConfig(getString(R.string.com_twitter_sdk_android_CONSUMER_KEY), getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET));

        TwitterConfig twitterConfig = new TwitterConfig.Builder(this)
                .logger(new DefaultLogger(Log.DEBUG))
                .twitterAuthConfig(authConfig)
                .build();

        twitter.setCallback(new Callback<TwitterSession>() {
            @Override
            public void success(Result<TwitterSession> result) {
                signInWithTwitter(result.data);
                twitter.setVisibility(View.VISIBLE);
                getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
            }

            @Override
            public void failure(TwitterException exception) {

            }
        });

        Twitter.initialize(twitterConfig);
        Twitter.getInstance();
        setContentView(R.layout.login_activity);

        mAuth = FirebaseAuth.getInstance();

        alreadyConnected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity();
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEmailLogin();
            }
        });
    }

    @OnClick(R.id.twitterLogin)
    public void onClickTwitterLogin() {
        Toast.makeText(this, "Log In Button Clicked", Toast.LENGTH_SHORT).show();
    }

    @OnClick(R.id.login_button)
    public void onClickFacebookLogin() {
        this.startFacebookLogin();
    }

    @OnClick(R.id.signInButton)
    public void onClickGoogleSignIn() {
        this.startGoogleSign();
    }

    @Override
    protected void onResume() {
        super.onResume();
        resumeUI();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.handleResponseAfterSignIn(requestCode, resultCode, data);
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.login_activity;
    }

    private void resumeUI() {
        if(this.isCurrentUserLogged()) {
            alreadyConnected.setVisibility(View.VISIBLE);
        } else {
            alreadyConnected.setVisibility(View.GONE);
        }
    }

    private void startFacebookLogin() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Collections.singletonList(new AuthUI.IdpConfig.FacebookBuilder().build()))
                        .setIsSmartLockEnabled(false, true)
                        .build(),
                RC_SIGN_IN_FACEBOOK);
    }

    private void startEmailLogin() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                .setAvailableProviders(Collections.singletonList(new AuthUI.IdpConfig.EmailBuilder().build()))
                .setIsSmartLockEnabled(false, true)
                .build(),
                RC_SIGN_IN_EMAIL);
    }

    private void signInWithTwitter(TwitterSession session) {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Collections.singletonList(new AuthUI.IdpConfig.TwitterBuilder().build()))
                        .setIsSmartLockEnabled(false, true)
                        .build(),
                RC_SIGN_IN_TWIITER);

        AuthCredential credential = TwitterAuthProvider.getCredential(session.getAuthToken().token,
                session.getAuthToken().secret);

        mAuth.signInWithCredential(credential);
    }

    private void startGoogleSign() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Collections.singletonList(new AuthUI.IdpConfig.GoogleBuilder().build()))
                        .setIsSmartLockEnabled(false, true)
                        .build(),
                RC_SIGN_IN_GOOGLE);
    }

    private void handleResponseAfterSignIn(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN_GOOGLE || requestCode == RC_SIGN_IN_FACEBOOK || requestCode == RC_SIGN_IN_EMAIL) {
            if (resultCode == RESULT_OK) {
                this.createUser();
                startMainActivity();
            }
        }
    }

    private void startMainActivity() {
       Intent intent = new Intent(this, MainActivity.class);
       startActivity(intent);
    }

    private void createUser() {
        if(this.isCurrentUserLogged()) {
            Map<String, String> user = new HashMap<>();
            user.put("id", this.getCurrentUser().getUid());
            user.put("name", this.getCurrentUser().getDisplayName());
            user.put("urlPicture", this.getCurrentUser().getPhotoUrl().toString());
            user.put("mail", this.getCurrentUser().getEmail());
            user.put("restaurantName", null);
            user.put("restaurantId", null);
            user.put("restaurantAdress", null);
            user.put("restaurantPicture", null);
            user.put("restaurantWeb", null);
            user.put("restaurantPhone", null);
            db.collection("user").document(getCurrentUser().getUid()).set(user);
        }
    }
}