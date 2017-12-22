package co.appreactor.conocimentos.persistencia.servicio.serviciobase;

import android.content.Context;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by lord_nightmare on 19/12/17.
 */

public abstract class ArcGisService {

    // Instancia de Retrofit para consumir webServices
    protected final Retrofit retrofit;
    protected final Context contexto;

    public ArcGisService(Context contexto) {
        // Asignar el contexto de quien invoca un servicio
        this.contexto = contexto;

        // construir la instacia de retrofit
        this.retrofit = new Retrofit.Builder()
                .baseUrl("https://sigponal.policia.gov.co:8080/InverseGeocoding.svc/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
