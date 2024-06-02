package com.finalproject.mysac.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.finalproject.mysac.R;
import com.finalproject.mysac.data.local.db.DbHelper;
import com.finalproject.mysac.data.local.preferences.SharedPreferencesManager;
import com.finalproject.mysac.data.model.User;
import com.finalproject.mysac.ui.auth.MainActivity;
import com.finalproject.mysac.ui.recipes.RecipeListActivity;
import com.finalproject.mysac.ui.settings.AboutUsActivity;
import com.finalproject.mysac.ui.settings.ChangePasswordActivity;
import com.finalproject.mysac.ui.settings.EditProfileActivity;

public class SettingsFragment extends Fragment {

    private SharedPreferencesManager sharedPreferencesManager;
    DbHelper dbHelper;
    User loggedUser;
    boolean isClicked = false;

    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferencesManager = new SharedPreferencesManager(view.getContext());
        String loggedUserId = sharedPreferencesManager.getLoggedUsername();

        dbHelper = new DbHelper(view.getContext());
        loggedUser = dbHelper.getUserByUsername(loggedUserId);

        // Find buttons by ID
        Button buttonEditProfile = view.findViewById(R.id.buttonEditProfile);
        Button buttonAboutUs = view.findViewById(R.id.buttonAboutUs);
        Button buttonMyRecipes = view.findViewById(R.id.button_resep_saya);
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

        buttonMyRecipes.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), RecipeListActivity.class);
            intent.putExtra("username", loggedUser.getUsername());
            intent.putExtra("name", loggedUser.getName());
            startActivity(intent);
        });

        buttonChangePassword.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), ChangePasswordActivity.class);
            startActivity(intent);
        });

        buttonLogout.setOnClickListener(v -> {
            if (!isClicked) {
                isClicked = true;
                sharedPreferencesManager.setIsLoggedIn(false);
                Intent intent = new Intent(getActivity(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                isClicked = false;
            }
        });
    }
}
