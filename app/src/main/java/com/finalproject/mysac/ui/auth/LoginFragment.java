package com.finalproject.mysac.ui.auth;

import static android.content.Intent.FLAG_ACTIVITY_CLEAR_TASK;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.finalproject.mysac.R;
import com.finalproject.mysac.data.local.db.DbHelper;
import com.finalproject.mysac.data.local.preferences.SharedPreferencesManager;
import com.finalproject.mysac.ui.home.HomeActivity;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class LoginFragment extends Fragment {

    TextInputEditText tietUsername;
    TextInputEditText tietPassword;
    Button btnLogin;
    TextView tvRegister;
    DbHelper dbHelper;
    private SharedPreferencesManager sharedPreferencesManager;
    boolean isClicked = false;

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

            if (!isClicked) {

                isClicked = true;
                String username = tietUsername.getText().toString();
                String password = tietPassword.getText().toString();

                if (username.isEmpty() || password.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(view, "Mohon isi seluruh field.", Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(ContextCompat.getColor(view.getContext(), R.color.snackbarred));
                    snackbar.show();
                    isClicked = false;
                } else {
                    try {
                        if (dbHelper.login(username, password)) {
                            sharedPreferencesManager.setIsLoggedIn(true);
                            sharedPreferencesManager.setLoggedUsername(username);
                            Intent intent = new Intent(getActivity(), HomeActivity.class);
                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            isClicked = false;
                        } else {
                            Snackbar snackbar = Snackbar.make(view, "Username atau Password salah.", Snackbar.LENGTH_SHORT);
                            snackbar.setBackgroundTint(ContextCompat.getColor(view.getContext(), R.color.snackbarred));
                            snackbar.show();
                            isClicked = false;
                        }
                    } catch (Exception e) {
                        Snackbar snackbar = Snackbar.make(view, "Gagal melakukan login.", Snackbar.LENGTH_SHORT);
                        snackbar.setBackgroundTint(ContextCompat.getColor(view.getContext(), R.color.snackbarred));
                        snackbar.show();
                        isClicked = false;
                    }
                }
            }

        });

    }

    void bindViews(View view) {
        dbHelper = new DbHelper(view.getContext());
        tietUsername = view.findViewById(R.id.tiet_username);
        tietPassword = view.findViewById(R.id.tiet_password);
        btnLogin = view.findViewById(R.id.btn_login);
        tvRegister = view.findViewById(R.id.tv_registlink_login);
    }

}