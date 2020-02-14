package com.jennyfer.jenna.Services;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.jennyfer.jenna.Atributos.AmigosAtributos;
import com.jennyfer.jenna.Atributos.SolicitudAtributos;
import com.jennyfer.jenna.BuzonDeCorreo.Amigos.Mensajeria;
import com.jennyfer.jenna.Login;
import com.jennyfer.jenna.R;
import com.jennyfer.jenna.Tools.AceptarSolicitud;
import com.jennyfer.jenna.Tools.EliminarAU;
import com.jennyfer.jenna.Tools.EliminarAmigos;
import com.jennyfer.jenna.Tools.EliminarSolicitud;
import com.jennyfer.jenna.Tools.EliminarSolicitudUsuarios;
import com.jennyfer.jenna.Tools.RecibirSolicitud;

import org.greenrobot.eventbus.EventBus;

import java.util.Random;

public class FirebaseServiceMensaje extends FirebaseMessagingService {

    @Override
    public void onNewToken(String s) { super.onNewToken(s); }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

        String type        = remoteMessage.getData().get("tipo");
        String cabecera    = remoteMessage.getData().get("cabecera");
        String cuerpo      = remoteMessage.getData().get("cuerpo");

        switch (type){
            case "mensaje":
                String mensaje     = remoteMessage.getData().get("mensaje");
                String horaMensaje = remoteMessage.getData().get("hora");
                String receptor    = remoteMessage.getData().get("receptor");
                String emisorPHP   = remoteMessage.getData().get("emisor");
                String emisor = Preferences.obtenerPreferencesString(this,Preferences.PREFERENCES_USUARIO_LOGIN);

                if (emisor.equals(receptor)){
                    Mensaje(mensaje,horaMensaje,emisorPHP);
                    BuzonNotificacion(cabecera, cuerpo);
                }
                break;

            case "solicitud":
                String subtipo_Solicitud = remoteMessage.getData().get("sub_type");
                switch (subtipo_Solicitud){
                    case "enviarSolicitud":
                        EventBus.getDefault().post(new SolicitudAtributos(remoteMessage.getData().get("usuario_envioSolicitud"),
                                remoteMessage.getData().get("u_EnvioSolicitudNombre"),3,
                                remoteMessage.getData().get("u_EnvioSolicitudImagen"),
                                remoteMessage.getData().get("u_EnvioSolicitudCarrera")));

                        EventBus.getDefault().post(new RecibirSolicitud(remoteMessage.getData().get("usuario_envioSolicitud")));
                        BuzonNotificacion(cabecera, cuerpo);
                        break;

                    case "cancelarSolicitud":
                        EventBus.getDefault().post(new EliminarSolicitudUsuarios(remoteMessage.getData().get("usuario_envioSolicitud")));
                        EventBus.getDefault().post(new EliminarSolicitud(remoteMessage.getData().get("usuario_envioSolicitud")));
                        break;

                    case "aceptarSolicitud":
                        EventBus.getDefault().post(new EliminarSolicitud(remoteMessage.getData().get("usuario_envioSolicitud")));
                        EventBus.getDefault().post(new AmigosAtributos(remoteMessage.getData().get("usuario_envioSolicitud"),
                                remoteMessage.getData().get("u_EnvioSolicitudNombre"),
                                remoteMessage.getData().get("ultimoMensaje") == null ? "null" : remoteMessage.getData().get("ultimoMensaje"),
                                remoteMessage.getData().get("hora_mensaje"),
                                remoteMessage.getData().get("u_EnvioSolicitudImagen"),
                                remoteMessage.getData().get("u_EnvioSolicitudCarrera"),
                                remoteMessage.getData().get("typeMensaje") == null ? "null" : remoteMessage.getData().get("typeMensaje")));
                        EventBus.getDefault().post(new AceptarSolicitud(remoteMessage.getData().get("usuario_envioSolicitud")));
                        BuzonNotificacion(cabecera, cuerpo);
                        break;

                        //Para cortar la hora
                    /*remoteMessage.getData().get("hora_mensaje") == null ? "null":
                                        remoteMessage.getData().get("hora_mensaje").split(",")[0],*/
                    case "eliminarAmigo":
                        EventBus.getDefault().post(new EliminarAmigos(remoteMessage.getData().get("usuario_envioSolicitud")));
                        EventBus.getDefault().post(new EliminarAU(remoteMessage.getData().get("usuario_envioSolicitud")));
                        break;
                }
                String usuario_envioSolicitud = remoteMessage.getData().get("usuario_envioSolicitud");
                break;

            case "enviarTareaCreada":
                String emisorTarea = remoteMessage.getData().get("emisor");
                String alumnoPHP   = remoteMessage.getData().get("receptor");
                /*String tituloT       = remoteMessage.getData().get("titulo");
                String descriT       = remoteMessage.getData().get("descripcion");
                String horaT         = remoteMessage.getData().get("hora");
                String tutorPHP      = remoteMessage.getData().get("emisor");*/
                String alumno         = Preferences.obtenerPreferencesString(this,Preferences.PREFERENCES_USUARIO_LOGIN);

                //Se verifica que el envio de tarea sea el mismo receptor
                if (alumno.equals(alumnoPHP)){
                    BuzonNotificacion(cabecera, cuerpo);
                    //Tarea(tituloT,descriT,horaT,tutorPHP);
                }

                break;

        }

    }

    private void Mensaje(String mensaje, String horaMensaje, String emisor){
        Intent i = new Intent(Mensajeria.MENSAJE);
        i.putExtra("key_mensaje",mensaje);
        i.putExtra("horaMensaje",horaMensaje);
        i.putExtra("key_emisorPHP",emisor);
        LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(i);
    }


    private void BuzonNotificacion(String cabecera, String cuerpo){
        Intent i = new Intent(this, Login.class);
        PendingIntent PI = PendingIntent.getActivity(this,0,i,PendingIntent.FLAG_ONE_SHOT);

        Uri SN = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"Channel_ID");
        builder.setPriority(Notification.PRIORITY_HIGH);
        builder.setAutoCancel(true);
        builder.setContentTitle(cabecera);
        builder.setContentText(cuerpo);
        builder.setSound(SN);
        builder.setSmallIcon(R.drawable.notificacion_chat);
        builder.setTicker(cuerpo);
        builder.setContentIntent(PI);

        NotificationManager NM = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        Random random = new Random();
        NM.notify(random.nextInt(),builder.build());
    }
}
