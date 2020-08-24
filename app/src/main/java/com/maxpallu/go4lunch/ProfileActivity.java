package com.maxpallu.go4lunch;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.maxpallu.go4lunch.base.BaseActivity;

import butterknife.BindView;

public class ProfileActivity extends BaseActivity {

    @BindView(R.id.profile_avatar) ImageView profileAvatar;
    @BindView(R.id.profile_name) TextView profileName;
    @BindView(R.id.profile_email) TextView profileEmail;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.updateUIWhenCreating();
    }

    @Override
    public int getFragmentLayout() {
        return 0;
    }

    private void updateUIWhenCreating() {

        if(this.getCurrentUser() != null) {
            if(this.getCurrentUser().getPhotoUrl() != null) {
                Glide.with(this)
                                .load(this.getCurrentUser().getPhotoUrl())
                                .apply(RequestOptions.circleCropTransform())
                                .into(profileAvatar);
            }

            String email = TextUtils.isEmpty(this.getCurrentUser().getEmail()) ?
                    getString(R.string.info_no_email_found) : this.getCurrentUser().getEmail();
            String username = TextUtils.isEmpty(this.getCurrentUser().getDisplayName()) ?
                    getString(R.string.info_no_username_found) : this.getCurrentUser().getDisplayName();

            this.profileEmail.setText(email);
            this.profileName.setText(username);
        }
    }
}
