package co.appreactor.conocimentos.negocio.adaptadores;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import co.appreactor.conocimentos.R;
import co.appreactor.conocimentos.negocio.util.AlertaUtil;
import co.appreactor.conocimentos.negocio.util.FragmentoUtil;
import co.appreactor.conocimentos.persistencia.dto.EliminarPreguntaDto;
import co.appreactor.conocimentos.persistencia.entidades.Pregunta;
import co.appreactor.conocimentos.persistencia.servicio.PreguntaTestService;

/**
 * Created by lord_nightmare on 6/12/17.
 */

public class PreguntaAdapter extends RecyclerView.Adapter<PreguntaAdapter.ViewHolder> {

    private Context contexto;
    private List<Pregunta> dataSet;

    private EliminarPreguntaDto preguntaEliminar;


    public PreguntaAdapter(Context contexto) {
        this.contexto = contexto;
        dataSet = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pregunta, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Pregunta pregunta = dataSet.get(position);
        holder.lblNumeroPregunta.setText("Pregunta " + pregunta.getPreguntaTestId());
        holder.lblPregunta.setText(pregunta.getPregunta());

        // asignacion dinamica del evento click para todos los items del RecyclerView
        holder.cvPregunta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // asignacion de los argumentos que se van a enviar al siguiente fragmento
                Bundle argumentos = new Bundle();
                argumentos.putSerializable("pregunta", pregunta);

                // navegacion al fragmento que muestra las respuestas de la pregunta
                FragmentoUtil.obtenerFragmento(
                        v.getId(),
                        argumentos,
                        ((AppCompatActivity) contexto).getSupportFragmentManager().beginTransaction(),
                        R.id.flContenedor
                );
            }
        });

        /**
         * El evento long click permitira eliminar una pregunta de la base de datos y refrescar la lista
         */
        holder.cvPregunta.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                // convertir a atributo de clase la pregunta que se va a a eliminar

                preguntaEliminar = new EliminarPreguntaDto();
                preguntaEliminar.setId(pregunta.getPreguntaTestId());

                AlertaUtil.mostrarAlerta("Eliminar pregunta",
                        "Esta seguro de eliminar la pregunta?",
                        clickEliminar,
                        null,
                        contexto
                );
                return true;
            }
        });

    }


    private DialogInterface.OnClickListener clickEliminar = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            new PreguntaTestService(contexto).eliminarPregunta(preguntaEliminar);
        }
    };

    public void asignarPreguntas(List<Pregunta> listaPreguntas) {
        // borrar todos los valores del dataset
        dataSet.clear();
        dataSet.addAll(listaPreguntas);
        // lanzar la notificacion de cambio de datos al adapter
        notifyDataSetChanged();
    }

    /**
     * Le indica al adaptador cuantos items debe generar a nivel visual segun el numero
     * que este metodo retorne
     *
     * @return
     */
    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    /**
     * Representa a nivel logico el template que va a usar el adapter para que cada dato que convierta
     * se trasnforme en un item visual basado en el layout (res/layout/item_pregunta.xml)
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        // atributos de clase que representan los controles del layout (res/layout/item_pregunta.xml)
        private TextView lblNumeroPregunta;
        private TextView lblPregunta;
        private CardView cvPregunta;

        public ViewHolder(View itemView) {
            super(itemView);
            // asignacion de los valores a los controles del item_pregunta
            lblNumeroPregunta = itemView.findViewById(R.id.lblNumeroPregunta);
            lblPregunta = itemView.findViewById(R.id.lblPregunta);
            cvPregunta = itemView.findViewById(R.id.cvPregunta);
        }
    }
}
