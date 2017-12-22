package co.appreactor.conocimentos.persistencia.servicio.call;

import co.appreactor.conocimentos.persistencia.dto.EliminarPreguntaDto;
import co.appreactor.conocimentos.persistencia.entidades.Pregunta;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

/**
 * Created by lord_nightmare on 14/12/17.
 */

public interface PreguntaTestCall {

    // Agregar el metodo abstracto que representa el web service que vamos a consumir
    @POST("api/EliminarPreguntaTest")
    Call<ResponseBody> eliminarPregunta(
            @Header("Authorization") String token,
            @Body EliminarPreguntaDto pregunta
    );

    // Llamada para editar una pregunta

    @POST("api/ActualizarPreguntaTest")
    Call<ResponseBody> actualizarPregunta(
            @Header("Authorization") String token,
            @Body Pregunta pregunta
    );

}
