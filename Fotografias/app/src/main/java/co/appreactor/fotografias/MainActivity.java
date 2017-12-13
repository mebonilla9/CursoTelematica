package co.appreactor.fotografias;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {

    private final static String CARPETA_APP = "Mis_Fotos_Sexys";
    private final static String CARPETA_FOTOS = CARPETA_APP + "/fotos";

    private final static int MIS_PERMISOS = 100;
    private final static int ACTIVIDAD_FOTO = 200;
    private final static int ACTIVIDAD_GALERIA = 300;

    private String[] permisos = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA};

    private Button btnOpciones;
    private ImageView imgPreview;
    private String rutaFoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.imgPreview = (ImageView) findViewById(R.id.imgPreview);
        this.btnOpciones = (Button) findViewById(R.id.btnOpciones);

        // Determinar si la aplicacion tiene permisos para tomar fotos o seleccionar de galeria
        // para habilitar el boton que dispara estas acciones
        if (permisosSolicitados()) {
            btnOpciones.setEnabled(true);
        } else {
            btnOpciones.setEnabled(false);
        }

        // asignacion de evento click para el boton de opciones
        btnOpciones.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mostrarOpciones();
            }
        });

        if (savedInstanceState != null){
            visualizarResultadoFoto();
        }


    }

    private void mostrarOpciones() {

        // crear las opciones de botones para la ventana de dialogo
        final CharSequence[] opciones = {"Tomar Fotografia", "Elegir de Galeria", "Cancelar"};

        // Crear una instancia de Alerta con las opciones creadas previamente
        final AlertDialog.Builder alerta = new AlertDialog.Builder(MainActivity.this);
        alerta.setTitle("Elegir una opción");
        alerta.setItems(opciones, clickAlerta);
        alerta.show();
    }

    private boolean permisosSolicitados() {
        // Validar si la version del sistema operativo en la cual la aplicacion se esta ejecutando
        // es superior a Marshmallow
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }

        // Operacion clasica de determinar si los permisos fueron otorgados anteriormente
        if ((checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
                && (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED)) {
            return true;
        }

        // Se realiza la solicitud de permisos
        requestPermissions(permisos, MIS_PERMISOS);
        return false;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // validar que el requestCode que recibimos sea igual al que enviamos
        if (requestCode != MIS_PERMISOS) {
            return;
        }

        if (grantResults.length == 2
                && grantResults[0] == PackageManager.PERMISSION_GRANTED
                && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(
                    MainActivity.this,
                    "Permisos de camara y almacenamiento concedidos",
                    Toast.LENGTH_LONG
            ).show();
            btnOpciones.setEnabled(true);
        }
    }

    private DialogInterface.OnClickListener clickAlerta = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            switch (which) {
                case 0:
                    abrirCamara();
                    break;
                case 1:
                    abrirGaleria();
                    break;
                default:
                    break;

            }
        }
    };

    private void abrirCamara() {
        File carpetaFotos = new File(Environment.getExternalStorageDirectory(), CARPETA_APP);

        if (!carpetaFotos.exists()) {
            carpetaFotos.mkdirs();
        }

        // Parte del nombre de la foto usando la hora del sistema en formato UNIX divido entre mil
        Long tiempoActual = System.currentTimeMillis() / 1000;

        String nombreFoto = tiempoActual.toString() + ".jpg";

        rutaFoto = Environment.getExternalStorageDirectory()
                + "/" + CARPETA_APP
                + "/" + nombreFoto;

        // Archivo final para el uso del almacenamiento de la foto en el dispositivo
        // utilizando un intent
        File archivoFinal = new File(rutaFoto);

        // crear intent para apertura de camara
        Intent intencionCamara = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        // parametro adicional para notificarle a la aplicacion de la camara la ruta y el nombre
        // del archivo que va a guardar
        intencionCamara.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(archivoFinal));
        startActivityForResult(intencionCamara, ACTIVIDAD_FOTO);
    }

    private void abrirGaleria() {
        Intent intencionGaleria = new Intent(
                Intent.ACTION_PICK,
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Asignar el mimeType que puede abrir la galeria
        intencionGaleria.setType("image/*");

        // El create chooser solo esta disponible para el api 22 de android
        // en adelante, se requiere validacion del sdk del dispositivio
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1) {
            startActivityForResult(
                    intencionGaleria.createChooser(
                            intencionGaleria,
                            "Seleccione una imagen"
                    )
                    , ACTIVIDAD_GALERIA);
        } else {
            startActivityForResult(intencionGaleria, ACTIVIDAD_GALERIA);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Validar codigo de resultado de la actividad
        if (resultCode != RESULT_OK){
            return;
        }

        // Validar que el codigo request sea nuestro
        switch (requestCode){
            case ACTIVIDAD_FOTO:
                visualizarResultadoFoto();
                break;
            case ACTIVIDAD_GALERIA:
                // Obtener la Uri de los datos del intent
                Uri ruta = data.getData();
                // asignar al ImageView la imagen con base al objeto ruta
                imgPreview.setImageURI(ruta);
                break;
        }

    }

    private void visualizarResultadoFoto() {
        // obtener los datos de la camara y convertirlos a un mapa de bits
        Bitmap bmap = BitmapFactory.decodeFile(rutaFoto);
        imgPreview.setImageBitmap(bmap);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("ruta_foto",rutaFoto);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        rutaFoto = savedInstanceState.getString("ruta_foto");
    }
}
