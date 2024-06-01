package com.finalproject.mysac.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.finalproject.mysac.R;
import com.finalproject.mysac.data.local.preferences.SharedPreferencesManager;
import com.finalproject.mysac.ui.home.HomeActivity;
import com.finalproject.mysac.ui.recipes.RecipeDetailActivity;
import com.finalproject.mysac.ui.auth.MainActivity;

public class SplashScreenActivity extends AppCompatActivity {

    private static  final long ANIMATION_DELAY = 50; //delay per huruf dalam milidetik
    private SharedPreferencesManager sharedPreferencesManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        sharedPreferencesManager = new SharedPreferencesManager(getBaseContext());

        boolean isLoggedIn = sharedPreferencesManager.isLoggedIn();

        ImageView logo = findViewById(R.id.iv_logo);
        Animation fadeInAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        logo.startAnimation(fadeInAnimation);

        TextView textView = findViewById(R.id.tv_mysac);
        String text = "Resep Terbaik dari Hati ke Hati";

        animateText(textView, text);

        //memulai halaman utama setelah animasi selesai
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isLoggedIn) {
                    startActivity(new Intent(SplashScreenActivity.this, HomeActivity.class));
                } else {
                    startActivity(new Intent(SplashScreenActivity.this, MainActivity.class));
                }
                finish();
            }
        }, text.length() * ANIMATION_DELAY + 1000); //delay berdasarkan panjang teks ditambah waktu tambahan
    }

    private void animateText(final TextView textView, final  String text){
        final  Handler handler = new Handler();
        for (int i = 0; i < text.length(); i++){
            final int index = i;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    textView.setText(text.substring(0, index + 1));
                }
            }, i * ANIMATION_DELAY);
        }
    }

}