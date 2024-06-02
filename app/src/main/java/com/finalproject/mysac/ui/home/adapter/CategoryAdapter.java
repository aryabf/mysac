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
import com.finalproject.mysac.data.model.Kategori;
import com.finalproject.mysac.ui.recipes.RecipeDetailActivity;
import com.finalproject.mysac.ui.recipes.RecipeListActivity;

import java.util.ArrayList;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private ArrayList<Kategori> listKategori;
    private Context context;

    public CategoryAdapter(ArrayList<Kategori> listKategori, Context context) {
        this.listKategori = listKategori;
        this.context = context;
    }

    @NonNull
    @Override
    public CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CategoryViewHolder holder, int position) {
        Kategori kategori = listKategori.get(position);

        if (kategori.getGambar() != null) {
            Glide.with(context).load(kategori.getGambar()).into(holder.ivCategory);
        }

        holder.tvCategory.setText(kategori.getNama());

        holder.itemView.setOnClickListener(view -> {
            Intent intent = new Intent(context, RecipeListActivity.class);
            intent.putExtra("category", kategori.getNama());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listKategori.size();
    }

    public static class CategoryViewHolder extends RecyclerView.ViewHolder {

        ImageView ivCategory;
        TextView tvCategory;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            ivCategory = itemView.findViewById(R.id.iv_category);
            tvCategory = itemView.findViewById(R.id.tv_title_category);
        }
    }

    public static class VerticalSpaceItemDecoration extends RecyclerView.ItemDecoration {
        private final int verticalSpaceHeight;

        public VerticalSpaceItemDecoration(int verticalSpaceHeight) {
            this.verticalSpaceHeight = verticalSpaceHeight;
        }

        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            int position = parent.getChildAdapterPosition(view);
            if (position == 0) {
                outRect.top = verticalSpaceHeight;
            }
            outRect.bottom = verticalSpaceHeight;
        }
    }
}