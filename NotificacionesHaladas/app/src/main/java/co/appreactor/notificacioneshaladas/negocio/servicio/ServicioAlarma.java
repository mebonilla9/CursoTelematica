package co.appreactor.notificacioneshaladas.negocio.servicio;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.NotificationCompat;

import co.appreactor.notificacioneshaladas.R;
import co.appreactor.notificacioneshaladas.negocio.actividades.MainActivity;

/**
 * Created by lord_nightmare on 18/10/17.
 */

public class ServicioAlarma extends Service {

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mostrarNotificacion();
        return super.onStartCommand(intent, flags, startId);
    }

    private void mostrarNotificacion() {
        // Objeto para generar la notificacion Pull
        NotificationCompat.Builder notificacion = new NotificationCompat.Builder(this);
        // aplicar un icono para la notificacion
        notificacion.setSmallIcon(R.mipmap.ic_launcher_round);
        // Asignar titulo a notificacion
        notificacion.setContentTitle("Notificacion de prueba");
        // Asignar texto a la notificacion
        notificacion.setContentText("Hola, soy una notificacion de prueba");
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
                this,
                1,
                new Intent(this, MainActivity.class),
                PendingIntent.FLAG_UPDATE_CURRENT);
        notificacion.setContentIntent(intencion);

        // Invocar al servicio de notificaciones
        NotificationManager manejador = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manejador.notify(1,notificacion.build());
    }
}
