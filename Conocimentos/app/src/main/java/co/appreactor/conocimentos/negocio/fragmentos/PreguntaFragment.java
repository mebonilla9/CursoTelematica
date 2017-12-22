package co.appreactor.conocimentos.negocio.fragmentos;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import co.appreactor.conocimentos.R;
import co.appreactor.conocimentos.negocio.actividades.MainActivity;
import co.appreactor.conocimentos.negocio.adaptadores.RespuestaAdapter;
import co.appreactor.conocimentos.persistencia.entidades.Pregunta;
import co.appreactor.conocimentos.persistencia.entidades.Respuesta;
import co.appreactor.conocimentos.persistencia.servicio.RespuestaTestService;


/**
 * A simple {@link Fragment} subclass.
 */
public class PreguntaFragment extends Fragment {

    private Pregunta preguntaActual;
    private TextView lblValorPregunta;
    private RecyclerView rvRespuestas;

    private RespuestaAdapter adaptador;

    private FloatingActionButton fab;

    // Cada vez que se utiliza el recycler view se debe crear un adaptador para la visualizacion
    // de los datos que ese recycler view debe mostrar

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View vista = inflater.inflate(R.layout.fragment_pregunta, container, false);

        // obtener los argumentos enviados a este fragmento
        // el valor del argumento se debe asignar aun atributo de clase
        preguntaActual = (Pregunta) getArguments().getSerializable("pregunta");

        this.rvRespuestas = vista.findViewById(R.id.rvRespuestas);
        this.lblValorPregunta = vista.findViewById(R.id.lblValorPregunta);
        // Configurar el RecyclerView antes de asignarle el adaptador
        rvRespuestas.setHasFixedSize(true);
        rvRespuestas.setLayoutManager(new LinearLayoutManager(
                getActivity(),
                LinearLayoutManager.VERTICAL,
                false));

        // asignar al label del valor de la pregunta el texto del objeto recibido como argumento
        lblValorPregunta.setText(preguntaActual.getPregunta());

        // obtener la instancia del boton de accion flotante de la MainActivity
        this.fab = ((MainActivity) getActivity()).getFab();

        // metodo que invoca al servicio para consultar las respuestas de una pregunta
        consultarRespuestas();
        return vista;
    }

    /**
     * Metodo del ciclo de vida del fragmento para restaurar valores al regresar a este fragmento
     * pro navegacion con el boton de atras del dispositivo
     */
    @Override
    public void onResume() {
        super.onResume();
        // obtener la instancia especifica de la actividad que contiene a los fragmentos

        ((MainActivity) getActivity()).activarBotonFlotante(true);

        ((MainActivity) getActivity()).modificarTituloSubtitulo("Pregunta " + preguntaActual.getPreguntaTestId(), "Respuestas de la pregunta");


        // metodo para la asignacion de eventos
        asignarEventos();
    }

    private void asignarEventos() {
        this.fab.setOnClickListener(new View.OnClickListener() {

            /**
             * instancia implementada del metodo abstracto onclick de la interface OnClickListener
             * utilizada para canalizar o escuchar el evento click de una vista de android
             *
             * @param v representa al objeto que disparo el evento click
             */
            @Override
            public void onClick(View v) {
                // ventana de dialogo del fragmento para editar
                // cuando queremos llamar a un fragmento dentro de otro,

                // getSupportFragmentManager -> Fragmento / Actividad
                // getChildFragmentManager -> Fragmento / Fragmento

                // invocar al componente de transacciones entre fragmentos
                FragmentTransaction transaccion = getChildFragmentManager().beginTransaction();
                EditarPreguntaFragment dialogo = new EditarPreguntaFragment(preguntaActual,lblValorPregunta);
                dialogo.show(transaccion, "editar_pregunta");
            }
        });
    }

    /**
     * llama una nueva instancia del RespuestaTestService para lanzar la peticion al servidor
     */
    private void consultarRespuestas() {
        // invocacion del web services
        new RespuestaTestService(getActivity()).consultarRespuestasPregunta(preguntaActual.getPreguntaTestId().toString());
    }

    /**
     * Metodo que va a recibir y a manipular los datos del web services, para renderizarlos en la
     * interfaz grafica de usuario utilizando un adaptador y un RecyclerView
     * <p>
     * invocado en RespuestaTestService
     *
     * @param listaRespuestas
     */
    public void procesarRespuestas(List<Respuesta> listaRespuestas) {
        // guardar la lista de respuestas en el atributo de clase que representa a la pregunta actual
        preguntaActual.setRespuestaTest(listaRespuestas);

        // construir el adaptador asignando la lista de respuestas como dataset y el contexto del fragmento
        adaptador = new RespuestaAdapter(preguntaActual.getRespuestaTest(), getActivity());

        // asignarmos al recyclerview el adaptador
        rvRespuestas.setAdapter(adaptador);
    }

    /**
     * Metodo que recibe la respuesta de la actualizacion de una pregunta en el servidor
     */
    public void procesarActualizacionPregunta() {
        Toast.makeText(getActivity(),"La pregunta ha sido Actualizada",Toast.LENGTH_LONG).show();
    }
}
