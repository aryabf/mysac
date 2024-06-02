package com.finalproject.mysac.ui.settings;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import com.finalproject.mysac.R;
import com.finalproject.mysac.data.local.db.DbHelper;
import com.finalproject.mysac.data.local.preferences.SharedPreferencesManager;
import com.finalproject.mysac.data.model.User;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

public class ChangePasswordActivity extends AppCompatActivity {

    ImageView ivBack;
    DbHelper dbHelper;
    private SharedPreferencesManager sharedPreferencesManager;
    public User loggedUser;
    TextInputEditText tietNewPassword;
    TextInputEditText tietConfirmPassword;
    Button btnChange;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_change_password);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getWindow().setStatusBarColor(getResources().getColor(R.color.pumpkin));

        bindViews();

        sharedPreferencesManager = new SharedPreferencesManager(getBaseContext());
        String loggedUserId = sharedPreferencesManager.getLoggedUsername();

        dbHelper = new DbHelper(this);
        loggedUser = dbHelper.getUserByUsername(loggedUserId);

        btnChange.setOnClickListener(view -> {
            if (tietNewPassword.getText().toString().equals(tietConfirmPassword.getText().toString())) {
                dbHelper.updateUser(
                        loggedUser.getUsername(),
                        loggedUser.getName(),
                        tietNewPassword.getText().toString(),
                        loggedUser.getBio(),
                        loggedUser.getLinkFb(),
                        loggedUser.getLinkIg(),
                        loggedUser.getLinkYt(),
                        loggedUser.getPhoto(),
                        loggedUser.getJumlahResep()
                );
                Toast.makeText(ChangePasswordActivity.this, "Password berhasil diganti.", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Snackbar snackbar = Snackbar.make(view, "Password tidak sesuai.", Snackbar.LENGTH_SHORT);
                snackbar.setBackgroundTint(ContextCompat.getColor(view.getContext(), R.color.snackbarred));
                snackbar.show();
            }
        });

        ivBack.setOnClickListener(view -> {
            finish();
        });
    }

    void bindViews() {
        tietNewPassword = findViewById(R.id.tiet_password);
        tietConfirmPassword = findViewById(R.id.tiet_confirm);
        ivBack = findViewById(R.id.iv_back);
        btnChange = findViewById(R.id.buttonChangePassword);
    }
}