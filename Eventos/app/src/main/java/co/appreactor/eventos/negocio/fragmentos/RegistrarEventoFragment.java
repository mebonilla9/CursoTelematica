package co.appreactor.eventos.negocio.fragmentos;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import co.appreactor.eventos.R;
import co.appreactor.eventos.persistencia.dto.FechaDTO;
import co.appreactor.eventos.persistencia.dto.RespuestaDTO;
import co.appreactor.eventos.persistencia.entidades.Evento;
import co.appreactor.eventos.persistencia.entidades.Sala;
import co.appreactor.eventos.persistencia.entidades.TipoEvento;
import co.appreactor.eventos.persistencia.servicio.ServicioHttp;


public class RegistrarEventoFragment extends Fragment implements Handler.Callback {

    private List<TipoEvento> listaTipoEvento;
    private List<Sala> listaSalas;

    private TextInputLayout txtNombre;
    private TextInputLayout txtFecha;
    private TextInputLayout txtHoraInicio;
    private TextInputLayout txtHoraFin;
    private Spinner spTipoEvento;
    private Spinner spSala;
    private FloatingActionButton fab;

    private FechaDTO fechaDialogo;

    // Objeto que reciba la data del servidor
    private Handler puente;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_registrar_evento, container, false);
        this.spSala = (Spinner) vista.findViewById(R.id.spSala);
        this.spTipoEvento = (Spinner) vista.findViewById(R.id.spTipoEvento);
        this.txtHoraFin = (TextInputLayout) vista.findViewById(R.id.txtHoraFin);
        this.txtHoraInicio = (TextInputLayout) vista.findViewById(R.id.txtHoraInicio);
        this.txtFecha = (TextInputLayout) vista.findViewById(R.id.txtFecha);
        this.txtNombre = (TextInputLayout) vista.findViewById(R.id.txtNombre);
        this.puente = new Handler(this);
        this.fechaDialogo = new FechaDTO();
        llenarSpinners();
        return vista;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Invocar la instancia del boton flotante que reside en MainActivity
        fab = (FloatingActionButton) getActivity().findViewById(R.id.fab);
        // linea para ocultar el boton flotante
        // fab.setVisibility(View.GONE);
        // linea para mostrar el boton flotante
        // fab.setVisibility(View.VISIBLE);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (spTipoEvento.getSelectedItemPosition() < 1) {
                    Toast.makeText(getActivity(), "Debe seleccionar una Tipo de evento",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (spSala.getSelectedItemPosition() < 1) {
                    Toast.makeText(getActivity(), "Debe seleccionar una Sala",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                Evento gEvento = new Evento();
                gEvento.setNombre(txtNombre.getEditText().getText().toString());

                gEvento.setFecha(new Date(fechaDialogo.getFechaUnix()));

                gEvento.setHoraInicio(txtHoraInicio.getEditText().getText().toString());
                gEvento.setHoraFin(txtHoraFin.getEditText().getText().toString());

                // Obtener posicion de list segun indice de spinner seleccionado

                TipoEvento te = listaTipoEvento.get(spTipoEvento.getSelectedItemPosition() - 1);
                gEvento.setTipoEvento(te);

                Sala sa = listaSalas.get(spSala.getSelectedItemPosition() - 1);
                gEvento.setSala(sa);

                // Enviar Peticion al servidor
                Type tipoRespuesta = new TypeToken<RespuestaDTO>() {
                }.getType();
                ServicioHttp.invocar(gEvento, null, "POST", tipoRespuesta, puente, "/evento/insertar");

            }
        });

        // Listener que escucha los cambios de foco de un campo de texto
        View.OnFocusChangeListener eventoFoco = new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    // Creacion de un objeto del dialogo para seleccionar una fecha
                    DialogFragment dialogo = null;

                    if (v.equals(txtFecha.getEditText())) {
                        dialogo = new DialogoFecha((EditText) v, fechaDialogo);
                    } else {
                        dialogo = new DialogoHora((EditText) v);
                    }

                    // Obtener la transaccion del fragmentos usando childSupportFragmentManager
                    FragmentTransaction transaccion = getChildFragmentManager().beginTransaction();
                    dialogo.show(transaccion, "Seleccionar Fecha");
                }
            }
        };

        // Reutilizacion de lister de foco para tres campos de texto
        txtFecha.getEditText().setOnFocusChangeListener(eventoFoco);
        txtHoraInicio.getEditText().setOnFocusChangeListener(eventoFoco);
        txtHoraFin.getEditText().setOnFocusChangeListener(eventoFoco);
    }

    private void llenarSpinners() {
        // Obtener con gson el tipo de dato al cual se va a convertir la data serializada que viene
        // del servidor.
        Type tipoEvento = new TypeToken<RespuestaDTO<List<TipoEvento>>>() {
        }.getType();
        ServicioHttp.invocar("", null, "POST", tipoEvento, puente, "/tipoevento/consultar");

        Type tipoSala = new TypeToken<RespuestaDTO<List<Sala>>>() {
        }.getType();
        ServicioHttp.invocar("", null, "POST", tipoSala, puente, "/sala/consultar");
    }

    @Override
    public boolean handleMessage(Message msg) {
        RespuestaDTO respuesta = (RespuestaDTO) msg.obj;
        // siempre validar el codigo de la respuesta que sea 1 para poder procesar los datos
        // si el codigo es menor a 1 representa una falla en el servidor o la inexistencia de datos
        if (respuesta.getCodigo() <= 0) {
            Toast.makeText(getActivity(), respuesta.getMensaje(), Toast.LENGTH_LONG).show();
            return false;
        }
        // obtener la ruta del servicio que se solicito
        String ruta = respuesta.getRuta();

        if (ruta.equals("/tipoevento/consultar")) {
            cargarTipoEventos(respuesta);
        }
        if (ruta.equals("/sala/consultar")) {
            cargarSala(respuesta);
        }
        if (ruta.equals("/evento/insertar")) {
            resultadoInsertarEvento(respuesta);
        }
        return false;
    }

    private void resultadoInsertarEvento(RespuestaDTO respuesta) {
        if (respuesta.getCodigo() > 0) {
            txtNombre.getEditText().setText("");
            txtFecha.getEditText().setText("");
            txtHoraInicio.getEditText().setText("");
            txtHoraFin.getEditText().setText("");
            spTipoEvento.setSelection(0);
            spSala.setSelection(0);
        }
        Snackbar.make(fab, respuesta.getMensaje(), Snackbar.LENGTH_LONG)
                .setAction("Aceptar", null).show();
    }

    private void cargarSala(RespuestaDTO<List<Sala>> respuesta) {
        List<String> salas = new ArrayList<>();
        salas.add("Seleccione...");
        listaSalas = respuesta.getDatos();
        for (Sala sa : listaSalas) {
            salas.add(sa.getNombre());
        }
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, salas);
        spSala.setAdapter(adaptador);
    }

    private void cargarTipoEventos(RespuestaDTO<List<TipoEvento>> respuesta) {
        List<String> tiposEvento = new ArrayList<>();
        tiposEvento.add("Seleccione...");
        // asignar al atributo de clase la lista de datos que viene del servidor
        listaTipoEvento = respuesta.getDatos();
        // Cargar la lista de String para el spinner con los nombres de los datos del servidor
        for (TipoEvento te : listaTipoEvento) {
            tiposEvento.add(te.getNombre());
        }
        // Carga de la lista de strings al spinner
        ArrayAdapter<String> adaptador = new ArrayAdapter<>(getActivity(),
                android.R.layout.simple_list_item_1, tiposEvento);
        spTipoEvento.setAdapter(adaptador);
    }
}
