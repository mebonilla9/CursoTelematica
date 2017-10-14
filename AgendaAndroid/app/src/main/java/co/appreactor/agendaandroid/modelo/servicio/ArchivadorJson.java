package co.appreactor.agendaandroid.modelo.servicio;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import co.appreactor.agendaandroid.modelo.entidades.Persona;

/**
 * Created by lord_nightmare on 22/09/17.
 */

public class ArchivadorJson implements IGestorArchivo {

    private final String nombreArchivo = "/archivo_json.txt";
    private static final String TAG_JSON = "ArchivadorJson";

    public ArchivadorJson() {
        File carpeta = new File(rutaArchivo);
        if (!carpeta.exists()){
            carpeta.mkdir();
            Log.d(TAG_JSON, "Ruta carpeta: "+carpeta.getAbsolutePath());
        }
    }

    @Override
    public void escribir(List<Persona> listaPersonas) throws IOException{
        PrintStream escritor = new PrintStream(
                rutaArchivo+nombreArchivo
        );
        escritor.print(new Gson().toJson(listaPersonas));
        escritor.flush();
        escritor.close();
    }

    @Override
    public List<Persona> leer() {
        List<Persona> listaPersonas = new ArrayList<>();
        try{

            BufferedReader lector = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(
                                    rutaArchivo+nombreArchivo
                            )
                    )
            );

            StringBuilder contenido = new StringBuilder();
            while (lector.ready()){
                contenido.append(lector.readLine());
            }
            // obtener tipo de dato a deserializar en tiempo de ejecución
            Type tipoDato = new TypeToken<List<Persona>>(){}.getType();
            listaPersonas = new Gson().fromJson(contenido.toString(),tipoDato);
        } catch(Exception e){
            Log.e(TAG_JSON, e.getMessage());
        }
        return listaPersonas;
    }
}
