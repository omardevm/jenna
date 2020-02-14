package com.jennyfer.jenna.Internet;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.jennyfer.jenna.Tools.VolleyRP;

import org.json.JSONObject;

import java.util.HashMap;

public abstract class SolicitudesJSON {

    public final static String URL_GETDATOS         = "https://pitav3.000webhostapp.com/PRUEBAPHP/Datos_GETALL.php?id=";
    public final static String URL_GETMENSAJES_ID   = "https://pitav3.000webhostapp.com/PRUEBAPHP/Mensajes_GETID.php";
    public final static String URL_TOKEN            = "https://pitav3.000webhostapp.com/PRUEBAPHP/Token_INSERTUPDATE.php";
    public final static String URL_CANCELAR         = "https://pitav3.000webhostapp.com/PRUEBAPHP/Solicitudes_CANCELAR.php";
    public final static String URL_ACEPTARSOLICITUD = "https://pitav3.000webhostapp.com/PRUEBAPHP/Solicitudes_ACEPTAR.php";
    public final static String URL_ENVIARSOLICITUD  = "https://pitav3.000webhostapp.com/PRUEBAPHP/Solicitudes_ENVIAR.php";
    public final static String URL_ELIMINARAMIGO    = "https://pitav3.000webhostapp.com/PRUEBAPHP/Solicitudes_ELIMINAR.php";
    public final static String URL_USUARIOID        = "https://pitav3.000webhostapp.com/PRUEBAPHP/Usuarios_GETID.php?usuario=";
    public final static String URL_UPLOADIMAGE      = "https://pitav3.000webhostapp.com/PRUEBAPHP/Imagen_UPLOAD.php";
    public final static String URL_FLIPPER          = "https://pitav3.000webhostapp.com/Imagenes/noticiasITESCO.png";
    public final static String URL_USUARIOUPDATE    = "https://pitav3.000webhostapp.com/PRUEBAPHP/Usuarios_UPDATE.php";
    public final static String URL_FORGOTPASSWORD   = "https://pitav3.000webhostapp.com/PRUEBAPHP/RecuperarContrase%C3%B1a_GETID.php?usuario=";

    //Actividades
    public final static String URL_INSERTTAREA      = "https://pitav3.000webhostapp.com/PRUEBAPHP/Tareas_INSERT.php";
    public final static String URL_ACTIVIDAPROFESOR = "https://pitav3.000webhostapp.com/PRUEBAPHP/Actividades_GETPROFESOR.php?usuario=";
    public final static String URL_ACTIVIDADALUMNO  = "https://pitav3.000webhostapp.com/PRUEBAPHP/Actividades_GETALUMNO.php?usuario=";
    public final static String URL_INSERTTAREADF    = "https://pitav3.000webhostapp.com/PRUEBAPHP/Tareas_INSERTPDF.php";

    //Divisiony Grupo
    public static final String URL_CREATORGRUPO     = "https://pitav3.000webhostapp.com/PRUEBAPHP/Grupo_CREATE.php?usuario=";
    public final static String URL_ADDGRUPO         = "https://pitav3.000webhostapp.com/PRUEBAPHP/Grupo_INSERT.php";
    public final static String URL_DIVISION         = "https://pitav3.000webhostapp.com/PRUEBAPHP/Division.php";
    public final static String URL_GRUPO            = "https://pitav3.000webhostapp.com/PRUEBAPHP/Grupo.php";
    public final static String URL_GETGRUPO         = "https://pitav3.000webhostapp.com/PRUEBAPHP/Grupo_GETID.php?usuario=";
    public final static String URL_GETDIVISION      = "https://pitav3.000webhostapp.com/PRUEBAPHP/Division_GETID.php?usuario=";
    public final static String URL_GETLISTAGYD      = "https://pitav3.000webhostapp.com/PRUEBAPHP/ListaGrupo_GETID.php?usuario=";

    public abstract void solicitudCompletada(JSONObject j);
    public abstract void solicitudErronea();

    public  SolicitudesJSON(){}

    public void JSONGET(Context c, String URL){
        JsonObjectRequest solicitud = new JsonObjectRequest(URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject datos) {
               solicitudCompletada(datos);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                solicitudErronea();
            }
        });
        VolleyRP.addToQueue(solicitud,VolleyRP.getInstance(c).getRequestQueue(),c,VolleyRP.getInstance(c));
    }

    public void JSONPOST(Context c, String URL, HashMap h){
        JsonObjectRequest solicitud = new JsonObjectRequest(Request.Method.POST,URL, new JSONObject(h), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject datos) {
                solicitudCompletada(datos);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                solicitudErronea();
            }
        });
        VolleyRP.addToQueue(solicitud,VolleyRP.getInstance(c).getRequestQueue(),c,VolleyRP.getInstance(c));
    }

}
