package co.appreactor.eventos.negocio.util;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;

import co.appreactor.eventos.R;
import co.appreactor.eventos.negocio.fragmentos.ListarSalaFragment;
import co.appreactor.eventos.negocio.fragmentos.RegistrarEventoFragment;

/**
 * Created by lord_nightmare on 9/10/17.
 */

public final class FragmentUtil {

    public static void obtenerFragmento(int idVista, Bundle argumentos,
                                        FragmentTransaction transaccion,
                                        int contenedor){

        Fragment fragmento = null;
        String tag = "";
        // asignar el tipo de fragmento a utilizar segun el item de menu
        // seleccionado.
        switch (idVista){
            case R.id.miRegistrarEvento:
                fragmento = new RegistrarEventoFragment();
                tag = "Registrar Evento";
                break;
            case R.id.miVerSalas:
                fragmento = new ListarSalaFragment();
                tag = "Consultar Salas";
                break;
            case R.id.miVerTipoEvento:

                break;
            default:
                fragmento = new Fragment();
                tag = "Fragment";
                break;
        }
        // Determinar si hay argumentos para enviar al fragmento
        if (argumentos != null){
            fragmento.setArguments(argumentos);
        }
        // reemplazar el fragmento en el contenedor
        transaccion.replace(contenedor,fragmento).addToBackStack(tag).commit();
    }
}
