package co.appreactor.eventos.persistencia.servicio;

import android.os.Handler;
import android.os.Message;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import co.appreactor.eventos.persistencia.dto.RespuestaDTO;

/**
 * Created by lord_nightmare on 6/10/17.
 */

public final class ServicioHttp {

    private static final String URL_SERVIDOR = "http://appreactor.co:80/eventos";
    //private static final String URL_SERVIDOR = "http://192.168.1.171:8080/eventos";

    public static void invocar(final Object paramentros,
                               final String nombreParametros,
                               final String metodo,
                               final Type tipoRespuesta,
                               final Handler puente,
                               final String ruta){

        // Hilo generador de a peticion al servidor
        Thread hiloServidor = new Thread(new Runnable() {
            @Override
            public void run() {
                // Aqui va la logica para conectarnos al servidor

                // Instancia del Message que almacenara la respuesta
                // new GsonBuilder().setDateFormat("MMM d, yyyy HH:mm:ss a").create()
                // del servidor
                Message mensaje = new Message();
                RespuestaDTO respuesta = new RespuestaDTO();
                String data = new GsonBuilder().setDateFormat("MMM d, yyyy HH:mm:ss a").create().toJson(paramentros);
                // trabajo de conexion con el servidor
                try{
                    URL url = new URL(URL_SERVIDOR+ruta);
                    // objeto que representa la conexion con el servidor
                    HttpURLConnection conexion = (HttpURLConnection) url.openConnection();
                    // asignacion del typo de metodo de acceso por http
                    conexion.setRequestMethod(metodo);
                    // asignacion de propiedad de contenido a enviar
                    conexion.setRequestProperty("Content-Type","application/json");
                    // facultar a la conexion para que pueda enviar parametros
                    conexion.setDoOutput(true);
                    // facultar a la conexion para que reciba informacion del servidor
                    conexion.setDoInput(true);

                    // Enviar los parametros al servidor
                    OutputStreamWriter salida = new OutputStreamWriter(
                            conexion.getOutputStream()
                    );

                    // enviar los parametros de manera adecuada segun el metodo de envio
                    if (metodo.equals("GET")){
                        salida.write(nombreParametros+"="+paramentros);
                    } else {
                        salida.write(data);
                    }
                    salida.flush();
                    salida.close();

                    // procesar la respuesta del servidor
                    // Crear el canal de entrada para procesar el texto de la respuesta
                    BufferedReader entrada = new BufferedReader(
                            new InputStreamReader(conexion.getInputStream())
                    );
                    // "IMPORTANTE" leer la primera linea despues de crear el objeto del
                    // canal de entrada
                    String linea = entrada.readLine();

                    StringBuilder contenido = new StringBuilder();
                    while (linea != null){
                        contenido.append(linea);
                        linea = entrada.readLine();
                    }
                    entrada.close();

                    // Transformar la respuesta JSON a objetos de clase Java
                    respuesta = new GsonBuilder().setDateFormat("MMM d, yyyy HH:mm:ss a").create().fromJson(contenido.toString(),tipoRespuesta);
                    respuesta.setRuta(ruta);

                    mensaje.obj = respuesta;
                    conexion.disconnect();

                } catch (IOException e){
                    e.printStackTrace();
                    respuesta.setCodigo(-1);
                    respuesta.setMensaje("Error fatal: "+e.getMessage());
                }
                // transmitir el objeto del mensaje al Handler que reside
                // en la actividad o fragmento que llama al metodo invocar
                puente.sendMessage(mensaje);
            }
        });
        // Poner en marcha el hilo
        hiloServidor.start();

    }

}
