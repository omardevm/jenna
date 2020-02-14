package com.jennyfer.jenna;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.jennyfer.jenna.CentroDeActividades.Tareas.CrearTarea;
import com.jennyfer.jenna.Internet.SolicitudesJSON;
import com.jennyfer.jenna.Tools.VolleyRP;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class ForgotPassword extends AppCompatActivity {

    private EditText clave;
    private Button recuperar;

    private VolleyRP volleyRP = VolleyRP.getInstance(this);
    private RequestQueue requestQueue = volleyRP.getRequestQueue();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_password);

        clave     = findViewById(R.id.clave);
        recuperar = findViewById(R.id.recuperarButton);
        recuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecuperarContraseña(clave.getText().toString().trim());
            }
        });


    }

    private void RecuperarContraseña (String clave){
        if (!validarVacio()) return;
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("usuario", clave);

        JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.POST, SolicitudesJSON.URL_FORGOTPASSWORD, new JSONObject(hashMap), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject datos) {
                try {
                    String respuesta = datos.getString("respuesta");
                    if (respuesta.equalsIgnoreCase("200")) {
                        Toast.makeText(ForgotPassword.this, "Le enviamos un correo a su cuenta", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                } catch (JSONException e) {
                    Toast.makeText(ForgotPassword.this, "Intentalo más tarde", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(ForgotPassword.this, "Problemas con el dispositivo", Toast.LENGTH_SHORT).show();
            }
        });
        VolleyRP.addToQueue(solicitud, requestQueue, this, volleyRP);
        }

    private boolean validarVacio() {
        boolean valid = true;

        String CLAVE = clave.getText().toString();

        if (CLAVE.trim().isEmpty()) { clave.setError("Llene el campo");
        } else {
            clave.setError(null);
        }

        return valid;
    }

    @Override
    public void onBackPressed() { finish(); }
}
