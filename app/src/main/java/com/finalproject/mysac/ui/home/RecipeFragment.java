package com.finalproject.mysac.ui.home;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.finalproject.mysac.R;
import com.finalproject.mysac.data.model.Kategori;
import com.finalproject.mysac.data.model.response.ResponseKategori;
import com.finalproject.mysac.data.retrofit.APIServices;
import com.finalproject.mysac.data.retrofit.RetrofitBuilder;
import com.finalproject.mysac.ui.home.adapter.CategoryAdapter;
import com.finalproject.mysac.ui.home.adapter.HomeCategoryAdapter;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeFragment extends Fragment {

    RecyclerView rvRecipe;
    ArrayList<Kategori> listKategori;
    ProgressBar pbRecipe;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_recipe, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        bindViews(view);

        APIServices client = RetrofitBuilder.builder(view.getContext()).create(APIServices.class);
        client.getCategoryList().enqueue(new Callback<ResponseKategori>() {
            @Override
            public void onResponse(Call<ResponseKategori> call, Response<ResponseKategori> response) {
                if (response.isSuccessful()) {
                    pbRecipe.setVisibility(View.GONE);
                    rvRecipe.setVisibility(View.VISIBLE);
                    listKategori = response.body().getKategori();
                    rvRecipe.setLayoutManager(new LinearLayoutManager(view.getContext()));
                    rvRecipe.setHasFixedSize(true);
                    CategoryAdapter adapterCategory = new CategoryAdapter(listKategori);
                    rvRecipe.setAdapter(adapterCategory);
                    int verticalSpace = getResources().getDimensionPixelSize(R.dimen.horizontal_margin);
                    rvRecipe.addItemDecoration(new CategoryAdapter.VerticalSpaceItemDecoration(verticalSpace));
                } else {
                    Toast.makeText(view.getContext(), response.body().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseKategori> call, Throwable t) {
                Toast.makeText(view.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    void bindViews(View view) {
        rvRecipe = view.findViewById(R.id.rv_recipe);
        pbRecipe = view.findViewById(R.id.pb_recipe);
    }
}