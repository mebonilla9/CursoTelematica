package co.appreactor.pokedex.modelo.servicio.call;

import co.appreactor.pokedex.modelo.entidades.PokemonRespuesta;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by lord_nightmare on 19/10/17.
 */

public interface PokemonCall {

    // referencia el metodo del web service que vamos a acceder
    @GET("pokemon")
    Call<PokemonRespuesta> obtenerListaPokemon(@Query("limit") int limit, @Query("offset") int offset);
}
