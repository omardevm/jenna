package com.jennyfer.jenna.PerfilUsuario;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.jennyfer.jenna.Imagen.UploadPhoto;
import com.jennyfer.jenna.Inicio;
import com.jennyfer.jenna.Internet.SolicitudesJSON;
import com.jennyfer.jenna.R;
import com.jennyfer.jenna.Services.Preferences;

import org.json.JSONException;
import org.json.JSONObject;

public class PerfilAlumno extends Fragment {
    private TextView tvNombre, tvPaterno,tvMaterno,tvFecha, tvUsuario, tvDivision, tvSemestre, tvCorreo, typeUser;
    private ImageView f, infoEdit;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        container.removeAllViews();
        View view = inflater.inflate(R.layout.perfil, container, false);

        ((Inicio) getActivity()).getSupportActionBar().setTitle("Mi perfil");

        tvNombre        = view.findViewById(R.id.key_nombre);
        tvPaterno       = view.findViewById(R.id.key_paterno);
        tvMaterno       = view.findViewById(R.id.key_materno);
        tvFecha         = view.findViewById(R.id.key_fecha);
        tvUsuario       = view.findViewById(R.id.key_control);
        tvDivision      = view.findViewById(R.id.key_division);
        tvSemestre      = view.findViewById(R.id.key_semestre);
        tvCorreo        = view.findViewById(R.id.key_correo);
        typeUser        = view.findViewById(R.id.key_tipousuario);
        f               = view.findViewById(R.id.perfil);
        infoEdit        = view.findViewById(R.id.editInfo);

        infoEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), EditaPerfil.class);
                startActivity(intent);
            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        actualizarInformacion();
    }

    public void actualizarInformacion (){
        String usuario = Preferences.obtenerPreferencesString(getActivity(),Preferences.PREFERENCES_USUARIO_LOGIN);
        SolicitudesJSON jsonS = new SolicitudesJSON() {
            @Override
            public void solicitudCompletada(JSONObject j) {
                try {
                    String respuesta = j.getString("resultado");
                    if (respuesta.equals("CC")) {
                        JSONObject json = new JSONObject(j.getString("datos"));

                        json.getString("id");
                        tvUsuario.setText(json.getString("usuario"));
                        tvNombre.setText(json.getString("nombre"));
                        tvPaterno.setText(json.getString("paterno"));
                        tvMaterno.setText(json.getString("materno"));
                        tvDivision.setText(json.getString("division"));
                        tvFecha.setText(json.getString("fecha_nacimiento"));
                        tvSemestre.setText(json.getString("semestre"));
                        tvCorreo.setText(json.getString("correo"));
                        typeUser.setText(json.getString("tipousuario"));

                        String foto = json.getString("fotoPerfil");
                        Glide.with(getActivity())
                                .load(foto)
                                .error(R.drawable.student)
                                .into(f);


                    }
                } catch (JSONException e) { Toast.makeText(getContext(), "ERROR", Toast.LENGTH_SHORT).show(); }
            }

            @Override
            public void solicitudErronea() { Toast.makeText(getContext(), "NO SE PUDO", Toast.LENGTH_SHORT).show(); }};
        jsonS.JSONGET(getContext(), SolicitudesJSON.URL_USUARIOID + usuario);
    }

}
