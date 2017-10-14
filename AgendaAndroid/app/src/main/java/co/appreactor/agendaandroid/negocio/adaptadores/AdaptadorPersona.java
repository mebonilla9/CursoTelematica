package co.appreactor.agendaandroid.negocio.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import co.appreactor.agendaandroid.R;
import co.appreactor.agendaandroid.modelo.entidades.Persona;

/**
 * Created by lord_nightmare on 22/09/17.
 */

public class AdaptadorPersona extends BaseAdapter {

    // set de datos para alimentar el ListView
    private List<Persona> dataSet;
    // Contexto de actividad que invoca al adaptador
    private Context contexto;

    public AdaptadorPersona(List<Persona> dataSet, Context contexto) {
        this.dataSet = dataSet;
        this.contexto = contexto;
    }

    @Override
    public int getCount() {
        return dataSet.size();
    }

    @Override
    public Object getItem(int position) {
        return dataSet.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null){
            convertView = LayoutInflater.from(contexto)
                    .inflate(R.layout.item_persona,null);
        }

        TextView lblNombrePersona = (TextView) convertView.findViewById(R.id.lblNombrePersona);
        TextView lblCorreoPersona = (TextView) convertView.findViewById(R.id.lblCorreoPersona);

        Persona miPersona = this.dataSet.get(position);

        lblNombrePersona.setText(miPersona.getNombre());
        lblCorreoPersona.setText(miPersona.getCorreo());


        return convertView;
    }
}
