package com.jennyfer.jenna.Quizz.Activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.jennyfer.jenna.R;

public class ActivityResultados extends AppCompatActivity {

    RecyclerView re;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.actividad_resultados);
        re = findViewById(R.id.recycler_students);



        //re.setAdapter(new );
    }
}
