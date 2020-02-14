package com.jennyfer.jenna.Quizz.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.jennyfer.jenna.Quizz.Global.Constantes;
import com.jennyfer.jenna.Quizz.Models.FactoresResp;
import com.jennyfer.jenna.Quizz.Models.GraphData;
import com.jennyfer.jenna.Quizz.Utilities.RecyclerGraph;
import com.jennyfer.jenna.Quizz.Utilities.RequestUtility;
import com.jennyfer.jenna.Quizz.Utilities.VolleyCallback;
import com.jennyfer.jenna.R;
import com.jennyfer.jenna.Services.Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FragmentoResultado extends Fragment {


    private RecyclerView recyclerView;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        /*
         * EL SHARED PREF MANAGER JEJEJEJEJEJE
         */

        return inflater.inflate(R.layout.fragment_graphs, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = getActivity().findViewById(R.id.recycler_graphs);
        obtenerFactores(Preferences.obtenerPreferencesString(getContext(), Preferences.PREFERENCES_USUARIO_LOGIN));

    }

    private void obtenerFactores(String usuario) {
        RequestUtility ru = new RequestUtility(getActivity().getApplicationContext());
        Map<String, String> params = new HashMap<>();
        params.put("usuario", String.valueOf(usuario));
        ru.__callServerPOST(Constantes.GET_GRAPHS_DATA, params, new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {

                    JSONArray a1 = new JSONArray(result);




                    GraphData g1 = new GraphData();
                    g1.setTitulo(a1.getJSONObject(0).getString("titulo"));
                    g1.setColor_gnal(a1.getJSONObject(0).getString("color_gnal"));
                    ArrayList<FactoresResp> factoress = new ArrayList<>();
                    JSONArray a11 = a1.getJSONObject(0).getJSONArray("factores");
                    for(int i = 0 ; i<a11.length();i++){
                        factoress.add(new FactoresResp(a11.getJSONObject(i).getString("titulo"),
                                a11.getJSONObject(i).getBoolean("status"),
                                a11.getJSONObject(i).getString("color"),
                                a11.getJSONObject(i).getString("calif")
                                ));
                    }
                    g1.setFactores(factoress);
//
                    GraphData g2 = new GraphData();
                    g2.setTitulo(a1.getJSONObject(1).getString("titulo"));
                    g2.setColor_gnal(a1.getJSONObject(1).getString("color_gnal"));
                    ArrayList<FactoresResp> factoresss = new ArrayList<>();
                    JSONArray a111 = a1.getJSONObject(1).getJSONArray("factores");
                    for(int i = 0 ; i<a111.length();i++){
                        factoress.add(new FactoresResp(a11.getJSONObject(i).getString("titulo"),
                                a11.getJSONObject(i).getBoolean("status"),
                                a11.getJSONObject(i).getString("color"),
                                a11.getJSONObject(i).getString("calif")
                        ));
                    }
                    g2.setFactores(factoresss);

                    GraphData g3 = new GraphData();
                    g3.setTitulo(a1.getJSONObject(2).getString("titulo"));
                    g3.setColor_gnal(a1.getJSONObject(2).getString("color_gnal"));
                    ArrayList<FactoresResp> factoressss = new ArrayList<>();
                    JSONArray a1111 = a1.getJSONObject(2).getJSONArray("factores");
                    for(int i = 0 ; i<a1111.length();i++){
                        factoress.add(new FactoresResp(a11.getJSONObject(i).getString("titulo"),
                                a11.getJSONObject(i).getBoolean("status"),
                                a11.getJSONObject(i).getString("color"),
                                a11.getJSONObject(i).getString("calif")
                        ));
                    }
                    g3.setFactores(factoressss);

                    ArrayList<GraphData> data = new ArrayList<>();
                    data.add(g1);
                    data.add(g2);
                    data.add(g3);
                    setupRecyclerView(data);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String result) {

                Log.d("JIJI", "error: " + result);
            }
        });
    }

    private void setupRecyclerView(ArrayList<GraphData> data) {
        recyclerView.setAdapter(new RecyclerGraph(data));
    }
}
