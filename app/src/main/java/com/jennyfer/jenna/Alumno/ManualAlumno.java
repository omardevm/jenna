package com.jennyfer.jenna.Alumno;

import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.jennyfer.jenna.Inicio;
import com.jennyfer.jenna.R;

public class ManualAlumno extends Fragment {

    private Button DManual;
    private ImageView foto;
    DownloadManager downloadManager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        container.removeAllViews();
        View view = inflater.inflate(R.layout.manual_alumno, container, false);

        ((Inicio) getActivity()).getSupportActionBar().setTitle("Manual del SNIT");


        foto = (ImageView)view.findViewById(R.id.foto);
        //String url = "http://pita.x10host.com/Imagenes/doodles-A.jpg";
        String url = "https://pitav3.000webhostapp.com/Imagenes/doodles-A.jpg";
        Glide.with(getActivity())
                .load(url)
                .centerCrop()
                .thumbnail(0.5f)
                .into(foto);

        DManual = (Button)view.findViewById(R.id.manual_descarga);
        DManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downloadManager = (DownloadManager)getActivity().getSystemService(Context.DOWNLOAD_SERVICE);
                //Uri uri = Uri.parse("http://pita.x10host.com/Manual%20de%20SNIT/CUADERNO_DE_TRABAJO_DE_TUTORIA_DEL_ESTUDIANTE.pdf");
                Uri uri = Uri.parse("https://pitav3.000webhostapp.com/Manual%20de%20SNIT/CUADERNO_DE_TRABAJO_DE_TUTORIA_DEL_ESTUDIANTE.pdf");
                DownloadManager.Request request = new DownloadManager.Request(uri);
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                Long reference = downloadManager.enqueue(request);
        }
        });

        return view;
    }


}
