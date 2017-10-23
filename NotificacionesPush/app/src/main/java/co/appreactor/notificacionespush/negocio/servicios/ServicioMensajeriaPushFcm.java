package co.appreactor.notificacionespush.negocio.servicios;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import co.appreactor.notificacionespush.R;
import co.appreactor.notificacionespush.negocio.util.NotificacionUtil;

/**
 * Created by lord_nightmare on 23/10/17.
 */

public class ServicioMensajeriaPushFcm extends FirebaseMessagingService {

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        if (remoteMessage.getFrom().equals("/topics/"+getString(R.string.tema_notificacion))){
            String titulo = remoteMessage.getNotification().getTitle();
            String mensaje = remoteMessage.getNotification().getBody();
            NotificacionUtil.mostrarNotificacion(this, titulo, mensaje);
        }
    }
}
