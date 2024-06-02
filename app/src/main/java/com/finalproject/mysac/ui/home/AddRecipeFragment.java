package com.finalproject.mysac.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.finalproject.mysac.R;
import com.finalproject.mysac.ui.home.adapter.BahanAdapter;
import com.finalproject.mysac.ui.home.model.Bahan;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Objects;

public class AddRecipeFragment extends Fragment {

    ImageView ivGambar;
    TextInputEditText tietNama;
    TextInputEditText tietAsal;
    TextInputEditText tietInstruksi;
    Spinner spnKategori;
    TextView tvTambahBahan;
    RecyclerView rvBahan;
    Button btnBuat;
    BahanAdapter bahanAdapter;

    ArrayList<Bahan> listBahan;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_add_recipe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindViews(view);

        ivGambar.setOnClickListener(view1 -> {
            openFileChooser();
        });

        rvBahan.setHasFixedSize(true);
        rvBahan.setLayoutManager(new LinearLayoutManager(view.getContext()));
        rvBahan.setAdapter(bahanAdapter);
        tvTambahBahan.setOnClickListener(view1 -> {

            if (bahanAdapter.areAllFieldsFilled()) {
                if (listBahan.size() < 20) {
                    Log.d("ASDKLASD", "onViewCreated: monggo monggo");
                    listBahan.add(new Bahan("", ""));
                    bahanAdapter.notifyItemInserted(listBahan.size() - 1);
                    rvBahan.post(() -> rvBahan.scrollToPosition(bahanAdapter.getItemCount() - 1));
                } else {
                    Toast.makeText(view.getContext(), "Maksimal 20 bahan", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(view.getContext(), "Isi semua bahan sebelum menambah yang baru", Toast.LENGTH_SHORT).show();
            }
        });

    }

    void bindViews(View view) {

        listBahan = new ArrayList<>();
        listBahan.add(new Bahan("", ""));

        ivGambar = view.findViewById(R.id.iv_nama);
        tietNama = view.findViewById(R.id.tiet_nama);
        tietAsal = view.findViewById(R.id.tiet_asal);
        tietInstruksi = view.findViewById(R.id.tiet_instruksi);
        spnKategori = view.findViewById(R.id.spn_kategori);
        tvTambahBahan = view.findViewById(R.id.tv_tambah_bahan);
        btnBuat = view.findViewById(R.id.btn_post);
        rvBahan = view.findViewById(R.id.rv_bahan);
        bahanAdapter = new BahanAdapter(listBahan, rvBahan);
    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), 1);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == Activity.RESULT_OK && data != null && data.getData() != null) {
            Uri imageUri = data.getData();
            Glide.with(this).load(imageUri).into(ivGambar);
        }
    }

}