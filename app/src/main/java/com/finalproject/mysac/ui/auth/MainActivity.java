package com.finalproject.mysac.ui.auth;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;

import com.finalproject.mysac.R;
import com.finalproject.mysac.ui.auth.LoginFragment;
import com.finalproject.mysac.ui.home.AccountFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().replace(R.id.fcv_main, new LoginFragment()).commit();

        // Memuat AccountFragment ke dalam MainActivity
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.fcv_main, new AccountFragment())
                .commit();
    }
}