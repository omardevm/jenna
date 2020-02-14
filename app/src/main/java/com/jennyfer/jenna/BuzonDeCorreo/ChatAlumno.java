package com.jennyfer.jenna.BuzonDeCorreo;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.jennyfer.jenna.Adapter.UsuariosAdapter;
import com.jennyfer.jenna.Atributos.AmigosAtributos;
import com.jennyfer.jenna.Atributos.BuscadorAtributos;
import com.jennyfer.jenna.Atributos.SolicitudAtributos;
import com.jennyfer.jenna.Inicio;
import com.jennyfer.jenna.Internet.SolicitudesJSON;
import com.jennyfer.jenna.R;
import com.jennyfer.jenna.Services.Preferences;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ChatAlumno extends Fragment {

    private TabLayout tab;
    private ViewPager viewPager;
    private EventBus bus;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        container.removeAllViews();

        View view = inflater.inflate(R.layout.chat_alumno, container, false);
        ((Inicio) getActivity()).getSupportActionBar().setTitle("Buzón de correo");

        bus = EventBus.getDefault();

        tab       = view.findViewById(R.id.tabListAmigos);
        viewPager = view.findViewById(R.id.viewListAmigos);

        tab.setupWithViewPager(viewPager);
        viewPager.setAdapter(new UsuariosAdapter(getChildFragmentManager()));
        viewPager.setOffscreenPageLimit(3);

        SolicitudesJSON json = new SolicitudesJSON() {
            @Override
            public void solicitudCompletada(JSONObject j) {
                //Toast.makeText(getActivity(),j.toString(),Toast.LENGTH_SHORT).show();
                try {
                    JSONArray ja = j.getJSONArray("resultado");
                    for (int i = 0; i < ja.length(); i++) {
                        JSONObject object = ja.getJSONObject(i);
                        String id = object.getString("usuario");
                        if (!id.equals(Preferences.obtenerPreferencesString(getActivity(), Preferences.PREFERENCES_USUARIO_LOGIN))) {
                            String nombre = object.getString("nombre") + " " + object.getString("paterno") + " " + object.getString("materno");
                            String estado = object.getString("estado");
                            String carrera = object.getString("division");
                            String foto = object.getString("fotoPerfil");
                            BuscadorAtributos comuni = new BuscadorAtributos();
                            comuni.setId(id);
                            comuni.setFotoPerfil(foto);
                            comuni.setNombre(nombre);
                            comuni.setEstado(1);
                            comuni.setCarrera(carrera);
                            SolicitudAtributos sa;
                            switch (estado) {
                                case "2":
                                    comuni.setEstado(2);
                                    sa = new SolicitudAtributos();
                                    sa.setId(id);
                                    sa.setFotoPerfil(foto);
                                    sa.setNombre(nombre);
                                    sa.setCarrera(carrera);
                                    sa.setEstado(2);
                                    bus.post(sa);
                                    break;
                                case "3":
                                    comuni.setEstado(3);
                                    sa = new SolicitudAtributos();
                                    sa.setId(id);
                                    sa.setFotoPerfil(foto);
                                    sa.setNombre(nombre);
                                    sa.setCarrera(carrera);
                                    sa.setEstado(3);
                                    bus.post(sa);
                                    break;
                                case "4":
                                    comuni.setEstado(4);
                                    AmigosAtributos a = new AmigosAtributos();
                                    a.setId(id);
                                    a.setNombre(nombre);
                                    a.setFotoPerfil(foto);
                                    a.setMensajito(object.getString("mensaje"));
                                    a.setTipoM(object.getString("tipoMensaje"));
                                    String hora = object.getString("horaMensaje");
                                    String h[] = hora.split("\\,");
                                    a.setHoramensajito(h[0]);
                                    a.setCarrera(carrera);
                                    bus.post(a);
                                    break;
                            }
                            bus.post(comuni);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void solicitudErronea() {
                Toast.makeText(getContext(),"Ocurrio un error",Toast.LENGTH_SHORT).show();

            }
        };
        String usuario = Preferences.obtenerPreferencesString(getActivity(),Preferences.PREFERENCES_USUARIO_LOGIN);
        json.JSONGET(getContext(), SolicitudesJSON.URL_GETDATOS + usuario);

        return view;
    }
}//Llave principal
