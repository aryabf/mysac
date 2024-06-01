package com.finalproject.mysac.utils;

import com.finalproject.mysac.data.model.Resep;
import com.finalproject.mysac.data.model.response.ResponseResep;

import java.util.ArrayList;

public class ResponseResepConverter {

    public Resep responseToResep(ResponseResep response) {
        Resep resep = new Resep(
                response.getId(),
                response.getNama(),
                response.getKategori(),
                response.getInstruksi(),
                response.getGambar(),
                "The Meal DB",
                response.getArea(),
                null
        );

        ArrayList<String> bahanBahan = new ArrayList<String>();
        bahanBahan.add(response.getBahan1());
        bahanBahan.add(response.getBahan2());
        bahanBahan.add(response.getBahan3());
        bahanBahan.add(response.getBahan4());
        bahanBahan.add(response.getBahan5());
        bahanBahan.add(response.getBahan6());
        bahanBahan.add(response.getBahan7());
        bahanBahan.add(response.getBahan8());
        bahanBahan.add(response.getBahan9());
        bahanBahan.add(response.getBahan10());
        bahanBahan.add(response.getBahan11());
        bahanBahan.add(response.getBahan12());
        bahanBahan.add(response.getBahan13());
        bahanBahan.add(response.getBahan14());
        bahanBahan.add(response.getBahan15());
        bahanBahan.add(response.getBahan16());
        bahanBahan.add(response.getBahan17());
        bahanBahan.add(response.getBahan18());
        bahanBahan.add(response.getBahan19());
        bahanBahan.add(response.getBahan20());
        ArrayList<String> bahanBahanReal = new ArrayList<String>();
        for (int i = 0; i < bahanBahan.size(); i++) {
            if (bahanBahan.get(i) != null && !bahanBahan.get(i).isEmpty()) {
                bahanBahanReal.add(bahanBahan.get(i));
            }
        }

        ArrayList<String> takaranTakaran = new ArrayList<String>();
        takaranTakaran.add(response.getUkuran1());
        takaranTakaran.add(response.getUkuran2());
        takaranTakaran.add(response.getUkuran3());
        takaranTakaran.add(response.getUkuran4());
        takaranTakaran.add(response.getUkuran5());
        takaranTakaran.add(response.getUkuran6());
        takaranTakaran.add(response.getUkuran7());
        takaranTakaran.add(response.getUkuran8());
        takaranTakaran.add(response.getUkuran9());
        takaranTakaran.add(response.getUkuran10());
        takaranTakaran.add(response.getUkuran11());
        takaranTakaran.add(response.getUkuran12());
        takaranTakaran.add(response.getUkuran13());
        takaranTakaran.add(response.getUkuran14());
        takaranTakaran.add(response.getUkuran15());
        takaranTakaran.add(response.getUkuran16());
        takaranTakaran.add(response.getUkuran17());
        takaranTakaran.add(response.getUkuran18());
        takaranTakaran.add(response.getUkuran19());
        takaranTakaran.add(response.getUkuran20());
        ArrayList<String> takaranTakaranReal = new ArrayList<String>();
        for (int i = 0; i < takaranTakaran.size(); i++) {
            if (takaranTakaran.get(i) != null && !takaranTakaran.get(i).isEmpty()) {
                takaranTakaranReal.add(takaranTakaran.get(i));
            }
        }

        resep.setBahanBahan(bahanBahanReal);
        resep.setUkuranUkuran(takaranTakaranReal);
        return resep;
    }

}
