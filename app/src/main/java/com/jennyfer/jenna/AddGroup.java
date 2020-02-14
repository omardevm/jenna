package com.jennyfer.jenna;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.jennyfer.jenna.Internet.SolicitudesJSON;
import com.jennyfer.jenna.Services.Preferences;
import com.jennyfer.jenna.Tools.VolleyRP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class AddGroup extends AppCompatActivity {
    private ImageView banner;
    private Spinner d, g;
    private Button agregar;
    ArrayList<String> divi = new ArrayList<>();
    ArrayList<String> grup = new ArrayList<>();
    private VolleyRP volleyRP;
    private RequestQueue requestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_group);

        volleyRP     = VolleyRP.getInstance(this);
        requestQueue = volleyRP.getRequestQueue();

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        banner = findViewById(R.id.banner);
        String url ="https://pitav3.000webhostapp.com/Imagenes/grupo_banner.png";
        Glide.with(this)
                .load(url)
                .thumbnail(0.5f)
                .into(banner);

        d = findViewById(R.id.division);
        g = findViewById(R.id.grupo);
        listaDivision();
        listaGrupo();

        agregar = findViewById(R.id.agregarGrupo);
        agregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String grupoT = g.getSelectedItem().toString();
                String divisT = d.getSelectedItem().toString();
                AgregarGrupo(divisT, grupoT);
            }
        });

    }

    private void AgregarGrupo(String divisionA, String grupoA) {
        String usuario = Preferences.obtenerPreferencesString(this,Preferences.PREFERENCES_USUARIO_LOGIN);

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("usuario", usuario);
        hashMap.put("division", divisionA);
        hashMap.put("grupoTutorado", grupoA);

        JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.POST, SolicitudesJSON.URL_ADDGRUPO, new JSONObject(hashMap), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject datos) {
                try {
                    String respuesta = datos.getString("respuesta");
                    if (respuesta.equals("200")) {
                        Toast.makeText(AddGroup.this, "Se inserto un nuevo grupo", Toast.LENGTH_SHORT).show();
                        finish();
                    } else if (respuesta.equals("-1")) {
                        Toast.makeText(AddGroup.this, "Puede que ya tengas a este grupo registrado", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } catch (JSONException e) {
                    Toast.makeText(AddGroup.this, "Intentelo más tarde", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(AddGroup.this, "No se pudo registrar", Toast.LENGTH_SHORT).show();
            }
        });
        VolleyRP.addToQueue(solicitud, requestQueue, this, volleyRP);
    }


    private void listaGrupo() {
        SolicitudesJSON json = new SolicitudesJSON() {
            @Override
            public void solicitudCompletada(JSONObject j) {
                try {
                    JSONArray jsa = j.getJSONArray("resultado");
                    for (int i=0; i<jsa.length(); i++) {
                        JSONObject jo      = jsa.getJSONObject(i);
                        String grupo    = jo.getString("grupo");
                        grup.add(grupo);
                    }
                    g.setAdapter(new ArrayAdapter<String>(AddGroup.this, android.R.layout.simple_spinner_dropdown_item, grup));
                } catch (JSONException e) {
                    Toast.makeText(AddGroup.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void solicitudErronea() { }
        };
        json.JSONGET(this, SolicitudesJSON.URL_GRUPO);
    }

    private void listaDivision() {
        SolicitudesJSON json = new SolicitudesJSON() {
            @Override
            public void solicitudCompletada(JSONObject j) {
                try {
                    JSONArray jsa = j.getJSONArray("resultado");
                    for (int i=0; i<jsa.length(); i++) {
                        JSONObject jo      = jsa.getJSONObject(i);
                        String division    = jo.getString("division");

                        divi.add(division);
                    }
                    d.setAdapter(new ArrayAdapter<String>(AddGroup.this, android.R.layout.simple_spinner_dropdown_item, divi));
                } catch (JSONException e) {
                    Toast.makeText(AddGroup.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void solicitudErronea() { }
        };
        json.JSONGET(this, SolicitudesJSON.URL_DIVISION);
    }
}
