package co.appreactor.conocimentos.persistencia.servicio;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.widget.Toast;

import java.util.List;

import co.appreactor.conocimentos.R;
import co.appreactor.conocimentos.negocio.actividades.MainActivity;
import co.appreactor.conocimentos.negocio.fragmentos.CategoriaFragment;
import co.appreactor.conocimentos.negocio.util.PreferenciasUtil;
import co.appreactor.conocimentos.persistencia.entidades.Categoria;
import co.appreactor.conocimentos.persistencia.servicio.call.CategoriaTestCall;
import co.appreactor.conocimentos.persistencia.servicio.serviciobase.GenericoService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lord_nightmare on 6/12/17.
 */

public class CategoriaTestService extends GenericoService {

    private CategoriaTestCall service;

    public CategoriaTestService(Context contexto) {
        super(contexto);
        this.service = this.retrofit.create(CategoriaTestCall.class);
    }

    public void consultarCategorias(){
        Call<List<Categoria>> respuesta = this.service.consultarCategorias(
                PreferenciasUtil.leerPreferencia("token",contexto)
        );
        respuesta.enqueue(new Callback<List<Categoria>>() {
            @Override
            public void onResponse(Call<List<Categoria>> call, Response<List<Categoria>> response) {
                if (!response.isSuccessful() || response.body().isEmpty()){
                    Toast.makeText(contexto, "No se encontraron datos", Toast.LENGTH_SHORT).show();
                    return;
                }
                // obtener el fragmento actual que se esta ejecutando dentro de la actividad

                Fragment fragmento = ((MainActivity) contexto).getSupportFragmentManager().findFragmentById(R.id.flContenedor);
                if (fragmento instanceof CategoriaFragment){
                    ((CategoriaFragment) fragmento).procesarCategorias(response.body());
                }
                // diferentes if para los otros fragmentos que invoquen esta peticion
            }

            @Override
            public void onFailure(Call<List<Categoria>> call, Throwable t) {

            }
        });
    }
}
