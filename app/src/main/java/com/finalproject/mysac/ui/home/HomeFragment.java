package com.finalproject.mysac.ui.home;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.finalproject.mysac.R;
import com.finalproject.mysac.data.local.db.DbHelper;
import com.finalproject.mysac.data.local.preferences.SharedPreferencesManager;
import com.finalproject.mysac.data.model.Kategori;
import com.finalproject.mysac.data.model.Resep;
import com.finalproject.mysac.data.model.User;
import com.finalproject.mysac.data.model.response.ResponseKategori;
import com.finalproject.mysac.data.retrofit.APIServices;
import com.finalproject.mysac.data.retrofit.RetrofitBuilder;
import com.finalproject.mysac.ui.auth.MainActivity;
import com.finalproject.mysac.ui.home.adapter.HomeCategoryAdapter;
import com.finalproject.mysac.ui.home.adapter.HomeMealAdapter;
import com.finalproject.mysac.ui.splash.SplashScreenActivity;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    RecyclerView rvKategori;
    RecyclerView rvRekomendasi;
    TextView tvNama;
    ArrayList<Kategori> listKategori;
    ArrayList<Resep> listResep;

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

        bindViews(view);

        HomeActivity homeActivity = (HomeActivity) getActivity();
        if (homeActivity.loggedUser == null) {
            Log.d("oshiete kudasai", "onViewCreated: auuu");
        } else {
            tvNama.setText(homeActivity.loggedUser.getName());
        }

        APIServices client = RetrofitBuilder.builder(view.getContext()).create(APIServices.class);
        client.getCategoryList().enqueue(new Callback<ResponseKategori>() {
            @Override
            public void onResponse(Call<ResponseKategori> call, Response<ResponseKategori> response) {
                if (response.isSuccessful()) {
                    listKategori = response.body().getKategori();
                    Collections.shuffle(listKategori);
                    listKategori = new ArrayList<>(listKategori.subList(0, 4));
                    rvKategori.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
                    rvKategori.setHasFixedSize(true);
                    HomeCategoryAdapter adapterCategory = new HomeCategoryAdapter(listKategori, view.getContext());
                    rvKategori.setAdapter(adapterCategory);
                    int horizontalSpace = getResources().getDimensionPixelSize(R.dimen.horizontal_margin);
                    rvKategori.addItemDecoration(new HomeCategoryAdapter.HorizontalSpaceItemDecoration(horizontalSpace));
                } else {
                    Toast.makeText(view.getContext(), response.body().toString(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseKategori> call, Throwable t) {
                Toast.makeText(view.getContext(), t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        byte[] byteList = {1, 2};

        // Ngapain cb masukin instruksi semua wkowk ^^
        listResep = new ArrayList<>();
        listResep.add(new Resep("52821", "Laksa King Prawn Noodles", "Seafood", "Heat the oil in a medium saucepan and add the chilli. Cook for 1 min, then add the curry paste, stir and cook for 1 min more. Dissolve the stock cube in a large jug in 700ml boiling water, then pour into the pan and stir to combine. Tip in the coconut milk and bring to the boil.\\r\\nAdd the fish sauce and a little seasoning. Toss in the noodles and cook for a further 3-4 mins until softening. Squeeze in the lime juice, add the prawns and cook through until warm, about 2-3 mins. Scatter over some of the coriander.\\r\\nServe in bowls with the remaining coriander and lime wedges on top for squeezing over.", "https://www.themealdb.com/images/media/meals/rvypwy1503069308.jpg", "The Meal DB", "Malaysian", byteList));
        listResep.add(new Resep("52943", "Oxtail with broad beans", "Beef", "Toss the oxtail with the onion, spring onion, garlic, ginger, chilli, soy sauce, thyme, salt and pepper. Heat the vegetable oil in a large frying pan over medium-high heat. Brown the oxtail in the pan until browned all over, about 10 minutes. Place into a pressure cooker, and pour in 375ml water. Cook at pressure for 25 minutes, then remove from heat, and remove the lid according to manufacturer's directions.\\r\\nAdd the broad beans and pimento berries, and bring to a simmer over medium-high heat. Dissolve the cornflour in 2 tablespoons water, and stir into the simmering oxtail. Cook and stir a few minutes until the sauce has thickened, and the broad beans are tender.", "https://www.themealdb.com/images/media/meals/1520083578.jpg", "The Meal DB", "Jamaican", byteList));
        listResep.add(new Resep("52796", "Chicken Alfredo Primavera", "Chicken", "Heat 1 tablespoon of butter and 2 tablespoons of olive oil in a large skillet over medium-high heat. Season both sides of each chicken breast with seasoned salt and a pinch of pepper. Add the chicken to the skillet and cook for 5-7 minutes on each side, or until cooked through.  While the chicken is cooking, bring a large pot of water to a boil. Season the boiling water with a few generous pinches of kosher salt. Add the pasta and give it a stir. Cook, stirring occasionally, until al dente, about 12 minutes. Reserve 1/2 cup of  pasta water before draining the pasta.  Remove the chicken from the pan and transfer it to a cutting board; allow it to rest. Turn the heat down to medium and dd the remaining 1 tablespoon of butter and olive oil to the same pan you used to cook the chicken. Add the veggies (minus the garlic) and red pepper flakes to the pan and stir to coat with the oil and butter (refrain from seasoning with salt until the veggies are finished browning). Cook, stirring often, until the veggies are tender, about 5 minutes. Add the garlic and a generous pinch of salt and pepper to the pan and cook for 1 minute.  Deglaze the pan with the white wine. Continue to cook until the wine has reduced by half, about 3 minutes. Stir in the milk, heavy cream, and reserved pasta water. Bring the mixture to a gentle boil and allow to simmer and reduce for 2-3 minutes. Turn off the heat and add the Parmesan cheese and cooked pasta. Season with salt and pepper to taste. Garnish with Parmesan cheese and chopped parsley, if desired.", "https://www.themealdb.com/images/media/meals/syqypv1486981727.jpg", "The Meal DB", "Italian", byteList));
        listResep.add(new Resep("52883", "Sticky Toffee Pudding", "Dessert", "Preheat the oven to 180C/160C Fan/Gas 4. Butter a wide shallow 1.7-litre/3-pint ovenproof dish.\\r\\nPut the butter, sugar, eggs, flour, baking powder, bicarbonate of soda and treacle into a mixing bowl. Beat using an electric handheld whisk for about 30 seconds or until combined. Pour in the milk gradually and whisk again until smooth. Pour into the prepared dish. Bake for 35–40 minutes or until well risen and springy in the centre.\\r\\nTo make the sauce, put all the ingredients into a saucepan and stir over a low heat until the sugar has dissolved and the butter has melted. Bring to the boil, stirring for a minute.\\r\\nTo serve, pour half the sauce over the pudding in the baking dish. Serve with the cream or ice cream.", "https://www.themealdb.com/images/media/meals/xqqqtu1511637379.jpg", "The Meal DB", "Brittish", byteList));
        listResep.add(new Resep("52808", "Lamb Rogan josh", "Lamb", "\\r\\nPut the onions in a food processor and whizz until very finely chopped. Heat the oil in a large heavy-based pan, then fry the onion with the lid on, stirring every now and then, until it is really golden and soft. Add the garlic and ginger, then fry for 5 mins more.\\r\\nTip the curry paste, all the spices and the bay leaves into the pan, with the tomato purée. Stir well over the heat for about 30 secs, then add the meat and 300ml water. Stir to mix, turn down the heat, then add the yogurt.\\r\\nCover the pan, then gently simmer for 40-60 mins until the meat is tender and the sauce nice and thick. Serve scattered with coriander, with plain basmati or pilau rice.", "https://www.themealdb.com/images/media/meals/vvstvq1487342592.jpg", "The Meal DB", "Indian", byteList));
        listResep.add(new Resep("52848", "Bean & Sausage Hotpot", "Miscellaneous", "In a large casserole, fry the sausages until brown all over – about 10 mins.\\r\\n\\r\\nAdd the tomato sauce, stirring well, then stir in the beans, treacle or sugar and mustard. Bring to the simmer, cover and cook for 30 mins. Great served with crusty bread or rice.", "https://www.themealdb.com/images/media/meals/vxuyrx1511302687.jpg", "The Meal DB", "British", byteList));
        listResep.add(new Resep("52987", "Lasagna Sandwiches", "Pasta", "1. In a small bowl, combine the first four ingredients; spread on four slices of bread. Layer with bacon, tomato and cheese; top with remaining bread.\\r\\n\\r\\n2. In a large skillet or griddle, melt 2 tablespoons butter. Toast sandwiches until lightly browned on both sides and cheese is melted, adding butter if necessary.\\r\\n\\r\\nNutrition Facts\\r\\n1 sandwich: 445 calories, 24g fat (12g saturated fat), 66mg cholesterol, 1094mg sodium, 35g carbohydrate (3g sugars, 2g fiber), 21g protein.", "https://www.themealdb.com/images/media/meals/xr0n4r1576788363.jpg", "The Meal DB", "American", byteList));
        listResep.add(new Resep("53032", "Tonkatsu pork", "Pork", "STEP 1\\r\\nRemove the large piece of fat on the edge of each pork loin, then bash each of the loins between two pieces of baking parchment until around 1cm in thickness – you can do this using a meat tenderiser or a rolling pin. Once bashed, use your hands to reshape the meat to its original shape and thickness – this step will ensure the meat is as succulent as possible.\\r\\n\\r\\nSTEP 2\\r\\nPut the flour, eggs and panko breadcrumbs into three separate wide-rimmed bowls. Season the meat, then dip first in the flour, followed by the eggs, then the breadcrumbs.\\r\\n\\r\\nSTEP 3\\r\\nIn a large frying or sauté pan, add enough oil to come 2cm up the side of the pan. Heat the oil to 180C – if you don’t have a thermometer, drop a bit of panko into the oil and if it sinks a little then starts to fry, the oil is ready. Add two pork chops and cook for 1 min 30 secs on each side, then remove and leave to rest on a wire rack for 5 mins. Repeat with the remaining pork chops.\\r\\n\\r\\nSTEP 4\\r\\nWhile the pork is resting, make the sauce by whisking the ingredients together, adding a splash of water if it’s particularly thick. Slice the tonkatsu and serve drizzled with the sauce.", "https://www.themealdb.com/images/media/meals/lwsnkl1604181187.jpg", "The Meal DB", "Japanese", byteList));
        listResep.add(new Resep("52918", "Fish Stew with Rouille", "Seafood", "Twist the heads from the prawns, then peel away the legs and shells, but leave the tails intact. Devein each prawn. Fry the shells in 1 tbsp oil for 5 mins, until dark pink and golden in patches. Add the wine, boil down by two thirds, then pour in the stock. Strain into a jug, discarding the shells.\\r\\nHeat the rest of the oil in a deep frying pan or casserole. Add the fennel, onion and garlic, season, then cover and gently cook for 10 mins until softened. Meanwhile, peel the potato and cut into 2cm-ish chunks. Put into a pan of cold water, bring to the boil and cook for 5 mins until almost tender. Drain in a colander.\\r\\nPeel a strip of zest from the orange. Put the zest, star anise, bay and ½ tsp harissa into the pan. Fry gently, uncovered, for 5-10 mins, until the vegetables are soft, sweet and golden.\\r\\nStir in the tomato purée, cook for 2 mins, then add the tomatoes and stock. Simmer for 10 mins until the sauce thickens slightly. Season to taste. The sauce can be made ahead, then reheated later in the day. Meantime, scrub the mussels or clams and pull away any stringy beards. Any that are open should be tapped sharply on the worktop – if they don’t close after a few seconds, discard them.\\r\\nReheat the sauce if necessary, then stir the potato, chunks of fish and prawns very gently into the stew. Bring back to the boil, then cover and gently simmer for 3 mins. Scatter the mussels or clams over the stew, then cover and cook for 2 mins more or until the shells have opened wide. Discard any that remain closed. The chunks of fish should flake easily and the prawns should be pink through. Scatter with the thyme leaves.\\r\\nTo make the quick rouille, stir the rest of the harissa through the mayonnaise. Serve the stew in bowls, topped with spoonfuls of rouille, which will melt into the sauce and enrich it. Have some good bread ready, as you’ll definitely want to mop up the juices.", "https://www.themealdb.com/images/media/meals/vptqpw1511798500.jpg", "The Meal DB", "French", byteList));
        listResep.add(new Resep("52903", "French Onion Soup", "Side", "Melt the butter with the oil in a large heavy-based pan. Add the onions and fry with the lid on for 10 mins until soft. Sprinkle in the sugar and cook for 20 mins more, stirring frequently, until caramelised. The onions should be really golden, full of flavour and soft when pinched between your fingers. Take care towards the end to ensure that they don’t burn.\\r\\nAdd the garlic for the final few mins of the onions’ cooking time, then sprinkle in the flour and stir well. Increase the heat and keep stirring as you gradually add the wine, followed by the hot stock. Cover and simmer for 15-20 mins.\\r\\nTo serve, turn on the grill, and toast the bread. Ladle the soup into heatproof bowls. Put a slice or two of toast on top of the bowls of soup, and pile on the cheese. Grill until melted. Alternatively, you can complete the toasts under the grill, then serve them on top.", "https://www.themealdb.com/images/media/meals/xvrrux1511783685.jpg", "The Meal DB", "French", byteList));
        listResep.add(new Resep("52842", "Broccoli & Stilton soup", "Starter", "Heat the rapeseed oil in a large saucepan and then add the onions. Cook on a medium heat until soft. Add a splash of water if the onions start to catch.\\r\\n\\r\\nAdd the celery, leek, potato and a knob of butter. Stir until melted, then cover with a lid. Allow to sweat for 5 minutes. Remove the lid.\\r\\n\\r\\nPour in the stock and add any chunky bits of broccoli stalk. Cook for 10 – 15 minutes until all the vegetables are soft.\\r\\n\\r\\nAdd the rest of the broccoli and cook for a further 5 minutes. Carefully transfer to a blender and blitz until smooth. Stir in the stilton, allowing a few lumps to remain. Season with black pepper and serve.", "https://www.themealdb.com/images/media/meals/tvvxpv1511191952.jpg", "The Meal DB", "British", byteList));
        listResep.add(new Resep("52794", "Vegan Chocolate Cake", "Vegan", "Simply mix all dry ingredients with wet ingredients and blend altogether. Bake for 45 min on 180 degrees. Decorate with some melted vegan chocolate", "https://www.themealdb.com/images/media/meals/qxutws1486978099.jpg", "The Meal DB", "American", byteList));
        listResep.add(new Resep("53078", "Beetroot Soup (Borscht)", "Pork", "", "https://www.themealdb.com/images/media/meals/zadvgb1699012544.jpg", "The Meal DB", "Indian", byteList));
        listResep.add(new Resep("52957", "Fruit and Cream Cheese Breakfast Pastries", "Pork", "", "https://www.themealdb.com/images/media/meals/1543774956.jpg", "The Meal DB", "Indian", byteList));

        Collections.shuffle(listResep);
        listResep = new ArrayList<>(listResep.subList(0, 5));

        rvRekomendasi.setHasFixedSize(true);
        rvRekomendasi.setLayoutManager(new LinearLayoutManager(view.getContext(), LinearLayoutManager.HORIZONTAL, false));
        HomeMealAdapter adapterMeal = new HomeMealAdapter(listResep, view.getContext());
        rvRekomendasi.setAdapter(adapterMeal);

        int horizontalSpace = getResources().getDimensionPixelSize(R.dimen.horizontal_margin);
        rvRekomendasi.addItemDecoration(new HomeMealAdapter.HorizontalSpaceItemDecoration(horizontalSpace));
    }

    void bindViews(View view) {
        tvNama = view.findViewById(R.id.nama);
        rvKategori = view.findViewById(R.id.rv_kategori);
        rvRekomendasi = view.findViewById(R.id.rv_rekomendasi);
    }

}