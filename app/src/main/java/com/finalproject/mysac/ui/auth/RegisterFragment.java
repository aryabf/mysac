package com.finalproject.mysac.ui.auth;

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
import com.google.android.material.textfield.TextInputEditText;

public class RegisterFragment extends Fragment {

    TextInputEditText tietName;
    TextInputEditText tietUsername;
    TextInputEditText tietPassword;
    TextInputEditText tietConfirm;
    Button btnRegister;
    TextView tvLogin;

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

    }

    void bindViews(View view) {
        tietName = view.findViewById(R.id.tiet_nama);
        tietUsername = view.findViewById(R.id.tiet_username);
        tietPassword = view.findViewById(R.id.tiet_password);
        tietConfirm = view.findViewById(R.id.tiet_confirm);
        btnRegister = view.findViewById(R.id.btn_register);
        tvLogin = view.findViewById(R.id.tv_loginlink_register);
    }
}