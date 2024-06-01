package com.finalproject.mysac.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.finalproject.mysac.R;
import com.google.android.material.imageview.ShapeableImageView;

public class HomeFragment extends Fragment {

    ShapeableImageView sivSalmonRisotto;
    ShapeableImageView sivPadSeeEw;
    ShapeableImageView sivEnglishBreakfast;
    ShapeableImageView sivPumpkinPie;
    ShapeableImageView sivCreme;
    ShapeableImageView sivPasta;
    ShapeableImageView sivChicken;
    ShapeableImageView sivSeafood;
    ShapeableImageView sivDessert;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        String imgBaseUrl = "https://www.themealdb.com/images/media/meals/";
        String categoryBaseUrl = "https://www.themealdb.com/images/category/";

        sivSalmonRisotto = getView().findViewById(R.id.salmonrisottoimg);
        sivPadSeeEw = getView().findViewById(R.id.padseeewimg);
        sivEnglishBreakfast = getView().findViewById(R.id.englishimg);
        sivPumpkinPie = getView().findViewById(R.id.pumpkinimg);
        sivCreme = getView().findViewById(R.id.cremeimg);
        sivPasta = getView().findViewById(R.id.pastaimg);
        sivChicken = getView().findViewById(R.id.chickenimg);
        sivSeafood = getView().findViewById(R.id.seafoodimg);
        sivDessert = getView().findViewById(R.id.dessertimg);

        Glide.with(this).load(imgBaseUrl + "xxrxux1503070723.jpg").into(sivSalmonRisotto);
        Glide.with(this).load(imgBaseUrl + "uuuspp1468263334.jpg").into(sivPadSeeEw);
        Glide.with(this).load(imgBaseUrl + "utxryw1511721587.jpg").into(sivEnglishBreakfast);
        Glide.with(this).load(imgBaseUrl + "usuqtp1511385394.jpg").into(sivPumpkinPie);
        Glide.with(this).load(imgBaseUrl + "uryqru1511798039.jpg").into(sivCreme);
        Glide.with(this).load(categoryBaseUrl + "pasta.jpg").into(sivPasta);
        Glide.with(this).load(categoryBaseUrl + "chicken.jpg").into(sivChicken);
        Glide.with(this).load(categoryBaseUrl + "seafood.jpg").into(sivSeafood);
        Glide.with(this).load(categoryBaseUrl + "dessert.jpg").into(sivDessert);
    }
}