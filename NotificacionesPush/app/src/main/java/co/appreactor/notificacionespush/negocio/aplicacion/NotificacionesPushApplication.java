package co.appreactor.notificacionespush.negocio.aplicacion;


import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import co.appreactor.notificacionespush.negocio.servicios.GestorMensajeriaPush;

/**
 * Created by lord_nightmare on 23/10/17.
 */

public class NotificacionesPushApplication extends MultiDexApplication{

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(NotificacionesPushApplication.this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (!servicioEnEjecucion("co.appreactor.notificacionespush.negocio.servicios.GestorMensajeriaPush")){
            startService(new Intent(this, GestorMensajeriaPush.class));
        }

        // validacion igual para el segundo evento
    }

    private boolean servicioEnEjecucion(String servicio){
        // Obtener el servicio de actividades
        ActivityManager manager = (ActivityManager) getSystemService(ACTIVITY_SERVICE);
        // iterar a los servicios en ejecucion
        for (ActivityManager.RunningServiceInfo ejecutado : manager.getRunningServices(Integer.MAX_VALUE)){
            if (ejecutado.service.getClassName().equals(servicio))
                return true;
        }
        return false;
    }
}
