package co.appreactor.agendaandroid.modelo.servicio;

import android.os.Environment;

import java.io.IOException;
import java.util.List;

import co.appreactor.agendaandroid.modelo.entidades.Persona;

/**
 * Created by lord_nightmare on 21/09/17.
 */

public interface IGestorArchivo {

    String rutaArchivo = Environment.getExternalStorageDirectory()
            .getAbsolutePath()+"/agenda_android";

    void escribir(List<Persona> listaPersonas) throws IOException;

    List<Persona> leer();

}
