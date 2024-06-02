package com.finalproject.mysac.ui.recipes;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.finalproject.mysac.R;
import com.finalproject.mysac.data.local.db.DbHelper;
import com.finalproject.mysac.data.model.Resep;
import com.finalproject.mysac.data.model.response.ResponseDaftarResep;
import com.finalproject.mysac.data.model.response.ResponseResep;
import com.finalproject.mysac.data.retrofit.APIServices;
import com.finalproject.mysac.data.retrofit.RetrofitBuilder;
import com.finalproject.mysac.ui.home.adapter.RecipeAdapter;
import com.finalproject.mysac.utils.ResponseResepConverter;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListActivity extends AppCompatActivity {

    String DEFAULT_CATEGORY = "Seafood";
    ImageView ivBack;
    TextView tvTitle;
    RecyclerView rvRecipeList;
    ProgressBar pbRecipeList;
    DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_recipe_list);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        bindViews();
        String category;
        String username;

        if (getIntent().getStringExtra("category") != null) {
            category = getIntent().getStringExtra("category");
        } else {
            category = DEFAULT_CATEGORY;
        }

        ivBack.setOnClickListener(view -> finish());

        if (getIntent().getStringExtra("username") != null) {

            username = getIntent().getStringExtra("username");
            String name = getIntent().getStringExtra("name");
            tvTitle.setText("Resep " + name.substring(0, name.indexOf(' ')));
            ArrayList<Resep> resepFromDb = dbHelper.getRecipesByUsername(username);

            Log.d("auu", "onCreate: " + resepFromDb.size());
            pbRecipeList.setVisibility(View.GONE);
            rvRecipeList.setVisibility(View.VISIBLE);
            rvRecipeList.setHasFixedSize(true);
            rvRecipeList.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
            RecipeAdapter adapter = new RecipeAdapter(resepFromDb, RecipeListActivity.this);
            rvRecipeList.setAdapter(adapter);
            rvRecipeList.addItemDecoration(new RecipeAdapter.GridSpacingItemDecoration(2, 24, true));

        } else {
            tvTitle.setText(category);

            APIServices client = RetrofitBuilder.builder(this).create(APIServices.class);
            client.getMealListByCategory(category).enqueue(new Callback<ResponseDaftarResep>() {
                @Override
                public void onResponse(Call<ResponseDaftarResep> call, Response<ResponseDaftarResep> response) {

                    pbRecipeList.setVisibility(View.GONE);

                    if (response.isSuccessful()) {

                        List<ResponseResep> responseList = response.body().getDaftarResep();
                        ArrayList<Resep> resepList = new ArrayList<Resep>();
                        ResponseResepConverter converter = new ResponseResepConverter();

                        ArrayList<Resep> resepFromDatabase = dbHelper.getRecipesByCategory(category);

                        for (int i = 0; i < responseList.size(); i++) {
                            Resep resep = converter.responseToResep(responseList.get(i));
                            resepList.add(resep);
                        }
                        resepList.addAll(resepFromDatabase);
                        Log.d("halah", "onResponse: " + resepList.get(resepList.size() - 1).getNama());

                        rvRecipeList.setVisibility(View.VISIBLE);

                        rvRecipeList.setHasFixedSize(true);
                        rvRecipeList.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
                        RecipeAdapter adapter = new RecipeAdapter(resepList, RecipeListActivity.this);
                        rvRecipeList.setAdapter(adapter);
                        rvRecipeList.addItemDecoration(new RecipeAdapter.GridSpacingItemDecoration(2, 24, true));

                    } else {
                        Toast.makeText(RecipeListActivity.this, response.body().toString(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ResponseDaftarResep> call, Throwable t) {
                    pbRecipeList.setVisibility(View.GONE);
                    Toast.makeText(RecipeListActivity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });

        }

    }

    void bindViews() {
        dbHelper = new DbHelper(this);
        ivBack = findViewById(R.id.iv_back);
        tvTitle = findViewById(R.id.tv_title);
        rvRecipeList = findViewById(R.id.rv_recipe_list);
        pbRecipeList = findViewById(R.id.pb_recipe_list);
    }
}