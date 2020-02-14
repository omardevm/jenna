package com.jennyfer.jenna.Quizz.Activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.jennyfer.jenna.Quizz.Fragments.FragmentDimension;
import com.jennyfer.jenna.Quizz.Models.Progreso;
import com.jennyfer.jenna.R;

/**
 * Manda el progreso (@link generalProgress) como un objeto a través del intent
 * se convierte en variable "estática" para que el test
 * y los fragmentos puedan acceder al progreso
 * @author Omar Dominguez
 * @since 01/12/19
 */
public class ActivityHome extends AppCompatActivity {

    public static Progreso generalProgress;

    //activity lifecycle


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        generalProgress = (Progreso) getIntent().getSerializableExtra("PROGRESS");
        //Log.d("JIJI"," que pedo"+generalProgress.getFactor_1());
        this.getSupportFragmentManager().beginTransaction()
                .add(R.id.contenedor, new FragmentDimension())
                .commit();
    }
}
