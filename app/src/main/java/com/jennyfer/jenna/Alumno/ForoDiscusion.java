package com.jennyfer.jenna.Alumno;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jennyfer.jenna.Inicio;
import com.jennyfer.jenna.R;

public class ForoDiscusion extends Fragment {


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        container.removeAllViews();
        View view = inflater.inflate(R.layout.departamentos_alumno, container, false);

        ((Inicio) getActivity()).getSupportActionBar().setTitle("Foro de discusión");


        return view;
    }
}
