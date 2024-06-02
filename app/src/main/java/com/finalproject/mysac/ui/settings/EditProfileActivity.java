package com.finalproject.mysac.ui.settings;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.bumptech.glide.Glide;
import com.finalproject.mysac.R;
import com.finalproject.mysac.data.local.db.DbHelper;
import com.finalproject.mysac.data.local.preferences.SharedPreferencesManager;
import com.finalproject.mysac.data.model.User;
import com.finalproject.mysac.utils.PhotoUtils;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;

public class EditProfileActivity extends AppCompatActivity {


    DbHelper dbHelper;
    private SharedPreferencesManager sharedPreferencesManager;
    public User loggedUser;
    ImageView ivFoto;
    ImageView ivBack;
    EditText tietUsername;
    EditText tietNama;
    EditText tietBio;
    EditText tietLinkFb;
    EditText tietLinkIg;
    EditText tietLinkYt;
    Button btnSave;
    byte[] newPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit_profile);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bindViews();

        sharedPreferencesManager = new SharedPreferencesManager(getBaseContext());
        String loggedUserId = sharedPreferencesManager.getLoggedUsername();

        dbHelper = new DbHelper(this);
        loggedUser = dbHelper.getUserByUsername(loggedUserId);

        tietUsername.setText(loggedUser.getUsername());
        tietNama.setText(loggedUser.getName());
        tietBio.setText(loggedUser.getBio());
        tietLinkFb.setText(loggedUser.getLinkFb());
        tietLinkIg.setText(loggedUser.getLinkIg());
        tietLinkYt.setText(loggedUser.getLinkYt());

        Log.d("DbHelper", "Photo Length: " + (loggedUser.getPhoto() != null ? loggedUser.getPhoto().length : "null"));

        if (loggedUser.getPhoto() != null) {
            Glide.with(EditProfileActivity.this).load(loggedUser.getPhoto()).into(ivFoto);
        }

        btnSave.setOnClickListener(view -> {
            String usernameBaru = tietUsername.getText().toString();
            String nameBaru = tietNama.getText().toString();
            String bioBaru = tietBio.getText().toString();
            String linkFbBaru = tietLinkFb.getText().toString();
            String linkIgBaru = tietLinkIg.getText().toString();
            String linkYtBaru = tietLinkYt.getText().toString();

            if (usernameBaru.equals("") || nameBaru.equals("")) {
                Snackbar snackbar = Snackbar.make(view, "Mohon isi Username dan Nama.", Snackbar.LENGTH_SHORT);
                snackbar.setBackgroundTint(ContextCompat.getColor(view.getContext(), R.color.snackbarred));
                snackbar.show();
            } else {
                if (dbHelper.updateUser(usernameBaru, nameBaru, loggedUser.getPassword(), bioBaru, linkFbBaru, linkIgBaru, linkYtBaru, newPhoto) == -1) {
                    Snackbar snackbar = Snackbar.make(view, "Gagal memperbaharui akun.", Snackbar.LENGTH_SHORT);
                    snackbar.setBackgroundTint(ContextCompat.getColor(view.getContext(), R.color.snackbarred));
                    snackbar.show();
                } else {
                    finish();
                }
            }
        });

        ivFoto.setOnClickListener(view1 -> {
            openFileChooser();
        });

        ivBack.setOnClickListener(view -> {
            finish();
        });
    }

    void bindViews() {
        ivBack = findViewById(R.id.iv_back);
        ivFoto = findViewById(R.id.iv_foto);
        tietUsername = findViewById(R.id.tiet_username);
        tietNama = findViewById(R.id.tiet_name);
        tietBio = findViewById(R.id.tiet_bio);
        tietLinkFb = findViewById(R.id.tiet_facebook);
        tietLinkIg = findViewById(R.id.tiet_instagram);
        tietLinkYt = findViewById(R.id.tiet_youtube);
        btnSave = findViewById(R.id.btnSave);
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
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imageUri);
                newPhoto = PhotoUtils.bitmapToBytes(bitmap);
                Glide.with(this).load(imageUri).into(ivFoto);
            } catch (IOException e) {
                Snackbar snackbar = Snackbar.make(ivFoto.getRootView(), "Gagal mengambil foto.", Snackbar.LENGTH_SHORT);
                snackbar.setBackgroundTint(ContextCompat.getColor(ivFoto.getContext(), R.color.snackbarred));
                snackbar.show();
            }
        }
    }

}