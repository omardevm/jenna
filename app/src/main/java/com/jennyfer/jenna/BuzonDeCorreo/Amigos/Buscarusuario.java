package com.jennyfer.jenna.BuzonDeCorreo.Amigos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.jennyfer.jenna.Adapter.BuscadorAdapter;
import com.jennyfer.jenna.Atributos.AmigosAtributos;
import com.jennyfer.jenna.Atributos.BuscadorAtributos;
import com.jennyfer.jenna.Atributos.SolicitudAtributos;
import com.jennyfer.jenna.ComunicaciónFragments.Comunicacion;
import com.jennyfer.jenna.Internet.SolicitudesJSON;
import com.jennyfer.jenna.R;
import com.jennyfer.jenna.Services.Preferences;
import com.jennyfer.jenna.Tools.AceptarSolicitud;
import com.jennyfer.jenna.Tools.EliminarAU;
import com.jennyfer.jenna.Tools.EliminarAmigos;
import com.jennyfer.jenna.Tools.EliminarSolicitud;
import com.jennyfer.jenna.Tools.EliminarSolicitudUsuarios;
import com.jennyfer.jenna.Tools.RecibirSolicitud;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Buscarusuario extends Fragment {

    private RecyclerView rv;
    private BuscadorAdapter adapter;
    private List<BuscadorAtributos> atributos;//Conectada al adapter
    private List<BuscadorAtributos> listAuxiliar;
    private SearchView s;
    private LinearLayout lvacio;

    private EventBus bus = EventBus.getDefault();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.buscar_usuario, container, false);
        setHasOptionsMenu(true);
        lvacio = view.findViewById(R.id.noUsuarioExistente);

        atributos = new ArrayList<>();
        listAuxiliar = new ArrayList<>();

        rv = view.findViewById(R.id.rvSearchUsuarios);
        LinearLayoutManager lm = new LinearLayoutManager(getContext());
        rv.setLayoutManager(lm);
        adapter = new BuscadorAdapter(atributos,getContext(),this);
        rv.setAdapter(adapter);

        s = view.findViewById(R.id.search);
        s.setQueryHint("Buscar");
        s.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                buscador("" + newText);
                return false;
            }
        });

        verificarsiexisteusuario();
        return view;
    }

    public void verificarsiexisteusuario(){
        if (atributos.isEmpty()) {
            lvacio.setVisibility(View.VISIBLE);
            rv.setVisibility(View.GONE);
        }else {
            lvacio.setVisibility(View.GONE);
            rv.setVisibility(View.VISIBLE);
        }
    }

    public void insertarUsuario(String foto, String nombre, int estado,String id, String carrera){
        BuscadorAtributos buscadorAtributos = new BuscadorAtributos();
        buscadorAtributos.setFotoPerfil(foto);
        buscadorAtributos.setNombre(nombre);
        buscadorAtributos.setEstado(estado);
        buscadorAtributos.setId(id);
        buscadorAtributos.setCarrera(carrera);
        atributos.add(buscadorAtributos);
        listAuxiliar.add(buscadorAtributos);
        adapter.notifyDataSetChanged();
        verificarsiexisteusuario();
    }

    public void insertarUsuario(BuscadorAtributos b){
        atributos.add(b);
        listAuxiliar.add(b);
        adapter.notifyDataSetChanged();
        verificarsiexisteusuario();
    }

    public void buscador (String texto){
        atributos.clear();
        for (int i = 0; i<listAuxiliar.size(); i++){

            if (listAuxiliar.get(i).getNombre().toLowerCase().contains(texto.toLowerCase())){
                atributos.add(listAuxiliar.get(i));
            }
        }
        adapter.notifyDataSetChanged();
        verificarsiexisteusuario();
    }


    @Subscribe
    public void ejecutarLlamada(BuscadorAtributos b){ insertarUsuario(b); }

    @Subscribe (threadMode = ThreadMode.MAIN)
    public void cancelarSolicitud (EliminarSolicitudUsuarios e){
        cambiarEstado(e.getId(),1);
    }

    @Subscribe (threadMode = ThreadMode.MAIN)
    public void aceptarSolicitud (AceptarSolicitud a){ cambiarEstado(a.getId(),4); }

    @Subscribe (threadMode = ThreadMode.MAIN)
    public void eliminarUsuario (EliminarAU e){ cambiarEstado(e.getId(),1); }

    @Subscribe (threadMode = ThreadMode.MAIN)
    public void recibirSolicitud (RecibirSolicitud e){ cambiarEstado(e.getId(),3); }

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
    public void eliminarUsuario (final String id){
        String EMISOR   = Preferences.obtenerPreferencesString(getContext(), Preferences.PREFERENCES_USUARIO_LOGIN);
        SolicitudesJSON s = new SolicitudesJSON() {
            @Override
            public void solicitudCompletada(JSONObject j) {
                try {
                    String respuesta = j.getString("respuesta");
                    if (respuesta.equals("200")) {
                        cambiarEstado(id,1);
                        bus.post(new EliminarAmigos(id));
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

    @Subscribe (threadMode = ThreadMode.MAIN)
    public void cancelarSolicitud(final String id){
        String EMISOR   = Preferences.obtenerPreferencesString(getContext(), Preferences.PREFERENCES_USUARIO_LOGIN);
        SolicitudesJSON s = new SolicitudesJSON() {
            @Override
            public void solicitudCompletada(JSONObject j) {
                try {
                    String respuesta = j.getString("respuesta");
                    if (respuesta.equals("200")) {
                        cambiarEstado(id,1);
                        bus.post(new EliminarSolicitud(id));
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
    public void enviarSolicitud(final String id){
        final String EMISOR   = Preferences.obtenerPreferencesString(getContext(), Preferences.PREFERENCES_USUARIO_LOGIN);

        SolicitudesJSON json = new SolicitudesJSON() {
            @Override
            public void solicitudCompletada(JSONObject j) {
                try {
                    String respuesta  = j.getString("respuesta");
                    if (respuesta.equals("200")){
                        Toast.makeText(getContext(), "Se ha enviado la solicitud", Toast.LENGTH_SHORT).show();

                        SolicitudAtributos s = new SolicitudAtributos();
                        s.setId(id);
                        s.setEstado(j.getInt("estado"));
                        s.setNombre(j.getString("nombre"));
                        s.setCarrera(j.getString("carrera"));
                        s.setFotoPerfil(j.getString("fotoPerfil"));
                        bus.post(s);

                        cambiarEstado(id,2);
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
        json.JSONPOST(getContext(), SolicitudesJSON.URL_ENVIARSOLICITUD,h);
    }

    @Subscribe (threadMode = ThreadMode.MAIN)
    public void aceptarSolicitud(final String id){
        String EMISOR   = Preferences.obtenerPreferencesString(getContext(), Preferences.PREFERENCES_USUARIO_LOGIN);

        SolicitudesJSON json = new SolicitudesJSON() {
            @Override
            public void solicitudCompletada(JSONObject j) {
                try {
                    String respuesta  = j.getString("respuesta");
                    if (respuesta.equals("200")){
                        cambiarEstado(id,4);
                        bus.post(new EliminarSolicitud(id));
                        AmigosAtributos a = new AmigosAtributos();
                        a.setId(id);
                        a.setNombre(j.getString("nombre"));
                        a.setFotoPerfil(j.getString("fotoPerfil"));
                        a.setMensajito(j.getString("UltimoMensaje"));
                        a.setHoramensajito(j.getString("hora"));
                        a.setTipoM(j.getString("typeMensaje"));
                        bus.post(a);
                    }else if (respuesta.equals("-1")){
                        Toast.makeText(getContext(), "Error al enviar solicitud", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(getContext(), "Error al enviar solicitud catch", Toast.LENGTH_SHORT).show();
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

    @Subscribe (threadMode = ThreadMode.MAIN)
    private void cambiarEstado (String id, int estado){
        for (int i=0; i<listAuxiliar.size(); i++){
            if (listAuxiliar.get(i).getId().equals(id)){
                listAuxiliar.get(i).setEstado(estado);
                //break;
            }
        }

        int posicionUser = -1;
        for (int i=0; i<atributos.size(); i++){
            if (atributos.get(i).getId().equals(id)){
                atributos.get(i).setEstado(estado);
                posicionUser = i ;
                //break;
            }
        }

        if (posicionUser != 1){
            adapter.notifyItemChanged(posicionUser);
        } else {
            Toast.makeText(getContext(),"No se actualizo el estado",Toast.LENGTH_SHORT).show();
        }
    }

    private Comunicacion obtenerID (String id){
        for (int i=0; i<atributos.size(); i++) {
            if (atributos.get(i).getId().equals(id)) {
                return atributos.get(i);
            }
        }
        return null;
    }


    /*@Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu, menu);
        MenuItem searchItem = menu.findItem(R.id.buscar);
        SearchManager searchManager = (SearchManager) getActivity().getSystemService(Context.SEARCH_SERVICE);

        if (searchItem != null) {
            searchView = (SearchView) searchItem.getActionView();
        }
        if (searchView != null) {
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getActivity().getComponentName()));

            queryTextListener = new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String newText) {
                    buscador("" + newText);
                    return true;
                }
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }
            };
            searchView.setOnQueryTextListener(queryTextListener);
        }
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.buscar:
                // Not implemented here
                return false;
            default:
                break;
        }
        s.setOnQueryTextListener(queryTextListener);
        return super.onOptionsItemSelected(item);
    }*/

}