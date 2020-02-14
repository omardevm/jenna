package com.jennyfer.jenna.Quizz.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.jennyfer.jenna.Quizz.Global.Constantes;
import com.jennyfer.jenna.Quizz.Models.Progreso;
import com.jennyfer.jenna.Quizz.Utilities.RequestUtility;
import com.jennyfer.jenna.Quizz.Utilities.VolleyCallback;
import com.jennyfer.jenna.R;
import com.jennyfer.jenna.Services.Preferences;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActivityWelcome extends AppCompatActivity implements View.OnClickListener {

    private Button init;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        init = findViewById(R.id.button_start);
        init.setOnClickListener(this);
        progressBar = findViewById(R.id.progressBarx);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == init.getId()) {
        /// aqui se jala la variable del SharedPref
            String username = Preferences.obtenerPreferencesString(ActivityWelcome.this, Preferences.PREFERENCES_USUARIO_LOGIN);
            init.setEnabled(false);
            solicitudBackend(username);

        }
    }

    /**
     * Requiere recuperar este dato desde el SharedPrefManager
     *
     * @param username
     */
    private void solicitudBackend(String username) {
        RequestUtility ru = new RequestUtility(this);
        Map<String, String> params = new HashMap<>();
        params.put("usuario", String.valueOf(username));
        ru.__callServerPOST(Constantes.GET_PROGRESO_BY_USER, params, new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    init.setEnabled(true);
                    JSONObject obj = new JSONObject(result);
                    Progreso p = new Progreso();
                    p.setUsuario(obj.getString("usuario"));
                    p.setFactor_1(obj.getString("factor_1"));
                    p.setFactor_2(obj.getString("factor_2"));
                    p.setFactor_3(obj.getString("factor_3"));
                    p.setFactor_4(obj.getString("factor_4"));
                    p.setFactor_5(obj.getString("factor_5"));
                    p.setFactor_6(obj.getString("factor_6"));
                    p.setFactor_7(obj.getString("factor_7"));
                    p.setFactor_8(obj.getString("factor_8"));
                    p.setFactor_9(obj.getString("factor_9"));

                    Intent intent = new Intent(ActivityWelcome.this, ActivityHome.class);
                    intent.putExtra("PROGRESS", p);
                    startActivity(intent);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String result) {
                init.setEnabled(true);Log.d("JIJI", "error: " + result);
            }
        });
    }
}
