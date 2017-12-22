package co.appreactor.conocimentos.persistencia.servicio;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import java.util.List;

import co.appreactor.conocimentos.R;
import co.appreactor.conocimentos.negocio.actividades.MainActivity;
import co.appreactor.conocimentos.negocio.fragmentos.PreguntaFragment;
import co.appreactor.conocimentos.negocio.util.PreferenciasUtil;
import co.appreactor.conocimentos.persistencia.entidades.Respuesta;
import co.appreactor.conocimentos.persistencia.servicio.call.RespuestaTestCall;
import co.appreactor.conocimentos.persistencia.servicio.serviciobase.GenericoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Representa el objeto que consume la directiva del web service
 * tambien genera el trabajo asincrono para obtener el valor
 * de la respuesta del web service que invoca
 * Created by lord_nightmare on 12/12/17.
 */
public class RespuestaTestService extends GenericoService {

    /**
     * Instancia de la directiva para consumir el web service
     */
    private RespuestaTestCall service;

    /**
     * Constructor de clase que recibe como parametro el contexto de la actividad o fragmento
     * que lo invoca y asigna la directiva para el consumo de las peticion al servidor.
     * @param contexto contexto de la actividad o fragmento que invoca el consumo del web service
     */
    public RespuestaTestService(Context contexto) {
        super(contexto);
        // creacion del acceso al web service utilizando la interface
        // que representa la directiva
        this.service = this.retrofit.create(RespuestaTestCall.class);
    }

    /**
     * Obtiene la informacion de las respuestas que hacen parte de una pregunta almacenada en el
     * servidor, enviando la llave primara de la tabla PreguntaTest
     * @param idPregunta id o llave primaria de la pregunta a la cual vamos a consultar sus
     *                   respuestas.
     */
    public void consultarRespuestasPregunta(String idPregunta){
        Call<List<Respuesta>> respuesta = this.service.consultarRespuestasPregunta(
                idPregunta,
                PreferenciasUtil.leerPreferencia("token", contexto)
        );
        // generar tarea asicrona para la recepcion de la respuesta del servidor
        respuesta.enqueue(new Callback<List<Respuesta>>() {

            /**
             * Representa la recepcion de la respuesta (Exitosa o fallida) enviada
             * por el servidor
             * @param call Representa la peticion realizada al servidor (Request)
             * @param response Representa la respuesta del servidor a la aplicacion
             */
            @Override
            public void onResponse(Call<List<Respuesta>> call, Response<List<Respuesta>> response) {
                // validar que la respuesta sea satisfactoria o que contenga datos
                if (!response.isSuccessful() || response.body().isEmpty()){
                    Toast.makeText(contexto, "No se encontraron datos", Toast.LENGTH_SHORT).show();
                    return;
                }

                // obtener el fragmento actual que se encuentra activo para poder invocar el metodo
                // que va a manipular los datos de la respuesta del servidor

                // utilizando el contexto recibido en el contructor obtenemos el fragmento que se
                // encuentra dentro del frameLayout de la MainActivity
                Fragment fragmento = ((MainActivity) contexto)
                        .getSupportFragmentManager().findFragmentById(R.id.flContenedor);

                // validar la instancia del fragmento (Saber cual fragmento disparo este metodo)
                if (fragmento instanceof PreguntaFragment){
                    // metodo del fragmento que va a recibir y a manejar los datos de la respuesta
                    ((PreguntaFragment) fragmento).procesarRespuestas(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Respuesta>> call, Throwable t) {

            }
        });
    }
}
