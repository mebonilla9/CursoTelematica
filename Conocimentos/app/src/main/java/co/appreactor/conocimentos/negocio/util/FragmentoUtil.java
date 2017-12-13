package co.appreactor.conocimentos.negocio.util;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import co.appreactor.conocimentos.negocio.fragmentos.PreguntaFragment;
import co.appreactor.conocimentos.R;
import co.appreactor.conocimentos.negocio.fragmentos.RespuestaFragment;
import co.appreactor.conocimentos.negocio.fragmentos.CategoriaFragment;

/**
 * Created by lord_nightmare on 5/12/17.
 */

public final class FragmentoUtil {

    public static void obtenerFragmento(int idVista, Bundle argumentos,
                                        FragmentTransaction transaccion,
                                        int contenedor) {

        Fragment fragmento = null;
        String etiqueta = "";
        // buscar el fragmento respectivo para el id de menu o de boton
        // que solicita el nuevo fragmento
        switch (idVista) {
            case R.id.miCategorias:
                fragmento = new CategoriaFragment();
                etiqueta = "categoria";
                break;
            case R.id.miPreguntas:
                fragmento = new PreguntaFragment();
                etiqueta = "pregunta";
                break;
            case R.id.miRespuestas:
                fragmento = new RespuestaFragment();
                etiqueta = "respuesta";
                break;
            case R.id.cvPregunta:
                fragmento = new PreguntaFragment();
                etiqueta = "pregunta";
                break;
            default:
                fragmento = new Fragment();
                break;
        }
        // determinar si el fragmento que vamos a instanciar recibe
        // atributos
        if (argumentos != null){
            // asignacion de los argumentos o parametros enviados a un fragmento
            // desde una actividad o desde otro fragmento
            fragmento.setArguments(argumentos);
        }
        transaccion.replace(contenedor,fragmento,etiqueta).addToBackStack(etiqueta).commit();
    }

}
