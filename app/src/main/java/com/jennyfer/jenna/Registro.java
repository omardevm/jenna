package com.jennyfer.jenna;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.text.InputType;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.jennyfer.jenna.Internet.SolicitudesJSON;
import com.jennyfer.jenna.Tools.VolleyRP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

public class Registro extends AppCompatActivity {
    //private static final String IP_REGISTRAR = "http://pita.x10host.com/PHP/Registro_INSERT.php";
    private static final String IP_REGISTRAR = "https://pitav3.000webhostapp.com/PRUEBAPHP/Registro_INSERT.php";

    private VolleyRP volleyRP;
    private RequestQueue requestQueue;

    private EditText nombre, paterno, materno, control, correo, contraseña;
    private Button crear;
    private RadioButton alumno, maestro;

    ArrayList<String> divi = new ArrayList<>();
    ArrayList<String> grup = new ArrayList<>();
    private Spinner division, semestre;

    //Calender
    private EditText fecha;
    private ImageView fechaNac;
    private int day,month,year;

    private CheckBox passshow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registro);

        listaDivision(); listaGrupo();

        volleyRP     = VolleyRP.getInstance(this);
        requestQueue = volleyRP.getRequestQueue();

        nombre     = findViewById(R.id.etNombre);
        nombre.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        paterno    = findViewById(R.id.etAPaterno);
        paterno .setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        materno    = findViewById(R.id.etAMaterno);
        materno .setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_CAP_SENTENCES);

        nombre     = findViewById(R.id.etNombre);
        paterno    = findViewById(R.id.etAPaterno);
        materno    = findViewById(R.id.etAMaterno);
        control    = findViewById(R.id.etControl);
        division   = findViewById(R.id.etDivision);
        semestre   = findViewById(R.id.etSemestre);
        correo     = findViewById(R.id.etCorreo);
        contraseña = findViewById(R.id.etContraseña);

        crear    = findViewById(R.id.btnCrear);
        alumno   = findViewById(R.id.rbAlumno);
        maestro  = findViewById(R.id.rbMaestro);

        //Para el calendario
        Calendar calendar = Calendar.getInstance();
        fecha    = findViewById(R.id.etFechaNAc);
        day      = calendar.get(Calendar.DAY_OF_MONTH);
        month    = calendar.get(Calendar.MONTH);
        year     = calendar.get(Calendar.YEAR);
        fechaNac = findViewById(R.id.btnCalender);
        passshow = findViewById(R.id.showpassword);

        //Mostrar contraseña
        passshow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked){
                    contraseña.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }else{
                    contraseña.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });

        //Botón para fecha
        fechaNac.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog dialog = new DatePickerDialog(Registro.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        month = monthOfYear + 1;
                        String diaFormateado = (dayOfMonth < 10)? 0 + String.valueOf(dayOfMonth):String.valueOf(dayOfMonth);
                        String mesFormateado = (monthOfYear < 10)? 0 + String.valueOf(monthOfYear):String.valueOf(monthOfYear);
                        fecha.setText(year + "-" +mesFormateado + "-" +diaFormateado);
                    }
                },year,month,day);
                dialog.show();
            }
        });

        //RadioButton para escoger tipo de usuario
        alumno.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                maestro.setChecked(false);
            }
        });
        maestro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alumno.setChecked(false);
            }
        });

        //Botón para crear cuenta
        crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String typeUsuario = "";
                if (alumno.isChecked()) typeUsuario = "Estudiante";
                else if (maestro.isChecked()) typeUsuario =  "Profesor";
                String grupito = semestre.getSelectedItem().toString();
                String divis = division.getSelectedItem().toString();
                NuevaCuenta(
                        typeUsuario,
                        getStringET(nombre),
                        getStringET(paterno),
                        getStringET(materno),
                        getStringET(control),
                        divis,
                        getStringET(fecha),
                        grupito,
                        getStringET(correo),
                        getStringET(contraseña));
            }
        });

    }


    private void NuevaCuenta(String StipoUsuario, String Snombre, String Spaterno, String Smaterno, final String Scontrol_rfc, String Sdivision,
                             String Sfecha, String Ssemestre, String Scorreo, String Scontraseña){
        if (!validarVacio()) return;

        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("tipoUsuario", StipoUsuario);
        hashMap.put("nombre", Snombre);
        hashMap.put("paterno", Spaterno);
        hashMap.put("materno", Smaterno);
        hashMap.put("usuario", Scontrol_rfc);
        hashMap.put("division", Sdivision);
        hashMap.put("fecha_nacimiento", Sfecha);
        hashMap.put("semestre", Ssemestre);
        hashMap.put("correo", Scorreo);
        hashMap.put("password", Scontraseña);

        JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.POST, IP_REGISTRAR, new JSONObject(hashMap), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject datos) {
                //VerificarPassword(datos);
                try {
                    String estado = datos.getString("resultado");
                    if (estado.equalsIgnoreCase("Se registro correctamente")) {
                        Toast.makeText(Registro.this, estado, Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(Registro.this, estado, Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    Toast.makeText(Registro.this, "Intentelo más tarde", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Registro.this, "No se pudo registrar", Toast.LENGTH_SHORT).show();
            }
        });
        VolleyRP.addToQueue(solicitud, requestQueue, this, volleyRP);
    }

    private String getStringET (EditText e){
        return e.getText().toString().trim();
    }

    private boolean validarVacio() {
        boolean valid = true;

        String uNombre = nombre.getText().toString();
        String uPaterno= paterno.getText().toString();
        String uMaterno = materno.getText().toString();
        String uControl = control.getText().toString();
        String uFecha = fecha.getText().toString();
        String uCorreo = correo.getText().toString();
        String sPassword = contraseña.getText().toString();

        if  (uNombre.trim().isEmpty() ) {
            nombre.setError("Llene el campo");
        } else {
            nombre.setError(null);
        }
        if (uPaterno.trim().isEmpty()) {
            paterno.setError("Llene el campo");
            valid = false;
        } else {
            paterno.setError(null);
        }
        if (uMaterno.trim().isEmpty()) {
            materno.setError("Llene el campo");
            valid = false;
        } else {
            materno.setError(null);
        }
        if  (uControl.trim().isEmpty() ) {
            control.setError("Llene el campo");
        } else {
            control.setError(null);
        }
        if (uFecha.trim().isEmpty()) {
            fecha.setError("Llene el campo");
            valid = false;
        } else {
            fecha.setError(null);
        }
        if  (uCorreo.trim().isEmpty() ) {
            correo.setError("Llene el campo");
        } else {
            correo.setError(null);
        }
        if (sPassword.trim().isEmpty() || sPassword.trim().length() < 4 || sPassword.trim().length() > 10) {
            contraseña.setError("Su contraseña debe tener entre 4 y 10 caracteres alfanuméricos");
            valid = false;
        } else {
            contraseña.setError(null);
        }

        return valid;
    }


    private void listaGrupo() {
        SolicitudesJSON json = new SolicitudesJSON() {
            @Override
            public void solicitudCompletada(JSONObject j) {
                try {
                    JSONArray jsa = j.getJSONArray("resultado");
                    for (int i=0; i<jsa.length(); i++) {
                        JSONObject jo      = jsa.getJSONObject(i);
                        String g    = jo.getString("grupo");
                        grup.add(g);
                    }
                    semestre.setAdapter(new ArrayAdapter<String>(Registro.this, android.R.layout.simple_spinner_dropdown_item, grup));
                } catch (JSONException e) {
                    Toast.makeText(Registro.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
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
                        String d    = jo.getString("division");

                        divi.add(d);
                    }
                    division.setAdapter(new ArrayAdapter<String>(Registro.this, android.R.layout.simple_spinner_dropdown_item, divi));
                } catch (JSONException e) {
                    Toast.makeText(Registro.this, "Ocurrio un error", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void solicitudErronea() { }
        };
        json.JSONGET(this, SolicitudesJSON.URL_DIVISION);
    }
}