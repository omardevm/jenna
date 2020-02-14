package com.jennyfer.jenna;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.jennyfer.jenna.Internet.SolicitudesJSON;
import com.jennyfer.jenna.Services.Preferences;
import com.jennyfer.jenna.Tools.VolleyRP;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

import static android.widget.Toast.LENGTH_SHORT;

public class Login extends AppCompatActivity {

    private EditText etUsuario, etContraseña;
    private Button btnAcceder;
    private VolleyRP volleyRP;
    private RequestQueue requestQueue;
    private String USER = "";
    private String PASSWORD = "";
    private CheckBox passshow;
    private TextView register, contra;

    private EditText clave;

    private static final String IP = "https://pitav3.000webhostapp.com/PRUEBAPHP/Login_GETID.php?usuario=";
    //private static final String IP = "http://pita.x10host.com/PHP/Login_GETID.php?usuario=";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        if (Preferences.obtenerPreferencesBoolean(this, Preferences.PREFERENCES_ESTADO)){
            Intent i = new Intent(Login.this, Inicio.class);
            startActivity(i);
            finish();
        }

        volleyRP     = VolleyRP.getInstance(this);
        requestQueue = volleyRP.getRequestQueue();
        etUsuario    = findViewById(R.id.etUsuario);
        etContraseña = findViewById(R.id.etContraseña);
        btnAcceder   = findViewById(R.id.btnAcceder);
        passshow     = findViewById(R.id.showpassword);
        register     = findViewById(R.id.lg_registro);
        contra       = findViewById(R.id.lg_CONTRASEÑA);


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this,Registro.class);
                startActivity(i);
            }
        });

        contra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this, ForgotPassword.class);
                startActivity(i);
            }
        });

        btnAcceder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VerificarLogin(etUsuario.getText().toString(),etContraseña.getText().toString());
            }
        });

        //Mostrar contraseña
        passshow.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (!isChecked){
                    etContraseña.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }else{
                    etContraseña.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                }
            }
        });
    }//OnCreate

    public void VerificarLogin(String user, String password){
        USER = user;
        PASSWORD = password;
        SolicitudJSON(IP+user);
    }//LoginVerificar

    public void SolicitudJSON(String URL){
        JsonObjectRequest solicitud = new JsonObjectRequest(URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject datos) {
                VerificarPassword(datos);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, "¡Vaya! Parece que ha ocurrido un error inesperado", LENGTH_SHORT).show();
            }
        });
        VolleyRP.addToQueue(solicitud,requestQueue,this,volleyRP);
    }

    public void VerificarPassword(JSONObject datos){
        if (!validarVacio()) return;
        try {
            String estado = datos.getString("resultado");
            if (estado.equals("CC")) {
                JSONObject objectDatos = new JSONObject(datos.getString("datos"));
                String usuario = objectDatos.getString("usuario");
                String contraseña = objectDatos.getString("password");
                String type = objectDatos.getString("tipoUsuario");
                Preferences.savePreferencesString(Login.this,type,Preferences.PREFERENCES_TIPO_USUARIO);
                if(usuario.equals(USER) && contraseña.equals(PASSWORD) ){
                    FirebaseInstanceId.getInstance().getInstanceId().addOnSuccessListener(Login.this, new OnSuccessListener<InstanceIdResult>() {
                        @Override
                        public void onSuccess(InstanceIdResult instanceIdResult) {
                            String TOKEN = instanceIdResult.getToken();
                            SubirToken(TOKEN);
                        }
                    });
                }else {
                    Toast.makeText(this,"Parece que has escrito mal los datos", LENGTH_SHORT).show();
                }
            }else{
                Toast.makeText(this,estado,Toast.LENGTH_LONG).show();
            }
        } catch(JSONException e){}

    }
    private boolean validarVacio() {
        boolean valid = true;

        String uControl = etUsuario.getText().toString().trim();
        String sPassword = etContraseña.getText().toString().trim();

        if  (uControl.trim().isEmpty() || uControl.trim().length() < 8 || uControl.trim().length() > 13) {
            etUsuario.setError("El usuario debe tener entre 8 y 13 caracteres permitidos");
        } else {
            etUsuario.setError(null);
        }
        if (sPassword.trim().isEmpty() || sPassword.trim().length() < 4 || sPassword.trim().length() > 10) {
            etContraseña.setError("Su contraseña debe tener entre 4 y 10 caracteres alfanuméricos");
            valid = false;
        } else {
            etContraseña.setError(null);
        }

        return valid;
    }
    public void SubirToken(String token){
        HashMap <String, String> hashMap = new HashMap<>();
        hashMap.put("id", USER);
        hashMap.put("token", token);

        JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.POST, SolicitudesJSON.URL_TOKEN,new JSONObject(hashMap), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject datos) {
                //VerificarPassword(datos);
                Preferences.savePreferencesBoolean(Login.this,btnAcceder.isClickable(),Preferences.PREFERENCES_ESTADO);
                Preferences.savePreferencesString(Login.this,USER,Preferences.PREFERENCES_USUARIO_LOGIN);
                try {
                    Toast.makeText(Login.this, datos.getString("resultado"), LENGTH_SHORT).show();
                    Intent i = new Intent(Login.this,Inicio.class);
                    startActivity(i);
                    Toast.makeText(Login.this,"Que gusto verte "+ USER,Toast.LENGTH_SHORT).show();
                    finish();
                } catch (JSONException e) { }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, "No se pudo subir el token a la base de datos", LENGTH_SHORT).show();
            }
        });
        VolleyRP.addToQueue(solicitud,requestQueue,this,volleyRP);
    }

    private void RecuperarContraseña (String clave){
        if (!validarVaciocontraseña()) return;
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("usuario", clave);

        JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.POST, SolicitudesJSON.URL_FORGOTPASSWORD, new JSONObject(hashMap), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject datos) {
                try {
                    String respuesta = datos.getString("respuesta");
                    Toast.makeText(Login.this, respuesta, Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    Toast.makeText(Login.this, "Intentalo más tarde", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Login.this, "Problemas con el dispositivo", Toast.LENGTH_SHORT).show();
            }
        });
        VolleyRP.addToQueue(solicitud, requestQueue, this, volleyRP);
    }

    private boolean validarVaciocontraseña() {
        boolean valid = true;

        String CLAVE = clave.getText().toString();

        if (CLAVE.trim().isEmpty()) { clave.setError("Llene el campo");
        } else {
            clave.setError(null);
        }

        return valid;
    }


}