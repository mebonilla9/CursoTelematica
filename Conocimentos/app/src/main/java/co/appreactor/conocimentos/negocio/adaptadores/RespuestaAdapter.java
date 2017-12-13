package co.appreactor.conocimentos.negocio.adaptadores;

import android.content.Context;
import android.content.res.ColorStateList;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import co.appreactor.conocimentos.R;
import co.appreactor.conocimentos.persistencia.entidades.Respuesta;

/**
 * Created by lord_nightmare on 12/12/17.
 */

/**
 * Clase que representa el adaptador que mostrara de manera adecuada los
 * valores de las respuestas de una pregunta
 * utiliza un herencia de RecyclerView.Adapter para adquirir el comportamiento
 * de adaptador, recibira una coleccion de datos de la clase Respuesta
 * para asignar valor a los items que va a mostrar
 */
public class RespuestaAdapter extends RecyclerView.Adapter<RespuestaAdapter.ViewHolder> {

    private List<Respuesta> dataSet;
    private Context contexto;

    /**
     * Crea una nueva instancia de un adaptador para datos de la clase
     * Respuesta
     *
     * @param dataSet  Coleccion de datos que va a mostrar el adaptador
     *                 utilizando un template o plantilla disenado en xml
     * @param contexto Representa la actividad o fragmento activo para realizar
     *                 modificacion en la vista o consumo de caracteristicas
     *                 del sistema operativo (Toast, Preferences, Alerts)
     */
    public RespuestaAdapter(List<Respuesta> dataSet, Context contexto) {
        this.dataSet = dataSet;
        this.contexto = contexto;
    }

    /**
     * Se le indica al adaptador que xml de la carpeta layout de los recursos
     * debe utilizar para ejercer como plantilla y asignarlo a la clase interna
     * ViewHolder
     *
     * @param parent   Representa al layout que contiene el recyclerView que va a usar
     *                 este adaptador
     * @param viewType Tipo de vista que tiene el RecyclerView
     * @return Objeto de la clase interna ViewHolder o una instancia logica de la
     * plantilla
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_respuesta, parent, false);
        return new ViewHolder(itemView);
    }

    /**
     * Este metodo se dispara por cada uno de los objetos que contenga el dataset
     * (dataset.size() == 1000) -> onBindViewHolder * 1000
     * <p>
     * Este metodo se utiliza para asignar los valores al template por cada objeto
     * del dataset
     *
     * @param holder   Instancia del Template o de la plantilla que es un objeto
     *                 de la clase interna, se utiliza para asignar los valores
     *                 que cada posicion del dataset va a tener segun las reglas
     *                 del negocio
     * @param position Representa la posicion que vamos a renderizar del dataset
     *                 (0 -> n)
     *                 el valor maximo y minimo de este parametro lo determina el
     *                 metodo getItemCount de esta misma clase
     */
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // obtener el objeto del dataset que queremos renderizar
        Respuesta resp = dataSet.get(position);
        // asignar el texto de la respuesta
        holder.lblValorRespuesta.setText((position + 1) + ". " + resp.getRespuesta());
        // evaluar si la respuesta es correcta o no, para determinar el color de fondo
        // de la tarjeta que contiene el valor de la respuesta
        int color;
        if (resp.getCorrecta()) {
            color = contexto.getResources().getColor(
                    android.R.color.holo_green_light);
        } else {
            color = contexto.getResources().getColor(
                    android.R.color.holo_red_light);
        }
        cambiarFondoControl(holder.cvContenedorRespuesta,color);
    }

    /**
     * Permite la asignacion de la tinta de fondo de un control visual de android
     * se debe validar la version del sistema donde la aplicacion esta ejecutandose
     * se utiliza una forma para asignar dicho color en versions iguales o
     * superiores a Android Lollipop
     * @param contenedor
     * @param color
     */
    private void cambiarFondoControl(CardView contenedor, int color) {
        // determinar si la version en ejecucion es mayor o igual a android
        // lollipop 5.1 API 21
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            contenedor.setBackgroundTintList(
                    ColorStateList.valueOf(color)
            );
        } else {
            // modificar el color usando la clase ViewCompat que genera funciones de
            // compatibilidad con android 4.4 o inferiores.
            ViewCompat.setBackgroundTintList(contenedor, ColorStateList.valueOf(color));
        }
    }

    /**
     * Informar al RecyclerView la cantidad de items que debe visualizar en pantalla
     * <p>
     * (return 1) -> 1 item
     * (return 10) -> 10 item
     * (return n) -> n item
     * <p>
     * return tamaño del dataset
     *
     * @return cantidad de objetos que deben ser visualizados
     */
    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    /**
     * Clase Interna
     * <p>
     * esta clase ViewHolder representa a nivel logico el template
     * cuyo nombre es item_respuesta
     */
    public class ViewHolder extends RecyclerView.ViewHolder {

        private CardView cvContenedorRespuesta;
        private TextView lblValorRespuesta;

        /**
         * Metodo constructor del template que es invocado cuando el recyclerView
         * invoque el inflado del adaptador onCreateViewHolder
         *
         * @param itemView representa el item al que vamos a asignarle información
         */
        public ViewHolder(View itemView) {
            super(itemView);
            cvContenedorRespuesta = itemView.findViewById(R.id.cvContenedorRespuesta);
            lblValorRespuesta = itemView.findViewById(R.id.lblValorRespuesta);
        }
    }

}
