package co.appreactor.conocimentos.negocio.actividades;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import co.appreactor.conocimentos.R;
import co.appreactor.conocimentos.persistencia.dto.arcgis.request.Data;
import co.appreactor.conocimentos.persistencia.dto.arcgis.request.RequestArcGisDto;
import co.appreactor.conocimentos.persistencia.dto.arcgis.response.Attributes;
import co.appreactor.conocimentos.persistencia.dto.arcgis.response.ResponseArcGisDto;
import co.appreactor.conocimentos.persistencia.servicio.ArcGisGeoService;

public class MapsActivity extends AppCompatActivity implements OnMapReadyCallback, LocationListener {

    private GoogleMap mMap;
    private Toolbar toolbar;
    private FloatingActionButton fab;

    // constante que represente el codigo con el que solicitamos los permisos en tiempo de ejecucion
    // el valor del codigo de permiso es subjetivo
    private final int codigoPermiso = 987;

    // Arreglo que contenga la cantidad de permisos que vamos a solicitar al usuario
    private final String[] permisos = new String[]{
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private SupportMapFragment mapFragment;

    private LocationManager manejador;
    private boolean aptoParaCargar;
    private android.widget.TextView lblNombreCuadrante;
    private android.widget.TextView lblDireccionCuadrante;
    private android.widget.TextView lblComplementoCuadrante;
    private android.widget.TextView lblEstacionCuadrante;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);

        this.lblEstacionCuadrante = findViewById(R.id.lblEstacionCuadrante);
        this.lblComplementoCuadrante = findViewById(R.id.lblComplementoCuadrante);
        this.lblDireccionCuadrante = findViewById(R.id.lblDireccionCuadrante);
        this.lblNombreCuadrante = findViewById(R.id.lblNombreCuadrante);

        this.fab = findViewById(R.id.fab);
        this.toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // invocar al sistema de localizacion del dispositivo
        manejador = (LocationManager) getSystemService(LOCATION_SERVICE);

        // asignar evento click al boton de accion flotante
        this.fab.setOnClickListener(clickFlotante);
        aptoParaCargar = false;
        solicitarLocalizacion();
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in Sydney and move the camera
        LatLng sydney = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }


    /**
     * Metodo que solicita de manera inicial la localizacion del usuario en el mapa,
     * tambien es quien realiza la solicitud de los permisos en tiempo de ejecucion cuando aun
     * no han sido solicitados
     */
    private void solicitarLocalizacion() {
        // validar permisos en tiempo de ejecucion
        if (
                // validar que la aplicacion tenga permisos concedidos para el ACCESS_FINE_LOCATION
                (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED)

                ||

                // validar que la aplicacion tenga permisos concedidos para el ACCESS_COARSE_LOCATION
                (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED)) {

            // si estas condiciones no se cumple se realiza la solicitud de los permisos en tiempo de ejecucion
            ActivityCompat.requestPermissions(
                    MapsActivity.this,
                    permisos,
                    codigoPermiso
            );
            return;
        }

        // ejecutar la solicitud de localizacion pues los permisos ya han sido concedidos
        manejador.requestLocationUpdates(
                LocationManager.NETWORK_PROVIDER,
                4000,
                15,
                this
        );
    }

    /**
     * Representa el evento click del boton de accion flotante,
     * se usara para solicitar la actualizacion de la localizacion del usuario
     * y la posterior invocacion del web service de ArcGis para obtener el cuadrante
     */
    private View.OnClickListener clickFlotante = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            aptoParaCargar = true;
            solicitarLocalizacion();
        }
    };

    /**
     * metodo de tipo callback que se dispara en el momento que la latitud
     * o longitud geografica del proveedor de localizacion cambie
     * @param location representa el objeto que contiene las nuevas coodernadas
     *                 de localizacion del dispositivo
     */
    @Override
    public void onLocationChanged(Location location) {
        if  (!aptoParaCargar){
            return;
        }
        // realizar las tareas cuando la posicion geografica del usuario o del
        // dispositivo cambien
        if (location == null){
            Toast.makeText(
                    this,
                    "No te encuentro, donde estas",
                    Toast.LENGTH_SHORT
            ).show();
            return;
        }
        // obtener las coordenadas en un objeto que el mapa pueda entender
        LatLng miUbicacion = new LatLng(location.getLatitude(),location.getLongitude());
        // asegurarnos de limpiar las marcas anteriores que se generaron en el mapa
        mMap.clear();

        mMap.addMarker(
                new MarkerOptions()
                        .title("Me encuentro aqui")
                .position(miUbicacion));
        // realiza una animacion para el movimiento de la camara para que la transicion
        // no se vea de una manera brusquita.
        mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(miUbicacion,20));

        RequestArcGisDto peticion = new RequestArcGisDto();
        peticion.setClient("Usuario");
        // objeto de data para asignar latitud y longitud
        Data localizacion = new Data();
        localizacion.setLat(location.getLatitude());
        localizacion.setLon(location.getLongitude());

        peticion.setData(localizacion);

        // Invocar al webservices de ArcGis
        new ArcGisGeoService(MapsActivity.this).consultarCuadrante(peticion);

        aptoParaCargar = false;
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        // no lo vamos a usar
    }

    @Override
    public void onProviderEnabled(String provider) {
        // no lo vamos a usar
    }

    @Override
    public void onProviderDisabled(String provider) {
        // no lo vamos a usar
    }

    /**
     * Se encarga de genera una accion en la aplicacion cuando llegue el resultado de una
     * solicitud de permisos en tiempo de ejecucion
     *
     * @param requestCode codigo de la peticion con la que realizaron los permisos
     *                    en tiempo de ejecucion, compararlo con la constante que
     *                    creamos para generar el request code a la hora de solicitar
     *                    los permisos
     * @param permissions representa los permisos en forma de arreglo de String que fueron
     *                    solicitados
     * @param grantResults representa en un arreglo de numeros enteros los permisos que fueron
     *                     aprobados en la solicitud
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode != codigoPermiso){
            return;
        }

        // validar que permisos fueron aprobados
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                grantResults[1] == PackageManager.PERMISSION_GRANTED){
            Toast.makeText(
                    MapsActivity.this,
                    "Permisos de localización fueron concedidos",
                    Toast.LENGTH_LONG
            ).show();
        }
    }

    public void procesarCuadrante(ResponseArcGisDto cuadrante){
        Attributes atributos = cuadrante.getData().getAttributes();
        lblNombreCuadrante.setText(atributos.getNombrecuadrante());
        lblDireccionCuadrante.setText(atributos.getDireccion());
        lblComplementoCuadrante.setText(atributos.getDireccioncomplementa());
        lblEstacionCuadrante.setText(atributos.getEstacion());
    }
}
