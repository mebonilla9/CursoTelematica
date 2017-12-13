package co.appreactor.conocimentos.negocio.actividades;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import co.appreactor.conocimentos.R;
import co.appreactor.conocimentos.negocio.util.PreferenciasUtil;
import co.appreactor.conocimentos.persistencia.dto.TokenDto;
import co.appreactor.conocimentos.persistencia.servicio.AuthorizationService;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private TextInputLayout txtCorreo;
    private TextInputLayout txtContrasena;
    private Button btnRegistrar;
    private Button btnIniciarSesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // vincula el layout xml con la clase Activity (Controlador)
        setContentView(R.layout.activity_login);
        this.btnIniciarSesion = findViewById(R.id.btnIniciarSesion);
        this.btnRegistrar = findViewById(R.id.btnRegistrar);
        this.txtContrasena = findViewById(R.id.txtContrasena);
        this.txtCorreo = findViewById(R.id.txtCorreo);

        this.btnRegistrar.setOnClickListener(this);
        this.btnIniciarSesion.setOnClickListener(this);
    }

    /**
     * Metodo que captura el evento click de un widget (Componente visual)
     * @param view Representa al objeto que disparo el evento click
     */
    @Override
    public void onClick(View view) {
        // discriminar el evento click dependiendo de que boton fue el que disparo el evento
        switch (view.getId()){
            case R.id.btnRegistrar:
                irRegistrar();
                break;
            case R.id.btnIniciarSesion:
                iniciarSesion();
                break;
        }
    }

    private void irRegistrar() {
        // crear una navegacion entre actividades usando un objeto Intent
        startActivity(new Intent(LoginActivity.this,RegistrarActivity.class));
    }

    private void iniciarSesion() {
        String userName = txtCorreo.getEditText().getText().toString();
        String password = txtContrasena.getEditText().getText().toString();
        if (userName.isEmpty() || password.isEmpty()){
            Toast.makeText(this, "Debe digitar los campos correctamente", Toast.LENGTH_SHORT).show();
            return;
        }
        new AuthorizationService(LoginActivity.this).autenticarUsuario(userName.toLowerCase().trim(), password);
    }

    public void procesarAutenticacion(TokenDto token){
        String tokenfinal = token.getToken_type() + " " + token.getAccess_token();
        // asignacion del token en las preferencias de la aplicacion
        PreferenciasUtil.guardarPreferencia("token",tokenfinal,LoginActivity.this);
        startActivity(new Intent(LoginActivity.this,MainActivity.class));
    }
}
