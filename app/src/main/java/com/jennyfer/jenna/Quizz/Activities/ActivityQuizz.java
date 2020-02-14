package com.jennyfer.jenna.Quizz.Activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.button.MaterialButton;
import com.jennyfer.jenna.Quizz.Global.Constantes;
import com.jennyfer.jenna.Quizz.Models.Pregunta;
import com.jennyfer.jenna.Quizz.Utilities.RequestUtility;
import com.jennyfer.jenna.Quizz.Utilities.VolleyCallback;
import com.jennyfer.jenna.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Archivo recibe una lista de objetos Pregunta en relación a un factor
 * Después de escoger una opción, se actualiza el progreso del usuario y se inserta el valor de su respuesta en la bd
 * Este test acaba cuando  se alcanza el límite de la lista de preguntas
 *
 * @author Omar Dominguez
 * @since 1/12/19
 */
public class ActivityQuizz extends AppCompatActivity implements View.OnClickListener {

    private int contador;
    private int idfactor;
    private int idpreguntas;
    private ArrayList<Pregunta> preguntas;
    private Button button;
    private ImageView closee;
    private ProgressBar progressBar;
    private TextView pregunta;
    private RadioGroup radioGroup;

    /**
     * Prepara los elementos del xml para mostrar pregunta basada
     * en el progreso general registrado del @link ActivityHome,
     * preparamos el contador en base al factor de las preguntas
     */
    private void prepareView() {
        idfactor = preguntas.get(0).getIdfactores();
        switch (idfactor) {
            case 1:
                contador = Integer.parseInt(ActivityHome.generalProgress.getFactor_1());
                break;
            case 2:
                contador = Integer.parseInt(ActivityHome.generalProgress.getFactor_2());
                break;
            case 3:
                contador = Integer.parseInt(ActivityHome.generalProgress.getFactor_3());
                break;
            case 4:
                contador = Integer.parseInt(ActivityHome.generalProgress.getFactor_4());
                break;
            case 5:
                contador = Integer.parseInt(ActivityHome.generalProgress.getFactor_5());
                break;
            case 6:
                contador = Integer.parseInt(ActivityHome.generalProgress.getFactor_6());
                break;
            case 7:
                contador = Integer.parseInt(ActivityHome.generalProgress.getFactor_7());
                break;
            case 8:
                contador = Integer.parseInt(ActivityHome.generalProgress.getFactor_8());
                break;
            case 9:
                contador = Integer.parseInt(ActivityHome.generalProgress.getFactor_9());
                break;
        }
        contador = contador - 1;
        progressBar.setProgress(contador);
        prepareQuestions();
    }


    /**
     * Para preparar preguntas, esto toma en cuenta
     * el contador actualizado de forma local y no estática
     */
    private void prepareQuestions() {
        if (contador < preguntas.size()) {
            pregunta.setText(preguntas.get(contador).getContenido());
            prepareRadioGroup(preguntas.get(contador).getOpciones().split(","));
            idpreguntas = preguntas.get(contador).getIdpreguntas();
        } else {
            Toast.makeText(ActivityQuizz.this, "Àrea finalizada", Toast.LENGTH_SHORT).show();
        }

    }


    /**
     * Limpiamos las vistas anteriores para no sobrepoblar el radiogroup
     * (general duplicados)
     */
    private void prepareRadioGroup(String[] answ) {
        radioGroup.removeAllViews();
        radioGroup.clearCheck();
        RadioButton[] rb = new RadioButton[answ.length];
        for (int i = 0; i < answ.length; i++) {
            rb[i] = new RadioButton(this);
            rb[i].setBackground(ContextCompat.getDrawable(this, R.drawable.yourbuttonbg));
            rb[i].setId(i + 100);
            rb[i].setText(answ[i]);
            rb[i].setTextSize(12.0f);
            rb[i].setButtonDrawable(android.R.color.transparent);
            rb[i].setLayoutParams(new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT));
            radioGroup.addView(rb[i]);
        }
    }

    private void moveOnLogic() {
        if (contador < preguntas.size()) {
            contador = contador + 1;
            progressBar.setProgress(contador);
            prepareQuestions();
        } else {
            finish();
            Toast.makeText(ActivityQuizz.this, "Àrea finalizada", Toast.LENGTH_SHORT).show();
        }
    }


    //lifecycle

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quizz);
        Log.d("JIJI","ola");
        progressBar = findViewById(R.id.progress_quizz);
        radioGroup = findViewById(R.id.radiogroup_quizz);
        pregunta = findViewById(R.id.txt_contenido_pregunta);
        button = findViewById(R.id.btn_quizz_next);
        button.setOnClickListener(this);
        closee = findViewById(R.id.btn_quizz_close);
        closee.setOnClickListener(this);
        preguntas = (ArrayList<Pregunta>) getIntent().getSerializableExtra("PREGUNTAS");

        progressBar.setMax(preguntas.size());
        prepareView();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_quizz_next:
                int answId = radioGroup.getCheckedRadioButtonId();
                if(answId!=-1){
                    RadioButton rb = radioGroup.findViewById(answId);
                    int vindince = radioGroup.indexOfChild(rb);
                    String valor = rb.getText().toString();
                    if(idpreguntas==32 && valor.equals("No")){
                        finish();
                    }
                    RequestUtility ru = new RequestUtility(this);
                    Map<String, String> params = new HashMap<>();

                    params.put("iduser",ActivityHome.generalProgress.getUsuario());
                    params.put("idpreguntas",String.valueOf(idpreguntas) );
                    params.put("valor", valor);
                    params.put("vindice",""+vindince);
                    params.put("idfactor",String.valueOf(idfactor));
                    params.put("contador",String.valueOf(contador));
                    Log.d("JIJI","a ver equis de"+params.get("iduser")+" "+params.get("idpreguntas")+" "+params.get("idfactor")+" "+params.get("contador"));
                    ru.__callServerPOST(Constantes.SEND_ANSWER, params, new VolleyCallback() {
                        @Override
                        public void onSuccess(String result) {
                            try {
                                JSONObject obj = new JSONObject(result);
                                if(obj.getBoolean("exito")){

                                    moveOnLogic();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        @Override
                        public void onError(String result) {
                            Log.d("JIJI", "error: " + result);
                        }
                    });
                }
                break;
            case R.id.btn_quizz_close:
                finish();
                break;
        }
    }
}
