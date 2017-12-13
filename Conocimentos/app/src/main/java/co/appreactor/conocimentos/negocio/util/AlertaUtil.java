package co.appreactor.conocimentos.negocio.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by lord_nightmare on 25/09/17.
 */

public final class AlertaUtil {

    public static void mostrarAlerta(String titulo, String mensaje,
                                     DialogInterface.OnClickListener eventoOk,
                                     DialogInterface.OnClickListener eventoCancel,
                                     Context contexto){
        AlertDialog.Builder alerta = new AlertDialog.Builder(contexto);
        alerta.setTitle(titulo);
        alerta.setMessage(mensaje);
        alerta.setPositiveButton("Aceptar",eventoOk);
        alerta.setNegativeButton("Cancelar",eventoCancel);
        alerta.show();
    }

}
