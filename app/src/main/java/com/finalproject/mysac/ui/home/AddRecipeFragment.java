package com.finalproject.mysac.ui.home;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.finalproject.mysac.R;
import com.finalproject.mysac.data.local.db.DbHelper;
import com.finalproject.mysac.data.local.preferences.SharedPreferencesManager;
import com.finalproject.mysac.data.model.Resep;
import com.finalproject.mysac.data.model.User;
import com.finalproject.mysac.ui.home.adapter.BahanAdapter;
import com.finalproject.mysac.ui.home.model.Bahan;
import com.finalproject.mysac.utils.PhotoUtils;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.UUID;

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
    DbHelper dbHelper;
    byte[] gambarResep;
    SharedPreferencesManager sharedPreferencesManager;
    User loggedUser;
    String kategoriResep;
    boolean isClicked = false;
    boolean isPhotoClicked = false;

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

        sharedPreferencesManager = new SharedPreferencesManager(view.getContext());
        String loggedUserId = sharedPreferencesManager.getLoggedUsername();

        dbHelper = new DbHelper(view.getContext());
        loggedUser = dbHelper.getUserByUsername(loggedUserId);

        spnKategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                kategoriResep = adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        ivGambar.setOnClickListener(view1 -> {
            if (!isPhotoClicked) {
                isPhotoClicked = true;
                openFileChooser();
            }
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

        btnBuat.setOnClickListener(view1 -> {

            if (!isClicked) {
                isClicked = true;

                String namaResep = tietNama.getText().toString();
                String asalResep = tietAsal.getText().toString();
                String instruksiResep = tietInstruksi.getText().toString();

                if (namaResep == "" || asalResep == "" || instruksiResep == "" || !bahanAdapter.areAllFieldsFilled()) {
                    Toast.makeText(view.getContext(), namaResep + asalResep + instruksiResep + bahanAdapter.areAllFieldsFilled(), Toast.LENGTH_SHORT).show();
                    Snackbar snackbar = Snackbar.make(view, "Mohon isi seluruh field.", Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(ContextCompat.getColor(view.getContext(), R.color.snackbarred));
                    snackbar.show();
                    isClicked = false;
                } else {
                    if (dbHelper.createRecipe(
                            UUID.randomUUID().toString(),
                            namaResep,
                            kategoriResep,
                            instruksiResep,
                            loggedUser.getUsername(),
                            loggedUser.getName(),
                            asalResep,
                            bahanAdapter.getDataBahan(),
                            bahanAdapter.getDataTakaran(),
                            gambarResep,
                            loggedUser.getPhoto()
                    ) != -1) {
                        if (dbHelper.updateUser(
                                loggedUser.getUsername(),
                                loggedUser.getName(),
                                loggedUser.getPassword(),
                                loggedUser.getBio(),
                                loggedUser.getLinkFb(),
                                loggedUser.getLinkIg(),
                                loggedUser.getLinkYt(),
                                loggedUser.getPhoto(),
                                loggedUser.getJumlahResep() + 1
                        ) != -1) {
                            Toast.makeText(view.getContext(), "Resep berhasil diunggah", Toast.LENGTH_SHORT).show();
                            Glide.with(view.getContext()).load(R.drawable.baseline_add_photo_alternate_24).into(ivGambar);
                            gambarResep = null;
                            tietNama.setText("");
                            spnKategori.setSelection(0);
                            tietAsal.setText("");
                            tietInstruksi.setText("");
                            listBahan.clear();
                            bahanAdapter.notifyDataSetChanged();
                            rvBahan.scrollToPosition(0);
                            isClicked = false;
                        } else {
                            Snackbar snackbar = Snackbar.make(view, "Gagal mengunggah resep.", Snackbar.LENGTH_SHORT);
                            snackbar.setBackgroundTint(ContextCompat.getColor(view.getContext(), R.color.snackbarred));
                            snackbar.show();
                            isClicked = false;
                        }
                    } else {
                        Snackbar snackbar = Snackbar.make(view, "Gagal mengunggah resep.", Snackbar.LENGTH_SHORT);
                        snackbar.setBackgroundTint(ContextCompat.getColor(view.getContext(), R.color.snackbarred));
                        snackbar.show();
                        isClicked = false;
                    }

                }

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
        dbHelper = new DbHelper(view.getContext());
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
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                gambarResep = PhotoUtils.bitmapToBytes(bitmap);
                Glide.with(this).load(imageUri).into(ivGambar);
                isPhotoClicked = false;
            } catch (IOException e) {
                Snackbar snackbar = Snackbar.make(ivGambar.getRootView(), "Gagal mengambil foto.", Snackbar.LENGTH_SHORT);
                snackbar.setBackgroundTint(ContextCompat.getColor(ivGambar.getContext(), R.color.snackbarred));
                snackbar.show();
                isPhotoClicked = false;
            }
        }
        isPhotoClicked = false;
    }

}