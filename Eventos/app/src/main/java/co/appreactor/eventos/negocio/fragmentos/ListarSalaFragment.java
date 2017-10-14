package co.appreactor.eventos.negocio.fragmentos;


import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import co.appreactor.eventos.R;
import co.appreactor.eventos.negocio.adaptadores.AdaptadorSala;
import co.appreactor.eventos.persistencia.dto.RespuestaDTO;
import co.appreactor.eventos.persistencia.entidades.Sala;
import co.appreactor.eventos.persistencia.servicio.ServicioHttp;


public class ListarSalaFragment extends Fragment implements Handler.Callback {

    private RecyclerView rvSala;
    private List<Sala> listaSalas;
    private AdaptadorSala adaptador;

    private Handler puente;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View vista = inflater.inflate(R.layout.fragment_listar_sala, container, false);
        this.rvSala = (RecyclerView) vista.findViewById(R.id.rvSala);
        this.puente = new Handler(this);
        consultarSalas();
        return vista;
    }

    private void consultarSalas() {
        Type tipoListaSalas = new TypeToken<RespuestaDTO<List<Sala>>>() {
        }.getType();
        ServicioHttp.invocar("", null, "POST", tipoListaSalas, puente, "/sala/consultar");

    }

    @Override
    public boolean handleMessage(Message msg) {
        RespuestaDTO<List<Sala>> respuesta = (RespuestaDTO<List<Sala>>) msg.obj;
        if (respuesta.getCodigo() < 1){
            Toast.makeText(getActivity(), respuesta.getMensaje(),Toast.LENGTH_LONG).show();
            return false;
        }
        // Obtener en la lista de la clase el resultado de los datos del servidor
        listaSalas = respuesta.getDatos();

        // Construir el adaptador para el recycler view con base a la lista de la
        // clase
        adaptador = new AdaptadorSala(listaSalas, getActivity());
        // Asignar la forma en que los cardview se van a visualizar
        rvSala.setLayoutManager(new LinearLayoutManager(getActivity()));
        // Asignar el adaptador al recycler view
        rvSala.setAdapter(adaptador);
        return false;
    }
}
