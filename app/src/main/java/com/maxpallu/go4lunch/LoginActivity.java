package com.maxpallu.go4lunch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;


import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.TwitterAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;
import com.maxpallu.go4lunch.api.User;
import com.maxpallu.go4lunch.api.UserHelper;
import com.maxpallu.go4lunch.base.BaseActivity;
import com.twitter.sdk.android.core.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;
import com.twitter.sdk.android.core.TwitterConfig;

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
    private Button alreadyConnected;
    private Button facebook;
    private Button google;
    private Button email;
    private Button twitter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        alreadyConnected = findViewById(R.id.button_already_connected);
        twitter = findViewById(R.id.twitterLogin);
        Twitter.initialize(this);
        email = findViewById(R.id.emailSignIn);

        alreadyConnected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startMainActivity();
            }
        });

        twitter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startTwitterLogin();
            }
        });

        email.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEmailLogin();
            }
        });
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

    private void startTwitterLogin() {
        TwitterAuthConfig authConfig = new TwitterAuthConfig(getString(R.string.com_twitter_sdk_android_CONSUMER_KEY), getString(R.string.com_twitter_sdk_android_CONSUMER_SECRET));

        TwitterConfig twitterConfig = new TwitterConfig.Builder(this)
                .twitterAuthConfig(authConfig)
                .build();

        Twitter.initialize(twitterConfig);
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