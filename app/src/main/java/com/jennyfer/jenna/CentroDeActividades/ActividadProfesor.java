package com.jennyfer.jenna.CentroDeActividades;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.jennyfer.jenna.Adapter.ActividadesAdapter;
import com.jennyfer.jenna.Adapter.ListaGrupoTutorAdapter;
import com.jennyfer.jenna.Atributos.ActividadesAtributos;
import com.jennyfer.jenna.CentroDeActividades.Tareas.CrearTarea;
import com.jennyfer.jenna.Inicio;
import com.jennyfer.jenna.Internet.SolicitudesJSON;
import com.jennyfer.jenna.R;
import com.jennyfer.jenna.Services.Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActividadProfesor extends Fragment {
    private FloatingActionButton fab;

    private List<ActividadesAtributos> atributos;
    private ListaGrupoTutorAdapter adapter;
    private RecyclerView rv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.actividad_profesor, container, false);
        container.removeAllViews();
        setHasOptionsMenu(true);
        ((Inicio) getActivity()).getSupportActionBar().setTitle("Centro de actividades");

        fab = view.findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CrearTarea.class);
                startActivity(intent);
            }
        });

        atributos = new ArrayList<>();
        rv = view.findViewById(R.id.rvCarrera);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(lm);
        rv.setHasFixedSize(true);
        adapter = new ListaGrupoTutorAdapter(atributos,getContext(), this);
        rv.setAdapter(adapter);
        ListaGrupoTutor();
        return view;
    }


    private void ListaGrupoTutor() {
        SolicitudesJSON json = new SolicitudesJSON() {
            @Override
            public void solicitudCompletada(JSONObject j) {
                try {
                    JSONArray jsa = j.getJSONArray("resultado");
                    for (int i=0; i<jsa.length(); i++) {
                        JSONObject jo      = jsa.getJSONObject(i);
                        String grupo       = jo.getString("grupoTutorado");
                        String division    = jo.getString("division");
                        insertarGrupo(grupo, division);

                    }
                } catch (JSONException e) {
                    //Toast.makeText(getActivity(), "Ocurrio un error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void solicitudErronea() { }
        };
        String usuario = Preferences.obtenerPreferencesString(getContext(), Preferences.PREFERENCES_USUARIO_LOGIN);
        json.JSONGET(getActivity(), SolicitudesJSON.URL_GETLISTAGYD + usuario);
    }

    private void insertarGrupo(String grupo, String carrera) {
        ActividadesAtributos aa = new ActividadesAtributos();
        aa.setGrupo(grupo);
        aa.setDivision(carrera);
        atributos.add(aa);
        adapter.notifyDataSetChanged();
    }

}
