package co.appreactor.conocimentos.persistencia.servicio.serviciobase;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lord_nightmare on 1/12/17.
 */

public abstract class GenericoService {

    // Instancia de Retrofit para consumir webServices
    protected final Retrofit retrofit;
    protected final Context contexto;

    public GenericoService(Context contexto) {
        // Asignar el contexto de quien invoca un servicio
        this.contexto = contexto;

        // construir la instacia de retrofit
        this.retrofit = new Retrofit.Builder()
                .baseUrl("http://sw.pruebasdeconocimiento.com.co")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
