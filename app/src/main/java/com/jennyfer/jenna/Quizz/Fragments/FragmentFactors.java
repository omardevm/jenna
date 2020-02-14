package com.jennyfer.jenna.Quizz.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.button.MaterialButton;
import com.jennyfer.jenna.Quizz.Activities.ActivityQuizz;
import com.jennyfer.jenna.Quizz.Global.Constantes;
import com.jennyfer.jenna.Quizz.Models.Dimension;
import com.jennyfer.jenna.Quizz.Models.Pregunta;
import com.jennyfer.jenna.Quizz.Presentation.PresenterF;
import com.jennyfer.jenna.Quizz.Presentation.ViewPagerTransactions;
import com.jennyfer.jenna.Quizz.Utilities.RequestUtility;
import com.jennyfer.jenna.Quizz.Utilities.SlideAdapter;
import com.jennyfer.jenna.Quizz.Utilities.VolleyCallback;
import com.jennyfer.jenna.R;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Archivo manejador del fragmento de factores, el cual implementa un modelo MVP
 * Se entrega la dimensión como objeto para llenar el ViewPager con los factores correspondientes y sus imagenes.
 * Se implementa el ViewPagerTransactions como Interface del Presentador para las acciones de otras vistas implementadas
 * Se busca que cuando se de clic en el botón de un factor se cargue las preguntas correspondiente a dicho factor.
 *
 * @author Omar Dominguez
 * @since 1/12/19
 */
public class FragmentFactors extends Fragment implements ViewPagerTransactions {

    private ProgressBar progress;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_factors, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        progress = getActivity().findViewById(R.id.progress_factor);
        Toolbar toolbar = getActivity().findViewById(R.id.toolbar_);
        viewPager = getActivity().findViewById(R.id.viewPager);
        toolbar.setTitle("Factores");
        toolbar.setNavigationIcon(ContextCompat.getDrawable(getActivity(), R.drawable.ic_back));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().popBackStack();
            }
        });
        Bundle bundle = getArguments();
        Dimension dimension = (Dimension) bundle.getSerializable("DIMENSION");
        PresenterF f = new PresenterF(this);
        viewPager.setAdapter(new SlideAdapter(getActivity().getBaseContext(), f, dimension));
    }


    @Override
    public void clicButtonPager(String factor_, Button btn) {
        final Button boton = btn;
        progress.setVisibility(View.VISIBLE);
        btn.setEnabled(false);

        RequestUtility ru = new RequestUtility(getActivity().getApplicationContext());
        Map<String, String> params = new HashMap<>();
        params.put("factor", String.valueOf(factor_));
        ru.__callServerPOST(Constantes.GET_PREGUNTAS_BY_FACTOR, params, new VolleyCallback() {
            @Override
            public void onSuccess(String result) {
                try {
                    JSONArray array = new JSONArray(result);
                    ArrayList<Pregunta> preguntas = new ArrayList<>();
                    for(int i = 0;i<array.length();i++){
                        Pregunta p = new Pregunta();
                        p.setAtributo(array.getJSONObject(i).getString("atributo"));
                        p.setContenido(array.getJSONObject(i).getString("contenido"));
                        p.setIdfactores(Integer.parseInt(array.getJSONObject(i).getString("idfactores")));
                        p.setIdpreguntas(Integer.parseInt(array.getJSONObject(i).getString("idpreguntas")));
                        p.setOpciones(array.getJSONObject(i).getString("opciones"));
                        preguntas.add(p);
                    }
                    Intent intent = new Intent(getActivity().getApplicationContext(), ActivityQuizz.class);
                    intent.putExtra("PREGUNTAS", preguntas);
                    startActivity(intent);
                    boton.setEnabled(true);
                    progress.setVisibility(View.INVISIBLE);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(String result) {
                boton.setEnabled(true);
                progress.setVisibility(View.INVISIBLE);
                Log.d("JIJI", "error: " + result);
            }
        });
    }
}
