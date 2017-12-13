package co.appreactor.conocimentos.persistencia.servicio.call;

import co.appreactor.conocimentos.persistencia.entidades.Account;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by lord_nightmare on 1/12/17.
 */

public interface AccountCall {

    // representar como metodos abstractos las rutas de los servicios que vamos
    // a consumir para la tabla Account

    @POST("api/Account/Register")
    Call<ResponseBody> registrarUsuario(@Body Account usuario);


}
