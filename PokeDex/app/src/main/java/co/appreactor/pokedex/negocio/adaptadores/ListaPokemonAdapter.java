package co.appreactor.pokedex.negocio.adaptadores;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import co.appreactor.pokedex.R;
import co.appreactor.pokedex.modelo.entidades.Pokemon;

/**
 * Created by lord_nightmare on 19/10/17.
 */

public class ListaPokemonAdapter extends RecyclerView.Adapter<ListaPokemonAdapter.ViewHolder>{

    private ArrayList<Pokemon> dataSet;
    private Context contexto;

    public ListaPokemonAdapter(Context contexto) {
        this.dataSet = new ArrayList<>();
        this.contexto = contexto;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_pokemon,parent,false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Pokemon pokemon = dataSet.get(position);
        holder.txtNombrePokemon.setText(pokemon.getName());

        Glide.with(contexto)
                .load("http://pokeapi.co/media/sprites/pokemon/"+pokemon.getNumber()+".png")
                .into(holder.imgPokemon);
    }

    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void adicionarElementos(ArrayList<Pokemon> listaPokemon){
        this.dataSet.addAll(listaPokemon);
        // Notifica al adaptador que el volumen de datos cambio, y que debe
        // renderizar la nueva informacion
        notifyDataSetChanged();
    }

    // Clase que representa la vista del item_pokemon
    public class ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgPokemon;
        private TextView txtNombrePokemon;

        public ViewHolder(View itemView) {
            super(itemView);
            imgPokemon = itemView.findViewById(R.id.imgPokemon);
            txtNombrePokemon = itemView.findViewById(R.id.txtNombrePokemon);
        }
    }
}
