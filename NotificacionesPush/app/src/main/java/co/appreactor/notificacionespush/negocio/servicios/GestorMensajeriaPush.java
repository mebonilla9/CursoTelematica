package co.appreactor.notificacionespush.negocio.servicios;

import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.FirebaseInstanceIdService;
import com.google.firebase.messaging.FirebaseMessaging;

import co.appreactor.notificacionespush.R;

/**
 * Created by lord_nightmare on 23/10/17.
 */

public class GestorMensajeriaPush extends FirebaseInstanceIdService {

    @Override
    public void onTokenRefresh() {
        super.onTokenRefresh();
        String tokenActual = FirebaseInstanceId.getInstance().getToken();
        if (tokenActual == null && tokenActual.isEmpty()){
            FirebaseMessaging.getInstance().subscribeToTopic(getString(R.string.tema_notificacion));
        }
    }
}
