package co.appreactor.agendaandroid.negocio.actividades;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import co.appreactor.agendaandroid.R;
import co.appreactor.agendaandroid.modelo.dao.PersonaDao;
import co.appreactor.agendaandroid.modelo.entidades.Persona;
import co.appreactor.agendaandroid.negocio.util.AlertaUtil;

public class NuevoActivity extends AppCompatActivity {

    private TextInputLayout txtEdad;
    private TextInputLayout txtTelefono;
    private TextInputLayout txtDireccion;
    private TextInputLayout txtCorreo;
    private TextInputLayout txtNombre;
    private Button btnGuardar;

    //private IGestorArchivo gestorArchivo;


    private DialogInterface.OnClickListener confirmarGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);
        this.btnGuardar = (Button) findViewById(R.id.btnGuardar);
        this.txtNombre = (TextInputLayout) findViewById(R.id.txtNombre);
        this.txtCorreo = (TextInputLayout) findViewById(R.id.txtCorreo);
        this.txtDireccion = (TextInputLayout) findViewById(R.id.txtDireccion);
        this.txtTelefono = (TextInputLayout) findViewById(R.id.txtTelefono);
        this.txtEdad = (TextInputLayout) findViewById(R.id.txtEdad);
        // Adquirir el formato seleccionado por configuración
        //gestorArchivo = FormatoUtil.retornarFormato(NuevoActivity.this);
        asignarEventos();
    }

    private void asignarEventos() {
        confirmarGuardar = new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                startActivity(new Intent(NuevoActivity.this, MainActivity.class));
            }
        };

        this.btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarPersona();
            }
        });
    }

    private void guardarPersona() {
        //try {
        Persona personaGuardar = new Persona();
        personaGuardar.setNombre(this.txtNombre.getEditText().getText().toString());
        personaGuardar.setCorreo(this.txtCorreo.getEditText().getText().toString());
        personaGuardar.setDireccion(this.txtDireccion.getEditText().getText().toString());
        personaGuardar.setTelefono(this.txtTelefono.getEditText().getText().toString());
        personaGuardar.setEdad(this.txtEdad.getEditText().getText().toString());

        //List<Persona> listaPersonas = gestorArchivo.leer();

        //listaPersonas.add(personaGuardar);

        //gestorArchivo.escribir(listaPersonas);

        new PersonaDao(NuevoActivity.this).insertar(personaGuardar);

        AlertaUtil.mostrarAlerta("Guardar Persona",
                "Se ha guardado la informacion de una nueva persona",
                confirmarGuardar, null, NuevoActivity.this
        );
        /*} catch(IOException e){
            e.printStackTrace(System.err);
        }*/
    }
}
