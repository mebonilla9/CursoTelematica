package co.appreactor.conocimentos.persistencia.servicio;

import android.content.Context;
import android.widget.Toast;

import java.io.IOException;

import co.appreactor.conocimentos.negocio.actividades.RegistrarActivity;
import co.appreactor.conocimentos.persistencia.entidades.Account;
import co.appreactor.conocimentos.persistencia.servicio.call.AccountCall;
import co.appreactor.conocimentos.persistencia.servicio.serviciobase.GenericoService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lord_nightmare on 1/12/17.
 */

public class AccountService extends GenericoService {

    // Atributo del Call -> Rutas abstractas
    private AccountCall service;

    public AccountService(Context contexto) {
        super(contexto);
        // asignacion del call que determina a donde van las peticiones
        this.service = this.retrofit.create(AccountCall.class);
    }

    public void registrarUsuario(Account usuario){
        // Instruccion para enviar la peticion al servidor
        Call<ResponseBody> respuesta = this.service.registrarUsuario(usuario);
        // traer de manera asincrona la respuesta del servidor
        respuesta.enqueue(new Callback<ResponseBody>() {
            /**
             * Procesar la respuesta del servidor sea o no existosa
             * @param call instancia de la peticion que fue lanzada
             * @param response respuesta generada por el servidor
             */
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    // verificar que la respuesta sea satisfactoria
                    if (!response.isSuccessful()){
                        Toast.makeText(contexto,"No se pudo registrar al usuario",Toast.LENGTH_LONG).show();
                        return;
                    }
                    // obtener como referencia la actividad que lanzo la peticion y el metodo que va
                    // a procesar su resultado
                    ((RegistrarActivity) contexto).procesarRespuestaRegistro(response.body());
                } catch(IOException e){
                    e.printStackTrace();
                }
            }

            /**
             * Canalizar los errores que surgieron al procesar la peticion
             * (Conexion, timeout)
             * @param call instancia de la peticion que fue lanzada
             * @param t instancia de la excepcion que se disparo
             */
            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }
}
