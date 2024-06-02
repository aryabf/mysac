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
import androidx.fragment.app.Fragment;

import com.finalproject.mysac.R;

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

        HomeActivity homeActivity = (HomeActivity) getActivity();
        if (homeActivity.loggedUser == null) {

            Log.d("oshiete kudasai", "onViewCreated: auuu");
            // Set data dummy (data ini bisa diambil dari database atau API)
            profileImage.setImageResource(R.drawable.user); // Ganti dengan gambar profil Anda
            name.setText("-");
            username.setText("-");
            bio.setText("-");

        } else {

            name.setText(homeActivity.loggedUser.getName());
            username.setText(homeActivity.loggedUser.getUsername());
            bio.setText(homeActivity.loggedUser.getBio());
            tvResep.setText("" + homeActivity.loggedUser.getJumlahResep());

            if (!Objects.equals(homeActivity.loggedUser.getLinkFb(), "")) {
                ivFacebook.setOnClickListener(view1 -> {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(homeActivity.loggedUser.getLinkFb())));
                });
            } else {
                ivFacebook.setVisibility(View.GONE);
            }

            if (!Objects.equals(homeActivity.loggedUser.getLinkIg(), "")) {
                ivInstagram.setOnClickListener(view1 -> {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(homeActivity.loggedUser.getLinkIg())));
                });
            } else {
                ivInstagram.setVisibility(View.GONE);
            }

            if (!Objects.equals(homeActivity.loggedUser.getLinkYt(), "")) {
                ivYoutube.setOnClickListener(view1 -> {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(homeActivity.loggedUser.getLinkYt())));
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
