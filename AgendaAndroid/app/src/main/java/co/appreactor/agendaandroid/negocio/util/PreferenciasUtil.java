package co.appreactor.agendaandroid.negocio.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lord_nightmare on 29/09/17.
 */

public final class PreferenciasUtil {

    public static void guardarPreferencia(String llave, String valor, Context contexto){
        // Accesar a las preferencias compartidas en modo privado
        SharedPreferences preferencias = contexto.getSharedPreferences("agenda",Context.MODE_PRIVATE);
        // generar el editor de las preferencias de la aplicacion
        SharedPreferences.Editor editor = preferencias.edit();
        // guardar la preferencia como un HashMap
        editor.putString(llave,valor);
        // confirmar el guardado de la preferencia
        editor.apply();
    }

    public static String leerPreferencia(String llave, Context contexto){
        // Accesar a las preferencias compartidas en modo privado
        SharedPreferences preferencias = contexto.getSharedPreferences("agenda",Context.MODE_PRIVATE);
        return preferencias.getString(llave,"json");
    }
}
