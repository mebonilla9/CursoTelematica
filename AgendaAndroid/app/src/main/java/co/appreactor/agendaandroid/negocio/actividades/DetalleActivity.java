package co.appreactor.agendaandroid.negocio.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import co.appreactor.agendaandroid.R;
import co.appreactor.agendaandroid.modelo.entidades.Persona;

public class DetalleActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private CollapsingToolbarLayout toolbarlayout;
    private AppBarLayout appbar;
    private FloatingActionButton fab;

    private TextView lblCorreoDetalle;
    private TextView lblEdadDetalle;
    private TextView lblTelefonoDetalle;
    private TextView lblDireccionDetalle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);
        this.lblDireccionDetalle = (TextView) findViewById(R.id.lblDireccionDetalle);
        this.lblTelefonoDetalle = (TextView) findViewById(R.id.lblTelefonoDetalle);
        this.lblEdadDetalle = (TextView) findViewById(R.id.lblEdadDetalle);
        this.lblCorreoDetalle = (TextView) findViewById(R.id.lblCorreoDetalle);
        this.fab = (FloatingActionButton) findViewById(R.id.fab);
        this.appbar = (AppBarLayout) findViewById(R.id.app_bar);
        this.toolbarlayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        cargarInformacion();

        this.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DetalleActivity.this,MainActivity.class));
            }
        });
    }

    private void cargarInformacion() {
        // Recibir el objeto enviado desde el intent de mainActivity
        Persona personaRecibida = (Persona) getIntent().getSerializableExtra("persona_enviada");
        getSupportActionBar().setTitle(personaRecibida.getNombre());
        getSupportActionBar().setSubtitle(getString(R.string.info_persona));
        lblCorreoDetalle.setText(personaRecibida.getCorreo());
        lblEdadDetalle.setText(personaRecibida.getEdad());
        lblTelefonoDetalle.setText(personaRecibida.getTelefono());
        lblDireccionDetalle.setText(personaRecibida.getDireccion());
    }
}
