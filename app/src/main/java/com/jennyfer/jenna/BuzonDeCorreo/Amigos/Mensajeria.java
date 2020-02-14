package com.jennyfer.jenna.BuzonDeCorreo.Amigos;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.jennyfer.jenna.Adapter.MensajeAdapter;
import com.jennyfer.jenna.Atributos.MensajeTexto;
import com.jennyfer.jenna.Internet.SolicitudesJSON;
import com.jennyfer.jenna.R;
import com.jennyfer.jenna.Services.Preferences;
import com.jennyfer.jenna.Tools.VolleyRP;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import static android.widget.Toast.LENGTH_SHORT;


public class Mensajeria extends AppCompatActivity{

    public static final String MENSAJE = "MENSAJE";

    private BroadcastReceiver BR;
    private RecyclerView rv;
    private List<MensajeTexto> mensajeTextos;
    private MensajeAdapter mensajeAdapter;
    private Button mEnviar;
    private EditText mMensaje;
    private String MENSAJE_ENVIAR = "";
    private String EMISOR = "";
    private String RECEPTOR;

    private VolleyRP volleyRP;
    private RequestQueue requestQueue;
    //private static final String IP_MENSAJE = "http://pita.x10host.com/PHP/E_M.php";
    private static final String IP_MENSAJE = "https://pitav3.000webhostapp.com/PRUEBAPHP/E_M.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mensajeria);
        Intent i = getIntent();

