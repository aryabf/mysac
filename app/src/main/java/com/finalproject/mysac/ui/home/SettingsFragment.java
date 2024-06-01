package com.finalproject.mysac.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.finalproject.mysac.R;
import com.finalproject.mysac.ui.settings.AboutUsActivity;
import com.finalproject.mysac.ui.settings.ChangePasswordActivity;
import com.finalproject.mysac.ui.settings.EditProfileActivity;

public class SettingsFragment extends Fragment {

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        // Find buttons by ID
        Button buttonEditProfile = view.findViewById(R.id.buttonEditProfile);
        Button buttonAboutUs = view.findViewById(R.id.buttonAboutUs);
        Button buttonChangePassword = view.findViewById(R.id.buttonChangePassword);
        Button buttonLogout = view.findViewById(R.id.buttonLogout);

        // Set onClick listeners
        buttonEditProfile.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), EditProfileActivity.class);
            startActivity(intent);
        });

        buttonAboutUs.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), AboutUsActivity.class);
            startActivity(intent);
        });

        buttonChangePassword.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
            startActivity(intent);
        });

        buttonLogout.setOnClickListener(v -> {
            // keluar
        });

        return view;
    }
}
