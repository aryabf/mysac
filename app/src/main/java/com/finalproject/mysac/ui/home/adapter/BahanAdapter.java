package com.finalproject.mysac.ui.home.adapter;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.finalproject.mysac.R;
import com.finalproject.mysac.ui.home.model.Bahan;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class BahanAdapter extends RecyclerView.Adapter<BahanAdapter.BahanViewHolder> {

    public ArrayList<Bahan> listBahan;
    RecyclerView recyclerView;

    public BahanAdapter(ArrayList<Bahan> listBahan, RecyclerView recyclerView) {
        this.listBahan = listBahan;
        this.recyclerView = recyclerView;
    }

    @NonNull
    @Override
    public BahanViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_bahan, parent, false);
        return new BahanAdapter.BahanViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BahanViewHolder holder, int position) {
        holder.tietBahan.setText(listBahan.get(position).getNamaBahan());
        holder.tietTakaran.setText(listBahan.get(position).getTakaran());
        if (listBahan.size() <= 1) {
            holder.ivDelete.setVisibility(View.GONE);
        } else {
            holder.ivDelete.setOnClickListener(v -> {
                listBahan.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, listBahan.size());
            });
        }
    }

    public boolean areAllFieldsFilled() {
        for (int i = 0; i < getItemCount(); i++) {
            RecyclerView.ViewHolder holder = recyclerView.findViewHolderForAdapterPosition(i);
            if (holder != null) {
                BahanViewHolder bahanViewHolder = (BahanViewHolder) holder;
                if (bahanViewHolder.tietBahan.getText().toString().trim().isEmpty()) {
                    return false;
                }
                if (bahanViewHolder.tietTakaran.getText().toString().trim().isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public int getItemCount() {
        return listBahan.size();
    }

    public class BahanViewHolder extends RecyclerView.ViewHolder {

        TextInputEditText tietBahan;
        TextInputEditText tietTakaran;
        ImageView ivDelete;

        public BahanViewHolder(@NonNull View itemView) {
            super(itemView);
            tietBahan = itemView.findViewById(R.id.tiet_bahan);
            tietTakaran = itemView.findViewById(R.id.tiet_takaran);
            ivDelete = itemView.findViewById(R.id.iv_delete);
        }
    }

}
