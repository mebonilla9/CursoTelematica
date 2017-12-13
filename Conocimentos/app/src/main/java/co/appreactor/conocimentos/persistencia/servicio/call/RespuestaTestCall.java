package co.appreactor.conocimentos.persistencia.servicio.call;

import java.util.List;

import co.appreactor.conocimentos.persistencia.entidades.Respuesta;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

/**
 * Created by lord_nightmare on 12/12/17.
 */

public interface RespuestaTestCall {

    /**
     * Directiva de ruta que nos va a permitir consumir el web services que termina
     * por la ruta del parametro de la anotacion @GET
     *
     * @param id id de la pregunta que se le proporciona al web service
     * @param token Parametro de cabecera de la peticion para la autenticación
     * @return Coleccion de objeros de la clase Respuesta
     */
    @GET("api/PreguntaRespuesta/{id}")
    Call<List<Respuesta>> consultarRespuestasPregunta(
            @Path("id") String id,
            @Header("Authorization") String token
    );

}
