package co.appreactor.miprimeraapp;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    private TextInputLayout txtNombre;
    private TextInputLayout txtApellido;
    private Button btnLlamar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.txtNombre = (TextInputLayout) findViewById(R.id.txtNombre);
        this.txtApellido = (TextInputLayout) findViewById(R.id.txtApellido);
        this.btnLlamar = (Button) findViewById(R.id.btnLlamar);
    }

    public void clickLlamar(View view){
        String nombre = txtNombre.getEditText().getText().toString();
        String apellido = txtApellido.getEditText().getText().toString();

        Toast.makeText(this, "Hola "+nombre+" "+apellido, Toast.LENGTH_LONG).show();
    }
}
