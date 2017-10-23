package co.appreactor.notificacionespush.negocio.util;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;

import co.appreactor.notificacionespush.R;
import co.appreactor.notificacionespush.negocio.actividades.MainActivity;

/**
 * Created by lord_nightmare on 23/10/17.
 */

public final class NotificacionUtil {

    public static void mostrarNotificacion(Context contexto, String titulo, String mensaje){
        // Objeto para generar la notificacion Pull
        NotificationCompat.Builder notificacion = new NotificationCompat.Builder(contexto,"1");
        // aplicar un icono para la notificacion
        notificacion.setSmallIcon(R.mipmap.ic_launcher_round);
        // Asignar titulo a notificacion
        notificacion.setContentTitle(titulo);
        // Asignar texto a la notificacion
        notificacion.setContentText(mensaje );
        // Definir prioridad a notificacion
        notificacion.setPriority(NotificationCompat.PRIORITY_MAX);
        // Aplicar vibracion al lanzar la notificacion
        notificacion.setDefaults(NotificationCompat.DEFAULT_VIBRATE);
        // Asignar un tono de notificacion
        Uri sonidoDefecto = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        notificacion.setSound(sonidoDefecto);
        notificacion.setAutoCancel(true);
        notificacion.setOngoing(true);

        // Intent para redireccionar a la aplicacion al dar click
        PendingIntent intencion = PendingIntent.getActivity(
                contexto,
                1,
                new Intent(contexto, MainActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        notificacion.setContentIntent(intencion);

        // Invocar al servicio de notificaciones
        NotificationManager manejador = (NotificationManager)
                contexto.getSystemService(Context.NOTIFICATION_SERVICE);
        manejador.notify(1,notificacion.build());
    }
}
