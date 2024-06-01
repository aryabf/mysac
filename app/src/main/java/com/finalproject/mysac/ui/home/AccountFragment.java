package com.finalproject.mysac.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.finalproject.mysac.R;

public class AccountFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);

        // Inisialisasi Views
        ImageView profileImage = view.findViewById(R.id.profile_image);
        TextView name = view.findViewById(R.id.name);
        TextView username = view.findViewById(R.id.username);
        TextView bio = view.findViewById(R.id.bio);

        // Set data dummy (data ini bisa diambil dari database atau API)
        profileImage.setImageResource(R.drawable.user); // Ganti dengan gambar profil Anda
        name.setText("John Doe");
        username.setText("@johndoe");
        bio.setText("Ini adalah bio pengguna. Tuliskan sesuatu yang menarik tentang Anda.");

        return view;
    }
}
