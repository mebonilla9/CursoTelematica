package co.appreactor.conocimentos.persistencia.servicio;

import android.content.Context;
import android.widget.Toast;

import co.appreactor.conocimentos.negocio.actividades.MapsActivity;
import co.appreactor.conocimentos.persistencia.dto.arcgis.request.RequestArcGisDto;
import co.appreactor.conocimentos.persistencia.dto.arcgis.response.ResponseArcGisDto;
import co.appreactor.conocimentos.persistencia.servicio.call.ArcGisGeoCall;
import co.appreactor.conocimentos.persistencia.servicio.serviciobase.ArcGisService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by lord_nightmare on 19/12/17.
 */

public class ArcGisGeoService extends ArcGisService {

    private ArcGisGeoCall service;

    public ArcGisGeoService(Context contexto) {
        super(contexto);
        this.service = this.retrofit.create(ArcGisGeoCall.class);
    }

    public void consultarCuadrante(RequestArcGisDto localizacion){
        // invocacion del webservice
        Call<ResponseArcGisDto> respuesta = this.service.consultarCuadrante(localizacion);
        // trabajo de peticion asincrona
        respuesta.enqueue(new Callback<ResponseArcGisDto>() {
            @Override
            public void onResponse(Call<ResponseArcGisDto> call, Response<ResponseArcGisDto> response) {
                // validar que la peticion no sea exitosa, segun las reglas de negocio
                if (!response.isSuccessful() || response.body() == null ){
                    Toast.makeText(contexto, "Cuadrante no disponible para su ubicacion",Toast.LENGTH_LONG).show();
                    return;
                }
                // metodo del MapsActivity que manipula el objeto de la respuesta
                ((MapsActivity) contexto).procesarCuadrante(response.body());
            }

            @Override
            public void onFailure(Call<ResponseArcGisDto> call, Throwable t) {
                Toast.makeText(contexto, "No ha sido posible establecer conexion con el servicio de cuadrantes",Toast.LENGTH_LONG).show();
            }
        });
    }
}
