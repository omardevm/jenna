package com.jennyfer.jenna.Quizz.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.google.android.material.appbar.MaterialToolbar;
import com.jennyfer.jenna.Quizz.Models.Dimension;
import com.jennyfer.jenna.R;

/**
 * Primer fragmento, maneja las dimensiones de test
 * cada dimension se llama con "include" desde el xml,
 * por lo que no se implementa directamente un View.OnClicklistener
 * desde la clase
 * @author Omar Dominguez
 * @since 1/12/19
 */
public class FragmentDimension extends Fragment {



    private Dimension[] dimensiones;
    private Toolbar toolbar;
    private ImageView img;


    private void generarDimensiones(){
        Dimension dim1 = new Dimension();
            dim1.setTag("DIMENSIÒN ACADÈMICA");
            dim1.setFactores_imagenes(new int[]{R.drawable.ic_books, R.drawable.icboss,R.drawable.ic_calif});
            dim1.setFactores(new String[]{"ORIENTACION VOCACIONAL","MATERIAS REPROBADAS","MOTIVACION ESCOLAR"});
            dim1.setDim_imagen(R.drawable.icdim1);
        Dimension dim2 = new Dimension();
            dim2.setTag("DIMENSIÒN ECONÓMICA");
            dim2.setFactores_imagenes(new int[]{R.drawable.ic_work, R.drawable.ic_money, R.drawable.ic_homej});
            dim2.setFactores(new String[]{"TRABAJO","PROBLEMAS ECONOMICO-FAMILIARES","LUGAR DE RESIDENCIA"});
            dim2.setDim_imagen( R.drawable.icdim3);
        Dimension dim3 = new Dimension();
            dim3.setTag("DIMENSIÒN PSICOLÒGICA");
            dim3.setFactores_imagenes(new int[]{R.drawable.ic_decition, R.drawable.ic_friend, R.drawable.ic_family});
            dim3.setFactores(new String[]{"PSICOLOGIA I","INFLUENCIA DE AMIGOS","PROBLEMAS FAMILIARES"});
            dim3.setDim_imagen( R.drawable.ic_dim2);

            Dimension[] lasDimensiones = {dim1,dim2,dim3};
            dimensiones = lasDimensiones;
    }

    /**
     * En un arreglo de #include layouts (containers), se itera como un arreglo
     * bidimensional entre los contenedores y las dimensiones
     */
    private void prepararTitles(){

        View[] contenedores = {getActivity().findViewById(R.id.container_dim_1),getActivity().findViewById(R.id.container_dim_2),getActivity().findViewById(R.id.container_dim_3)};
        for(int i = 0; i<contenedores.length;i++){
            final int j = i;
            TextView txt = contenedores[i].findViewById(R.id.item_dim_title) ;
            txt.setText(dimensiones[i].getTag());
            img = contenedores[i].findViewById(R.id.item_dim_img);
            img.setBackgroundResource(dimensiones[i].getDim_imagen());
            img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    FragmentFactors fragment = new FragmentFactors();
                    bundle.putSerializable("DIMENSION", dimensiones[j]);
                    fragment.setArguments(bundle);

                    getActivity().getSupportFragmentManager().beginTransaction()
                            .replace(R.id.contenedor, fragment)
                            .addToBackStack(null)
                            .commit();
                }
            });
        }
    }


    //lifecycle

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_dimensions,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar = getActivity().findViewById(R.id.toolbar_);
        toolbar.setTitle("Dimensiones");
        generarDimensiones();
        prepararTitles();
    }
}
