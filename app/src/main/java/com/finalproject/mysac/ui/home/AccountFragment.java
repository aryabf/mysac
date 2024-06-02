package com.finalproject.mysac.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.finalproject.mysac.R;
import com.finalproject.mysac.data.local.db.DbHelper;
import com.finalproject.mysac.data.local.preferences.SharedPreferencesManager;
import com.finalproject.mysac.data.model.User;
import com.finalproject.mysac.ui.settings.EditProfileActivity;
import com.google.android.material.snackbar.Snackbar;

import java.util.Objects;

public class AccountFragment extends Fragment {

    // Inisialisasi Views
    ImageView profileImage;
    TextView name;
    TextView username;
    TextView bio;
    ImageView ivFacebook;
    ImageView ivYoutube;
    ImageView ivInstagram;
    TextView tvResep;
    SharedPreferencesManager sharedPreferencesManager;
    DbHelper dbHelper;
    User loggedUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindViews(view);

        sharedPreferencesManager = new SharedPreferencesManager(view.getContext());
        String loggedUserId = sharedPreferencesManager.getLoggedUsername();

        dbHelper = new DbHelper(view.getContext());
        loggedUser = dbHelper.getUserByUsername(loggedUserId);

        HomeActivity homeActivity = (HomeActivity) getActivity();
        if (loggedUser == null) {

            profileImage.setImageResource(R.drawable.user); // Ganti dengan gambar profil Anda
            name.setText("-");
            username.setText("-");
            bio.setText("-");

        } else {

            name.setText(loggedUser.getName());
            username.setText(loggedUser.getUsername());
            bio.setText(loggedUser.getBio());
            tvResep.setText("" + loggedUser.getJumlahResep());

            if (loggedUser.getPhoto() != null) {
                Glide.with(view.getContext()).load(loggedUser.getPhoto()).into(profileImage);
            }

            if (!Objects.equals(loggedUser.getLinkFb(), "")) {
                ivFacebook.setOnClickListener(view1 -> {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(loggedUser.getLinkFb())));
                    } catch (Exception e) {
                        Snackbar snackbar = Snackbar.make(view, "Gagal membuka Facebook.", Snackbar.LENGTH_SHORT);
                        snackbar.setBackgroundTint(ContextCompat.getColor(view.getContext(), R.color.snackbarred));
                        snackbar.show();
                    }
                });
            } else {
                ivFacebook.setVisibility(View.GONE);
            }

            if (!Objects.equals(loggedUser.getLinkIg(), "")) {
                ivInstagram.setOnClickListener(view1 -> {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(loggedUser.getLinkIg())));
                    } catch (Exception e) {
                        Snackbar snackbar = Snackbar.make(view, "Gagal membuka Instagram.", Snackbar.LENGTH_SHORT);
                        snackbar.setBackgroundTint(ContextCompat.getColor(view.getContext(), R.color.snackbarred));
                        snackbar.show();
                    }
                });
            } else {
                ivInstagram.setVisibility(View.GONE);
            }

            if (!Objects.equals(loggedUser.getLinkYt(), "")) {
                ivYoutube.setOnClickListener(view1 -> {
                    try {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(loggedUser.getLinkYt())));
                    } catch (Exception e) {
                        Snackbar snackbar = Snackbar.make(view, "Gagal membuka YouTube.", Snackbar.LENGTH_SHORT);
                        snackbar.setBackgroundTint(ContextCompat.getColor(view.getContext(), R.color.snackbarred));
                        snackbar.show();
                    }
                });
            } else {
                ivYoutube.setVisibility(View.GONE);
            }

        }

    }

    void bindViews(View view) {
        profileImage = view.findViewById(R.id.profile_image);
        name = view.findViewById(R.id.name);
        username = view.findViewById(R.id.username);
        bio = view.findViewById(R.id.bio);
        ivFacebook = view.findViewById(R.id.icon_facebook);
        ivInstagram = view.findViewById(R.id.icon_instagram);
        ivYoutube = view.findViewById(R.id.icon_youtube);
        tvResep = view.findViewById(R.id.recipe_count);
    }
}
