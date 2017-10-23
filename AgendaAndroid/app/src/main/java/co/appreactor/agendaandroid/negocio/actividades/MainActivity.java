package co.appreactor.agendaandroid.negocio.actividades;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import co.appreactor.agendaandroid.R;
import co.appreactor.agendaandroid.modelo.dao.PersonaDao;
import co.appreactor.agendaandroid.modelo.entidades.Persona;
import co.appreactor.agendaandroid.negocio.adaptadores.AdaptadorPersona;

public class MainActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private TextInputLayout txtBuscar;
    private ListView lstPersonas;

    // Atributos para solicitud de permisos en tiempo de ejecucion
    private final int codigoPermiso = 666;
    private final String[] permisos = new String[]{
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

    //private IGestorArchivo gestorArchivo;
    private List<Persona> listaPersonas;
    private List<Persona> listaTemporal;

    // Evento click para confirmar eliminacion de persona
    private DialogInterface.OnClickListener clickEliminar;

    private int posicionEliminar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.lstPersonas = (ListView) findViewById(R.id.lstPersonas);
        this.txtBuscar = (TextInputLayout) findViewById(R.id.txtBuscar);
        this.fab = (FloatingActionButton) findViewById(R.id.fab);
        this.toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        asignarEventos();
        // Adquirir el formato seleccionado por configuración
        //gestorArchivo = FormatoUtil.retornarFormato(MainActivity.this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        int permisoConcedido = ContextCompat.checkSelfPermission(MainActivity.this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (permisoConcedido != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, permisos, codigoPermiso);
            return;
        }
        llenarLista();
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    public void onBackPressed() {
        // permite enviar la aplicacion a estado detenido y que la pantala
        // muestre el home del SO
        moveTaskToBack(true);
    }

    private void llenarLista() {
        try {
            //this.listaPersonas = this.gestorArchivo.leer();
            this.listaPersonas = new PersonaDao(MainActivity.this).consultar();
            AdaptadorPersona adaptador = new AdaptadorPersona(
                    listaPersonas, MainActivity.this);
            this.lstPersonas.setAdapter(adaptador);
        } catch (NullPointerException e) {
            Log.e("Archivo", "Primera Ejecucion o archivo no existe aun");
        }
    }

    private void asignarEventos() {
        // Evento click para Boton flotante
        this.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent irNuevo = new Intent(MainActivity.this, NuevoActivity.class);
                startActivity(irNuevo);
            }
        });

        // Evento click de los items de la lista
        this.lstPersonas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                visualizarPersona(position);
            }
        });

        // Evento click sostendido para los items de la lista
        /*this.lstPersonas.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                posicionEliminar = position;
                AlertaUtil.mostrarAlerta("Eliminacion de Persona",
                        "Esta seguro de que desea eliminar este registro",
                        clickEliminar, null, MainActivity.this);
                return true;
            }
        });*/

        // Evento click para confirmar eliminacion
        this.clickEliminar = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                eliminarPersona();
            }
        };

        // Evento change del control txtBuscar
        this.txtBuscar.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filtrarLista(s);
            }

            @Override
            public void afterTextChanged(Editable s) {
                //
            }
        });
    }

    private void eliminarPersona() {
        /*try {
            if (this.listaTemporal != null){
                Persona personaTemporal = this.listaTemporal.get(posicionEliminar);
                for (Persona personaBuscar : this.listaPersonas){
                    if (personaBuscar.equals(personaTemporal)){
                        this.listaPersonas.remove(personaBuscar);
                        break;
                    }
                }
            } else {
                listaPersonas.remove(posicionEliminar);
            }
            gestorArchivo.escribir(listaPersonas);
            llenarLista();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
    }

    private void filtrarLista(CharSequence s) {
        this.listaTemporal = new ArrayList<>();
        for(Persona iterador : this.listaPersonas){
            if (iterador.getNombre().toLowerCase().contains(s.toString().toLowerCase())){
                this.listaTemporal.add(iterador);
            }
        }
        // Cambiar el origen del dataset del ListView de persona usando un
        // adaptador
        lstPersonas.setAdapter(new AdaptadorPersona(this.listaTemporal,MainActivity.this));
    }

    private void visualizarPersona(int position) {
        Intent irDetalle = new Intent(MainActivity.this, DetalleActivity.class);
        Persona personaEnviar;
        // Evaluar si existe informacion en la lista temporal
        if (this.listaTemporal != null){
            personaEnviar = this.listaTemporal.get(position);
        } else {
            personaEnviar = this.listaPersonas.get(position);
        }
        irDetalle.putExtra("persona_enviada", personaEnviar);
        startActivity(irDetalle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            startActivity(new Intent(MainActivity.this, ConfiguracionActivity.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != codigoPermiso) {
            Toast.makeText(this, "Permiso de escritura no concedido", Toast.LENGTH_SHORT).show();
            return;
        }
        // logica en caso de que el if anterior no entre
        if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            llenarLista();
        }

    }
}
