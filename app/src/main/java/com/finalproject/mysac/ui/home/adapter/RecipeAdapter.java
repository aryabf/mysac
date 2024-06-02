package com.finalproject.mysac.ui.home.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.finalproject.mysac.R;
import com.finalproject.mysac.data.model.Resep;
import com.finalproject.mysac.ui.recipes.RecipeDetailActivity;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    public ArrayList<Resep> listMeal;
    Context context;

    ImageView ivRecipe;
    TextView tvRecipe;
    TextView tvCreator;

    public static class GridSpacingItemDecoration extends RecyclerView.ItemDecoration {

        private final int spanCount;
        private final int spacing;
        private final boolean includeEdge;

        public GridSpacingItemDecoration(int spanCount, int spacing, boolean includeEdge) {
            this.spanCount = spanCount;
            this.spacing = spacing;
            this.includeEdge = includeEdge;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view); // item position
            int column = position % spanCount; // item column

            if (includeEdge) {
                outRect.left = spacing - column * spacing / spanCount;
                outRect.right = (column + 1) * spacing / spanCount;
                if (position < spanCount) { // top edge
                    outRect.top = spacing;
                }
                outRect.bottom = spacing; // item bottom
            } else {
                outRect.left = column * spacing / spanCount;
                outRect.right = spacing - (column + 1) * spacing / spanCount;
                if (position >= spanCount) {
                    outRect.top = spacing; // item top
                }
            }
        }
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        return new RecipeAdapter.RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        if (listMeal.get(position).getLinkGambar() != null && !listMeal.get(position).getLinkGambar().isEmpty()) {
            Glide.with(holder.itemView.getContext()).load(listMeal.get(position).getLinkGambar()).into(ivRecipe);
        } else {
            Glide.with(holder.itemView.getContext()).load(listMeal.get(position).getGambar()).into(ivRecipe);
        }
        tvRecipe.setText(listMeal.get(position).getNama());
        if (listMeal.get(position).getPembuat() != null && !listMeal.get(position).getPembuat().isEmpty()) {
            tvCreator.setText(listMeal.get(position).getPembuat());
        } else {
            tvCreator.setText("The Meal DB");
        }
        holder.itemView.getRootView().setOnClickListener(view -> {
            Intent intent = new Intent(context, RecipeDetailActivity.class);
            intent.putExtra("mealId", listMeal.get(position).getId());
            if (listMeal.get(position).getId().length() > 10) {
                intent.putExtra("type", "local");
            }
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listMeal.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder {

        public RecipeViewHolder(@NonNull View itemView) {
            super(itemView);
            ivRecipe = itemView.findViewById(R.id.iv_recipe);
            tvRecipe = itemView.findViewById(R.id.tv_recipe_name);
            tvCreator = itemView.findViewById(R.id.tv_recipe_creator);
        }
    }

    public RecipeAdapter(ArrayList<Resep> listMeal, Context context) {
        this.listMeal = listMeal;
        this.context = context;
    }
}
