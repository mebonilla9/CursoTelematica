package co.appreactor.pokedex.negocio.actividades;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;

import co.appreactor.pokedex.R;
import co.appreactor.pokedex.modelo.entidades.Pokemon;
import co.appreactor.pokedex.modelo.entidades.PokemonRespuesta;
import co.appreactor.pokedex.modelo.servicio.call.PokemonCall;
import co.appreactor.pokedex.negocio.adaptadores.ListaPokemonAdapter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private Retrofit servicio;

    private final int limit = 10;
    private int offset;
    private boolean aptoParaCargar;

    private RecyclerView rvListaPokemon;

    private ListaPokemonAdapter adaptador;
    private GridLayoutManager layoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.rvListaPokemon = findViewById(R.id.rvListaPokemon);
        configurarServicio();
        asignarEventos();
    }

    private void asignarEventos() {
        this.rvListaPokemon.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                if (dy > 0) {
                    int itemsVisibles = layoutManager.getChildCount();
                    int totalItems = layoutManager.getItemCount();
                    int itemsDesplazados = layoutManager.findFirstVisibleItemPosition();
                    if (aptoParaCargar) {
                        if ((itemsVisibles + itemsDesplazados) >= totalItems) {
                            aptoParaCargar = false;
                            offset += limit;
                            llenarPokedex(offset);
                        }
                    }
                }
            }
        });

    }

    private void configurarServicio() {
        this.servicio = new Retrofit.Builder()
                .baseUrl("https://pokeapi.co/api/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        this.aptoParaCargar = true;
        this.rvListaPokemon.setHasFixedSize(true);
        this.layoutManager = new GridLayoutManager(MainActivity.this, 3);
        this.rvListaPokemon.setLayoutManager(layoutManager);
        this.adaptador = new ListaPokemonAdapter(MainActivity.this);
        this.rvListaPokemon.setAdapter(this.adaptador);
        llenarPokedex(this.offset);
    }

    private void llenarPokedex(int offset) {
        // Preperar el objeto que transmitira la peticion al webservices
        PokemonCall peticion = this.servicio.create(PokemonCall.class);
        // Generar el objeto que va a lanzar la peticion al servidor
        Call<PokemonRespuesta> respuesta = peticion.obtenerListaPokemon(limit, offset);

        // generar el callback para procesar los datos de la respuesta
        respuesta.enqueue(new Callback<PokemonRespuesta>() {
            @Override
            public void onResponse(Call<PokemonRespuesta> call, Response<PokemonRespuesta> response) {
                if (!response.isSuccessful()) {
                    return;
                }

                ArrayList<Pokemon> listaPokemon = response.body().getResults();
                aptoParaCargar = true;
                adaptador.adicionarElementos(listaPokemon);
            }

            @Override
            public void onFailure(Call<PokemonRespuesta> call, Throwable t) {
                aptoParaCargar = true;
            }
        });

    }
}
