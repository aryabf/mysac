package com.finalproject.mysac.ui.home.adapter;

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
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;

public class HomeCategoryAdapter extends RecyclerView.Adapter<HomeCategoryAdapter.HomeCategoryViewHolder> {

    public ArrayList<Kategori> listKategori;

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


    public HomeCategoryAdapter(ArrayList<Kategori> listKategori) {
        this.listKategori = listKategori;
    }

    @NonNull
    @Override
    public HomeCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_home_category_item, parent, false);
        return new HomeCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HomeCategoryViewHolder holder, int position) {
        Glide.with(holder.itemView.getRootView().getContext()).load(listKategori.get(position).getGambar()).into(sivCategoryImg);
        tvCategoryName.setText(listKategori.get(position).getNama());
    }

    @Override
    public int getItemCount() {
        return listKategori.size();
    }

    public class HomeCategoryViewHolder extends RecyclerView.ViewHolder {

        public HomeCategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            sivCategoryImg = itemView.findViewById(R.id.siv_category_img);
            tvCategoryName = itemView.findViewById(R.id.tv_category_name);
        }

    }

    ShapeableImageView sivCategoryImg;
    TextView tvCategoryName;

}
