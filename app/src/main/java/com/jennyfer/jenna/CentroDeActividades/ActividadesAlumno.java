package com.jennyfer.jenna.CentroDeActividades;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jennyfer.jenna.Adapter.ActividadesAdapter;
import com.jennyfer.jenna.Atributos.ActividadesAtributos;
import com.jennyfer.jenna.Inicio;
import com.jennyfer.jenna.Internet.SolicitudesJSON;
import com.jennyfer.jenna.R;
import com.jennyfer.jenna.Services.Preferences;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ActividadesAlumno extends Fragment {

    private List<ActividadesAtributos> atributos;
    private ActividadesAdapter adapter;
    private RecyclerView rv;

    //private ViewPager viewPager;
    //private ViewPagerAdapter adpt;
    //private List<Modelo> modelos;
    //private CardView uno, dos, tres;

   /* private Integer[] colors = null;
    private ArgbEvaluator argb = new ArgbEvaluator();*/

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        container.removeAllViews();
        setHasOptionsMenu(true);
        final View view = inflater.inflate(R.layout.actividades_alumno, container, false);
        ((Inicio) getActivity()).getSupportActionBar().setTitle("Centro de actividades");

        ListaActividadesAlumno();
        atributos = new ArrayList<>();
        rv = view.findViewById(R.id.rvactividades);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(lm);
        rv.setHasFixedSize(true);
        adapter = new ActividadesAdapter(atributos,getContext(), this);
        rv.setAdapter(adapter);

        /*modelos = new ArrayList<>();
        modelos.add(new Modelo(R.drawable.fondo_actividades, "Capítulo 1"));
        modelos.add(new Modelo(R.drawable.fondo_actividades, "Capítulo 2"));
        modelos.add(new Modelo(R.drawable.fondo_actividades, "Capítulo 3"));

        adpt = new ViewPagerAdapter(modelos,getContext());
        viewPager = view.findViewById(R.id.viewPagerA);
        viewPager.setAdapter(adpt);*/

        //Cambiar de color al momento de pasar el dedo al siguiente cardview
        //viewPager.setPadding(130,0,130,0);

       /* Integer [] colorsTemp = {
                getResources().getColor(R.color.blue),
                getResources().getColor(R.color.pink),
                getResources().getColor(R.color.green)
        };
        colors = colorsTemp;
        viewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if (position < (adpt.getCount() - 1) && position < (colors.length - 1)){
                    viewPager.setBackgroundColor((Integer) argb.evaluate(positionOffset, colors[position], colors[position + 1]));
                } else {
                    view.setBackgroundColor(colors[colors.length - 1]);
                }
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });*/

       //CardView de capitulos
       /*uno  = view.findViewById(R.id.capone);
       dos  = view.findViewById(R.id.captwo);
       tres = view.findViewById(R.id.capthree);

       uno.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent i = new Intent(getActivity(), Compromisos.class);
               startActivity(i);

           }
       });

        dos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Compromisos.class);
                startActivity(i);

            }
        });

        tres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(getActivity(), Compromisos.class);
                startActivity(i);

            }
        });*/
        return view;
    }

    private void ListaActividadesAlumno() {
        SolicitudesJSON json = new SolicitudesJSON() {
            @Override
            public void solicitudCompletada(JSONObject j) {
                try {
                    JSONArray jsa = j.getJSONArray("resultado");
                    for (int i=0; i<jsa.length(); i++) {
                        JSONObject jo      = jsa.getJSONObject(i);
                        String id          = jo.getString("id");
                        String titulo      = jo.getString("titulo");
                        String descripcion = jo.getString("descripcion");
                        String fecha       = jo.getString("fecha");
                        String hora        = jo.getString("hora");
                        String autor       = jo.getString("autor");
                        String fechaPubli  = jo.getString("fechaPublicacion");
                        String archivo     = jo.getString("archivo");
                        String grupo       = jo.getString("grupo");
                        String division    = jo.getString("division");

                        insertarActividad(id, titulo, descripcion, fechaPubli, fecha, hora, grupo, division,autor);
                    }
                } catch (JSONException e) {
                    //Toast.makeText(getActivity(), "Ocurrio un error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void solicitudErronea() { }
        };
        String usuario = Preferences.obtenerPreferencesString(getContext(), Preferences.PREFERENCES_USUARIO_LOGIN);
        json.JSONGET(getActivity(), SolicitudesJSON.URL_ACTIVIDADALUMNO + usuario);
    }

    private void insertarActividad(String id, String titulo, String descripcion, String fechaPubli, String fecha, String hora, String grupo, String division,String autor) {
        ActividadesAtributos aa = new ActividadesAtributos();
        aa.setIdA(id);
        aa.setTitulo(titulo);
        aa.setDescripcion(descripcion);
        aa.setFechaPublicacion(fechaPubli);
        aa.setFechaA(fecha);
        aa.setHoraA(hora);
        aa.setGrupo(grupo);
        aa.setDivision(division);
        aa.setAutor(autor);
        atributos.add(aa);
        adapter.notifyDataSetChanged();

    }
}