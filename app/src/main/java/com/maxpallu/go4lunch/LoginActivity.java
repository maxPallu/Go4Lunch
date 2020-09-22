package com.maxpallu.go4lunch;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;


import com.firebase.ui.auth.AuthUI;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.maxpallu.go4lunch.base.BaseActivity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import butterknife.OnClick;


public class LoginActivity extends BaseActivity {

    private int RC_SIGN_IN_GOOGLE = 123;
    private int RC_SIGN_IN_FACEBOOK = 456;
    private int RC_SIGN_IN_EMAIL = 789;
    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (this.isCurrentUserLogged()) {
            this.startMainActivity();
        }
    }

    @Override
    public int getFragmentLayout() {
        return R.layout.login_activity;
    }

    @OnClick(R.id.login_button)
    public void onClickFacebookLogin() {
        this.startFacebookLogin();
    }

    @OnClick(R.id.signInButton)
    public void onClickGoogleSignIn() {
        this.startGoogleSign();
    }

    @OnClick(R.id.twitterLogin)
    public void onClickTwitterLogin() {
        this.startTwitterLogin();
    }

    @OnClick(R.id.emailSignIn)
    public void onClickEmailLogin() {
        this.startEmailLogin();
    }

    private void startFacebookLogin() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Collections.singletonList(new AuthUI.IdpConfig.FacebookBuilder().build()))
                        .setIsSmartLockEnabled(false, true)
                        .build(),
                RC_SIGN_IN_FACEBOOK);
        Map<String, String> user = new HashMap<>();
        user.put("id", this.getCurrentUser().getUid());
        user.put("name", this.getCurrentUser().getDisplayName());
        user.put("urlPicture", this.getCurrentUser().getPhotoUrl().toString());
        db.collection("user").add(user);
    }

    private void startEmailLogin() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                .setAvailableProviders(Collections.singletonList(new AuthUI.IdpConfig.EmailBuilder().build()))
                .setIsSmartLockEnabled(false, true)
                .build(),
                RC_SIGN_IN_EMAIL);
        Map<String, String> user = new HashMap<>();
        user.put("id", this.getCurrentUser().getUid());
        user.put("name", this.getCurrentUser().getDisplayName());
        user.put("urlPicture", this.getCurrentUser().getPhotoUrl().toString());
        db.collection("user").add(user);
    }

    private void startTwitterLogin() {

    }

    private void startGoogleSign() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Collections.singletonList(new AuthUI.IdpConfig.GoogleBuilder().build()))
                        .setIsSmartLockEnabled(false, true)
                        .build(),
                RC_SIGN_IN_GOOGLE);
        Map<String, String> user = new HashMap<>();
        user.put("id", this.getCurrentUser().getUid());
        user.put("name", this.getCurrentUser().getDisplayName());
        user.put("urlPicture", this.getCurrentUser().getPhotoUrl().toString());
        db.collection("user").add(user);
    }

    private void startMainActivity() {
        Map<String, String> user = new HashMap<>();
        user.put("id", this.getCurrentUser().getUid());
        user.put("name", this.getCurrentUser().getDisplayName());
        user.put("urlPicture", this.getCurrentUser().getPhotoUrl().toString());
        db.collection("user").add(user);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}