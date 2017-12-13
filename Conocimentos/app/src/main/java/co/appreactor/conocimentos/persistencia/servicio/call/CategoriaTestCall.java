package co.appreactor.conocimentos.persistencia.servicio.call;

import java.util.List;

import co.appreactor.conocimentos.persistencia.entidades.Categoria;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;

/**
 * Created by lord_nightmare on 6/12/17.
 */

public interface CategoriaTestCall {

    @GET("api/CategoriaTests")
    Call<List<Categoria>> consultarCategorias(@Header("Authorization") String token);
}
