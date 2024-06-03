package com.finalproject.mysac.ui.recipes;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.UUID;

public class RecipeEditActivity extends AppCompatActivity {

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
    Button btnDelete;
    boolean isClicked = false;
    boolean isClickedDel = false;
    boolean isClickedConfirm = false;
    boolean isPhotoClicked = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recipe_edit);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getWindow().setStatusBarColor(getResources().getColor(R.color.pumpkin));

        bindViews();
        
        String mealId = getIntent().getStringExtra("recipeId");

        sharedPreferencesManager = new SharedPreferencesManager(getBaseContext());
        String loggedUserId = sharedPreferencesManager.getLoggedUsername();

        dbHelper = new DbHelper(getBaseContext());
        loggedUser = dbHelper.getUserByUsername(loggedUserId);

        Resep resep = dbHelper.getRecipeById(mealId);
        initializeView(resep);

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
        rvBahan.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        rvBahan.setAdapter(bahanAdapter);
        tvTambahBahan.setOnClickListener(view1 -> {

            if (bahanAdapter.areAllFieldsFilled()) {
                if (listBahan.size() < 20) {
                    Log.d("ASDKLASD", "onViewCreated: monggo monggo");
                    listBahan.add(new Bahan("", ""));
                    bahanAdapter.notifyItemInserted(listBahan.size() - 1);
                    rvBahan.post(() -> rvBahan.scrollToPosition(bahanAdapter.getItemCount() - 1));
                } else {
                    Toast.makeText(getBaseContext(), "Maksimal 20 bahan", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getBaseContext(), "Isi semua bahan sebelum menambah yang baru", Toast.LENGTH_SHORT).show();
            }
        });

        btnBuat.setOnClickListener(view1 -> {

            if (!isClicked) {
                isClicked = true;

                String namaResep = tietNama.getText().toString();
                String asalResep = tietAsal.getText().toString();
                String instruksiResep = tietInstruksi.getText().toString();

                if (namaResep == "" || asalResep == "" || instruksiResep == "" || !bahanAdapter.areAllFieldsFilled()) {
                    Toast.makeText(getBaseContext(), namaResep + asalResep + instruksiResep + bahanAdapter.areAllFieldsFilled(), Toast.LENGTH_SHORT).show();
                    Snackbar snackbar = Snackbar.make(btnBuat, "Mohon isi seluruh field.", Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(ContextCompat.getColor(getBaseContext(), R.color.snackbarred));
                    snackbar.show();
                    isClicked = false;
                } else {
                    if (dbHelper.updateRecipe(
                            resep.getId(),
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
                        Toast.makeText(RecipeEditActivity.this, "Resep berhasil diperbaharui", Toast.LENGTH_SHORT).show();
//                    Glide.with(RecipeEditActivity.this).load(R.drawable.baseline_add_photo_alternate_24).into(ivGambar);
//                    gambarResep = null;
//                    tietNama.setText("");
//                    spnKategori.setSelection(0);
//                    tietAsal.setText("");
//                    tietInstruksi.setText("");
//                    listBahan.clear();
//                    bahanAdapter.notifyDataSetChanged();
//                    rvBahan.scrollToPosition(0);
                        finish();
                        isClicked = false;
                    } else {
                        Snackbar snackbar = Snackbar.make(btnBuat, "Gagal mengunggah resep.", Snackbar.LENGTH_SHORT);
                        snackbar.setBackgroundTint(ContextCompat.getColor(RecipeEditActivity.this, R.color.snackbarred));
                        snackbar.show();
                        isClicked = false;
                    }

                }
            }

        });

        btnDelete.setOnClickListener(view -> {

            if (!isClickedDel) {
                isClickedDel = true;

                Dialog dialog = new Dialog(RecipeEditActivity.this, R.style.CustomDialog);
                dialog.setContentView(R.layout.custom_alert_dialog_background);
                int marginInPix = (int) (24 * RecipeEditActivity.this.getResources().getDisplayMetrics().density);
                Window window = dialog.getWindow();

                window.setLayout(RecipeEditActivity.this.getResources().getDisplayMetrics().widthPixels - 2 * marginInPix, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.findViewById(R.id.negative).setOnClickListener(view1 -> {
                    dialog.cancel();
                    isClickedDel = false;
                });
                dialog.findViewById(R.id.positive).setOnClickListener(view1 -> {
                    if (!isClickedConfirm) {
                        isClickedConfirm = true;
                        if (dbHelper.deleteRecipe(resep.getId()) > 0) {
                            if ((dbHelper.updateUser(
                                    loggedUser.getUsername(),
                                    loggedUser.getName(),
                                    loggedUser.getPassword(),
                                    loggedUser.getBio(),
                                    loggedUser.getLinkFb(),
                                    loggedUser.getLinkIg(),
                                    loggedUser.getLinkYt(),
                                    loggedUser.getPhoto(),
                                    loggedUser.getJumlahResep() - 1
                            ) != -1)) {
                                Toast.makeText(RecipeEditActivity.this, "Resep " + resep.getNama() + " berhasil dihapus.", Toast.LENGTH_SHORT).show();
                                dialog.cancel();
//                    finish();
                                Intent intent = new Intent(RecipeEditActivity.this, RecipeListActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                                intent.putExtra("username", loggedUser.getUsername());
                                intent.putExtra("name", loggedUser.getName());
                                startActivity(intent);
                                finish();
                                isClickedDel = false;
                                isClickedConfirm = false;
                            }
                        } else {
                            Snackbar snackbar = Snackbar.make(view, "Gagal menghapus resep.", Snackbar.LENGTH_SHORT);
                            snackbar.setBackgroundTint(ContextCompat.getColor(view.getContext(), R.color.snackbarred));
                            snackbar.show();
                            dialog.cancel();
                            isClickedConfirm = false;
                            isClickedDel = false;
                        }
                    }
                });
                dialog.show();
            }
        });
    }

    void bindViews() {

        listBahan = new ArrayList<>();

        ivGambar = findViewById(R.id.iv_nama);
        tietNama = findViewById(R.id.tiet_nama);
        tietAsal = findViewById(R.id.tiet_asal);
        tietInstruksi = findViewById(R.id.tiet_instruksi);
        spnKategori = findViewById(R.id.spn_kategori);
        tvTambahBahan = findViewById(R.id.tv_tambah_bahan);
        btnBuat = findViewById(R.id.btn_post);
        btnDelete = findViewById(R.id.btn_delete);
        rvBahan = findViewById(R.id.rv_bahan);
        bahanAdapter = new BahanAdapter(listBahan, rvBahan);
        dbHelper = new DbHelper(getBaseContext());
    }

    void initializeView(Resep resep) {
        tietNama.setText(resep.getNama());
        tietAsal.setText(resep.getArea());
        tietInstruksi.setText(resep.getInstruksi());
        if (resep.getGambar() != null) Glide.with(RecipeEditActivity.this).load(resep.getGambar()).into(ivGambar);
        for (int i = 0; i < resep.getBahanBahan().size(); i++) {
            listBahan.add(new Bahan(resep.getBahanBahan().get(i), resep.getUkuranUkuran().get(i)));
            gambarResep = resep.getGambar();
        }
        int spinnerIndex = 0;
        switch (resep.getKategori()) {
            case "Beef": {
                spinnerIndex = 0;
                break;
            }
            case "Chicken": {
                spinnerIndex = 1;
                break;
            }
            case "Dessert": {
                spinnerIndex = 2;
                break;
            }
            case "Lamb": {
                spinnerIndex = 3;
                break;
            }
            case "Miscellaneous": {
                spinnerIndex = 4;
                break;
            }
            case "Pasta": {
                spinnerIndex = 5;
                break;
            }
            case "Pork": {
                spinnerIndex = 6;
                break;
            }
            case "Seafood": {
                spinnerIndex = 7;
                break;
            }
            case "Side": {
                spinnerIndex = 8;
                break;
            }
            case "Starter": {
                spinnerIndex = 9;
                break;
            }
            case "Vegan": {
                spinnerIndex = 10;
                break;
            }
            case "Vegetarian": {
                spinnerIndex = 11;
                break;
            }
            case "Breakfast": {
                spinnerIndex = 12;
                break;
            }
            case "Goat": {
                spinnerIndex = 13;
                break;
            }
        }
        spnKategori.setSelection(spinnerIndex);
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
                // Get file size in bytes
                InputStream inputStream = getContentResolver().openInputStream(imageUri);
                int fileSizeInBytes = inputStream.available();
                inputStream.close();

                // Convert to megabytes
                double fileSizeInMB = fileSizeInBytes / (1024.0 * 1024.0);

                // Load bitmap and check orientation
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                bitmap = rotateImageIfRequired(bitmap, imageUri);

                // Check if file size is more than 2MB
                if (fileSizeInMB > 2.0) {
                    // Compress the image
                    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
                    int quality = 90; // Initial quality
                    bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);

                    // Reduce quality until the image size is under 2MB
                    while (outputStream.toByteArray().length > 2 * 1024 * 1024) {
                        outputStream.reset();
                        quality -= 10;
                        bitmap.compress(Bitmap.CompressFormat.JPEG, quality, outputStream);
                    }

                    // Get the compressed image
                    gambarResep = outputStream.toByteArray();
                    Glide.with(this).load(gambarResep).into(ivGambar);
                } else {
                    // Use the original image
                    gambarResep = PhotoUtils.bitmapToBytes(bitmap);
                    Glide.with(this).load(imageUri).into(ivGambar);
                }
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

    private Bitmap rotateImageIfRequired(Bitmap img, Uri selectedImage) throws IOException {
        InputStream input = getContentResolver().openInputStream(selectedImage);
        ExifInterface ei;
        if (Build.VERSION.SDK_INT > 23)
            ei = new ExifInterface(input);
        else
            ei = new ExifInterface(selectedImage.getPath());

        int orientation = ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED);

        switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                return rotateImage(img, 90);
            case ExifInterface.ORIENTATION_ROTATE_180:
                return rotateImage(img, 180);
            case ExifInterface.ORIENTATION_ROTATE_270:
                return rotateImage(img, 270);
            default:
                return img;
        }
    }

    public static Bitmap rotateImage(Bitmap img, int degree) {
        Matrix matrix = new Matrix();
        matrix.postRotate(degree);
        return Bitmap.createBitmap(img, 0, 0, img.getWidth(), img.getHeight(), matrix, true);
    }
}