        Toolbar toolbar = findViewById(R.id.toolbar);
        String tituloReceptor = i.getStringExtra("key_nombre");
        toolbar.setTitle(tituloReceptor);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) { finish();  }});

        mensajeTextos = new ArrayList<>();
        volleyRP     = VolleyRP.getInstance(this);
        requestQueue = volleyRP.getRequestQueue();

        EMISOR = Preferences.obtenerPreferencesString(this,Preferences.PREFERENCES_USUARIO_LOGIN);
        Bundle bundle = i.getExtras();
        if (bundle!=null){ RECEPTOR = bundle.getString("key_receptor"); }

        rv = findViewById(R.id.recycler_chat);
        final LinearLayoutManager lm = new LinearLayoutManager(this);
        lm.setStackFromEnd(true);
        rv.setLayoutManager(lm);

        mMensaje  = findViewById(R.id.cajamensaje);


        mEnviar = findViewById(R.id.btnmensaje);
        mEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mensaje = mMensaje.getText().toString().trim();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
                String hora = simpleDateFormat.format(new Date());

                SimpleDateFormat simple = new SimpleDateFormat("dd-MM-yyyy ");
                String fecha = simpleDateFormat.format(new Date());

               /* Date actual= Calendar.getInstance().getTime();
                try {
                    Date obtenida= new SimpleDateFormat("dd/MM/yyyy hh:mm:ss").parse("14/08/2019 12:00:00");
                    //tiempo en horas
                    long tiempoTranscurrido = (actual.getTime()-obtenida.getTime())/1000/60/60;
                    String texto="hace un momento";
                        if(tiempoTranscurrido == 1) {
                            texto = "hace una hora";
                        }else{
                            if(tiempoTranscurrido < 24) {
                                texto = "hace " + tiempoTranscurrido + " horas";
                            }
                        }
                    hora = texto;
                } catch (ParseException e) {
                    e.printStackTrace();
                }*/
                if (!mensaje.trim().isEmpty() && !RECEPTOR.isEmpty()) {
                    MENSAJE_ENVIAR = mensaje;
                    CrearMensaje(mensaje, hora, 1,fecha);
                    MandarMensaje();
                    setScrollbarChat();
                    mMensaje.setText("");
                }

            }
        });



        rv.addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                                         @Override
                                         public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                                             if (bottom < oldBottom){
                                                 final int lastAdapterItem = mensajeAdapter.getItemCount() - 1;
                                                 rv.post(new Runnable() {
                                                     @Override
                                                     public void run() {
                                                         int recyclerViewPositionOffset = -1000000;
                                                         View bottomView = lm.findViewByPosition(lastAdapterItem);
                                                         if (bottomView != null){
                                                             recyclerViewPositionOffset = 0 - bottomView.getHeight();
                                                         }
                                                         lm.scrollToPositionWithOffset(lastAdapterItem, recyclerViewPositionOffset);
                                                     }
                                                 });
                                             }
                                         }
                                     });

        mensajeAdapter = new MensajeAdapter(mensajeTextos, this);
        rv.setAdapter(mensajeAdapter);
        setScrollbarChat();

        BR = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String mensaje          = intent.getStringExtra("key_mensaje");
                String horaMensaje      = intent.getStringExtra("horaMensaje");
                String horaParametros[] = horaMensaje.split(",");
                String fecha            = intent.getStringExtra("horaMensaje");
                String emisor           = intent.getStringExtra("key_emisorPHP");
                if (emisor.equals(RECEPTOR)) {
                    CrearMensaje(mensaje, horaParametros[0], 2,fecha);
                }
            }
        };

        SolicitudesJSON s = new SolicitudesJSON() {
                @Override
                public void solicitudCompletada(JSONObject j) {
                    try {
                        JSONArray jsa = j.getJSONArray("resultado");
                        for (int i = 0; i < jsa.length(); i++) {
                            JSONObject jo = jsa.getJSONObject(i);
                            String mensaje = jo.getString("mensaje");
                            String dia = jo.getString("horaMensaje").split(",")[1];
                            String diaF = jo.getString("horaMensaje").split(",")[2];
                            String fecha = dia +", "+ diaF;
                            String hora = jo.getString("horaMensaje").split(",")[0];
                            int tipoM = jo.getInt("tipoMensaje");
                            CrearMensaje(mensaje, hora, tipoM,fecha);
                        }
                    } catch (JSONException e) {
                        Toast.makeText(Mensajeria.this, "Ocurrio un error al obtener los mensajes", LENGTH_SHORT).show();
                    }
                }

                @Override
                public void solicitudErronea() {

                }
            };
            HashMap<String, String> hs = new HashMap<>();
            hs.put("emisor", EMISOR);
            hs.put("receptor", RECEPTOR);
            s.JSONPOST(this, SolicitudesJSON.URL_GETMENSAJES_ID, hs);
        }


    private void MandarMensaje(){
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("emisor", EMISOR);
        hashMap.put("receptor", RECEPTOR);
        hashMap.put("mensaje", MENSAJE_ENVIAR);


        JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.POST,IP_MENSAJE,new JSONObject(hashMap), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject datos) {
                try {
                    Toast.makeText(Mensajeria.this, datos.getString("resultado"), LENGTH_SHORT).show();
                } catch (JSONException e) { }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Mensajeria.this, "Ocurrio un error", LENGTH_SHORT).show();
            }
        });
        VolleyRP.addToQueue(solicitud,requestQueue,this,volleyRP);

    }

    public void CrearMensaje(String mensaje, String horaMensaje, int tipoMensaje, String fecha) {

        MensajeTexto mensajeTextosA = new MensajeTexto();
        mensajeTextosA.setMensaje(mensaje);
        mensajeTextosA.setTipoMensaje(tipoMensaje);
        mensajeTextosA.setHoraMensaje(horaMensaje);
        mensajeTextosA.setFechaCompleta(fecha);
        mensajeTextos.add(mensajeTextosA);
        mensajeAdapter.notifyDataSetChanged();
        setScrollbarChat();
    }

    @Override
    public void onPause() {
        super.onPause();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(BR);
    }

    @Override
    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance(this).registerReceiver(BR, new IntentFilter(MENSAJE));
    }

    public void setScrollbarChat(){
        rv.scrollToPosition(mensajeAdapter.getItemCount()-1);
    }

}
