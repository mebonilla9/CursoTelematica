package co.appreactor.agendaandroid.negocio.actividades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import co.appreactor.agendaandroid.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        crearTemporizador();
    }

    private void crearTemporizador() {
        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
            }
        };
        Timer intervalo = new Timer();
        intervalo.schedule(tarea,2000);
    }
}
