package com.jennyfer.jenna.BuzonDeCorreo.Amigos;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.jennyfer.jenna.Adapter.AmigosAdapter;
import com.jennyfer.jenna.Internet.SolicitudesJSON;
import com.jennyfer.jenna.R;
import com.jennyfer.jenna.Atributos.AmigosAtributos;
import com.jennyfer.jenna.Tools.EliminarAU;
import com.jennyfer.jenna.Tools.EliminarAmigos;
import com.jennyfer.jenna.Services.Preferences;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ListAmigos extends Fragment {

    private List<AmigosAtributos> atributosList;
    private AmigosAdapter adapter;
    private RecyclerView rv;
    private EventBus bus = EventBus.getDefault();
    private LinearLayout vacio;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.list_amigos, container, false);

        vacio = view.findViewById(R.id.noAmigos);

        atributosList = new ArrayList<>();

        rv = view.findViewById(R.id.rvAmigosM);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(lm);

        adapter = new AmigosAdapter(atributosList,getContext(),this);
        rv.setAdapter(adapter);

        //SolicitudJSON();
        verificarAmigos();

        return view;
    }

    public void verificarAmigos(){
        if (atributosList.isEmpty()) {
            vacio.setVisibility(View.VISIBLE);
            rv.setVisibility(View.GONE);
        }else {
            vacio.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
        }
    }

    public void agregaramigos (String fotoA, String nombre, String mensaje, String hora, String usuario){
        AmigosAtributos amigosAtributos = new AmigosAtributos();
        amigosAtributos.setFotoPerfil(fotoA);
        amigosAtributos.setNombre(nombre);
        amigosAtributos.setMensajito(mensaje);
        amigosAtributos.setHoramensajito(hora);
        amigosAtributos.setId(usuario);
        atributosList.add(amigosAtributos);
        adapter.notifyDataSetChanged();
        verificarAmigos();
    }

    public void agregaramigos(AmigosAtributos a){
        atributosList.add(a);
        adapter.notifyDataSetChanged();
        verificarAmigos();
    }

    public void SolicitudJSON(){
        /*JsonObjectRequest solicitud = new JsonObjectRequest(GETALL_USERS, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject datos) {
                try {
                    String propioUsuario = Preferences.obtenerPreferencesString(getContext(),Preferences.PREFERENCES_USUARIO_LOGIN);
                    String todosDatos = datos.getString("resultado");
                    JSONArray array = new JSONArray(todosDatos);
                    for (int i=0;i<array.length();i++){
                        JSONObject JB = array.getJSONObject(i);
                        if (!propioUsuario.equals(JB.getString("usuario"))){
                            if (!JB.getString("usuario").equals(propioUsuario)){
                                agregaramigos(R.drawable.teacher,JB.getString("nombre")+" "+JB.getString("paterno")
                                        +" "+JB.getString("materno"), "mensaje ","00:00",JB.getString("usuario"));
                            }
                        }
                    }
                }catch (JSONException e){
                    Toast.makeText(getContext(), "No se descompuso el JSON", LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getContext(), "¡Vaya! Parece que ha ocurrido un error inesperado", LENGTH_SHORT).show();
            }
        });
        VolleyRP.addToQueue(solicitud,requestQueue,getContext(),volleyRP);*/
    }

    @Subscribe (threadMode = ThreadMode.MAIN)
    public void ejecutarLlamada(AmigosAtributos a){
        agregaramigos(a);
    }

    @Subscribe (threadMode = ThreadMode.MAIN)
    public void eliminarAmigos (EliminarAmigos ea){
        for (int i=0; i<atributosList.size(); i++){
            if (atributosList.get(i).getId().equals(ea.getId())){
                atributosList.remove(i);
                actualizarTarjetas();
            }
        }
    }

    @Subscribe (threadMode = ThreadMode.MAIN)
    public void eliminarAmigo (final String id){
        String EMISOR   = Preferences.obtenerPreferencesString(getContext(), Preferences.PREFERENCES_USUARIO_LOGIN);
        SolicitudesJSON s = new SolicitudesJSON() {
            @Override
            public void solicitudCompletada(JSONObject j) {
                try {
                    String respuesta = j.getString("respuesta");
                    if (respuesta.equals("200")) {
                        bus.post(new EliminarAU(id));
                        for (int i=0; i<atributosList.size(); i++){
                            if (atributosList.get(i).getId().equals(id)){
                                atributosList.remove(i);
                                actualizarTarjetas();
                            }
                        }
                        Toast.makeText(getContext(), j.getString("respuesta"), Toast.LENGTH_SHORT).show();
                    } else if (respuesta.equals("-1")) {
                        Toast.makeText(getContext(), j.getString("respuesta"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "No se pudo eliminar al usuario", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void solicitudErronea() {

            }
        };
        HashMap<String,String> h = new HashMap<>();
        h.put("emisor", EMISOR);
        h.put("receptor", id);
        s.JSONPOST(getContext(), SolicitudesJSON.URL_ELIMINARAMIGO,h);
    }

    public void actualizarTarjetas(){
        adapter.notifyDataSetChanged();
        verificarAmigos();
    }


    @Override
    public void onPause() {
        super.onPause();
        bus.unregister(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        bus.register(this);
    }



}//Llave principal

