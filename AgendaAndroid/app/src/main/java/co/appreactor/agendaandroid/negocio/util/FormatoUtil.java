package co.appreactor.agendaandroid.negocio.util;

import android.content.Context;

import co.appreactor.agendaandroid.modelo.servicio.ArchivadorDom;
import co.appreactor.agendaandroid.modelo.servicio.ArchivadorJson;
import co.appreactor.agendaandroid.modelo.servicio.IGestorArchivo;
import co.appreactor.agendaandroid.negocio.actividades.ConfiguracionActivity;

/**
 * Created by lord_nightmare on 29/09/17.
 */

public final class FormatoUtil {

    public static IGestorArchivo retornarFormato(Context contexto){
        IGestorArchivo archivador = null;
        String formatoSeleccionado = PreferenciasUtil.leerPreferencia("formato",contexto);
        switch (formatoSeleccionado) {
            case "json":
                archivador = new ArchivadorJson();
                break;
            case "sax":
                archivador = new ArchivadorJson();
                break;
            case "dom":
                archivador = new ArchivadorDom();
                break;
        }
        return archivador;
    }

}
