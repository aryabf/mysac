package com.finalproject.mysac.ui.recipes.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.finalproject.mysac.R;
import com.finalproject.mysac.ui.home.adapter.HomeMealAdapter;

import java.util.ArrayList;

public class RecipeDetailAdapter extends RecyclerView.Adapter<RecipeDetailAdapter.RecipeDetailViewHolder> {

    public ArrayList<String> listBahan;
    public ArrayList<String> listUkuran;

    @NonNull
    @Override
    public RecipeDetailViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.ingredient_item, parent, false);
        return new RecipeDetailAdapter.RecipeDetailViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeDetailViewHolder holder, int position) {
        int positionPlusOne = position + 1;
        tvNoBahan.setText(positionPlusOne + ".");
        tvBahan.setText(listBahan.get(position) + " " + listUkuran.get(position));
    }

    public RecipeDetailAdapter(ArrayList<String> listBahan, ArrayList<String> listUkuran) {
        this.listBahan = listBahan;
        this.listUkuran = listUkuran;
    }

    @Override
    public int getItemCount() {
        int size;
        if (listBahan.size() > listUkuran.size()) {
            size = listUkuran.size();
        } else {
            size = listBahan.size();
        }
        return size;
    }

    public class RecipeDetailViewHolder extends RecyclerView.ViewHolder {

        public RecipeDetailViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBahan = itemView.findViewById(R.id.tv_ingredient);
            tvNoBahan = itemView.findViewById(R.id.tv_no_ingredient);
        }
    }

    TextView tvBahan;
    TextView tvNoBahan;

}
