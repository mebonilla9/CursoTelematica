package co.appreactor.notificacioneshaladas.negocio.util;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;
import java.util.Date;

import co.appreactor.notificacioneshaladas.negocio.servicio.ServicioAlarma;

/**
 * Created by lord_nightmare on 17/10/17.
 */

public final class AlarmaUtil {

    public static void crearAlarma(Context contexto, int dia){
        PendingIntent servicioAlarma = PendingIntent.getService(
                contexto,1,
                new Intent(contexto, ServicioAlarma.class),
                PendingIntent.FLAG_UPDATE_CURRENT
        );
        long tiempoNotificacion = obtenerFecha(dia);
        // Generar una alarma con base al pending intent que fue creado
        AlarmManager manager = (AlarmManager) contexto.getSystemService(Context.ALARM_SERVICE);
        manager.set(AlarmManager.RTC_WAKEUP,tiempoNotificacion,servicioAlarma);
    }

    private static long obtenerFecha(int dia){
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(new Date());
        calendario.set(Calendar.DAY_OF_MONTH,dia);
        calendario.set(Calendar.HOUR_OF_DAY,8);
        calendario.set(Calendar.MINUTE,44);
        calendario.set(Calendar.SECOND,0);
        return calendario.getTime().getTime();
    }
}
