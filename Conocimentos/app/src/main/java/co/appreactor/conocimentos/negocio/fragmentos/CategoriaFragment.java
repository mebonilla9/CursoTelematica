package co.appreactor.conocimentos.negocio.fragmentos;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import co.appreactor.conocimentos.R;
import co.appreactor.conocimentos.negocio.actividades.MainActivity;
import co.appreactor.conocimentos.negocio.adaptadores.PreguntaAdapter;
import co.appreactor.conocimentos.negocio.util.AlertaUtil;
import co.appreactor.conocimentos.persistencia.entidades.Categoria;
import co.appreactor.conocimentos.persistencia.servicio.CategoriaTestService;


/**
 * A simple {@link Fragment} subclass.
 */
public class CategoriaFragment extends Fragment {

    /**
     * Representar a las categorias obtenidas del web service
     */
    private List<Categoria> categorias;

    /**
     * Representar a la categoria seleccionada en el spinner
     */
    private Categoria categoriaSeleccionada;

    private Spinner spCategorias;
    private RecyclerView rvPreguntas;

    private PreguntaAdapter adaptador;

    private boolean eliminar;
    private int indiceSeleccionado;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // crear la vista que va a renderizar el fragmento usando el LayoutInflater
        View vista = inflater.inflate(R.layout.fragment_categoria, container, false);
        this.rvPreguntas = vista.findViewById(R.id.rvPreguntas);
        this.spCategorias = vista.findViewById(R.id.spCategorias);
        // configurar el RecyclerView y el adaptador
        rvPreguntas.setHasFixedSize(true);
        // Asignacion de administrador de layout (Define distribucion y orientacion de los items internos)
        rvPreguntas.setLayoutManager(
                new LinearLayoutManager(
                        getActivity(),
                        LinearLayoutManager.VERTICAL,
                        false
                )
        );
        adaptador = new PreguntaAdapter(getActivity());
        rvPreguntas.setAdapter(adaptador);
        // metodo que almacene la asignacion de los eventos de este fragmento
        asignarEventos();
        consultarCategorias();
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

        ((MainActivity) getActivity()).activarBotonFlotante(false);

        ((MainActivity) getActivity()).modificarTituloSubtitulo("Preguntas", "Preguntas por categoria");
    }

    private void asignarEventos() {
        // manejar el evento change (Change Selection) del Spinner de las categorias
        spCategorias.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            /**
             * Metodo que captura el evento de seleccion de un spinner
             * @param parent Objeto padre donde se encuentra el item que seleccionamos
             * @param view el spinner o lista donde se almacena la coleccion de datos
             * @param position Posicion virtual a modo de array del item que seleccionamos
             * @param id en caso de que el adaptador maneje un id, retornaria ese dato del adaptador
             */
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0) {

                    // Cargar el objeto de la categoria seleccionada en el atributo de clase
                    categoriaSeleccionada = categorias.get(position - 1);

                    // asignar al adaptador, las preguntas que se encuentran dentro del objeto de
                    // categoria
                    adaptador.asignarPreguntas(categoriaSeleccionada.getPreguntaTest());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void consultarCategorias() {
        new CategoriaTestService(getActivity()).consultarCategorias();
    }

    public void procesarCategorias(List<Categoria> listaCategorias) {

        // Cargar atributo de clase con los datos de la respuesta del web service
        this.categorias = listaCategorias;

        // carga con adaptador
        List<String> categorias = new ArrayList<>();
        categorias.add("Seleccione una Categoría...");
        for (Categoria cat : this.categorias) {
            categorias.add(cat.getNombre());
        }
        spCategorias.setAdapter(
                new ArrayAdapter<>(
                        getActivity(),
                        android.R.layout.simple_dropdown_item_1line,
                        categorias
                )
        );

        if (eliminar) {
            categoriaSeleccionada = this.categorias.get(indiceSeleccionado);
            adaptador.asignarPreguntas(categoriaSeleccionada.getPreguntaTest());
            eliminar = false;
        }
    }

    /**
     * Permite recibir los datos de respuesta del web service para decirle al fragmento que hacer
     * despues de la eliminacion de una pregunta
     *
     * @param mensaje mensaje de respuesta del servidor
     */
    public void procesarEliminacionPregunta(String mensaje) {
        AlertaUtil.mostrarAlerta("Eliminación de pregunta",
                "El registro ha sido eliminado",
                restaurarEliminacion,
                null,
                getActivity()
        );
    }

    private DialogInterface.OnClickListener restaurarEliminacion = new DialogInterface.OnClickListener() {

        @Override
        public void onClick(DialogInterface dialog, int which) {
            eliminar = true;
            // guardar el indice selecciondo del spinner
            indiceSeleccionado = spCategorias.getSelectedItemPosition() - 1;
            // recargar la lista de las categorias
            consultarCategorias();
        }
    };

}
