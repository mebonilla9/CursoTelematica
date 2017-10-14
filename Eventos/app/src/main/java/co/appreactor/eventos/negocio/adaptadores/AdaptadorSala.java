package co.appreactor.eventos.negocio.adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

import co.appreactor.eventos.R;
import co.appreactor.eventos.persistencia.entidades.Sala;

/**
 * Created by lord_nightmare on 11/10/17.
 */

public class AdaptadorSala extends RecyclerView.Adapter<AdaptadorSala.ViewHolder>{

    private List<Sala> dataSet;
    private Context contexto;

    public AdaptadorSala(List<Sala> dataSet, Context contexto) {
        this.dataSet = dataSet;
        this.contexto = contexto;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // Inflar un objeto de vista con la referencia de layout escrito en XML
        View vista = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_sala,parent,false);
        // Asignar vista a la clase interna ViewHolder
        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Sala sala = dataSet.get(position);
        holder.txtNombreSala.setText(sala.getNombre());
        holder.txtDireccionSala.setText(sala.getDireccion());
        holder.txtTelefonoSala.setText(sala.getTelefono());

        Glide.with(contexto)
                .load("https://iceland-photo-tours.com/wp-content/uploads/2017/03/Iceland-Photo-Workshop09.jpg")
                .into(holder.imgSala);
    }

    @Override
    public int getItemCount() {
        return this.dataSet.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgSala;
        private TextView txtNombreSala;
        private TextView txtDireccionSala;
        private TextView txtTelefonoSala;

        public ViewHolder(View itemView) {
            super(itemView);
            imgSala = (ImageView) itemView.findViewById(R.id.imgSala);
            txtNombreSala = (TextView) itemView.findViewById(R.id.txtNombreSala);
            txtDireccionSala = (TextView) itemView.findViewById(R.id.txtDireccionSala);
            txtTelefonoSala = (TextView) itemView.findViewById(R.id.txtTelefonoSala);
        }
    }
}
