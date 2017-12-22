package co.appreactor.conocimentos.negocio.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import co.appreactor.conocimentos.R;
import co.appreactor.conocimentos.negocio.util.FragmentoUtil;
import co.appreactor.conocimentos.negocio.util.PreferenciasUtil;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private android.support.v7.widget.Toolbar toolbar;
    private android.support.design.widget.FloatingActionButton fab;
    private android.widget.FrameLayout flContenedor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.drawer = findViewById(R.id.drawer_layout);
        this.navigationView = findViewById(R.id.nav_view);
        this.toolbar = findViewById(R.id.toolbar);
        this.fab = findViewById(R.id.fab);
        this.flContenedor = findViewById(R.id.flContenedor);

        // para asignar una barra de accion o de tareas compatible para versiones anteriores
        // basado en el la toolbar que se creo con xml
        setSupportActionBar(toolbar);

        // evento click prefabricado por el ide para una funcion del boton de accion flotante
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // canaliza el evento y el gesto para la visualizacion de la caja de navegacion
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        // asignacion del evento de click para los items de menu de la caja de navegacion
        navigationView.setNavigationItemSelectedListener(this);

        // lectura del token (Ejemplo de SharedPrefereneces)
        String miToken = PreferenciasUtil.leerPreferencia("token", MainActivity.this);
        Toast.makeText(this, "Bienvenido usuario", Toast.LENGTH_SHORT).show();
    }

    /**
     * capturar el evento click del boton de regresar del sistema operativo
     * para esta actividad
     */
    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        // validar que el item de menu seleccionado no sea el de mapas (ArcGis)
        if (id == R.id.miMapas){
            startActivity(new Intent(MainActivity.this, MapsActivity.class));
            return true;
        }

        // FragmentoUtil
        FragmentoUtil.obtenerFragmento(
                id,
                null,
                getSupportFragmentManager().beginTransaction(),
                R.id.flContenedor
        );

        // permite el desplazamiento a la posicion escondidad del
        // Navigation Drawer
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Metodo para activar o desactivar la visualizacion del boton de accion flotante en los fragmentos
     * @param activo directiva para activar o desactivar el boton
     */
    public void activarBotonFlotante(boolean activo) {
        this.fab.setVisibility(activo ? View.VISIBLE : View.GONE);
    }

    /**
     * metodo de acceso para el boton flotante de la main activity en los fragmentos
     * @return
     */
    public FloatingActionButton getFab(){
        return this.fab;
    }

    /**
     * Metodo que permite de manera dinamica modificar el valor del titulo de la toolbar y su
     * subtitulo
     * @param titulo Representa el titulo para el fragmento activo
     * @param subtitulo Representa el subtitulo para el fragmento activo
     */
    public void modificarTituloSubtitulo(String titulo, String subtitulo){
        // obtener al toolbar para modificar su titulo y de ser necesario su subtitulo
        getSupportActionBar().setTitle(titulo);
        getSupportActionBar().setSubtitle(subtitulo);
    }
}
