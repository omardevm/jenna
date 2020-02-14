package com.jennyfer.jenna.PerfilUsuario;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.bumptech.glide.Glide;
import com.jennyfer.jenna.Imagen.UploadPhoto;
import com.jennyfer.jenna.Internet.SolicitudesJSON;
import com.jennyfer.jenna.R;
import com.jennyfer.jenna.Registro;
import com.jennyfer.jenna.Services.Preferences;
import com.jennyfer.jenna.Tools.VolleyRP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class EditaPerfil extends AppCompatActivity {
    private VolleyRP volleyRP     = VolleyRP.getInstance(this);
    private RequestQueue requestQueue = volleyRP.getRequestQueue();
    private int day,month,year;
    private EditText tvNombre, tvPaterno,tvMaterno,tvFecha, tvUsuario, tvCorreo, id;
    private ImageView fotoSave;
    private LinearLayout cambios;
    //ArrayList<String> divi = new ArrayList<>();
    //ArrayList<String> grup = new ArrayList<>();
    private Spinner division, semestre;
    //private RadioButton typeUserEstudiante, typeUserProfesor;

    String [] carreras = new String[]
            {
                    "Ingeniería Bioquímica",
                    "Ingeniería Eléctrica",
                    "Ingeniería Electrónica",
                    "Ingeniería en Administración",
                    "Ingeniería en Animación Digital y Efectos",
                    "Ingeniería en Gestión Empresarial",
                    "Ingeniería en Sistemas Computacionales",
                    "Ingeniería Industrial",
                    "Ingeniería Informática",
                    "Ingeniería Mecánica",
                    "Ingeniería Mecatrónica",
                    "Ingeniería Petrolera",
                    "Ingeniería Química"
            };
    String [] grupos = new String[]
            {
              "1A", "1B", "1C",
              "2A", "2B", "2C",
              "3A", "3B", "3C",
              "4A", "4B", "4C"
            };

    List<String> listaCarreras = new ArrayList<>();
    List<String> listaGrupos = new ArrayList<>();
    ArrayAdapter<String> adpater;

    @Override
    protected void onCreate(Bundle savedInstanceState) { super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil_edit);

        actualizarInformacion();
        //listaDivision();
        //listaGrupo();

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dialog = new AlertDialog.Builder(EditaPerfil.this);
                dialog.setCancelable(false);
                dialog.setTitle("Cambios no guardados");
                dialog.setMessage("¿Descartar cambios?" );
                dialog.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        finish();
                    }
                })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                //Action for "Cancel".
                                dialog.cancel();
                            }
                        });

                final AlertDialog alert = dialog.create();
                alert.show();
            }
        });
        //EditText
        id  = findViewById(R.id.id);
        tvNombre        = findViewById(R.id.edit_nombre);
        tvPaterno       = findViewById(R.id.edit_paterno);
        tvMaterno       = findViewById(R.id.edit_materno);

        Calendar calendar = Calendar.getInstance();
        day      = calendar.get(Calendar.DAY_OF_MONTH);
        month    = calendar.get(Calendar.MONTH);
        year     = calendar.get(Calendar.YEAR);
        tvFecha         = findViewById(R.id.edit_fecha);
        tvFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(EditaPerfil.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        month = monthOfYear + 1;
                        String diaFormateado = (dayOfMonth < 10)? 0 + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                        String mesFormateado = (monthOfYear < 10)? 0 + String.valueOf(monthOfYear):String.valueOf(monthOfYear);
                        tvFecha.setText(year + "-" +mesFormateado + "-" +diaFormateado);
                    }
                },year,month,day);
                dialog.show();
            }
        });
        tvUsuario       = findViewById(R.id.edit_matricula);
        tvCorreo        = findViewById(R.id.edit_correo);

        //ImagenView
        fotoSave        = findViewById(R.id.fotoEdit);

        //Spinner
        division = findViewById(R.id.edit_division);
        semestre = findViewById(R.id.edit_semestre);
        Collections.addAll(listaCarreras,carreras);
        adpater = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listaCarreras);
        division.setAdapter(adpater);
        Collections.addAll(listaGrupos,grupos);
        adpater = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, listaGrupos);
        semestre.setAdapter(adpater);

        /*CheckBox
        typeUserProfesor   = findViewById(R.id.edit_tipoUsuarioProfesor);
        typeUserEstudiante = findViewById(R.id.edit_tipoUsuarioEstudiante);*/

        cambios = findViewById(R.id.linear);
        cambios.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*String user = "";
                if (typeUserEstudiante.isChecked()) user = "Estudiante";
                else if (typeUserProfesor.isChecked()) user =  "Profesor";*/
                String grupito = semestre.getSelectedItem().toString();
                String divis = division.getSelectedItem().toString();
                actualizarDatos(
                        getStringET(id),
                        //user,
                        getStringET(tvNombre),
                        getStringET(tvPaterno),
                        getStringET(tvMaterno),
                        getStringET(tvUsuario),
                        getStringET(tvFecha),
                        getStringET(tvCorreo),
                        divis,  grupito);
            }
        });

    }
    private String getStringET (EditText e){
        return e.getText().toString();
    }

    public void actualizarDatos(String id  , String nombre, String paterno, String materno, String matricula, String fecha, String correo, String division, String semestre) {

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("id", id);
        //hashMap.put("tipoUsuario", tipo);
        hashMap.put("nombre", nombre);
        hashMap.put("paterno", paterno);
        hashMap.put("materno", materno);
        hashMap.put("usuario", matricula);
        hashMap.put("division", division);
        hashMap.put("fecha_nacimiento", fecha);
        hashMap.put("semestre", semestre);
        hashMap.put("correo", correo);

        JsonObjectRequest actualizar = new JsonObjectRequest(Request.Method.POST, SolicitudesJSON.URL_USUARIOUPDATE, new JSONObject(hashMap), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject datos) {
                try {
                    String update = datos.getString("resultado");
                    Toast.makeText(EditaPerfil.this, update, Toast.LENGTH_SHORT).show();
                    finish();
                } catch (JSONException e) {
                    Toast.makeText(EditaPerfil.this, "Intentelo más tarde", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(EditaPerfil.this, "No se pudo actualizar", Toast.LENGTH_SHORT).show();
            }
        });
        VolleyRP.addToQueue(actualizar, requestQueue, this, volleyRP);
    }



        /*public void onRadioButton(View view) {
        boolean marcado = ((RadioButton) view).isChecked();
        switch (view.getId()) {
            case R.id.edit_tipoUsuarioProfesor:
                if (marcado) { String pr = "Profesor"; }
                break;

            case R.id.edit_tipoUsuarioEstudiante:
                if (marcado) { String es = "Estudiante"; }
                break;
        }
    }*/

    @Override
    public void onResume() {
        super.onResume();
        actualizarInformacion();
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder dialog = new AlertDialog.Builder(EditaPerfil.this);
        dialog.setCancelable(false);
        dialog.setTitle("Cambios no guardados");
        dialog.setMessage("¿Descartar cambios?" );
        dialog.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int id) {
                    finish();
                }
            })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
            final AlertDialog alert = dialog.create();
            alert.show();
    }

    public void actualizarInformacion () {
        String usuario = Preferences.obtenerPreferencesString(getApplicationContext(), Preferences.PREFERENCES_USUARIO_LOGIN);
        SolicitudesJSON jsonS = new SolicitudesJSON() {
            @Override
            public void solicitudCompletada(JSONObject j) {
                try {
                    String respuesta = j.getString("resultado");
                    if (respuesta.equals("CC")) {
                        JSONObject json = new JSONObject(j.getString("datos"));

                        id.setText(json.getString("id"));
                        tvUsuario.setText(json.getString("usuario"));
                        tvNombre.setText(json.getString("nombre"));
                        tvNombre.setSelection(tvNombre.getText().length());
                        tvPaterno.setText(json.getString("paterno"));
                        tvMaterno.setText(json.getString("materno"));
                        tvFecha.setText(json.getString("fecha_nacimiento"));
                        tvCorreo.setText(json.getString("correo"));

                        int spinnerPosition = listaCarreras.indexOf(json.getString("division"));
                        division.setSelection(spinnerPosition);

                        int positionSpinner = listaGrupos.indexOf(json.getString("semestre"));
                        semestre.setSelection(positionSpinner);

                       /* String p = "Profesor";
                        String tipo = json.getString("tipousuario");
                        if (tipo.equals(p)){ typeUserProfesor.setChecked(true);
                        }else{ typeUserEstudiante.setChecked(true); }*/

                        json.getString("tipousuario");

                        final String foto = json.getString("fotoPerfil");
                        Glide.with(getApplicationContext())
                                .load(foto)
                                .error(R.drawable.student)
                                .into(fotoSave);

                        fotoSave.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                Intent intent = new Intent(getApplicationContext(), UploadPhoto.class);
                                intent.putExtra("fotoPerfil", foto);
                                startActivity(intent);
                            }
                        });
                    }
                } catch (JSONException e) {
                    Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void solicitudErronea() {
                Toast.makeText(getApplicationContext(), "NO SE PUDO", Toast.LENGTH_SHORT).show();
            }
        };
        jsonS.JSONGET(getApplicationContext(), SolicitudesJSON.URL_USUARIOID + usuario);
    }

}
