package co.appreactor.conocimentos.negocio.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by lord_nightmare on 4/12/17.
 */

public final class PreferenciasUtil {

    public static void guardarPreferencia(String llave, String valor, Context contexto){
        obtenerPreferencias(contexto).edit().putString(llave, valor).apply();
    }

    public static String leerPreferencia(String llave, Context contexto){
        return obtenerPreferencias(contexto).getString(llave,"");
    }

    public static void borrarPreferencia(String llave, Context contexto){
        obtenerPreferencias(contexto).edit().remove(llave).apply();
    }

    private static SharedPreferences obtenerPreferencias(Context contexto){
        return contexto.getSharedPreferences("conocimiento",Context.MODE_PRIVATE);
    }
}
