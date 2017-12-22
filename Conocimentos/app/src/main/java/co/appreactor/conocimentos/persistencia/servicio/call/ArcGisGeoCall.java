package co.appreactor.conocimentos.persistencia.servicio.call;

import co.appreactor.conocimentos.persistencia.dto.arcgis.request.RequestArcGisDto;
import co.appreactor.conocimentos.persistencia.dto.arcgis.response.ResponseArcGisDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by lord_nightmare on 19/12/17.
 */

public interface ArcGisGeoCall {

    @POST("INVERSEGEOCODE")
    Call<ResponseArcGisDto> consultarCuadrante(@Body RequestArcGisDto localizacion);

}
