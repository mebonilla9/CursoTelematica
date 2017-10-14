package co.appreactor.agendaandroid.negocio.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import co.appreactor.agendaandroid.R;
import co.appreactor.agendaandroid.negocio.util.AlertaUtil;
import co.appreactor.agendaandroid.negocio.util.PreferenciasUtil;

public class ConfiguracionActivity extends AppCompatActivity {

    private android.widget.Spinner spOpcionArchivo;
    private Button btnGuardarConf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracion);
        this.btnGuardarConf = (Button) findViewById(R.id.btnGuardarConf);
        this.spOpcionArchivo = (Spinner) findViewById(R.id.spOpcionArchivo);
        // cargar lista de strings al spinner usando un adaptador
        ArrayAdapter<CharSequence> adaptador = ArrayAdapter.createFromResource(
                ConfiguracionActivity.this,
                R.array.opciones_archivo,
                android.R.layout.simple_spinner_dropdown_item
        );

        this.spOpcionArchivo.setAdapter(adaptador);

        // Asignar evento click boton guardar
        this.btnGuardarConf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                establecerFormato();
                startActivity(new Intent(ConfiguracionActivity.this,MainActivity.class));
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        String formatoSeleccionado = PreferenciasUtil.leerPreferencia("formato",ConfiguracionActivity.this);
        int posicion = 0;
        switch (formatoSeleccionado) {
            case "json":
                posicion = 1;
                break;
            case "sax":
                posicion = 2;
                break;
            case "dom":
                posicion = 3;
                break;
        }
        this.spOpcionArchivo.setSelection(posicion);
    }

    private void establecerFormato() {
        int posicion = this.spOpcionArchivo.getSelectedItemPosition();
        if (posicion < 1) {
            AlertaUtil.mostrarAlerta("Selección de formato",
                    "Debe seleccionar un formato de archivo valido",
                    null, null, ConfiguracionActivity.this);
            return;
        }
        String formato = "";
        switch (posicion) {
            case 1:
                formato = "json";
                break;
            case 2:
                formato = "sax";
                break;
            case 3:
                formato = "dom";
                break;
        }
        PreferenciasUtil.guardarPreferencia("formato", formato, ConfiguracionActivity.this);
    }
}
