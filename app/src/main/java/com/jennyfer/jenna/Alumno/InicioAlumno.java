package com.jennyfer.jenna.Alumno;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterViewFlipper;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jennyfer.jenna.Adapter.FlipperAdapter;
import com.jennyfer.jenna.Inicio;
import com.jennyfer.jenna.Internet.SolicitudesJSON;
import com.jennyfer.jenna.R;
import com.jennyfer.jenna.Services.APIService;
import com.jennyfer.jenna.Tools.FlipperImage;
import com.jennyfer.jenna.Tools.FlipperImages;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class InicioAlumno extends Fragment {
    private ImageView a, b, c, d;
    private ImageView a1;
    private DialogFragment dialog;

    //Flipper
    //public static  final String BASE_URL = "http://pita.x10host.com/Imagenes/ViewFlipper/";
    public static  final String BASE_URL = "https://pitav3.000webhostapp.com/Imagenes/ViewFlipper/";
    private AdapterViewFlipper adapterViewFlipper;

    @Override
    public View onCreateView(final LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        container.removeAllViews();
        final View view = inflater.inflate(R.layout.alumno_inicio, container, false);

        ((Inicio) getActivity()).getSupportActionBar().setTitle("Inicio");

        a = view.findViewById(R.id.a);
        b = view.findViewById(R.id.b);
        c = view.findViewById(R.id.c);
        d = view.findViewById(R.id.d);

        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(v.getContext());
                LayoutInflater layoutA = LayoutInflater.from(v.getContext());
                final View view1 = layoutA.inflate(R.layout.popup_a,null);
                ad.setView(view1);
                ad.setNegativeButton("Entendido",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                ad.show();
            }
        });

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(v.getContext());
                LayoutInflater layoutA = LayoutInflater.from(v.getContext());
                final View view1 = layoutA.inflate(R.layout.popup_a,null);
                ad.setView(view1);
                ad.setNegativeButton("Entendido",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                ad.show();
            }
        });

        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(v.getContext());
                LayoutInflater layoutA = LayoutInflater.from(v.getContext());
                final View view1 = layoutA.inflate(R.layout.popup_a,null);
                ad.setView(view1);
                ad.setNegativeButton("Entendido",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                ad.show();
            }
        });

        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder ad = new AlertDialog.Builder(v.getContext());
                LayoutInflater layoutA = LayoutInflater.from(v.getContext());
                final View view1 = layoutA.inflate(R.layout.popup_a,null);
                ad.setView(view1);
                ad.setNegativeButton("Entendido",new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                ad.show();
            }
        });




//cuadros
        //String urlA = "http://pita.x10host.com/Imagenes/presentacionPITA.png";
        String urlA = "https://pitav3.000webhostapp.com/Imagenes/presentacionPITA.png";
        Glide.with(getActivity())
                .load(urlA)
                .centerCrop()
                .thumbnail(0.2f)
                .into(a);

        String urlB = SolicitudesJSON.URL_FLIPPER;
        Glide.with(getActivity())
                .load(urlB)
                .centerCrop()
                .thumbnail(0.2f)
                .into(b);

        //String urlC = "http://pita.x10host.com/Imagenes/modoTAREA.png";
        String urlC = "https://pitav3.000webhostapp.com/Imagenes/modoTAREA.png";
        Glide.with(getActivity())
                .load(urlC)
                .centerCrop()
                .thumbnail(0.2f)
                .into(c);

        //String urlD = "http://pita.x10host.com/Imagenes/hechaporALUMNO.png";
        String urlD = "https://pitav3.000webhostapp.com/Imagenes/hechaporALUMNO.png";
        Glide.with(getActivity())
                .load(urlD)
                .centerCrop()
                .thumbnail(0.2f)
                .into(d);


        adapterViewFlipper = view.findViewById(R.id.adapterView);

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        APIService service = retrofit.create(APIService.class);
        Call<FlipperImages> call = service.getFlipperImages();

        call.enqueue(new Callback<FlipperImages>() {
            @Override
            public void onResponse(Call<FlipperImages> call, Response<FlipperImages> response) {
                ArrayList<FlipperImage> flipperI = response.body().getFlipperI();
                FlipperAdapter adapter = new FlipperAdapter(getActivity().getApplicationContext(),flipperI);
                adapterViewFlipper.setAdapter(adapter);
                adapterViewFlipper.setFlipInterval(3000);
                adapterViewFlipper.startFlipping();
            }

            @Override
            public void onFailure(Call<FlipperImages> call, Throwable t) {
                Toast.makeText(getContext(),"Parece que no esta conectado",Toast.LENGTH_SHORT).show();
            }
        });


    return view;
    }
}