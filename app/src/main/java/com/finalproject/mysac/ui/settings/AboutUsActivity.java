package com.finalproject.mysac.ui.settings;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.finalproject.mysac.R;

public class AboutUsActivity extends AppCompatActivity {

    ImageView ivBack;
    TextView tvApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_about_us);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bindViews();

        getWindow().setStatusBarColor(getResources().getColor(R.color.pumpkin));

        tvApi.setOnClickListener(view -> {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.themealdb.com/api.php")));
        });

        ivBack.setOnClickListener(view -> {
            finish();
        });
    }

    void bindViews() {
        ivBack = findViewById(R.id.iv_back);
        tvApi = findViewById(R.id.textViewApiSource);
    }
}