package co.appreactor.eventos.negocio.actividades;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.Timer;
import java.util.TimerTask;

import co.appreactor.eventos.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        tareaTemporal();
    }

    private void tareaTemporal() {
        TimerTask tarea = new TimerTask() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this,MainActivity.class));
            }
        };
        Timer intervalo = new Timer();
        intervalo.schedule(tarea, 1500);
    }

}
