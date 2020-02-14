package com.jennyfer.jenna.BuzonDeCorreo.Amigos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jennyfer.jenna.Adapter.SolicitudAdapter;
import com.jennyfer.jenna.Atributos.AmigosAtributos;
import com.jennyfer.jenna.Internet.SolicitudesJSON;
import com.jennyfer.jenna.R;
import com.jennyfer.jenna.Tools.AceptarSolicitud;
import com.jennyfer.jenna.Tools.EliminarSolicitud;
import com.jennyfer.jenna.Tools.EliminarSolicitudUsuarios;
import com.jennyfer.jenna.Services.Preferences;
import com.jennyfer.jenna.Atributos.SolicitudAtributos;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SolicitudAmigos extends Fragment {

    private RecyclerView rv;
    private SolicitudAdapter adapter;
    private List<SolicitudAtributos> list;
    private LinearLayout sinSolicitud;

    private EventBus bus = EventBus.getDefault();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.solicitud_amigos,container, false);

        sinSolicitud = view.findViewById(R.id.noSolicitud);

        list = new ArrayList<>();
        rv = view.findViewById(R.id.rvsolicitud);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(lm);
        adapter = new SolicitudAdapter(list,getContext(),this);
        rv.setAdapter(adapter);


        //Se obtiene el usuario que esta coenctado en el celular o logueado
        /*String usuario = Preferences.obtenerPreferencesString(getContext(),Preferences.PREFERENCES_USUARIO_LOGIN);
        SolicitudJSON(GETALL_USERS + usuario);*/
       verificarSolicitudes();

        return view;
    }//Llave view

    public void verificarSolicitudes(){
        if (list.isEmpty()) {
            sinSolicitud.setVisibility(View.VISIBLE);
            rv.setVisibility(View.GONE);
        }else {
            sinSolicitud.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
        }
    }

    public void tarjetasSolicitud(String foto, String nombre, String id, int estado, String carrera){
        SolicitudAtributos solicitud = new SolicitudAtributos();
        solicitud.setFotoPerfil(foto);
        solicitud.setNombre(nombre);
        solicitud.setId(id);
        solicitud.setEstado(estado);
        solicitud.setCarrera(carrera);
        list.add(0,solicitud);
        actualizarTarjetas();
        verificarSolicitudes();
    }

    public void tarjetasSolicitud (SolicitudAtributos solicitud){
        list.add(0,solicitud);
        actualizarTarjetas();
        verificarSolicitudes();
    }

    public void actualizarTarjetas(){
        adapter.notifyDataSetChanged();
        verificarSolicitudes();
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


    @Subscribe (threadMode = ThreadMode.MAIN)
    public void ejecutarLlamada(SolicitudAtributos b){ tarjetasSolicitud(b); }

    @Subscribe (threadMode = ThreadMode.MAIN)
    public void cancelarSolicitud (EliminarSolicitud e) { eliminarTarjeta(e.getId());}

    @Subscribe (threadMode = ThreadMode.MAIN)
    public void cancelarSolicitud(final String id) {
        String EMISOR   = Preferences.obtenerPreferencesString(getContext(), Preferences.PREFERENCES_USUARIO_LOGIN);
        SolicitudesJSON s = new SolicitudesJSON() {
            @Override
            public void solicitudCompletada(JSONObject j) {
                try {
                    String respuesta = j.getString("respuesta");
                    if (respuesta.equals("200")) {
                        eliminarTarjeta(id);
                        bus.post(new EliminarSolicitudUsuarios(id));
                        Toast.makeText(getContext(), j.getString("respuesta"), Toast.LENGTH_SHORT).show();
                    } else if (respuesta.equals("-1")) {
                        Toast.makeText(getContext(), j.getString("respuesta"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "No se pudo cancelar", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void solicitudErronea() {

            }
        };
        HashMap<String,String> h = new HashMap<>();
        h.put("emisor", EMISOR);
        h.put("receptor", id);
        s.JSONPOST(getContext(), SolicitudesJSON.URL_CANCELAR,h);
    }

    @Subscribe (threadMode = ThreadMode.MAIN)
    public void aceptarSolicitud (final String id){
        String EMISOR   = Preferences.obtenerPreferencesString(getContext(), Preferences.PREFERENCES_USUARIO_LOGIN);
        SolicitudesJSON json = new SolicitudesJSON() {
            @Override
            public void solicitudCompletada(JSONObject j) {
                try {
                    String respuesta  = j.getString("respuesta");
                    if (respuesta.equals("200")){
                        bus.post(new AceptarSolicitud(id));
                        eliminarTarjeta(id);
                        AmigosAtributos a = new AmigosAtributos();
                        a.setId(id);
                        a.setNombre(j.getString("nombre"));
                        a.setFotoPerfil(j.getString("fotoPerfil"));
                        a.setMensajito(j.getString("UltimoMensaje"));
                        a.setHoramensajito(j.getString("hora"));
                        a.setTipoM(j.getString("typeMensaje"));
                        a.setCarrera(j.getString("division"));
                        bus.post(a);
                    }else if (respuesta.equals("-1")){
                        Toast.makeText(getContext(), "Error al enviar solicitud", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Error al enviar solicitud", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void solicitudErronea() {
                Toast.makeText(getContext(), "No se pudo enviar la solicitud", Toast.LENGTH_SHORT).show();
            }
        };
        HashMap<String,String> h = new HashMap<>();
        h.put("emisor", EMISOR);
        h.put("receptor", id);
        json.JSONPOST(getContext(), SolicitudesJSON.URL_ACEPTARSOLICITUD,h);
    }

    public void eliminarTarjeta(String id) {
        for (int i=0; i<list.size(); i++){
            if (list.get(i).getId().equals(id)){
                list.remove(i);
                actualizarTarjetas();
            }
        }
    }



}//Llave principal
