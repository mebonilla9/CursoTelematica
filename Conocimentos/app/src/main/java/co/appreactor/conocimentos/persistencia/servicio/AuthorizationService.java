package co.appreactor.conocimentos.persistencia.servicio;

import android.content.Context;
import android.widget.Toast;

import co.appreactor.conocimentos.negocio.actividades.LoginActivity;
import co.appreactor.conocimentos.persistencia.dto.TokenDto;
import co.appreactor.conocimentos.persistencia.servicio.call.AuthorizationCall;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lord_nightmare on 4/12/17.
 */

public class AuthorizationService extends GenericoService {

    private static final String GRANT_TYPE = "password";

    private AuthorizationCall service;

    public AuthorizationService(Context contexto) {
        super(contexto);
        service = this.retrofit.create(AuthorizationCall.class);
    }

    public void autenticarUsuario(String userName, String password){
        Call<TokenDto> respuesta = this.service.autenticarUsuario(GRANT_TYPE, userName, password);
        respuesta.enqueue(new Callback<TokenDto>() {
            @Override
            public void onResponse(Call<TokenDto> call, Response<TokenDto> response) {
                if (!response.isSuccessful() || response.body() == null){
                    Toast.makeText(
                            contexto,
                            "Usuario no encontrado, intente nuevamente",
                            Toast.LENGTH_SHORT
                    ).show();
                    return;
                }
                TokenDto token = response.body();
                ((LoginActivity) contexto).procesarAutenticacion(token);
            }

            @Override
            public void onFailure(Call<TokenDto> call, Throwable t) {

            }
        });
    }
}
