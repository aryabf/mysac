package com.finalproject.mysac.ui.auth;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.finalproject.mysac.R;
import com.finalproject.mysac.data.local.preferences.SharedPreferencesManager;
import com.finalproject.mysac.ui.home.HomeActivity;
import com.google.android.material.textfield.TextInputEditText;

public class LoginFragment extends Fragment {

    TextInputEditText tietUsername;
    TextInputEditText tietPassword;
    Button btnLogin;
    TextView tvRegister;
    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferencesManager = new SharedPreferencesManager(getContext());
        bindViews(view);

        tvRegister.setOnClickListener(view1 -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fcv_main, new RegisterFragment()).commit();
        });

        btnLogin.setOnClickListener(view1 -> {
            sharedPreferencesManager.setIsLoggedIn(true);

            Intent intent = new Intent(getActivity(), HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });

    }

    void bindViews(View view) {
        tietUsername = view.findViewById(R.id.tiet_username);
        tietPassword = view.findViewById(R.id.tiet_password);
        btnLogin = view.findViewById(R.id.btn_login);
        tvRegister = view.findViewById(R.id.tv_registlink_login);
    }

}