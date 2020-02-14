package com.jennyfer.jenna.Services;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferences {
    public static final String STRING_PREFERENCES = "com.jennyfer.pita";
    public static final String PREFERENCES_ESTADO= "estado.button.session";
    public static final String PREFERENCES_USUARIO_LOGIN= "usuario.login";
    public static final String PREFERENCES_TIPO_USUARIO = "tipoUsuario.login";

    //Cerrar sesión
    public static void savePreferencesBoolean(Context c, boolean b, String key){
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES, c.MODE_PRIVATE);
        preferences.edit().putBoolean(key,b).apply();
    }
    public static void savePreferencesString(Context c, String b, String key){
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES, c.MODE_PRIVATE);
        preferences.edit().putString(key,b).apply();
    }
    public static boolean obtenerPreferencesBoolean(Context c, String key){
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES, c.MODE_PRIVATE);
        return preferences.getBoolean(key,false);
    }
    public static String obtenerPreferencesString(Context c, String key){
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES, c.MODE_PRIVATE);
        return preferences.getString(key,"");
    }
}
