package co.appreactor.conocimentos.negocio.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import java.util.Timer;
import java.util.TimerTask;

import co.appreactor.conocimentos.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        crearTemporizador();
    }

    private void crearTemporizador() {
        // Crear una tarea de tiempo
        TimerTask tareaTemporal = new TimerTask() {
            @Override
            public void run() {
                // define la rutina de programacion que el hilo va a ejecutar

                // navegar a una nueva activad usando un objeto de intencion
                Intent intencion = new Intent(SplashActivity.this,LoginActivity.class);
                startActivity(intencion);
            }
        };
        // disparador de la tarea de tiempo
        Timer intevalo = new Timer();
        // asignacion del tiempo de espera
        intevalo.schedule(tareaTemporal,2000);
    }
}
