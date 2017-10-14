package co.appreactor.eventos.negocio.actividades;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import co.appreactor.eventos.R;
import co.appreactor.eventos.negocio.util.FragmentUtil;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView navigationView;
    private DrawerLayout drawer;
    private Toolbar toolbar;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.fab = (FloatingActionButton) findViewById(R.id.fab);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        this.drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        this.navigationView = (NavigationView) findViewById(R.id.nav_view);
        setSupportActionBar(toolbar);

        // evento click del boton flotante
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Generacion de la accion que abre la caja de navegacion con el evento
        // click de un boton ubicado en la parte superior izquierda de la interfaz
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        // asignacion del evento del toggle (setDrawerListener -> Deprecated)
        drawer.addDrawerListener(toggle);
        // permite que los cambios en tiempo de ejecucion sean efectivos y muestren
        // las respectivas animaciones
        toggle.syncState();

        // asignar el listener que administra la seleccion de items
        // de la caja de menu
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            moveTaskToBack(true);
        }
    }

    //@SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentUtil.obtenerFragmento(id,null,
                getSupportFragmentManager().beginTransaction(),
                R.id.flContenedor);

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        // Componente del transaccion de fragmentos
        //FragmentTransaction transaccion = getSupportFragmentManager().beginTransaction();
        //transaccion.replace(R.id.flContenedor,new RegistrarEventoFragment(),"otro");
        //transaccion.commit();
    }
}
