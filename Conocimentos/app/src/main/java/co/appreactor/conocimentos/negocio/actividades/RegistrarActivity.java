package co.appreactor.conocimentos.negocio.actividades;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import java.io.IOException;

import co.appreactor.conocimentos.R;
import co.appreactor.conocimentos.negocio.util.AlertaUtil;
import co.appreactor.conocimentos.persistencia.entidades.Account;
import co.appreactor.conocimentos.persistencia.servicio.AccountService;
import okhttp3.ResponseBody;

public class RegistrarActivity extends AppCompatActivity implements View.OnClickListener, TextWatcher{

    private Toolbar toolbar;
    private FloatingActionButton fab;
    private TextInputLayout txtCorreo;
    private TextInputLayout txtContrasena;
    private TextInputLayout txtConfirmContrasena;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.content_registrar);
        setContentView(R.layout.activity_registrar);
        this.fab = findViewById(R.id.fab);
        this.toolbar = findViewById(R.id.toolbar);
        this.txtCorreo =  findViewById(R.id.txtCorreo);
        this.txtConfirmContrasena = findViewById(R.id.txtConfirmContrasena);
        this.txtContrasena = findViewById(R.id.txtContrasena);
        // asignar evento de cambio a los campos de texto
        txtContrasena.getEditText().addTextChangedListener(this);
        txtConfirmContrasena.getEditText().addTextChangedListener(this);
        // asignar evento click al boton de accion flotante
        fab.setOnClickListener(this);
        setSupportActionBar(toolbar);
    }

    @Override
    public void onClick(View v) {
        Account usuario = new Account();

        usuario.setEmail(txtCorreo.getEditText().getText().toString());
        usuario.setPassword(txtContrasena.getEditText().getText().toString());
        usuario.setConfirmPassword(txtConfirmContrasena.getEditText().getText().toString());

        // invocar el servicio para registar un usuario
        new AccountService(RegistrarActivity.this).registrarUsuario(usuario);
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!txtContrasena.getEditText().getText().toString().equals(txtConfirmContrasena.getEditText().getText().toString())){
            fab.setEnabled(false);
            fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.darker_gray)));
            txtContrasena.setError("Las contraseñas no coinciden");
            txtConfirmContrasena.setError("Las contraseñas no coinciden");
            return;
        }
        txtContrasena.setError("");
        txtConfirmContrasena.setError("");
        fab.setEnabled(true);
        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(android.R.color.holo_green_dark)));
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    public void procesarRespuestaRegistro(ResponseBody respuesta) throws IOException {
        String titulo = "Registro de usuario";
        String mensaje = "Usuario registrado correctamente";
        AlertaUtil.mostrarAlerta(
                titulo,
                mensaje,
                clickAceptar,
                null,
                RegistrarActivity.this
        );
    }

    /**
     * Representar el evento click de la ventana de dialogo (Alerta) para cuando el usuario pulsa
     * el boton aceptar
     */

    private DialogInterface.OnClickListener clickAceptar = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            startActivity(new Intent(RegistrarActivity.this, LoginActivity.class));
        }
    };
}
