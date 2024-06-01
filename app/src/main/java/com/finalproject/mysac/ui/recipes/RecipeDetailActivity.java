package com.finalproject.mysac.ui.recipes;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.finalproject.mysac.R;

public class RecipeDetailActivity extends AppCompatActivity {

    ImageView ivIngredientArrow;
    ImageView ivInstructionArrow;
    boolean isIngredientsShown = false;
    boolean isInstructionsShown = false;
    RecyclerView rvIngredients;
    TextView tvInstructios;

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

        bindViews();

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
                tvInstructios.setVisibility(View.GONE);
                isInstructionsShown = false;
            }
            // Not Visible
            else {
                ivInstructionArrow.setImageResource(R.drawable.baseline_arrow_drop_up_24);
                tvInstructios.setVisibility(View.VISIBLE);
                isInstructionsShown = true;
            }
        });

    }

    void bindViews() {
        ivIngredientArrow = findViewById(R.id.iv_ingredients_arrow);
        ivInstructionArrow = findViewById(R.id.iv_instruksi_arrow);
        rvIngredients = findViewById(R.id.rv_ingredients);
        tvInstructios = findViewById(R.id.tv_instruction);
    }
}