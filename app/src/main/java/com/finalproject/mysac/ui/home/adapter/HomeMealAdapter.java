package com.finalproject.mysac.ui.home.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.finalproject.mysac.R;
import com.finalproject.mysac.data.model.Kategori;
import com.finalproject.mysac.data.model.Resep;
import com.finalproject.mysac.ui.recipes.RecipeDetailActivity;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class HomeMealAdapter extends RecyclerView.Adapter<HomeMealAdapter.HomeMealViewHolder> {

    public ArrayList<Resep> listMeal;
    Context context;

    public static class HorizontalSpaceItemDecoration extends RecyclerView.ItemDecoration {

        private final int horizontalSpaceWidth;

        public HorizontalSpaceItemDecoration(int horizontalSpaceWidth) {
            this.horizontalSpaceWidth = horizontalSpaceWidth;
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            if (position == 0) {
                outRect.left = horizontalSpaceWidth; // Margin kiri untuk item pertama
            }
            outRect.right = horizontalSpaceWidth; // Margin kanan untuk semua item
        }
    }

    public HomeMealAdapter(ArrayList<Resep> listMeal, Context context) {
        this.listMeal = listMeal;
        this.context = context;
    }

    @NonNull
    @Override
    public HomeMealViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_home_item, parent, false);
        return new HomeMealAdapter.HomeMealViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeMealViewHolder holder, int position) {
        if (listMeal.get(position).getLinkGambar() != null && !listMeal.get(position).getLinkGambar().isEmpty()) {
            Glide.with(holder.itemView.getContext()).load(listMeal.get(position).getLinkGambar()).into(sivMealImg);
        } else {
            Glide.with(holder.itemView.getContext()).load(listMeal.get(position).getGambar()).into(sivMealImg);
        }
        tvMealName.setText(listMeal.get(position).getNama());
        if (listMeal.get(position).getPembuat() != null && !listMeal.get(position).getPembuat().isEmpty()) {
            tvMealCreator.setText(listMeal.get(position).getNamaPembuat());
        } else {
            tvMealCreator.setText("The Meal DB");
        }
        holder.itemView.getRootView().setOnClickListener(view -> {
            Intent intent = new Intent(context, RecipeDetailActivity.class);
            intent.putExtra("mealId", listMeal.get(position).getId());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listMeal.size();
    }

    public class HomeMealViewHolder extends RecyclerView.ViewHolder {

        public HomeMealViewHolder(@NonNull View itemView) {
            super(itemView);
            sivMealImg = itemView.findViewById(R.id.siv_meal_image);
            tvMealName = itemView.findViewById(R.id.tv_meal_name);
            tvMealCreator = itemView.findViewById(R.id.tv_meal_username);
        }

    }

    ShapeableImageView sivMealImg;
    TextView tvMealName;
    TextView tvMealCreator;

}
