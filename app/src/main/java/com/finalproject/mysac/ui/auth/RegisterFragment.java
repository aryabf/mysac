package com.finalproject.mysac.ui.auth;

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
import android.widget.Toast;

import com.finalproject.mysac.R;
import com.finalproject.mysac.data.local.db.DbHelper;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class RegisterFragment extends Fragment {

    TextInputEditText tietName;
    TextInputEditText tietUsername;
    TextInputEditText tietPassword;
    TextInputEditText tietConfirm;
    Button btnRegister;
    TextView tvLogin;
    DbHelper dbHelper;
    boolean isClicked = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_register, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindViews(view);

        tvLogin.setOnClickListener(view1 -> {
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fcv_main, new LoginFragment()).commit();
        });

        btnRegister.setOnClickListener(view1 -> {
            if (!isClicked) {
                isClicked = true;
                String name = tietName.getText().toString();
                String username = tietUsername.getText().toString();
                String password = tietPassword.getText().toString();
                String confirm = tietConfirm.getText().toString();

                if (name.isEmpty() || username.isEmpty() || password.isEmpty() || confirm.isEmpty()) {
                    Snackbar snackbar = Snackbar.make(view, "Mohon isi seluruh field.", Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(ContextCompat.getColor(view.getContext(), R.color.snackbarred));
                    snackbar.show();
                    isClicked = false;
                } else if (!password.equals(confirm)) {
                    Snackbar snackbar = Snackbar.make(view, "Password tidak cocok.", Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(ContextCompat.getColor(view.getContext(), R.color.snackbarred));
                    snackbar.show();
                    isClicked = false;
                } else {
                    try {
                        if (dbHelper.registerUser(username, name, password) == -1) {
                            Snackbar snackbar = Snackbar.make(view, "Username sudah digunakan.", Snackbar.LENGTH_SHORT);
                            snackbar.setBackgroundTint(ContextCompat.getColor(view.getContext(), R.color.snackbarred));
                            snackbar.show();
                            isClicked = false;
                        } else {
                            Toast.makeText(view.getContext(), "Akun \"" + username + "\" berhasil dibuat", Toast.LENGTH_SHORT).show();
                            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.fcv_main, new LoginFragment()).commit();
                            isClicked = false;
                        }
                    } catch (Exception e) {
                        Snackbar snackbar = Snackbar.make(view, "Gagal menambahkan akun.", Snackbar.LENGTH_SHORT);
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
        tietName = view.findViewById(R.id.tiet_nama);
        tietUsername = view.findViewById(R.id.tiet_username);
        tietPassword = view.findViewById(R.id.tiet_password);
        tietConfirm = view.findViewById(R.id.tiet_confirm);
        btnRegister = view.findViewById(R.id.btn_register);
        tvLogin = view.findViewById(R.id.tv_loginlink_register);
    }
}