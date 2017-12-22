package co.appreactor.conocimentos.persistencia.servicio;

import android.content.Context;
import android.support.v4.app.Fragment;

import java.io.IOException;

import co.appreactor.conocimentos.R;
import co.appreactor.conocimentos.negocio.actividades.MainActivity;
import co.appreactor.conocimentos.negocio.fragmentos.CategoriaFragment;
import co.appreactor.conocimentos.negocio.fragmentos.PreguntaFragment;
import co.appreactor.conocimentos.negocio.util.PreferenciasUtil;
import co.appreactor.conocimentos.persistencia.dto.EliminarPreguntaDto;
import co.appreactor.conocimentos.persistencia.entidades.Pregunta;
import co.appreactor.conocimentos.persistencia.servicio.call.PreguntaTestCall;
import co.appreactor.conocimentos.persistencia.servicio.serviciobase.GenericoService;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lord_nightmare on 14/12/17.
 */

public class PreguntaTestService extends GenericoService {

    private PreguntaTestCall service;

    public PreguntaTestService(Context contexto) {
        super(contexto);
        this.service = this.retrofit.create(PreguntaTestCall.class);
    }

    public void eliminarPregunta(EliminarPreguntaDto pregunta){
        Call<ResponseBody> respuesta = this.service.eliminarPregunta(
                PreferenciasUtil.leerPreferencia("token",contexto),
                pregunta
        );

        respuesta.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    if (!response.isSuccessful() || response.body().string().isEmpty()){
                        return;
                    }
                    // Procesamiento de la respuesta
                    Fragment fragmento = ((MainActivity) contexto).getSupportFragmentManager().findFragmentById(R.id.flContenedor);
                    if (fragmento instanceof CategoriaFragment){
                        ((CategoriaFragment) fragmento).procesarEliminacionPregunta(response.body().string());
                    }
                } catch (IOException e){

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }

    public void actualizarPregunta(Pregunta preguntaEditar){
        Call<ResponseBody> respuesta = this.service.actualizarPregunta(
                PreferenciasUtil.leerPreferencia("token",contexto),
                preguntaEditar
        );

        // tratamiento Asincrono
        respuesta.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try{
                    if (!response.isSuccessful() || response.body().string().isEmpty()){
                        return;
                    }
                    // Procesamiento de la respuesta
                    Fragment fragmento = ((MainActivity) contexto).getSupportFragmentManager().findFragmentById(R.id.flContenedor);
                    if (fragmento instanceof PreguntaFragment){
                        ((PreguntaFragment) fragmento).procesarActualizacionPregunta();
                    }
                } catch (IOException e){

                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {

            }
        });
    }
}
