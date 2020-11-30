package com.maxpallu.go4lunch;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SwitchCompat;
import androidx.fragment.app.Fragment;

public class SettingsFragment extends Fragment {

    SwitchCompat notifications;
    boolean checked = true;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        notifications = view.findViewById(R.id.notif_switch);

        notifications.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked) {
                    checked = true;
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra("Checked", checked);
                } else {
                    checked = false;
                    Intent intent = new Intent(getActivity(), DetailActivity.class);
                    intent.putExtra("Checked", checked);
                }
            }
        });

        return view;
    }
}
