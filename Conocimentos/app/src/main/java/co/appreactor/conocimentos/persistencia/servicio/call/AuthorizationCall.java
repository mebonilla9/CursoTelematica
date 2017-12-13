package co.appreactor.conocimentos.persistencia.servicio.call;

import co.appreactor.conocimentos.persistencia.dto.TokenDto;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by lord_nightmare on 4/12/17.
 */

public interface AuthorizationCall {

    @POST("token")
    @FormUrlEncoded
    Call<TokenDto> autenticarUsuario(
            @Field("grant_type") String grantType,
            @Field("username") String userName,
            @Field("password") String password
    );
}
