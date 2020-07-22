package com.maxpallu.go4lunch;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.coordinatorlayout.widget.CoordinatorLayout;

import com.facebook.login.widget.LoginButton;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.common.SignInButton;
import com.maxpallu.go4lunch.base.BaseActivity;

import java.util.Collections;

import butterknife.BindView;
import butterknife.OnClick;


public class LoginActivity extends BaseActivity {

    private int RC_SIGN_IN = 1;

    @Override
    public int getFragmentLayout() {
        return R.layout.login_activity;
    }

    @OnClick(R.id.login_button)
    public void onClickFacebookLogin() {
        if(this.isCurrentUserLogged()) {
            this.startMainActivity();
        } else {
            this.startFacebookLogin();
        }
    }

    @OnClick(R.id.signInButton)
    public void onClickGoogleSignIn() {
        if(this.isCurrentUserLogged()) {
            this.startMainActivity();
        } else {
            this.startGoogleSign();
        }
    }

    private void startFacebookLogin() {
        startActivityForResult(
                AuthUI.getInstance()
                            .createSignInIntentBuilder()
                            .setAvailableProviders(Collections.singletonList(new AuthUI.IdpConfig.Builder(AuthUI.FACEBOOK_PROVIDER).build()))
                            .setIsSmartLockEnabled(false, true)
                            .build(),
                            RC_SIGN_IN);
    }

    private void startGoogleSign() {
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(Collections.singletonList(new AuthUI.IdpConfig.Builder(AuthUI.GOOGLE_PROVIDER).build()))
                        .setIsSmartLockEnabled(false, true)
                        .build(),
                RC_SIGN_IN);
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}