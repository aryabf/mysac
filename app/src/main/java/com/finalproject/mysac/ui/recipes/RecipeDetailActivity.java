package com.finalproject.mysac.ui.recipes;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.finalproject.mysac.R;
import com.finalproject.mysac.data.model.Resep;
import com.finalproject.mysac.data.model.response.ResponseDaftarResep;
import com.finalproject.mysac.data.model.response.ResponseResep;
import com.finalproject.mysac.data.retrofit.APIServices;
import com.finalproject.mysac.data.retrofit.RetrofitBuilder;
import com.finalproject.mysac.ui.recipes.adapter.RecipeDetailAdapter;
import com.finalproject.mysac.utils.ResponseResepConverter;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeDetailActivity extends AppCompatActivity {

    String DEFAULT_ID = "52794";
    ImageView ivIngredientArrow;
    ImageView ivInstructionArrow;
    boolean isIngredientsShown = false;
    boolean isInstructionsShown = false;
    RecyclerView rvIngredients;
    TextView tvInstructions;
    ConstraintLayout clDetail;
    ConstraintLayout clHeaders;
    ProgressBar pbDetail;
    ShapeableImageView sivMeal;
    TextView tvName;
    TextView tvCategory;
    TextView tvArea;
    TextView tvUsername;
    ImageView ivBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recipe_detail);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        String mealId;

        if (getIntent().getStringExtra("mealId") != null) {
            mealId = getIntent().getStringExtra("mealId");
        } else {
            mealId = DEFAULT_ID;
        }

        bindViews();
        clDetail.setVisibility(View.GONE);
        clHeaders.setVisibility(View.GONE);
        APIServices client = RetrofitBuilder.builder(this).create(APIServices.class);
        client.getMealById(mealId).enqueue(new Callback<ResponseDaftarResep>() {
            @Override
            public void onResponse(Call<ResponseDaftarResep> call, Response<ResponseDaftarResep> response) {
                pbDetail.setVisibility(View.GONE);
                clDetail.setVisibility(View.VISIBLE);
                clHeaders.setVisibility(View.VISIBLE);
                if (response.isSuccessful()) {

                    ResponseResepConverter converter = new ResponseResepConverter();

                    ResponseResep responseResep = response.body().getDaftarResep().get(0);
                    Resep resepView = converter.responseToResep(responseResep);

                    if (resepView.getLinkGambar() != null && !resepView.getLinkGambar().isEmpty()) {
                        Glide.with(RecipeDetailActivity.this).load(resepView.getLinkGambar()).into(sivMeal);
                    } else {
                        Glide.with(RecipeDetailActivity.this).load(resepView.getGambar()).into(sivMeal);
                    }
                    rvIngredients.setHasFixedSize(true);
                    rvIngredients.setLayoutManager(new LinearLayoutManager(RecipeDetailActivity.this));

                    RecipeDetailAdapter adapter = new RecipeDetailAdapter(resepView.getBahanBahan(), resepView.getUkuranUkuran());
                    rvIngredients.setAdapter(adapter);

                    tvName.setText(resepView.getNama());
                    tvCategory.setText(resepView.getKategori());
                    tvArea.setText(resepView.getArea());
                    tvUsername.setText(resepView.getPembuat());
                    tvInstructions.setText(resepView.getInstruksi());

                } else {
                    Toast.makeText(RecipeDetailActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseDaftarResep> call, Throwable t) {
                pbDetail.setVisibility(View.GONE);
                clDetail.setVisibility(View.VISIBLE);
                clHeaders.setVisibility(View.VISIBLE);
                Toast.makeText(RecipeDetailActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        ivBack.setOnClickListener(view -> {
            finish();
        });

        ivIngredientArrow.setOnClickListener(view -> {
            // Visible
            if (isIngredientsShown) {
                ivIngredientArrow.setImageResource(R.drawable.baseline_arrow_drop_down_24);
                rvIngredients.setVisibility(View.GONE);
                isIngredientsShown = false;
            }
            // Not Visible
            else {
                ivIngredientArrow.setImageResource(R.drawable.baseline_arrow_drop_up_24);
                rvIngredients.setVisibility(View.VISIBLE);
                isIngredientsShown = true;
            }
        });

        ivInstructionArrow.setOnClickListener(view -> {
            // Visible
            if (isInstructionsShown) {
                ivInstructionArrow.setImageResource(R.drawable.baseline_arrow_drop_down_24);
                tvInstructions.setVisibility(View.GONE);
                isInstructionsShown = false;
            }
            // Not Visible
            else {
                ivInstructionArrow.setImageResource(R.drawable.baseline_arrow_drop_up_24);
                tvInstructions.setVisibility(View.VISIBLE);
                isInstructionsShown = true;
            }
        });

    }

    void bindViews() {
        ivIngredientArrow = findViewById(R.id.iv_ingredients_arrow);
        ivInstructionArrow = findViewById(R.id.iv_instruksi_arrow);
        rvIngredients = findViewById(R.id.rv_ingredients);
        tvInstructions = findViewById(R.id.tv_instruction);
        clDetail = findViewById(R.id.cl_main);
        clHeaders = findViewById(R.id.cl_headers);
        pbDetail = findViewById(R.id.pb_detail);
        sivMeal = findViewById(R.id.siv_meal);
        tvName = findViewById(R.id.tv_name);
        tvCategory = findViewById(R.id.tv_category);
        tvArea = findViewById(R.id.tv_area);
        tvUsername = findViewById(R.id.tv_username);
        ivBack = findViewById(R.id.iv_back);
    }

}