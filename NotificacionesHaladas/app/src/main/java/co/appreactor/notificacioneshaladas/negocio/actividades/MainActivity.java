package co.appreactor.notificacioneshaladas.negocio.actividades;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import co.appreactor.notificacioneshaladas.R;
import co.appreactor.notificacioneshaladas.negocio.util.AlarmaUtil;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        AlarmaUtil.crearAlarma(this,18);
    }
}
